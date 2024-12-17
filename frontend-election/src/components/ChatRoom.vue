<script lang="ts">
import { defineComponent, nextTick } from "vue";
import Stomp from "stompjs";
import SockJS from "sockjs-client";
import { getCookie } from "@/utils/cookie/GetCookie";

export default defineComponent({
  name: "ChatRoom",
  // The theme is passed as a prop from the router

  props: {
    theme: {
      type: String,
      required: true,
    },
  },

  data() {
    return {
      url: "http://localhost:8080/chatroom",
      stompClient: null as Stomp.Client | null, // Type for stompClient
      messages: [] as { content: string; username: string; createdAt: Date }[], // Type messages
      message: "",
      greeting: "",
    };
  },
  methods: {
    /**
     * Connect to the chatroom WebSocket server.
     * subscribe to the chatroom topic and handle incoming messages.
     */
    connectToChatroom() {
      // Create a SockJS WebSocket connection
      const socket = new SockJS(this.url);

      // Create a Stomp client to communicate over the WebSocket connection
      this.stompClient = Stomp.over(socket);

      if (this.stompClient) {
        const headers = {
          login: "guest", // Replace with your login credentials if needed
          passcode: "guest", // Replace with your passcode if needed
        };

        this.stompClient.connect(
            headers, // Valid headers
            (frame) => {
              console.log("Connected: " + frame);

              const chatroomTopic = `/topic/chatroom/${this.theme}`;
              this.stompClient?.subscribe(chatroomTopic, (messageOutput) => {
                try {
                  const newMessage = JSON.parse(messageOutput.body);
                  this.messages.push(newMessage);
                } catch (error: any) {
                  this.messages.push({
                    content: messageOutput.body,
                    username: "System",
                    createdAt: new Date(),
                  });
                }
                this.scrollToBottom();
              });
            },
            (error) => {
              console.error("WebSocket connection failed:", error);
            }
        );
      }
    },


    /**
     * Send a message to the chatroom.
     */
    sendMessage() {
      // check if the message is empty
      if (this.message.trim() === "") {
        alert("Message cannot be empty.");
        return;
      }

      // check if the WebSocket connection is open
      if (this.stompClient && this.stompClient.connected) {
        const messagePayload = {
          content: this.message,
          theme: this.theme,
        };

        // get the user's token from the cookie
        const token = getCookie("token") || ""; // Default to an empty string if null

        this.stompClient.send(
            `/app/chatroom/${this.theme}`,
            { Authorization: token },
            JSON.stringify(messagePayload)
        );

        this.message = ""; // Clear the input field
      } else {
        alert("Failed to send message: Not connected to WebSocket server.");
      }
    },

    /**
     * Fetch messages from the backend.
     * This is used to load previous messages when a user joins the chatroom.
     */
    async getMessages() {
      try {
        const token = getCookie("token") || ""; // Default to an empty string if null

        const response = await fetch(`http://localhost:8080/message/get/${this.theme}`, {
          headers: {
            Authorization: token,
          },
        });

        this.messages = await response.json();
        this.scrollToBottom();
        console.log("Fetched messages:", this.messages);
      } catch (error) {
        console.error("Error fetching messages:", error);
        alert("Unable to load messages. Please try again.");
      }
    },


    // Scroll to the bottom of the chat messages container
    scrollToBottom() {
      nextTick(() => {
        const container = this.$el.querySelector(".messages-container");
        if (container) {
          container.scrollTop = container.scrollHeight;
        }
      });
    },

    /**
     * Check if the user is authenticated.
     * If not, redirect to the login page.
     */
    async checkAuth() {
      console.log("Checking authentication...");
      const token = getCookie("token");
      if (!token) {
        alert("You need to log in to access this page.");
        this.$router.push({ path: "/login" });
      }
    },
  },
  // When the component is mounted, check if the user is authenticated, get messages, and connect to the chatroom
  mounted() {
    this.checkAuth();
    this.getMessages();
    this.connectToChatroom();
  },
});
</script>


<template>
  <div class="background">
    <div class="chat-room">
      <h1>Chat Room: {{ theme }}</h1>

      <div class="messages-container">
        <div
          class="message"
          :class="{ system: msg.username === 'System' }"
          v-for="(msg, index) in messages"
          :key="index"
        >
          <span v-if="msg.username !== 'System'" class="username">{{ msg.username }}</span>
          <span class="timestamp">{{ new Date(msg.createdAt).toLocaleTimeString() }}</span>
          <p class="content">{{ msg.content }}</p>
        </div>
      </div>


      <div class="input-container">
        <input
          v-model="message"
          type="text"
          placeholder="Type your message..."
          @keydown.enter="sendMessage"
        />
        <button @click="sendMessage">Send</button>
      </div>
    </div>
  </div>
</template>


<style scoped>
.background {
  background-color: #24786E99;
  width: 100%;
  padding: 30px;
  min-height: 80vh;
  display: flex;
  justify-content: center;
  align-items: flex-start;
}

.chat-room {
  max-width: 1200px;
  width: 100%;
  margin: 0 auto;
  padding: 30px;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 12px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.chat-room h1 {
  font-size: 2.4rem;
  color: #333;
  text-align: center;
  margin-bottom: 30px;
}

.messages-container {
  border: 1px solid #ccc;
  padding: 20px;
  max-height: 700px;
  overflow-y: auto;
  background-color: #fff;
  border-radius: 12px;
  margin-bottom: 30px;
  box-shadow: inset 0 3px 6px rgba(0, 0, 0, 0.1);
}

.message {
  margin-bottom: 20px;
  padding: 15px;
  border-bottom: 1px solid #eee;
}

.message.system {
  font-style: italic;
  color: #888;
  text-align: center;
}


.message:last-child {
  border-bottom: none;
}

.username {
  font-weight: bold;
  color: #24786e;
  font-size: 1.2rem;
}

.timestamp {
  font-size: 1rem;
  color: #888;
  margin-left: 15px;
}

.content {
  margin-top: 8px;
  font-size: 1.2rem;
  color: #333;
}

.input-container {
  display: flex;
  align-items: center;
  gap: 15px;
}

input[type="text"] {
  flex: 1;
  padding: 15px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1.2rem;
  box-shadow: inset 0 3px 6px rgba(0, 0, 0, 0.05);
}

input[type="text"]:focus {
  outline: none;
  border-color: #24786e;
}

button {
  background-color: #28a745;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
  margin-top: 10px;
}

button:hover {
  background-color: #218838;
}

</style>
