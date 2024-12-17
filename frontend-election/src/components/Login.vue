<script lang="ts">
import { ref, onMounted } from 'vue';
import { login } from '@/utils/auth';
import router from "@/router/router";
import TokenVerification from '@/utils/TokenVerification'; // Import the singleton

export default {
  setup() {
    const username = ref('');
    const password = ref('');

    // Inject TokenVerification singleton
    const tokenService = new TokenVerification();

    const fetchLogin = async () => {

      if (!username.value || !password.value) {
        alert('Please enter your username and password');
        return;
      }

      try {
        const response = await fetch('http://localhost:8080/auth/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            username: username.value,
            password: password.value,
          }),
        });

        if (response.ok) {
          const data = await response.json();
          console.log('Login successful:', data);
          const token = data.token;
          const tokenExpiry = data.expiryTime;
          const userId = data.userId;

          // Set the token and userId in cookies
          document.cookie = `token=${token}; expires=${new Date(tokenExpiry).toUTCString()}; path=/`;
          document.cookie = `userId=${userId}; expires=${new Date(tokenExpiry).toUTCString()}; path=/`;

          console.log('Token:', data);

          // Update the global auth state
          login(username.value);

          // Redirect to the home page
          router.push('/');

          console.log('Login successful');
        } else {
          console.error('Login failed');
          alert('Invalid credentials');
        }
      } catch (error) {
        console.error('Error during login:', error);
      }
    };

    onMounted(async () => {
      // Check if the user is already logged in

      const isLoggedIn = await tokenService.verifyToken();
      if (isLoggedIn) {
        // Redirect to home if already logged in
        router.push('/');
      }
    });

    return {
      username,
      password,
      fetchLogin,
    };
  },
};
</script>

<template>
  <div class="login d-flex justify-content-center align-items-center vh-100">
    <div class="card p-5 shadow-lg" :style="{ width: '30%' }">
      <img src="@/assets/logo2.png" class="img-fluid mx-auto d-block w-25 mb-4" alt="Logo" />
      <h1 class="text-center text-primary mb-4">Login</h1>
      <form @submit.prevent="fetchLogin">
        <div class="form-group">
          <label for="username">Username:</label>
          <input type="text" class="form-control" v-model="username" id="username" name="username" placeholder="Enter your username">
        </div>
        <div class="form-group mt-4">
          <label for="password">Password:</label>
          <input type="password" class="form-control" v-model="password" id="password" name="password" placeholder="Enter your password">
        </div>
        <div class="d-grid gap-2 mt-5">
          <button class="btn btn-primary btn-block">Login</button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.login {
  background-color: white;
}
.card {
  border-radius: 10px;
  max-width: 100%;
}
.vh-100 {
  height: 100vh;
}
</style>
