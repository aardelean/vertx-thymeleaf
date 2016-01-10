var path = require('path');

module.exports = {
  context: path.join(__dirname, "client"),
  entry: {
    javascript: path.join(__dirname, "client", "app.js"),
    html: path.join(__dirname, "client", "index.html"),
  },

  output: {
    filename: "app.js",
    path: __dirname + "/dist/",
  },
 module: {
         loaders: [{
             test: /\.js?$/,
             loader: 'babel',
             query:
             {
                 presets:['es2015', "stage-0", "stage-1", 'react']
             }
         },
         {
             test: /\.html$/,
             loader: "file?name=index.[ext]",
        }]
 }
}
