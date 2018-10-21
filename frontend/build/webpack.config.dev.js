const merge = require('webpack-merge');
const baseConfig = require('./webpack.config.base');

const webpack = require('webpack')
const HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports = merge(baseConfig, {
  mode: 'development',
  devServer: {
    port: 3000
  },
  plugins : [
    new webpack.HotModuleReplacementPlugin(),
    new HtmlWebpackPlugin( {
      filename: 'index.html',
      template: 'index.html',
      inject: true
    })
  ]
});
