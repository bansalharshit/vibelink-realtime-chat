let stompClient = null;
let currentRoom = "general"; // default room; change as needed

function leaveRoom() {
  if (stompClient && stompClient.connected) {
    stompClient.disconnect(() => {
      console.log("Disconnected from room:", currentRoom);
    });
  }
  currentRoom = null;

  // Hide chat screen, go back to room selection
  document.getElementById("chat-screen").style.display = "none";
  document.getElementById("room-screen").style.display = "block";
}

async function loadHistory(roomId) {
  try {
    const res = await fetch(`/api/rooms/${encodeURIComponent(roomId)}/messages?last=50`);
    const msgs = await res.json();
    const chatBox = document.getElementById('chat-box');
    chatBox.innerHTML = '';
    msgs.forEach(m => appendMessage(m));
  } catch (e) {
    console.error('Failed to load history', e);
  }
}

function connect(roomId) {
  const socket = new SockJS('/server1');
  stompClient = Stomp.over(socket);
  stompClient.debug = null;
  stompClient.connect({}, () => onConnected(roomId), onError);
}

function onConnected(roomId) {
  console.log('Connected');
  stompClient.subscribe(`/topic/${roomId}`, (payload) => {
    try {
      const msg = JSON.parse(payload.body);
      appendMessage(msg);
    } catch (e) {
      console.error('Invalid message payload', payload.body);
    }
  });
}

function onError(error) {
  console.error('STOMP error:', error);
  setTimeout(() => connect(currentRoom), 2000);
}

function appendMessage({ sender, content, localDateTime }) {
  const chatBox = document.getElementById('chat-box');
  const username = document.getElementById('username').value.trim();
  const container = document.createElement('div');
  container.classList.add('message');
  container.classList.add(sender === username ? 'self' : 'other');

  const text = document.createElement('div');
  text.textContent = `${sender}: ${content}`;

  const meta = document.createElement('span');
  meta.classList.add('meta');
  const ts = localDateTime ? new Date(localDateTime) : new Date();
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

  stompClient.send(
    `/app/chat.sendMessage/${currentRoom}`,
    {},
    JSON.stringify({ sender: username, content })
  );
  document.getElementById('message').value = '';
}

// ✅ Add these new functions here
async function proceedToRooms() {
  const username = document.getElementById("username").value.trim();
  if (!username) {
    alert("Please enter a username first");
    return;
  }

  document.getElementById("login-screen").style.display = "none";
  document.getElementById("room-screen").style.display = "block";

  try {
    const res = await fetch("/api/v1/rooms");
    const rooms = await res.json();
    const roomSelect = document.getElementById("roomSelect");
    roomSelect.innerHTML = "";
    rooms.forEach(r => {
      const opt = document.createElement("option");
      opt.value = r.roomId;
      opt.textContent = r.roomId;
      roomSelect.appendChild(opt);
    });
  } catch (e) {
    console.error("Failed to fetch rooms", e);
  }
}

function joinSelectedRoom() {
  const selected = document.getElementById("roomSelect").value;
  const newRoom = document.getElementById("newRoom").value.trim();
  const room = newRoom || selected;

  if (!room) {
    alert("Please select or enter a room");
    return;
  }

  currentRoom = room;
  document.getElementById("room-screen").style.display = "none";
  document.getElementById("chat-screen").style.display = "block";

  loadHistory(currentRoom);
  connect(currentRoom);
}




document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('chat-form').addEventListener('submit', sendMessage);
  // Disable autocomplete noise
  document.getElementById('message').setAttribute('autocomplete', 'off');
});

