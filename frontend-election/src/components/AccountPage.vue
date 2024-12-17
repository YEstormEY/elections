<script lang="ts">
import {User} from '@/class/User'
import {authState} from '@/utils/auth'
import router from '@/router/router'
import {
  createArticle,
  createUser,
  deleteUserAdmin,
  fetchAllUsers,
  getCategories,
  saveUserChanges
} from '@/service/AdminService'
import article from '@/components/Article.vue'
import {deleteUser, fetchUser, saveChanges, updatePassword} from '@/service/UserService'
import {getCookie} from "@/utils/cookie/GetCookie";
import {inject} from "vue";
import {deleteCookie} from "@/utils/cookie/DeleteCookie";

export default {
  computed: {
    article() {
      return article
    },
    passwordStrength() {
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
      user_id: '',
      username: '',
      email: '',
      password: '',
      role: '',
      isEditing: false,
      isChangingPassword: false, // New flag for password change form
      currentPassword: '', // Current password input
      newPassword: '', // New password input
      confirmPassword: '', // Confirm new password input
      //admin section
      users: [], // New array to store all users
      editUser: null, // Store the user being edited
      editedUser: {
        userId: '',
        username: '',
        email: '',
        role: ''
      }, // Store the modified user data
      newUser: {
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
        role: ''
      },
      newArticle: {
        title: '',
        summary: '',
        content: '',
        image_path: '',
        categoryId: []
      }, // Store the new article data// Store the new user data
      categories: [],
      createNewArticle: false,
      createNewUser: false
    }
  },

  methods: {

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
        console.error('Error during user data fetch:', error)
      }
    },

    async deleteUser() {
      if (confirm('Are you sure you want to delete your account?')) {
        try {
          const response = await deleteUser()
          if (response) {
            const removeCookie = deleteCookie;
            removeCookie('token');
            removeCookie('userId');
          }
        } catch (error) {
          console.error('Error during user delete:', error)
        }
      }
    },

    toggleEdit() {
      this.isEditing = !this.isEditing
    },

    toggleChangePassword() {
      this.isChangingPassword = !this.isChangingPassword
    },

    async saveChanges() {
      try {
        const response = await saveChanges(this.user_id, this.username, this.email)
        if (response) {
          console.log(response)

          const token = response.token
          const tokenExpiry = response.tokenExpiry

          document.cookie = `token=${token}; expires=${new Date(tokenExpiry).toUTCString()}; path=/`;
          document.cookie = `userId=${userId}; expires=${new Date(tokenExpiry).toUTCString()}; path=/`;

          this.isEditing = false
        }
      } catch (error) {
        this.errorMessage = error.message
        console.log(this.errorMessage)
      }
    },

    async updatePassword() {

        try {
          const response = await updatePassword(this.user_id, this.currentPassword, this.newPassword, this.confirmPassword)
          if (response) {
            this.logoutUser()
            await router.push('/login')
          } else {
            console.log(response)
          }
        } catch (error) {
          this.errorMessage = error.message
          console.log(this.errorMessage)
        }
    },

    logoutUser() {
      this.logout()
    },

    async fetchAllUsers() {
      if (!this.role === 'ADMIN') {
        return
      }
      try {
        const response = await fetchAllUsers()
        if (response) {
          this.users = response
          console.log(response)
        }
      } catch (error) {
        this.errorMessage = error.message
        console.log(this.errorMessage)
      }
    },

    async saveUserChanges() {
      if (!this.role === 'ADMIN') {
        return
      }
      console.log(this.editedUser)
      try {
        const response = await saveUserChanges(this.editedUser)
        await this.fetchUser()
        await this.fetchAllUsers()
        this.cancelEdit()


        if (response) {
          console.log(response)
        }
      } catch (error) {
        this.errorMessage = error.message
        console.log(this.errorMessage)
      }
    },

    //toggle edit mode
    startEdit(user) {
      this.editUser = user.userId // Mark the current user for editing
      this.editedUser = {...user} // Copy the user data to be edited
    },

    // Cancel editing
    cancelEdit() {
      this.editUser = null // Exit edit mode without saving
    },

    async deleteUserAdmin(userId) {
      if (!this.role === 'ADMIN') {
        return
      }
      if (confirm('Are you sure you want to delete this user?')) {
        try {

          const response = await deleteUserAdmin(userId)
          await this.fetchAllUsers()
          if (response) {
            console.log(response)
          }
        } catch (error) {
          this.errorMessage = error.message
          console.log(this.errorMessage)
        }
      }
    },

    async createUser() {
      if (!this.role === 'ADMIN') {
        return
      }
      console.log(this.newUser)
      try {
        const response = await createUser(this.newUser)
        if (response) {
          console.log(response)
        }
      } catch (error) {
        this.errorMessage = error.message
        console.log(this.errorMessage)
      }
    },

    async createArticle() {
      console.log(this.newArticle)
      try {
        const response = await createArticle(this.newArticle) // Declare response properly
        if (response) {
          console.log(response)
        }
      } catch (error) {
        this.errorMessage = error.message
        console.log(this.errorMessage)
      }
    },

    async getCategories() {
      try {
        const response = await getCategories()
        if (response) {
          this.categories = response
          console.log(response)
        }
      } catch (error) {
        this.errorMessage = error.message
        console.log(this.errorMessage)
      }
    }
  },

  mounted() {

    if (getCookie('userId') === null) {
      this.$router.push('/')
    }

    this.fetchAllUsers()

    this.fetchUser()
    this.getCategories()
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
              <input v-if="isEditing" v-model="username" type="text" class="form-control" />
              <span v-else>{{ username }}</span>
            </div>

            <div class="form-group">
              <label>Email:</label>
              <input v-if="isEditing" v-model="email" type="email" class="form-control" />
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
              <input v-model="currentPassword" type="password" class="form-control" />
            </div>
            <div class="form-group">
              <label>New Password:</label>
              <input v-model="newPassword" type="password" class="form-control" />
              <small class="text-muted">{{ passwordStrength }}</small>
            </div>
            <div class="form-group">
              <label>Confirm New Password:</label>
              <input v-model="confirmPassword" type="password" class="form-control" />
            </div>
            </div>
            <div class="mt-3">
              <button class="btn btn-green me-3" @click="toggleChangePassword" v-if="!isChangingPassword">change password</button>
              <button class="btn btn-green me-3" @click="updatePassword" v-if="isChangingPassword">Save Password</button>
              <button class="btn btn-secondary" @click="toggleChangePassword" v-if="isChangingPassword">Cancel</button>
            </div>
          </div>


            <!-- Delete Profile Button -->
            <button class="btn btn-red" @click="deleteUser">Delete Profile</button>

            <!-- Admin Section -->
            <div v-if="role === 'ADMIN'">
              <h3>Admin Section</h3>
              <button class="btn btn-green" @click="fetchAllUsers">Refresh Users</button>

              <!-- Table to display all users -->
              <div class="table-responsive mt-3">
                <table class="table table-striped">
                  <thead>
                  <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Actions</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr v-for="user in users" :key="user.userId">
                    <td>{{ user.userId }}</td>

                    <!-- Edit mode: show input fields -->
                    <td v-if="editUser === user.userId">
                      <input v-model="editedUser.username"/>
                    </td>
                    <td v-else>{{ user.username }}</td>

                    <td v-if="editUser === user.userId">
                      <input v-model="editedUser.email"/>
                    </td>
                    <td v-else>{{ user.email }}</td>

                    <td v-if="editUser === user.userId">
                      <select v-model="editedUser.role">
                        <option value="USER">USER</option>
                        <option value="ADMIN">ADMIN</option>
                      </select>
                    </td>
                    <td v-else>{{ user.role }}</td>

                    <!-- Actions: edit/save/cancel -->
                    <td>
                      <div v-if="editUser === user.userId">
                        <button @click="saveUserChanges()">Save</button>
                        <button @click="cancelEdit">Cancel</button>
                      </div>
                      <div v-else>
                        <button @click="startEdit(user)">Edit</button>
                        <button @click="deleteUserAdmin(user.userId)">Delete</button>
                      </div>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>

              <form class="user-form mt-4" v-if="createNewUser">
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

              <form class="article-form mt-4" v-if="createNewArticle">
                <div class="form-group mb-3">
                  <label for="title">Title:</label>
                  <input type="text" id="title" v-model="newArticle.title" class="form-control"/>
                </div>

                <div class="form-group mb-3">
                  <label for="summary">Summary:</label>
                  <input type="text" id="summary" v-model="newArticle.summary" class="form-control"/>
                </div>

                <div class="form-group mb-3">
                  <label for="content">Content:</label>
                  <textarea id="content" v-model="newArticle.content" class="form-control"></textarea>
                </div>

                <div class="form-group mb-3">
                  <label for="imagePath">Image Path:</label>
                  <input type="text" id="imagePath" v-model="newArticle.image_path" value="localhost:8080/img/"
                         class="form-control"/>
                </div>

                <div class="form-group mb-3">
                  <label>Categories:</label>
                  <div>
                    <div v-for="category in categories" :key="category.categoryId" class="form-check">
                      <input
                          class="form-check-input"
                          type="checkbox"
                          :id="'category-' + category.categoryId"
                          :value="category.categoryId"
                          v-model="newArticle.categoryId"
                      >
                      <label class="form-check-label" :for="'category-' + category.categoryId">
                        {{ category.name }}
                      </label>
                    </div>
                  </div>
                </div>

                <div class="form-group mt-4">
                  <button class="btn btn-green me-3" @click.prevent="createArticle">Create Article</button>
                </div>
              </form>

              <br>

              <button class="btn btn-green" @click="createNewUser = !createNewUser"> Toggle Create New User</button>
              <br>
              <br>
              <button class="btn btn-green" @click="createNewArticle = !createNewArticle"> Toggle Create New Article
              </button>


            </div>

          </div>
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
  background-color: #8ef78f;
  border: none;
  color: black;
}

.btn-red {
  background-color: #fa5f5f;
  border: none;
  color: white;
}

/* Admin Section */

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

.user-form .btn-green {
  background-color: #8ef78f;
  color: black;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.user-form .btn-green:hover {
  background-color: #7dd67e;
}
</style>