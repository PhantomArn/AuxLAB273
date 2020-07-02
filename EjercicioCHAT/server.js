/*
 * server.js
 */

/* Librerías necesarias para crear la aplicación */
var app  = require('express')();
var http = require('http').Server(app);
var io   = require('socket.io')(http);


/*
 *  Configuramos nuestra ruta (index) principal con
 *  express para mostrar el chat
 */
app.get('/', function(req, res) {
  res.sendFile( __dirname + '/chat.html');
});


/*
 *  Configuramos Socket.IO para estar a la escucha de
 *  nuevas conexiones.
 */
io.on('connection', function(socket) {
  
  console.log('New user connected');
  
  /*
   * Cada nueva conexión deberá estar a la escucha
   * del evento 'nuevo mensaje', el cual se activa
   * cada vez que un usuario envia un mensaje.
   * 
   * @param  msj : Los datos enviados desde el cliente a 
   *               través del socket.
   */
  socket.on('nuevo mensaje', function(msj) {
    io.emit('nuevo mensaje', msj);
  });
  
  /*
   * Imprimimos en consola cada vez que un usuario
   * se desconecte del sistema.
   */
  socket.on('disconnect', function() {
    console.log('Usuario desconectado');
  });
  
});


/*
 * Iniciamos en el puerto 3000
 */
http.listen(3000, function() {
  console.log('Escuchando en *:3000');
});
