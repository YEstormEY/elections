<script lang="ts">
import router from "@/router/router";
import TokenVerification from '@/utils/TokenVerification';

export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
    };
  },
  methods: {
    async fetchRegister() {
      if (!this.username || !this.email || !this.password || !this.confirmPassword) {
        alert('Please fill in all fields');
        return;
      }
      if (this.password !== this.confirmPassword) {
        alert('Passwords do not match');
        return;
      }
      if (this.password.length < 8) {
        alert('Password must be at least 8 characters long');
        return;
      }

      try {
        const response = await fetch('http://localhost:8080/auth/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            username: this.username,
            email: this.email,
            password: this.password,
            confirmPassword: this.confirmPassword,
          }),
        });

        if (response.ok) {
          console.log('Registration successful');
          alert('Registration successful! Please log in to continue.');
          router.push('/login');
        } else {
          // Attempt to extract error message from response
          const errorData = await response.json();
          alert(`Registration failed: ${errorData.message || 'Unknown error'}`);
        }
      } catch (error) {
        if (error instanceof Error) {
          throw new Error(error.message);
        } else {
          throw new Error('An unknown error occurred');
        }
      }
    },
  },
  computed: {
    passwordStrength() {
      if (this.password.length < 8) {
        return 'really weak - Password must be at least 8 characters long';
      } else if (!/[A-Z]/.test(this.password) || !/[0-9]/.test(this.password)) {
        return 'weak - Password should contain an uppercase letter and a number';
      } else if (!/[!@#$%^&*]/.test(this.password)) {
        return 'medium - Password should contain a special character';
      } else {
        return 'Strong - Good to go!';
      }
    }
  },
  mounted() {
    const tokenService = new TokenVerification();
    tokenService.verifyToken().then(isLoggedIn => {
      if (isLoggedIn) {
        router.push('/'); // Redirect to homepage if already logged in
      }
    });
  }
};
</script>

<template>
  <div class="register d-flex justify-content-center align-items-center vh-100">
    <div class="card p-5 shadow-lg">
      <img src="@/assets/logo2.png" class="img-fluid mx-auto d-block w-25 mb-4" alt="Logo" />
      <h1 class="text-center text-primary mb-4">Register</h1>
      <form @submit.prevent="fetchRegister">
        <!-- Username field -->
        <div class="form-group">
          <label for="username">Username:</label>
          <input type="text" class="form-control" v-model="username" id="username" name="username" placeholder="Enter your username" />
        </div>

        <!-- Email field -->
        <div class="form-group mt-4">
          <label for="email">Email:</label>
          <input type="email" class="form-control" v-model="email" id="email" name="email" placeholder="Enter your email" />
        </div>

        <!-- Password field -->
        <div class="form-group mt-4">
          <label for="password">Password:</label>
          <input type="password" class="form-control" v-model="password" id="password" name="password" placeholder="Enter your password" />
          <small class="text-muted">{{ passwordStrength }}</small>
        </div>

        <!-- Confirm Password field -->
        <div class="form-group mt-4">
          <label for="confirm-password">Confirm Password:</label>
          <input type="password" class="form-control" v-model="confirmPassword" id="confirmPassword" name="confirm-password" placeholder="Confirm your password" />
        </div>

        <!-- Register button -->
        <div class="d-grid gap-2 mt-5">
          <button type="submit" class="btn btn-primary btn-block">Register</button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
/* Center the registration form vertically and horizontally */
.register {
  background-color: white;
}

.card {
  border-radius: 10px;
  max-width: 100%; /* Ensure the card doesn't overflow on small screens */
}

.vh-100 {
  height: 100vh;
}
</style>
