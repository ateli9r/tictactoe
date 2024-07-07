import express, { Request, Response, Application } from 'express';
import path from 'path';
import fs from 'fs';
import { FrontendBuilder } from './frontend';

interface Config {
  serverDocRoot: string;
  serverAppName: [string];
  requestSuffix: string;
  serverIndexDir: string;
  serverRootFiles: [any];
  serverOptions: any;
}

class Server {
  private app: Application;
  private port: number;
  private config: Config | null;

  constructor(port: number, configFilePath: string) {
    this.app = express()
    this.port = port
    this.config = this.readConfigFile(configFilePath)

    if (!this.config) {
      throw new Error('Failed to read config file.')
    }

    this.setupRoutes()
    this.setupOptions()
  }

  private readConfigFile(configFilePath: string): Config | null {
    try {
      const rawData = fs.readFileSync(configFilePath, 'utf8');
      const config = JSON.parse(rawData);
      return config;
    } catch (error) {
      console.error('Error reading the config file:', error);
      return null;
    }
  }

  private setupRoutes(): void {
    const htmlDir = this.config!.serverDocRoot;
    const listAppName = this.config!.serverAppName;
    const suffix = this.config!.requestSuffix;
    const indexDir = this.config!.serverIndexDir;
    const listRootFiles = this.config!.serverRootFiles;

    this.app.use('/assets', express.static(path.join(__dirname, 'assets')));

    this.app.get('/', (req: Request, res: Response) => {
      res.sendFile(path.resolve(indexDir))
    });

    listRootFiles.forEach((rootFile) => {
      this.app.get(`/${rootFile.name}`, (req: Request, res: Response) => {
        res.sendFile(path.resolve(rootFile.path))
      });
    })

    console.log('Frontend App Setup', listAppName)
    listAppName.forEach((appName) => {
      this.app.get(`/${appName}/:id${suffix}`, (req: Request, res: Response) => {
        const filePath = `${htmlDir}/${appName}/${req.params.id}.html`;
        res.sendFile(path.resolve(filePath));
      });
    })
  }

  private setupOptions(): void {
    const options = this.config!.serverOptions

    if (options.watchFrontend) {
      const builder = new FrontendBuilder()
      builder.watch()
    }
  }

  public start(): void {
    this.app.listen(this.port, () => {
      console.log(`Server running at http://localhost:${this.port}/`);
    });
  }
}

const configFilePath = path.join(__dirname, 'config.json');
const server = new Server(3000, configFilePath);
server.start();
