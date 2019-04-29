const TerserJSPlugin = require('terser-webpack-plugin');
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const OptimizeCSSAssetsPlugin = require('optimize-css-assets-webpack-plugin');

module.exports = {
    cache: true,
    mode: "production",
    devtool: "sourcemaps",
    entry: {
        app: "./src/main/js/app.js",
        styles: "./src/main/js/app.scss"
    },
    output: {
        path: __dirname,
        filename: "./src/main/resources/static/[name].js"
    },
    plugins: [
        new MiniCssExtractPlugin({
            path: __dirname,
            filename: "./src/main/resources/static/[name].css"
        })
    ],
    optimization: {
        minimizer: [new TerserJSPlugin({}), new OptimizeCSSAssetsPlugin({})],
    },
    module: {
        rules: [

            // react build
            {
                test: /\.js$/,
                exclude: /(node_modules)/,
                use: [{
                    loader: "babel-loader",
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            },

            {
                test: /\.scss$/,
                use: [ MiniCssExtractPlugin.loader, "css-loader", "sass-loader" ]
            }
        ]
    }
};
