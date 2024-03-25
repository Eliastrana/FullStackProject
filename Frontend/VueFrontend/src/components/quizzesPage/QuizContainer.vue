// QuizContainer.vue
<template>
  <div class="quiz-container">
    <div v-for="quiz in quizzes" :key="quiz.id" class="quiz-box" @click="handleQuizClick(quiz)">
      <img :src="quiz.image" alt="Quiz Image">
      <h2>{{ quiz.title }}</h2>
      <p>{{ quiz.description }}</p>
      <p><strong>Category:</strong> {{ quiz.category }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

import { defineEmits } from 'vue';

// Assuming QuizContainer directly manages individual quiz items
const emit = defineEmits(['select-quiz']);

const handleQuizClick = (quiz) => {
  emit('select-quiz', quiz);
};

const quizzes = ref([]);

onMounted(async () => {
  try {
    const response = await fetch('mockJSON/testdata.json');
    if (response.ok) {
      quizzes.value = await response.json();
    } else {
      console.error('Failed to load testdata.json', response.status);
    }
  } catch (error) {
    console.error('Error while fetching testdata.json', error);
  }
});
</script>




<style scoped>

.quiz-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px; /* This creates space between your items */
  height: auto;
}



.quiz-box {
  flex: 1; /* Flex-grow set to 1 to allow boxes to grow */
  margin: 10px; /* You can adjust the margin if necessary */
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  min-width: calc(33.33% - 40px); /* Set min-width to work with 3 in a row, considering the gap */
  max-width: calc(33.33% - 40px); /* Set max-width to ensure consistency */
  height: 100%; /* Ensure the box takes up the full height */
  background-color: #f9f9f9; /* Change to the color you desire */
}

.quiz-box:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* Media query for larger screens */
@media (min-width: 1200px) { /* Adjust this breakpoint as needed for when you want 4 columns */
  .quiz-box {
    min-width: calc(30% - 40px); /* Set min-width to work with 4 in a row */
    max-width: calc(30% - 40px); /* Set max-width to maintain 4 in a row */
  }
}

h2 {
  font-size: 2rem;
  margin-bottom: 10px;
}

img {
  object-fit: cover;
  border-radius: 8px;

  width: 100%; /* Ensure the image takes up the full width */
  max-height: 200px; /* Set a max-height   to prevent images from stretching */

}

h2, p {
  display: -webkit-box;
  -webkit-line-clamp: 2; /* Adjust number of lines for h2, and separately for p */
  -webkit-box-orient: vertical;
  overflow: hidden;
}



</style>


