const globule = require("globule");
const path = require("path");
const { DefinePlugin } = require('webpack');

module.exports = {
    mode: "development",
    entry: "./index.ts",
    devtool: "inline-source-map",
    module: {
        rules: [
            { test: /\.tsx?$/, use: "ts-loader", exclude: "/node_modules/" },
        ],
    },
    resolve: {
        extensions: [".tsx", ".ts", ".js"],
        fallback: {
            "fs": false,
        }
    },
    output: {
        filename: "app.js",
        // path: path.resolve(__dirname, "../../docs/assets/js/"),
        path: path.resolve(__dirname, "../frontend/assets"),
        library: "ateli9r",
    },
    plugins: [
        new DefinePlugin({
            '__VUE_OPTIONS_API__': JSON.stringify(true),
            '__VUE_PROD_DEVTOOLS__': JSON.stringify(false),
            '__VUE_PROD_HYDRATION_MISMATCH_DETAILS__': JSON.stringify(false)
        })
    ]
};
