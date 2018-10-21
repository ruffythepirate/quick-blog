const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
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
        test: /\.css$/,
        loader: ['vue-style-loader', 'css-loader'],
      },
      {
        test: /\.js$/,
        loader: 'babel-loader',
      },
    ],
  },
  plugins: [
    new VueLoaderPlugin(),
  ],
};
