<script setup>
import { ref, onMounted } from 'vue';
import { UserService } from '@/services/UserService.js';
import { RoleService } from '@/services/RoleService.js'

const users = ref([]);

const fetchUsers = async () => {
  try {
    const allUsers = await UserService.getAllUsers();
    users.value = allUsers;
    console.log('User data loaded:', users.value);
  } catch (error) {
    console.error('Failed to load user data:', error);
  }
};

const deleteUser = async (username) => {
  console.log('Deleting user with username:', username);
  await UserService.deleteUser(username);
  await fetchUsers();
};

const giveAdmin = async (username) => {
  console.log('Giving admin to user with username:', username);
  await RoleService.asignRoleToUser(username, 'ADMIN');
  await fetchUsers();
};

onMounted(fetchUsers);

</script>

<template>
  <div class="user-container">
    <div class="headings">
      <h1>All User Information</h1>
      <h2>View all users</h2>
    </div>
    <div v-for="user in users" :key="user.id" class="profile">
      <h3>Username: {{ user.username }}</h3>
      <p>User ID: {{ user.id }}</p>
      <h4>Email: {{ user.email }}</h4>

      <div class="action-icons">
        <div @click="deleteUser(user.username)" class="delete-icon">
          <span class="material-symbols-outlined">delete</span>
        </div>
        <div @click="giveAdmin(user.username)" class="block-icon">
          <span class="material-symbols-outlined">admin_panel_settings</span>
        </div>
      </div>
    </div>
  </div>
</template>




<style scoped>

/* Base styles for headings */
h1, h2 {
  font-family: 'DM Sans', sans-serif;
}

h2 {
  color: #3232ff;
}

/* Main container for user profiles */
.user-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  margin-left: 1%;
  max-width: 90%;
  padding: 20px;
  background-color: #ececec;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
}

/* Individual user profile card */
.profile {
  position: relative;
  width: 100%;
  padding: 20px;
  margin-bottom: 20px;
  background-color: #fafafa;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  transition: transform 0.3s ease;
}

.profile:hover {
  transform: translateY(-5px);
  background-color: #f0f0f0;
}

/* Flex container for action icons */
.action-icons {
  position: absolute;
  top: 10px;
  right: 10px;
  display: flex;
  gap: 10px; /* Adjust gap between icons */
}

/* Styling for both delete and block icons */
.delete-icon, .block-icon {
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 24px; /* Adjust based on actual size */
  height: 24px; /* Adjust based on actual size */
  fill: #4a5568; /* Icon color */
}

.delete-icon:hover, .block-icon:hover {
  fill: rgba(0, 0, 0, 0.8); /* Icon hover color */
}

/* Material icons */
.material-symbols-outlined {
  font-size: 24px; /* Adjust based on preference */
  color: inherit; /* Ensures icon color matches parent */
}

</style>


