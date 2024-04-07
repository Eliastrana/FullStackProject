<script setup>
import { ref, onMounted } from 'vue';
import { AttemptService } from '@/services/AttemptService.js';
import store from '@/store/index.js'
import { QuestionService as questionService } from '@/services/QuestionService.js';

/**
 * Quiz attempts data
 * @type {import('vue').Ref<Array>}
 */
const quizAttempts = ref([]);

const userInfo = ref(null);

const totalQuizzesDone = ref(0); // New ref for storing total quizzes done




/**
 * Fetches quiz attempts data from the API when the component is mounted
 */
onMounted(async () => {
  try {
    const userId = store.getters['user/userId'];
    if (!userId) {
      console.error('UserId is undefined. Make sure the user is logged in.');
      return;
    }

    let attempts = await AttemptService.getAttemptByUserId(userId);

    // Fetch questions count for each attempt
    attempts = await Promise.all(attempts.map(async (attempt) => {
      const questionsCount = await questionService.getQuestionsByQuizId(attempt.quizId).then(response => response.length).catch(error => {
        console.error('Failed to load questions for quiz:', error);
        return 0; // Default to 0 if failed to fetch
      });
      return { ...attempt, questionsCount };
    }));

    quizAttempts.value = attempts;
    totalQuizzesDone.value = attempts.length;

    store.dispatch('quizAttempt/updateTotalQuizzesDone', totalQuizzesDone.value);
  } catch (error) {
    console.error('Failed to load quiz attempts:', error);
  }
});
</script>


<template>
  <div class="achievements-container">
  <div class="attempts-container">
    <h1>Your Quiz Attempts</h1>
    <h2>Review your progress</h2>
    <div class="tiles">
      <div class="tile" v-for="(attempt, index) in quizAttempts" :key="index">

<!--        <h2>{{userInfo.username}}</h2>-->
        <h3>{{ attempt.quizTitle}}</h3>
        <h4>Correct answers: {{attempt.score}}</h4>
        <div class="progress-bar-container">
          <div class="progress-bar" :style="{ width: (attempt.score / attempt.questionsCount) * 100 + '%' }"></div>
        </div>
      </div>
    </div>
  </div>

  </div>
</template>


<style scoped>
h1, h2 {
  font-family: 'DM Sans', sans-serif;
  text-align: center;
}

h2 {
  color: #3232ff;
  margin-top: -20px;
}

.achievements-container {
  max-width: 800px; /* or your desired width */
  margin-top: 2%;
  margin-right: auto;
  margin-left: auto;
  display: block; /* Default, but explicitly stated for clarity */
  padding: 20px;
  /* other styles */

  flex-direction: column; /* Align children vertically */
  align-items: stretch; /* Stretch items to fill the horizontal space */
  min-width: 96%;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
}

.tiles {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); /* Adjust based on content */
  gap: 20px;
  width: 100%; /* Fill the width of its parent */
  margin: 0; /* Remove any default margins */
}

.tile {
  background-color: #fafafa;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  transition: transform 0.3s, background-color 0.3s;
}

.tile:hover {
  transform: translateY(-5px);
  background-color: #f0f0f0;
}

.progress-bar-container {
  background-color: #e0e0e0;
  border-radius: 10px;
  overflow: hidden;
  width: 100%;
}

.progress-bar {
  background-color: #4caf50;
  height: 10px;
  border-radius: 20px;
}

@media (max-width: 480px) {
  .tiles {
    grid-template-columns: 1fr; /* Ensure single column layout on smaller screens */
  }

  h1, h2 {
    margin: 10px; /* Adjust margins for smaller screens */
  }
}
</style>


