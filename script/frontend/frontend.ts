import * as fs from 'fs';
import * as path from 'path';

class HTMLProcessor {
  private frontendRoot: string;
  private buildPath: string = '';
  private fileExtension: string;
  private assetsPath: string = ''; // Initialize assetsPath with an empty string
  private distPath: string = '';

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
      this.assetsPath = config.assetsPath || ''; // Update assetsPath based on config
      this.distPath = config.distPath || './dist';
      this.buildPath = config.buildPath || './output';
    } catch (error: any) {
      console.error(`Error loading config file at ${configPath}: ${error.message}`);
    }
  }

  // private copyDirectory(src: string, dest: string, excludes: [string?] = []): void {
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
        // let isExclude = false
        // for (let exc of excludes) {
        //     if (entry.name.indexOf(exc!) > -1) {
        //         isExclude = true
        //         break
        //     }
        // }
        // if (isExclude) continue

      const srcPath = path.join(src, entry.name);
      const destPath = path.join(dest, entry.name);

      if (entry.isDirectory()) {
        // this.copyDirectory(srcPath, destPath, excludes);
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
              content = this.processIncludes(content);
            } else {
              console.error(`Include file "${includePath}" does not exist.`);
            }
          }
      }

    }
    return content.trim(); // Trim leading and trailing whitespace
  }

  private processDirectory(dirPath: string): void {
    if (!fs.existsSync(dirPath)) {
      console.error(`Directory "${dirPath}" does not exist.`);
      return;
    }
    const entries = fs.readdirSync(dirPath, { withFileTypes: true });

    for (let entry of entries) {
      const fullPath = path.join(dirPath, entry.name);

      if (entry.isDirectory()) {
        this.processDirectory(fullPath); // Recursive call for subdirectories
      } else if (entry.isFile() && entry.name.endsWith('.html')) {
        // Process HTML files
        const pageContent = fs.readFileSync(fullPath, 'utf8');
        const processedContent = this.processIncludes(pageContent);

        const relativePath = path.relative(path.join(this.frontendRoot, 'template', 'page'), fullPath);
        const outputFilePath = path.join(this.buildPath, relativePath.replace(/\.html$/, `.${this.fileExtension}`));

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

      const pageContent = fs.readFileSync(pageFilePath, 'utf8');
      const processedContent = this.processIncludes(pageContent);
      const outputFilePath = path.join(this.buildPath, page.replace(/\.html$/, `.${this.fileExtension}`));
      fs.writeFileSync(outputFilePath, processedContent, 'utf8');
      console.log(`Processed ${page} to ${outputFilePath}`);
    } else {
      const pagePath = path.join(this.frontendRoot, 'template', 'page');
      this.processDirectory(pagePath);
    }
  }

  public dist(): void {
    const outputDirectory = this.distPath;
    const srcDir = this.buildPath;
    const destDir = path.resolve(outputDirectory);
    const srcAssetsDir = path.resolve(`${srcDir}/assets`);
  
    if (!fs.existsSync(destDir)) {
      fs.mkdirSync(destDir, { recursive: true });
    }
  
    // Copy all files from outputRoot to destDir
    // this.copyDirectory(srcDir, destDir, ['assets']);
    this.copyDirectory(srcDir, destDir);
    console.log(`Copied files from ${srcDir} to ${destDir}`);
  
    if (fs.existsSync(srcAssetsDir)) {
        const assetsPath = `${this.assetsPath}/assets`
        let destAssetsDir = path.resolve(assetsPath); // Get absolute path of assets directory

        if (!fs.existsSync(destAssetsDir)) {
            fs.mkdirSync(destAssetsDir, { recursive: true })
        }
        this.copyDirectory(srcAssetsDir, destAssetsDir)

        console.log(`Copied assets from ${srcAssetsDir} to ${destAssetsDir}`)
    }

  }
}

// Script execution
const processor = new HTMLProcessor();

// Determine which command is being run
const command = process.argv[2];
if (command === 'build') {
  const page = process.argv[3]; // Optional page argument for build command
  processor.build(page, false);
} else if (command === 'dist') {
    processor.build('', true);
    processor.dist();
} else {
  console.error('Unknown command. Please use "build" or "dist".');
}
