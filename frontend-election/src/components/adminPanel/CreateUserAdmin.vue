<script lang="ts">
import {defineComponent} from 'vue'
import {createUser} from "@/service/AdminService";

export default defineComponent({
  name: "CreateUserAdmin",

  data() {
    return {
      newUser: {
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
        role: '',
      },
      errorMessage: '', // Add this property
    };
  },

  methods: {
    /**
     * Create a new user account by the admin
     */
    async createUser() {
      // Check if the user is an admin
      if (this.newUser.role !== 'ADMIN') {
        return;
      }
      console.log(this.newUser)
      try {
        // Call the createUser method with the new user data
        await createUser(this.newUser)
      } catch (error) {
        // Handle errors
        if (error instanceof Error) {
          this.errorMessage = error.message;
        } else {
          this.errorMessage = 'An unknown error occurred';
        }
      }
    },
  }


})
</script>

<template>

  <form class="user-form mt-4">
    <div class="form-group mb-3">
      <label for="username">Username:</label>
      <input type="text" id="username" v-model="newUser.username" class="form-control"/>
    </div>

    <div class="form-group mb-3">
      <label for="email">Email:</label>
      <input type="email" id="email" v-model="newUser.email" class="form-control"/>
    </div>

    <div class="form-group mb-3">
      <label for="password">Password:</label>
      <input type="password" id="password" v-model="newUser.password" class="form-control"/>
    </div>

    <div class="form-group mb-3">
      <label for="confirmPassword">Confirm Password:</label>
      <input type="password" id="confirmPassword" v-model="newUser.confirmPassword" class="form-control"/>
    </div>

    <div class="form-group mb-3">
      <label for="role">Role:</label>
      <select id="role" v-model="newUser.role" class="form-control">
        <option value="USER">USER</option>
        <option value="ADMIN">ADMIN</option>
      </select>
    </div>

    <div class="form-group mt-4">
      <button class="btn btn-green me-3" @click="createUser">Create user</button>
    </div>
  </form>

</template>

<style scoped>


.user-form {
  background-color: #f5f5f5;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.user-form .form-group {
  margin-bottom: 15px;
}

.user-form label {
  font-weight: bold;
  color: #333;
}

.user-form .form-control {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-top: 5px;
}

.btn-green {
  background-color: #28a745;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}
.btn-green:hover {
  background-color: #218838;
}

</style>