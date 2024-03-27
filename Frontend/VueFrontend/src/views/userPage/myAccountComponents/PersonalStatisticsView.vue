<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios'; // Assuming you're using Axios for HTTP requests

const achievements = ref([]);

onMounted(async () => {
  try {
    const response = await axios.get('/mockJSON/statistics/achievements/achievements.json');
    achievements.value = response.data;
  } catch (error) {
    console.error('Failed to load achievements:', error);
  }
});
</script>

<template>
  <div class="achievements-container">
    <h1>Your statistics</h1>
    <h2>Keep working hard!</h2>

    <!-- Tiles Container -->
    <div class="tiles">
      <!-- Dynamically rendered Achievement Tiles -->
      <div class="tile" v-for="(achievement, index) in achievements" :key="index"
           :class="{'gold-background': achievement.progress === 100}">
        <h3>{{ achievement.title }}</h3>
        <p>{{ achievement.description }}</p>
        <div class="progress-bar-container">
          <div class="progress-bar" :style="{ width: achievement.progress + '%' }"></div>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>
h1, h2 {
  font-family: 'DM Sans', sans-serif;
}

h2 {
  color: #3232ff;
  margin-top: -20px;

}

.achievements-container {

  background-color: #ececec;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  width: fit-content;
  padding: 20px;
  margin-top: 5%;
  margin-left: 2%;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
}


.tiles {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  width: 100%;
}

.tile {
  background-color: #fafafa;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);

}

.tile:hover {
  transform: translateY(-5px);
  transition: transform 0.3s;
  background-color: #f0f0f0;
}

.progress-bar-container {
  background-color: #e0e0e0;
  border-radius: 10px;
  overflow: hidden;
}

.progress-bar {
  background-color: #4caf50;
  height: 10px;
  border-radius: 20px;

}


.gold-background {
  background-color: gold !important; /* Use important to override other background color styles if necessary */
  border: 4px solid goldenrod;
}

.gold-background:hover {
  background-color: goldenrod !important; /* Use important to override other background color styles if necessary */
}
</style>