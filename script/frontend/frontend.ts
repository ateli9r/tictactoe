import * as fs from 'fs';
import * as path from 'path';

class FrontendBuilder {
  private frontendRoot: string;
  private buildPath: string = '';
  private fileExtension: string;
  private distAssetsPath: string = ''; // Initialize assetsPath with an empty string
  private distPath: string = '';
  private watchPath: string = '';

  constructor() {
    this.frontendRoot = '.';
    this.fileExtension = 'html'; // Default file extension
    this.loadConfig(); // Load configuration including assetsPath
  }

  private loadConfig(): void {
    const configPath = path.join(__dirname, 'config.json');
    try {
      const configData = fs.readFileSync(configPath, 'utf8');
      const config = JSON.parse(configData);
      this.distAssetsPath = config.distAssetsPath || ''; // Update assetsPath based on config
      this.distPath = config.distPath || './dist';
      this.buildPath = config.buildPath || './output';
      this.watchPath = config.watchPath || './output/build'
    } catch (error: any) {
      console.error(`Error loading config file at ${configPath}: ${error.message}`);
    }
  }

  private copyDirectory(src: string, dest: string): void {
    if (!fs.existsSync(src)) {
      console.error(`Source directory "${src}" does not exist.`);
      return;
    }

    if (!fs.existsSync(dest)) {
      fs.mkdirSync(dest, { recursive: true });
    }
    const entries = fs.readdirSync(src, { withFileTypes: true });

    for (let entry of entries) {
      const srcPath = path.join(src, entry.name);
      const destPath = path.join(dest, entry.name);

      if (entry.isDirectory()) {
        this.copyDirectory(srcPath, destPath);
      } else {
        fs.copyFileSync(srcPath, destPath);
      }
    }
  }

