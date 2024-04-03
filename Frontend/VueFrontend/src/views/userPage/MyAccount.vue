<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios';
import PersonalStatisticsView from '@/views/userPage/myAccountComponents/PersonalStatisticsView.vue'
import PersonalInfoView from '@/views/userPage/myAccountComponents/PersonalInfoView.vue'
import PersonalQuizzesView from '@/views/userPage/myAccountComponents/PersonalQuizzesView.vue'
import store from '@/store/index.js'

const userName = computed(() => store.state.user.userInfo ? store.state.user.userInfo.username : '');


// Example user state
const user = ref({
  name: 'Loading...', // Default name, can be changed based on your initial state
});

onMounted(async () => {
  try {
    // Example: Fetch user information from an API
    const response = await axios.get('/api/user');
    user.value = response.data; // Assuming response.data contains user object with a name property
  } catch (error) {
    console.error('Failed to load user info:', error);
  }
});
</script>

<template>
  <h1>Welcome {{userName}}!</h1>
  <h2>Here you can view your private quizzes, statistics, and info</h2>
  <div class="container">
    <PersonalQuizzesView class="personal-quizzes"/>
    <PersonalStatisticsView class="personal-statistics"/>
    <PersonalInfoView class="personal-info"/>
  </div>
</template>



<style scoped>

h1 {
  font-family: 'DM Sans', sans-serif;
  text-align: left;
  margin-top: 20px;
  margin-left: 40px;
  font-size: 4rem;
}

h2 {
  font-family: 'DM Sans', sans-serif;
  text-align: left;
  margin-left: 40px;
  margin-right: 2%;
  font-size: 2rem;
}

.container {
  margin-top: -5%;
  display: flex;
  flex-wrap: wrap; /* Allows items to wrap as needed */
  justify-content: space-around; /* Distributes space around items */
  align-items: flex-start; /* Aligns items at the top */
  gap: 20px; /* Space between items */
}

/* Targets PersonalStatisticsView and PersonalInfoView components */
.personal-statistics, .personal-info {
  flex-basis: calc(47% - 20px); /* Adjusts width to sit side-by-side, accounting for gap */
  max-width: calc(47% - 20px); /* Ensures components do not exceed half the container's width */

}

/* Targets PersonalQuizzesView component */
.personal-quizzes {
  flex-basis: 100%; /* Allows this component to take the full width */
  max-width: 100%; /* Ensures the component spans the entire container widt h */
  margin-bottom: -3%;
}

/* Adjustments for smaller screens */
@media (max-width: 768px) {
  .personal-statistics, .personal-info, .personal-quizzes {
    flex-basis: 100%; /* Stacks all children vertically on smaller screens */
    max-width: 100%; /* Each child takes up the full width of the container */
  }
}
</style>

