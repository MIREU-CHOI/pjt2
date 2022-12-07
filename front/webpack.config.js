// const path = require("path");
// const { CleanWebpackPlugin } = require("clean-webpack-plugin");
// const HtmlWebpackPlugin = require("html-webpack-plugin");

// module.exports = {
//     plugins: [
//         new CleanWebpackPlugin(),
//         new HtmlWebpackPlugin({
//           template: "./template/index.html",
//         }),
//       ],
//     entry: "./src/index.js",
//     output: {
//         filename: `[name].[chunkhash].js`,
//         path: path.resolve(__dirname, "dist"),
//     },
//     module: {
//         rules: [
//         {
//             test: /\.js$/,
//             use: {
//             loader: "babel-loader",
//             options: {
//                 presets: ["@babel/preset-react"],
//             },
//             },
//         },
//         ],
//     },
// };