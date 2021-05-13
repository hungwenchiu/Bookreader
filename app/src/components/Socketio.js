import io from 'socket.io-client';
export let socket;
export const initiateSocket = (userid) => {
    socket = io.connect(`ws://localhost:9092?userId=${userid}`,    {
        upgrade: false});
}
export const disconnectSocket = () => {
    console.log('Disconnecting socket...');
    if(socket) socket.disconnect();
}

// subscribe topic
// export const subscribe = (topic) => {
//     if (!socket) return(true);
//     socket.on(topic, msg => {
//         console.log(msg);
//     });
// }
export const sendMessage = (topic, message) => {
    if (socket)  {
        socket.emit(topic, { msgContent: message });
    }
}