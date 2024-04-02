<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

// Change user to users and initialize as an empty array
const users = ref([]);

const deleteUser = (userId) => {
  alert(`Delete user with ID: ${userId}`);
  // Implement deletion logic here
  // This could involve filtering out the deleted user from the users array
  // Or making an API call to delete the user on the server
};


onMounted(async () => {
  try {
    // Adjust the endpoint if needed. Assuming this endpoint returns the JSON structure you provided
    const response = await axios.get('/mockJSON/user/profile.json');
    users.value = response.data.users; // Update based on your actual data structure
  } catch (error) {
    console.error('Failed to load user data:', error);
  }
});
</script>



<template>
  <div class="user-container">
    <div class="headings">

    <h1>All User Information</h1>
      <h2>View all users</h2>

    </div>
    <!-- Iterating over users array to display each user -->
    <div v-for="user in users" :key="user.userId" class="profile">
      <h2>User ID: {{ user.userId }}</h2>
      <h3>Username: {{ user.username }}</h3>
      <h4>Email: {{ user.email }}</h4>
      <h4>Quizzes: {{user.quizzes}} </h4>
      <div @click="deleteUser(user.userId)" class="delete-icon">
        <span class="material-symbols-outlined">delete</span>
      </div>
    </div>
  </div>
</template>



<style scoped>
h1, h2 {
  font-family: 'DM Sans', sans-serif;
}


h2 {
  font-family: 'DM Sans', sans-serif;
  color: #3232ff;

}
.user-container {
  width: 100%; /* Use 100% of the width */
  margin: 5% auto; /* Keep it centered */
  padding: 20px;
  background-color: #ececec;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* Align the container's items to the left */
  max-width: 90%; /* Ensure the container doesn't get too wide */

}

.profile {
  background-color: #fafafa;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
}

.user-stats {
  display: flex;
  gap: 20px;
  margin-top: 10px;
}




.profile {
  /* Existing styles */
  margin-bottom: 20px; /* Add space between user profiles */
}
/* Feel free to adjust or add more styles based on your design */

.profile {
   position: relative; /* Needed for absolute positioning of children */
   background-color: #fafafa;
   padding: 20px;
   border-radius: 8px;
   box-shadow: 0 2px 4px rgba(0,0,0,0.2);
   margin-bottom: 20px; /* Space between profiles */
   width: 95%; /* Ensure it fills the container */
  transition: transform 0.3s;

}


.profile:hover {
  transform: translateY(-5px);
  background-color: #f0f0f0;
}

.delete-icon {
  cursor: pointer;
  position: absolute; /* Position it within the .profile */
  top: 10px; /* Adjust top spacing */
  right: 10px; /* Adjust right spacing */
}

.material-symbols-outlined {
  font-size: 24px; /* Adjust the icon size */
  color: #4a5568; /* Icon color */
}

.material-symbols-outlined:hover {
  color: rgba(0, 0, 0, 0.8); /* Icon hover color */
}



h1, h2 {
  width: 100%; /* Full width to align text properly */
  text-align: left; /* Align the text to the left */
}
</style>


