const MiniCssExtractPlugin = require('mini-css-extract-plugin');

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
                use: [ "style-loader", MiniCssExtractPlugin.loader, "css-loader", "sass-loader" ]
            }
        ]
    }
};
