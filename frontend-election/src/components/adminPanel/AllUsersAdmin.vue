<script lang="ts">
import {defineComponent} from 'vue';
import {
  deleteUserAdmin,
  fetchAllUsers,
  saveUserChanges,
  searchUser,
} from '@/service/AdminService';

interface User {
  userId: string;
  username: string;
  email: string;
  role: string;
}

export default defineComponent({
  name: 'AllUsersAdmin',

  data() {
    return {
      users: [] as User[], // Type the users array
      editedUser: {
        userId: '',
        username: '',
        email: '',
        role: '',
      } as User,
      editUser: null, // ID of the user being edited
      searchUserInput: '', // Input for user search
    };
  },

  methods: {
    /**
     * Fetch all users from the backend
     */
    async fetchAllUsers() {
      try {
        const response = await fetchAllUsers();
        if (response) {
          this.users = response;
          console.log(response);
        }
      } catch (error) {
        if (error instanceof Error) {
          console.error('Error fetching users:', error.message);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }
    },

    /**
     * Save the changes made to a user
     */
    async saveUserChanges() {
      try {
        const response = await saveUserChanges(this.editedUser);
          console.log('User updated:', response);
          await this.fetchAllUsers();
          this.cancelEdit();
      } catch (error) {
        if (error instanceof Error) {
          console.error('Error fetching users:', error.message);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }
    },

    /**
     * Start editing a user
     * @param user - The user to edit
     */
    startEdit(user: any) {
      this.editUser = user.userId;
      this.editedUser = { ...user };
    },

    /**
     * Cancel editing
     */
    cancelEdit() {
      this.editUser = null;
      this.editedUser = {
        userId: '',
        username: '',
        email: '',
        role: '',
      };
    },

    /**
     * Delete a user by ID
     * @param userId - ID of the user to delete
     */
    async deleteUserAdmin(userId: string) {
      if (confirm('Are you sure you want to delete this user?')) {
        try {
          await deleteUserAdmin(Number(userId));
          await this.fetchAllUsers();
        } catch (error) {
          if (error instanceof Error) {
            console.error('Error fetching users:', error.message);
          } else {
            console.error('An unknown error occurred:', error);
          }
        }
      }
    },

    /**
     * Search for users
     */
    async searchUsers() {
      try {
        const response = await searchUser(this.searchUserInput);
        if (response) {
          this.users = response;
        }
      } catch (error) {
        if (error instanceof Error) {
          console.error('Error fetching users:', error.message);
        } else {
          console.error('An unknown error occurred:', error);
        }
      }
    },
  },

  mounted() {
    this.fetchAllUsers();
  },

});
</script>

<template>
  <div class="user-management">
    <div class="header">
      <h3>Admin - Manage Users</h3>
      <div class="actions">
        <input
            v-model="searchUserInput"
            type="text"
            placeholder="Search users..."
            class="form-control search-input"
        />
        <button class="btn btn-green" @click="searchUsers">Search</button>
        <button class="btn btn-green" @click="fetchAllUsers">Refresh</button>
      </div>
    </div>

    <div class="table-container">
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
          <td v-if="editUser === user.userId">
            <input v-model="editedUser.username" class="form-control" />
          </td>
          <td v-else>{{ user.username }}</td>
          <td v-if="editUser === user.userId">
            <input v-model="editedUser.email" class="form-control" />
          </td>
          <td v-else>{{ user.email }}</td>
          <td v-if="editUser === user.userId">
            <select v-model="editedUser.role" class="form-control">
              <option value="USER">USER</option>
              <option value="ADMIN">ADMIN</option>
            </select>
          </td>
          <td v-else>{{ user.role }}</td>
          <td>
            <div v-if="editUser === user.userId">
              <button class="btn btn-green" @click="saveUserChanges">Save</button>
              <button class="btn btn-secondary" @click="cancelEdit">Cancel</button>
            </div>
            <div v-else>
              <button class="btn btn-green" @click="startEdit(user)">Edit</button>
              <button class="btn btn-red" @click="deleteUserAdmin(user.userId)">Delete</button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
.user-management {
  background-color: #f5f5f5;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.header {
  display: flex;
  flex-wrap: wrap; /* Allow wrapping for small screens */
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  gap: 10px;
}

.actions {
  display: flex;
  flex-wrap: wrap; /* Wrap buttons on smaller screens */
  gap: 10px;
}

.search-input {
  width: 200px;
}

.table-container {
  margin-top: 20px;
  overflow-x: auto; /* Add horizontal scroll for small screens */
}

.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: 10px;
  text-align: left;
}

.table th {
  background-color: #f0f0f0;
}

.table-striped tbody tr:nth-child(odd) {
  background-color: #f9f9f9;
}

/* Button Styling */
.btn {
  padding: 10px 15px;
  margin: 10px;
  font-size: 0.9rem;
  border-radius: 5px;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-green {
  background-color: #28a745;
  color: white;
}

.btn-green:hover {
  background-color: #218838;
}

.btn-red {
  background-color: orangered;
  color: white;
}

.btn-red:hover {
  background-color: darkred;
}

.btn-secondary {
  background-color: #d6d6d6;
  color: black;
}

/* Responsive Adjustments */
@media (max-width: 768px) {
  .header {
    flex-direction: column; /* Stack elements vertically */
    align-items: flex-start;
    gap: 15px;
  }

  .search-input {
    width: 100%; /* Full width for inputs */
  }

  .actions {
    width: 100%; /* Wrap buttons and align them properly */
    flex-direction: column;
    align-items: flex-start;
  }

  .table-container {
    overflow-x: auto; /* Scroll horizontally on small screens */
  }

  .btn {
    width: 100%; /* Full-width buttons on small screens */
    text-align: center;
  }
}

</style>
