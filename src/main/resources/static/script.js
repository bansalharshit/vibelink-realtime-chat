let stompClient = null;

function connect() {
  // This must match your Spring endpoint in WebSocketConfig.registerStompEndpoints
  const socket = new SockJS('/server1');
  stompClient = Stomp.over(socket);

  // Optional: silence debug logs
  stompClient.debug = null;

  stompClient.connect({}, onConnected, onError);
}

function onConnected(frame) {
  console.log('Connected:', frame);
  // Subscribe to your topic (must match @SendTo or destinations you broadcast to)
  stompClient.subscribe('/topic/return-to', onMessageReceived);
}

function onError(error) {
  console.error('STOMP error:', error);
  // Basic retry (optional)
  setTimeout(connect, 2000);
}

function onMessageReceived(payload) {
  try {
    const msg = JSON.parse(payload.body);
    appendMessage(msg);
  } catch (e) {
    console.error('Invalid message payload:', payload.body);
  }
}

function appendMessage({ sender, content, timestamp }) {
  const chatBox = document.getElementById('chat-box');
  const username = document.getElementById('username').value.trim();

  const container = document.createElement('div');
  container.classList.add('message');
  container.classList.add(sender === username ? 'self' : 'other');

  const text = document.createElement('div');
  text.textContent = `${sender}: ${content}`;

  const meta = document.createElement('span');
  meta.classList.add('meta');
  const ts = timestamp ? new Date(timestamp) : new Date();
  meta.textContent = ts.toLocaleTimeString();

  container.appendChild(text);
  container.appendChild(meta);

  chatBox.appendChild(container);
  chatBox.scrollTop = chatBox.scrollHeight;
}

function sendMessage(e) {
  e.preventDefault();

  const username = document.getElementById('username').value.trim();
  const content = document.getElementById('message').value.trim();

  if (!username || !content || !stompClient) return;

  // Destination must match your @MessageMapping prefix (/app) + mapping path
  stompClient.send(
    '/app/chat.sendMessage',
    {},
    JSON.stringify({
      sender: username,
      content,
      timestamp: new Date().toISOString()
    })
  );

  document.getElementById('message').value = '';
}

document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('chat-form').addEventListener('submit', sendMessage);
  connect();
});
