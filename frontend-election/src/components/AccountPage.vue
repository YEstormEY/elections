<script lang="ts">
import {User} from '@/class/User'
import {authState} from '@/utils/auth'
import router from '@/router/router'
import {deleteUser, fetchUser, saveChanges, updatePassword} from '@/service/UserService'
import {getCookie} from "@/utils/cookie/GetCookie";
import {inject} from "vue";
import {deleteCookie} from "@/utils/cookie/DeleteCookie";
import ArticlesAdmin from '@/components/adminPanel/ArticlesAdmin.vue'
import CreateUserAdmin from "@/components/adminPanel/CreateUserAdmin.vue";
import CreateArticleAdmin from "@/components/adminPanel/CreateArticleAdmin.vue";
import AllUsersAdmin from "@/components/adminPanel/AllUsersAdmin.vue";

export default {
  components: {AllUsersAdmin, CreateArticleAdmin, CreateUserAdmin, ArticlesAdmin},
  computed: {
    // used to check the strength of the password
    passwordStrength() {
      // checks the length, if it contains an uppercase letter number and special character
      if (this.newPassword.length < 8) {
        return 'really weak - Password must be at least 8 characters long';
      } else if (!/[A-Z]/.test(this.newPassword) || !/[0-9]/.test(this.newPassword)) {
        return 'weak - Password should contain an uppercase letter and a number';
      } else if (!/[!@#$%^&*]/.test(this.newPassword)) {
        return 'medium - Password should contain a special character';
      } else {
        return 'Strong - Good to go!';
      }
    }
  },
  data() {
    return {
      user_id: 0,
      username: '',
      email: '',
      password: '',
      role: '',
      isEditing: false,
      isChangingPassword: false,
      currentPassword: '',
      newPassword: '',
      confirmPassword: '',
      errorMessage: '', // Add this property
      createNewArticle: false,
      createNewUser: false,
      toggleArticle: false,
      fetchAllUsers: false,
    };
  },


  methods: {

    /**
     * Fetch the user data from the backend
     */
    async fetchUser() {
      try {
        const data = await fetchUser()
        if (data) {
          const user = new User(data.userId, data.username, data.email, data.password, data.role)
          this.user_id = user.user_id
          this.username = user.username
          this.email = user.email
          this.password = user.password
          this.role = user.role
        }
      } catch (error) {
        if (error instanceof Error) {
          this.errorMessage = error.message;
          console.log(this.errorMessage);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }

    },

    /**
     * Delete the user account
     */
    async deleteUser() {
      if (confirm('Are you sure you want to delete your account?')) {
        try {
            await deleteUser()

            const removeCookie = deleteCookie; //assign removeCookie function to the imported deleteCookie
            removeCookie('token'); //remove the token cookie
            removeCookie('userId'); //remove the userId cookie

        } catch (error) {
          if (error instanceof Error) {
            this.errorMessage = error.message;
            console.log(this.errorMessage);
          } else {
            console.error('An unknown error occurred:', error);
          }
        }

      }
    },

    // used to toggle the edit mode
    toggleEdit() {
      this.isEditing = !this.isEditing
    },

    // used to toggle the password change form
    toggleChangePassword() {
      this.isChangingPassword = !this.isChangingPassword
    },

    /**
     * Save the changes made to the user account
     */
    async saveChanges() {
      try {
        // Call the saveChanges method with the updated user data
        const response = await saveChanges(this.user_id, this.username, this.email)
        if (response) {
          console.log(response)

          // Update the token and new token expiry date
          const token = response.token
          const tokenExpiry = response.tokenExpiry

          // Set the new token and user ID cookies
          document.cookie = `token=${token}; expires=${new Date(tokenExpiry).toUTCString()}; path=/`;
          document.cookie = `userId=${this.user_id}; expires=${new Date(tokenExpiry).toUTCString()}; path=/`;

          this.isEditing = false // Exit edit mode
        }
      } catch (error) {
        if (error instanceof Error) {
          this.errorMessage = error.message;
          console.log(this.errorMessage);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }

    },

    /**
     * Update the user password
     */
    async updatePassword() {
      try {
        // call the updatePassword method with the current password, new password and confirm password
        const response = await updatePassword(this.user_id, this.currentPassword, this.newPassword, this.confirmPassword)
        if (response) {
          // If the response is successful logout the user and redirect to the login page
          this.logoutUser()
          await router.push('/login')
        } else {
          console.log(response)
        }
      } catch (error) {
        if (error instanceof Error) {
          this.errorMessage = error.message;
          console.log(this.errorMessage);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }

    },

    // calls the logout method.
    logoutUser() {
      this.logout()
    },

  },

  // Fetch the user data and all users when the component is mounted
  mounted() {
    // Redirect to login if the user is not logged in
    if (getCookie('userId') === null) {
      this.$router.push('/')
    }

    // Fetch the user data
    this.fetchUser()
  },

  setup() {
    const tokenVerification = inject<{ logout: () => void }>('TokenVerification');

    if (!tokenVerification) {
      console.error('TokenVerification instance not found');
    }

    const logout = () => {
      if (tokenVerification && typeof tokenVerification.logout === 'function') {
        tokenVerification.logout();
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
}

</script>

<template>
  <div class="background">
    <div class="container">
      <div class="profile-container row justify-content-between">
        <!-- Profile Section -->
        <div class="profile-section col-md-4 text-center mb-4">
          <!--        <img src="" alt="Profile Picture">-->
          <h2 class="mt-2">{{ username }}</h2>
          <!--        <button class="btn btn-secondary mt-2">Edit profile picture</button>-->
          <div class="mt-3">
            <button class="btn btn-green mb-2">Add Friend</button>
            <button class="btn btn-red mb-2">Direct Message</button>
          </div>
        </div>
        <!-- Information Section -->
        <div class="info-section col-md-7">
          <h3>Profile Information</h3>

          <!-- Update Account Section -->
          <div class="account-section">
            <h4>Update Account Information</h4>
            <div class="form-group">
              <label>Username:</label>
              <input v-if="isEditing" v-model="username" type="text" class="form-control"/>
              <span v-else>{{ username }}</span>
            </div>

            <div class="form-group">
              <label>Email:</label>
              <input v-if="isEditing" v-model="email" type="email" class="form-control"/>
              <span v-else>{{ email }}</span>
            </div>

            <div class="mt-4">
              <button class="btn btn-green me-3" @click="toggleEdit" v-if="!isEditing">Change Profile</button>

              <div v-if="isEditing">
                <button class="btn btn-green me-3" @click="saveChanges">Save</button>
                <button class="btn btn-secondary" @click="toggleEdit">Cancel</button>
              </div>
            </div>
          </div>

          <!-- Change Password Section -->
          <div class="password-section mt-4">
            <h4>Change Password</h4>
            <strong v-if="!isChangingPassword">Password: ***********</strong>
            <div v-if="isChangingPassword">
              <div class="form-group">
                <label>Current Password:</label>
                <input v-model="currentPassword" type="password" class="form-control"/>
              </div>
              <div class="form-group">
                <label>New Password:</label>
                <input v-model="newPassword" type="password" class="form-control"/>
                <small class="text-muted">{{ passwordStrength }}</small>
              </div>
              <div class="form-group">
                <label>Confirm New Password:</label>
                <input v-model="confirmPassword" type="password" class="form-control"/>
              </div>
            </div>
            <div class="mt-3">
              <button class="btn btn-green me-3" @click="toggleChangePassword" v-if="!isChangingPassword">change
                password
              </button>
              <button class="btn btn-green me-3" @click="updatePassword" v-if="isChangingPassword">Save Password
              </button>
              <button class="btn btn-secondary" @click="toggleChangePassword" v-if="isChangingPassword">Cancel</button>
            </div>
          </div>


          <!-- Delete Profile Button -->
          <button class="btn btn-red" @click="deleteUser">Delete Profile</button>

          <!-- Admin Section -->
          <div v-if="role === 'ADMIN'">
            <br>
            <button class="btn btn-green" @click="fetchAllUsers = !fetchAllUsers">See all users</button>
            <br>
            <br>
            <button class="btn btn-green" @click="createNewUser = !createNewUser"> Toggle Create New User</button>
            <br>
            <br>
            <button class="btn btn-green" @click="createNewArticle = !createNewArticle"> Toggle Create New Article
            </button>
            <br>
            <br>
            <button class="btn btn-green" @click="toggleArticle = !toggleArticle"> Toggle to view, edit and delete
              articles
            </button>


          </div>

        </div>
      </div>
      <div class="admin-section" v-if="role === 'ADMIN'">
        <AllUsersAdmin v-if="fetchAllUsers"/>
        <CreateUserAdmin v-if="createNewUser"/>
        <CreateArticleAdmin v-if="createNewArticle"/>
        <ArticlesAdmin v-if="toggleArticle"/>
      </div>
    </div>
  </div>
</template>


<style scoped>
.background {
  background-color: #24786e99;
  padding: 20px;
  min-height: 60vh;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: wrap;
}

body {
  background-color: #d1e7e7;
}

.profile-container {
  padding: 20px;
  border-radius: 10px;
  margin-top: 50px;
}

.account-section, .password-section {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.account-section h4, .password-section h4 {
  margin-bottom: 15px;
  font-weight: bold;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  font-weight: bold;
  display: block;
  margin-bottom: 5px;
}

.form-control {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14px;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
}

.form-control:focus {
  border-color: #8ef78f;
  outline: none;
  box-shadow: 0 0 5px rgba(142, 247, 143, 0.5);
}

.btn-green {
  background-color: #28a745;
  border: none;
  color: white;
}

.btn-red {
  background-color: orangered;
  color: white;
  border: none;
}

.btn-green:hover {
  background-color: #218838;
}

.btn-red:hover {
  background-color: darkred;
}
</style>