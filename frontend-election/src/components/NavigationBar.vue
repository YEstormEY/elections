<script lang="ts">
import TokenVerification from '@/utils/TokenVerification'; // Ensure correct path
import { authState } from "@/utils/auth";
import { inject } from 'vue';



export default {


  data() {
    return {
      isMenuActive: false, // state for toggling the menu
    };
  },

  methods: {
    // Method to toggle menu
    toggleMenu() {
      this.isMenuActive = !this.isMenuActive;
    }

  },

  mounted() {
    // Inject the TokenVerification singleton
    const tokenService = new TokenVerification();

    // Method to check if the user is logged in by verifying the token
    const checkToken = async () => {
      const isValid = await tokenService.verifyToken();
      if (isValid) {
        console.log('Token is valid');

        // If the token is valid, update authState and get user info
        authState.isLoggedIn = true;
        const token = tokenService.getToken();
        const user = JSON.parse(atob(token.split('.')[1])); // Decode JWT to get user info
        authState.userName = user.sub; // Assuming 'sub' is the username

        // Force Vue to update the DOM
      } else {
        console.log('Token is invalid or expired');
        localStorage.removeItem('userId'); // Remove the token from localStorage

        // If token is invalid, update authState to reflect the logged-out state
        authState.isLoggedIn = false;
        authState.userName = '';
      }
    };

    // Call the token check when the component is mounted
    checkToken();
  },

  setup() {

    // Inject the TokenVerification instance provided by the parent component
    const tokenVerification = inject('TokenVerification');

    if (!tokenVerification) {
      console.error('TokenVerification instance not found');
    }

    // Logout function to clear the token and redirect the user
    const logout = () => {
      if (tokenVerification && typeof tokenVerification.logout === 'function') {
        tokenVerification.logout(); // Call the logout function
        console.log('User has been logged out');
      } else {
        console.error('Logout function is not available on TokenVerification');
      }
    };

    return {
      authState,
      logout,
    };
  },
};
</script>

<template>
  <header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-fluid">
        <a class="navbar-brand">
          <img src="../assets/logo2.png" class="navbar-logo img-fluid" alt="logo" />
        </a>
        <button
          class="navbar-toggler"
          type="button"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
          @click="toggleMenu"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div :class="['collapse', 'navbar-collapse', { show: isMenuActive }]">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <router-link class="nav-link active" to="/">Home</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/articlepage">Articles</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/Partijen">Partijen</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link active" to="/ShadowElections">Shadow elections</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/VotingGuidePage">Voting guide</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/ChatRooms">Chat Rooms</router-link>
            </li>


            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                Alle partijen
              </a>
              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" @click="goToParty('VVD')">VVD</a></li>
                <li><a class="dropdown-item" @click="goToParty('D66')">D66</a></li>
                <li><a class="dropdown-item" @click="goToParty('PVV')">PVV</a></li>
                <li><a class="dropdown-item" @click="goToParty('CDA')">CDA</a></li>
                <li><a class="dropdown-item" @click="goToParty('GroenLinks')">GroenLinks</a></li>
                <li><a class="dropdown-item" @click="goToParty('PvdA')">PvdA</a></li>
                <li><a class="dropdown-item" @click="goToParty('SP')">SP</a></li>
                <li><a class="dropdown-item" @click="goToParty('ChristenUnie')">ChristenUnie</a></li>
                <li><a class="dropdown-item" @click="goToParty('FVD')">FVD</a></li>
                <li><a class="dropdown-item" @click="goToParty('Partij voor de Dieren')">Partij voor de Dieren</a></li>
                <li><a class="dropdown-item" @click="goToParty('50PLUS')">50PLUS</a></li>
                <li><a class="dropdown-item" @click="goToParty('SGP')">SGP</a></li>
                <li><a class="dropdown-item" @click="goToParty('DENK')">DENK</a></li>
                <li><a class="dropdown-item" @click="goToParty('BIJ1')">BIJ1</a></li>
              </ul>
            </li>
          </ul>

          <!-- Show "Login" and "Sign Up" only if the user is not logged in -->
          <ul v-if="!authState.isLoggedIn" class="navbar-nav ms-auto">
            <li class="nav-item">
              <router-link class="btn btn-primary me-2" to="/register">
                <span class="glyphicon glyphicon-user"></span> Sign Up
              </router-link>
            </li>
            <li class="nav-item">
              <router-link class="btn btn-outline-primary" to="/login">
                <span class="glyphicon glyphicon-log-in"></span> Login
              </router-link>
            </li>
          </ul>

          <!-- Show user info when logged in -->
          <ul v-else class="navbar-nav ms-auto">
            <li class="nav-item">
              <router-link to="/account"><span class="nav-link" >Welcome, {{ authState.userName }}</span></router-link>
            </li>
            <li class="nav-item">
              <button @click="logout" class="btn btn-outline-danger">Logout</button>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </header>
</template>

<style scoped>
.navbar {
  width: 100%; /* Ensure the navbar takes full width */
  z-index: 9999;
}

.navbar-logo {
  max-height: 50px; /* Adjust this value to control the height */
  max-width: 100px; /* Adjust this value to control the width */
  object-fit: contain; /* Ensures the image scales correctly */
}

/* Add hover effect to nav links */
.nav-link {
  color: #333;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s ease; }

.nav-link:hover {
  color: #0056b3; /* Change text color on hover */
  background-color: #f8f9fa; /* Optional: Add background color on hover */
  border-radius: 5px; /* Add a slight rounded corner on hover */
  border: 1px solid #0056b3;
}
</style>
