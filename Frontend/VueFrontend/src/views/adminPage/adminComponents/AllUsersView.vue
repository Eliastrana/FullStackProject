<script setup>
import { ref, onMounted } from 'vue';
import { UserService } from '@/services/UserService.js';
import ConfirmationModal from '@/components/util/ConfirmationModal.vue';

const users = ref([]);
const showConfirmationModal = ref(false);
const pendingDeleteUsername = ref('');


onMounted(async () => {
  await fetchUsers();
});


/**
 * Fetches all users from the API and updates the users ref.
 */
const fetchUsers = async () => {
  users.value = await UserService.getAllUsers();
};

/**
 * Promotes a user to admin status.
 * @param {string} username - The username of the user to promote.
 */
const askDeleteUser = (username) => {
  pendingDeleteUsername.value = username;
  showConfirmationModal.value = true;
};

/**
 * Deletes a user after confirmation.
 */
const confirmDeleteUser = async () => {
  await UserService.deleteUser(pendingDeleteUsername.value);
  await fetchUsers();
  showConfirmationModal.value = false;
};

/**
 * Cancels the user deletion.
 */
const cancelDelete = () => {
  showConfirmationModal.value = false;
};
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
        <div @click="askDeleteUser(user.username)" class="delete-icon">
          <span class="material-symbols-outlined">delete</span>
        </div>
        <div @click="giveAdmin(user.username)" class="block-icon">
          <span class="material-symbols-outlined">admin_panel_settings</span>
        </div>
      </div>
    </div>
    <ConfirmationModal
      :isVisible="showConfirmationModal"
      message="Are you sure you want to delete this user?"
      @confirm="confirmDeleteUser"
      @cancel="cancelDelete"
    />
  </div>
</template>




<style scoped>

h1, h2 {
  font-family: 'DM Sans', sans-serif;
}

h2 {
  color: #3232ff;
}

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

.action-icons {
  position: absolute;
  top: 10px;
  right: 10px;
  display: flex;
  gap: 10px;
}

.delete-icon, .block-icon {
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 24px;
  height: 24px;
  fill: #4a5568;
}

.delete-icon:hover, .block-icon:hover {
  fill: rgba(0, 0, 0, 0.8);
}

/* Material icons */
.material-symbols-outlined {
  font-size: 24px;
  color: inherit;
}

</style>

