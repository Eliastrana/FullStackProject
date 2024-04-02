<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const quizzes = ref([]);

// Dummy function for editing quizzes
const deleteQuiz = (id) => {
  alert(`Delete quiz with ID: ${id}`);
  // Implementation for editing a quiz goes here
};

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

  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

  <div class="quizzes-container">
    <div class="headings">
      <h1>All Private Quizzes</h1>
      <h2>Monitor all users' quizzes</h2>
    </div>
    <div class="quizzes">
      <div class="quiz" v-for="(quiz, index) in quizzes" :key="quiz.id">
        <div class="quiz-info">
          <img :src="quiz.image" :alt="quiz.title" class="quiz-image"/>
          <div class="quiz-text">
            <h3>{{quiz.title }}</h3>
            <h3>Creator: {{ quiz.creator }}</h3>
            <p>{{ quiz.description }}</p>
            <p class="category-badge">#{{ quiz.category }}</p>
            <p>Questions: <strong>{{ quiz.questions.length }}</strong></p>
          </div>

          <div @click="deleteQuiz(quiz.id)" class="delete-icon">
            <span class="material-symbols-outlined">delete</span>
          </div>



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
  width: fit-content;
  min-width: 45%;
  padding: 20px;
  margin-top: 5%;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  background-color: #ececec;
}

.delete-icon {
  cursor: pointer;
  width: 20px; /* Adjust icon size as needed */
  height: 20px; /* Adjust icon size as needed */
  position: absolute;
  top: 20px;
  right: 20px;
  fill: #4a5568; /* Icon color */
}

.delete-icon:hover {
  fill: rgba(0, 0, 0, 0.8); /* Icon color on hover */
}

.quizzes {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
}

.quiz-info {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: -10px;
}

.quiz-image {
  width: 100px; /* Adjust the image size as needed */
  height: fit-content;
  border-radius: 8px;
}

.quiz-text {
  flex: 1;
}


.category-badge {
  display: inline-block; /* Treat the <p> tag more like an inline element */
  background-color: rgb(23, 22, 22); /* Example background color */
  color: #ffffff; /* Text color */
  padding: 5px 15px; /* Vertical and horizontal padding */
  border-radius: 20px; /* Rounded corners */
  font-size: 0.8rem; /* Adjust font size as needed */
  margin: 0; /* Remove default <p> margin if needed */
}

.quiz {
  background-color: #fafafa;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  transition: transform 0.3s;
  position: relative; /* This is necessary to position the edit icon correctly */

}

.quiz:hover {
  transform: translateY(-5px);
  background-color: #f0f0f0;
}

h1 {
  font-family: 'DM Sans', sans-serif;
}

h2 {
  font-family: 'DM Sans', sans-serif;
  color: #3232ff;
}

.headings {
  align-self: stretch; /* Makes the headings container take the full width */
  text-align: left; /* Aligns the text to the left */
}
</style>
