var express = require('express');
var cors = require('cors')
var app = express();
var io = require('socket.io')(8000);
const port = 7000

var bodyParser = require('body-parser')
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cors());

var ipToPinMap = new Map();
var pinToIpMap = new Map();
var sessions = new Map();

app.get('/', function (req, res) {
    console.log('hi')
    res.send('hello')
});

app.post('/getip/', function (req, res) {
    console.log('getip')
    var pin = req.body.pin;
    console.log(pin)
    if (ipToPinMap.has(pin)) {
        var ip = ipToPinMap.get(pin);
        res.status(200).json({ ip: ip })
    } else {
        res.status(422)
    }
});

app.post('/addip/', function (req, res) {
    console.log('addip')
    var ip = req.body.ip
    var pin = Math.floor(Math.random() * 10000);
    ipToPinMap.set(pin, ip);
    pinToIpMap.set(ip, pin)
    res.status(200).json({ pin: pin });
});

app.post('/validsession/', function (req, res) {
    console.log(req.body)
    var pin = parseInt(req.body.pin)
    if (sessions.has(pin)) {
        res.sendStatus(200)
    } else {
        res.sendStatus(422)
    }
})

app.get('/createsession/', function (req, res) {
    var pin = Math.floor(Math.random() * 10000);
    sessions.set(pin, false);
    res.status(200).json({ pin: pin })
})

app.post('/connect/', function (req, res) {
    var pin = req.body.pin;
    if (sessions.has(pin)) {
        sessions.set(pin, true);
        res.status(200);
    } else {
        res.status(422);
    }
})

app.listen(port, () => console.log('Routing Server listening on port: ' + port));

io.on('connection', function (socket) {
    console.log('new connection!')

    socket.send(socket.id)

    socket.on('TEST', function (from, msg) {
        console.log('tested from: ', from, ' saying: ', msg)
        io.emit('GOOD REPLY', "gotcha")
    })

    socket.on('createSession', function (data) {
        var pin = data.pin
        sessions.set(pin, false);
        console.log('finna emit')
        socket.join(pin)
        io.to(pin).emit("sessionCreated", {
            pin: pin
        })
    })

    socket.on('connectRemote', function (data) {
        console.log('in connecting remote')
        var pin = parseInt(data.pin)
        if (sessions.has(pin)) {
            socket.join(pin)
            var session = sessions.get(pin)
            if (session == false) {
                sessions.set(pin, true)
                console.log('about to emit successfulpairing')
                io.to(pin).emit("successfulPairing", {
                    pin: pin
                })
            }
        }
    })

    socket.on('newGesture', function (data) {
        var pin = parseInt(data.pin)
        console.log(data)
    })

    socket.on('disconnect', function () {
        io.emit('user disconnected');
      });
})