const http = require('http')
const app = require('./index')
const port = process.env.PORT || 1972
const server = http.createServer(app)
const WebSocket = require('ws');
const wsServer = new WebSocket.Server({port: 8080});
server.listen(port)

wsServer.on('connection', ws => {
    console.log("con")
    ws.on('message', message => {
      console.log(`Received message => ${message}`)
    })
    ws.send('ho!')
  })

const broadcast = (data) =>
console.log("aici")
    wsServer.clients.forEach((client) => {
    console.log(client)
    if (client.readyState === WebSocket.OPEN) {
      client.send(JSON.stringify(data));
    }
});

module.exports = broadcast