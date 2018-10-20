const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
  // mode: 'development',
  entry: {
    articleEditor: './src/articleEditor.js'
  },
  output: {
    filename: '[name].js'
  },
  module: {
    rules: [
      // {
      //   enforce: 'pre',
      //   test: /(\.js$|\.vue$)/,
      //   exclude: [/node_modules/, /dist/],
      //   loader: 'eslint-loader',
      // },
      {
        test: /\.vue$/,
        loader: 'vue-loader',
      },
      {
        test: /\.js$/,
        loader: 'babel-loader',
      },
      {
        test: /\.css$/,
        loader: 'css-loader',
      },
    ],
  },
  plugins: [
    new VueLoaderPlugin(),
  ],
};