  private processIncludes(content: string, isDist: boolean = false): string {
    const includePattern = /<!--\s*@?\s*(#extension\s+".*?"|#include\s+"([^"]+)")\s*-->/g;
    let match;
    while ((match = includePattern.exec(content)) !== null) {
      const command = match[0].trim().replace('<!-- ', '').replace(' -->', '');

      if (command.startsWith('@')) {
        // Ignore this command, remove from content
        content = content.replace(match[0], '');
      }

      if (command.startsWith('#extension')) {
        if (isDist && command.indexOf('"') > -1) {
          let fileExtension = command;
          fileExtension = fileExtension.substring(fileExtension.indexOf('"') + 1);
          fileExtension = fileExtension.substring(0, fileExtension.indexOf('"'));
          this.fileExtension = fileExtension;
        }
        content = content.replace(match[0], '');
      } else {
          const includePath = path.join(this.frontendRoot, 'template', match[2] + '.html');

          if (!isDist && match[2].indexOf('prefix') > -1) {
            content = content.replace(match[0], '');
          } else {
            if (fs.existsSync(includePath)) {
              const includeContent = fs.readFileSync(includePath, 'utf8');
              content = content.replace(match[0], includeContent);
  
              // Recursively process other includes
              content = this.processIncludes(content, isDist);
            } else {
              console.error(`Include file "${includePath}" does not exist.`);
            }
          }
      }

    }
    return content.trim(); // Trim leading and trailing whitespace
  }

  private processDirectory(dirPath: string, isDist: boolean): void {
    if (!fs.existsSync(dirPath)) {
      console.error(`Directory "${dirPath}" does not exist.`);
      return;
    }
    const entries = fs.readdirSync(dirPath, { withFileTypes: true });

    for (let entry of entries) {
      const fullPath = path.join(dirPath, entry.name);

      if (entry.isDirectory()) {
        this.processDirectory(fullPath, isDist); // Recursive call for subdirectories
      } else if (entry.isFile() && entry.name.endsWith('.html')) {
        // Process HTML files
        const pageContent = fs.readFileSync(fullPath, 'utf8');
        const processedContent = this.processIncludes(pageContent, isDist);

        const buildPath = `${this.buildPath}/${isDist ? 'dist' : 'build'}`
        const relativePath = path.relative(path.join(this.frontendRoot, 'template', 'page'), fullPath);
        const outputFilePath = path.join(buildPath, relativePath.replace(/\.html$/, `.${this.fileExtension}`));

        // Ensure the output directory exists
        const outputDir = path.dirname(outputFilePath);
        if (!fs.existsSync(outputDir)) {
          fs.mkdirSync(outputDir, { recursive: true });
        }

        fs.writeFileSync(outputFilePath, processedContent, 'utf8');
        console.log(`Processed ${relativePath} to ${outputFilePath}`);
      }
    }
  }

  public build(page: string = '', isDist: boolean): void {
    if (page.length > 0) {
      const pageFilePath = path.join(this.frontendRoot, 'template', 'page', page);
      if (!fs.existsSync(pageFilePath)) {
        console.error(`Page file "${pageFilePath}" does not exist.`);
        return;
      }

      const buildPath = `${this.buildPath}/${isDist ? 'dist' : 'build'}`
      const pageContent = fs.readFileSync(pageFilePath, 'utf8');
      const processedContent = this.processIncludes(pageContent, isDist);
      const outputFilePath = path.join(buildPath, page.replace(/\.html$/, `.${this.fileExtension}`));
      fs.writeFileSync(outputFilePath, processedContent, 'utf8');
      console.log(`Processed ${page} to ${outputFilePath}`);
    } else {
      const pagePath = path.join(this.frontendRoot, 'template', 'page');
      this.processDirectory(pagePath, isDist);
    }
  }

  public dist(): void {
    const outputDirectory = this.distPath;
    const srcDir = `${this.buildPath}/dist`;
    const destDir = path.resolve(outputDirectory);
    const srcAssetsDir = path.resolve(`${this.frontendRoot}/assets`);
  
    if (!fs.existsSync(destDir)) {
      fs.mkdirSync(destDir, { recursive: true });
    }
  
    // Copy all files from outputRoot to destDir
    this.copyDirectory(srcDir, destDir);
  
    if (fs.existsSync(srcAssetsDir)) {
        let destAssetsPath = `${this.distAssetsPath}/assets`
        let destAssetsDir = path.resolve(destAssetsPath); // Get absolute path of assets directory

        if (!fs.existsSync(destAssetsDir)) {
            fs.mkdirSync(destAssetsDir, { recursive: true })
        }
        this.copyDirectory(srcAssetsDir, destAssetsDir)

        console.log(`Copied assets from ${srcAssetsDir} to ${destAssetsDir}`)
    }
  }

  public watch(): void {
    const chokidar = require('chokidar');
    let isWatchInitialied = false

    chokidar.watch(this.watchPath).on('ready', () => {
      setTimeout(() => {
        isWatchInitialied = true
      }, 1000)
    })

    chokidar.watch(this.watchPath).on('all', (event: any, path: any) => {
      if (!isWatchInitialied) return
      if (!(event == 'add' || event == 'change')) return
      let isApplied = false

      if (path.indexOf('template/page/') > -1) {
        try {
          const page = path.replace(/template\/page\//, '')
          this.build(page, false)
          isApplied = true
        } catch (e) { /* ignore */ }
      }

      if (!isApplied) {
        this.build('', false)
      }
    })

    console.log(`Watching for changes in ${this.watchPath}`)
  }
}

if (process.argv.length > 2) {
  // Script execution
  const builder = new FrontendBuilder();

  // Determine which command is being run
  const command = process.argv[2];
  if (command === 'build') {
    const page = process.argv[3]; // Optional page argument for build command
    builder.build(page, false);
  } else if (command === 'dist') {
      builder.build('', true);
      builder.dist();
  } else if (command == 'watch') {
    builder.watch()
  } else {
    console.error('Unknown command. Please use "build" or "dist".');
  }
}

export { FrontendBuilder }