<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

/**
 * Quizzes data
 * @type {import('vue').Ref<Array>}
 */
const quizzes = ref([]);

/**
 * Edits a quiz with a given ID
 * @param {number} id - The ID of the quiz to edit
 */
const editQuiz = (id) => {
  alert(`Edit quiz with ID: ${id}`);
};

/**
 * Fetches quizzes data from the API when the component is mounted
 */
onMounted(async () => {
  try {
    const response = await axios.get('/mockJSON/testdata.json');
    quizzes.value = response.data;
  } catch (error) {
    console.error('Failed to load quizzes:', error);
  }
});
</script>

<template>
  <div class="quizzes-container">
    <h1>Private Quizzes</h1>
    <h2>These are your personal quizzes</h2>
    <div class="quizzes">
      <div class="quiz" v-for="(quiz, index) in quizzes" :key="quiz.id">
        <div class="quiz-info">
          <img :src="quiz.image" :alt="quiz.title" class="quiz-image"/>
          <div class="quiz-text">
            <h3>{{ quiz.title }}</h3>
            <p>{{ quiz.description }}</p>
            <p>Category: <strong>{{ quiz.category }}</strong></p>
            <p>Questions: <strong>{{ quiz.questions.length }}</strong></p>
          </div>
          <svg class="edit-icon" @click="editQuiz(quiz.id)" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
            <path d="M17.414 2.586a2 2 0 00-2.828 0L7 10.172V13h2.828l7.586-7.586a2 2 0 000-2.828zM3 17a2 2 0 002 2h12v-2H5v-2H3v2z"/>
          </svg>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

.quizzes-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 800px;
  padding: 20px;
  margin: 5% auto;
  margin-right: 2%;
  margin-left: 2%;

  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  background-color: #ececec;
}

.edit-icon {
  cursor: pointer;
  width: 20px;
  height: 20px;
  position: absolute;
  top: 20px;
  right: 20px;
  fill: #4a5568;
}

.edit-icon:hover {
  fill: rgba(0, 0, 0, 0.8);
}

.quizzes {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
}

.quiz-info {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 20px;
}

.quiz-image {
  width: 100px;
  height: auto;
  border-radius: 8px;
}

.quiz-text {
  flex: 1;
  min-width: 50%;
}

.quiz {
  background-color: #fafafa;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  transition: transform 0.3s;
  position: relative;
}

.quiz:hover {
  transform: translateY(-5px);
  background-color: #f0f0f0;
}

h1, h2 {
  font-family: 'DM Sans', sans-serif;
  text-align: center;
}

h2 {
  color: #3232ff;
  margin-bottom: 20px;
}

@media (max-width: 768px) {
  .quiz-info {
    flex-direction: column;
  }

  .quiz-image {
    width: 80%;
    max-width: 200px;
    margin: 0 auto;
  }
}

</style>

