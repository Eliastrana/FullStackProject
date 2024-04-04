<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const userInfo = ref(null);

onMounted(async () => {
  try {
    // Update the path to where your user.json file is located
    const response = await axios.get('/mockJSON/user/user.json');
    userInfo.value = response.data;
  } catch (error) {
    console.error('Failed to load user info:', error);
  }
});
</script>

<template>
  <div class="user-info-container" v-if="userInfo">
    <h1>Your Info</h1>
    <h2>This is you</h2>
    <img :src="userInfo.photo" alt="User photo" class="user-photo"/>
    <div class="basic-info">
      <p><strong>Name:</strong> {{ userInfo.name }}</p>
      <p><strong>Email:</strong> {{ userInfo.email }}</p>
    </div>
    <div class="level-info">
      <p><strong>Level:</strong> <span class="highlight">{{ userInfo.level }}</span></p>
      <p><strong>Total Quizzes Done:</strong> <span class="highlight">{{ userInfo.totalQuizzesDone }}</span></p>
    </div>
    <div class="achievements">
      <h3>Achievements:</h3>
      <ul>
        <li v-for="achievement in userInfo.achievements" :key="achievement.title">
          <strong>{{ achievement.title }}:</strong> {{ achievement.description }}
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
.user-info-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 90%; /* Allows container to scale but not exceed the screen width */
  margin: 5% auto; /* Centers the container and maintains margin from the top */
  padding: 20px;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  background-color: #ececec;
}

.basic-info, .level-info, .achievements {
  width: 100%;
  max-width: 600px; /* Ensures content within these divs doesn't become too wide */
  text-align: center;
  margin: 10px auto; /* Centers content blocks */
}

h1, h2, .highlight {
  font-family: 'DM Sans', sans-serif;
}

h2 {
  color: #3232ff;
}

.user-photo {
  width: 100px; /* Consider making this responsive with a percentage or max-width */
  height: 100px; /* Maintain aspect ratio */
  border-radius: 50%;
  margin: 20px 0;
}

.highlight {
  font-size: 1.2em;
  color: #4a5568;
}

.achievements ul {
  list-style-type: none;
  padding: 0;
}

.achievements li {
  background-color: #f0f0f0;
  margin: 5px 0;
  padding: 10px;
  border-radius: 8px;
  box-shadow: 0 1px 2px rgba(0,0,0,0.1);
}

@media (max-width: 768px) {
  .user-info-container {
    width: auto; /* Allows the container to fill the screen width on smaller devices */
    margin: 5% 10px; /* Adds a small margin to the sides */
    padding: 10px; /* Adjusts padding for smaller screens */
  }

  .user-photo {
    width: 80px; /* Slightly smaller photo for smaller screens */
    height: 80px;
  }

  .highlight {
    font-size: 1em; /* Adjusts font size for smaller screens */
  }
}
</style>


