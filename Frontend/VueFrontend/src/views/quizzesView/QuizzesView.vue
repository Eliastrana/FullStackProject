<script setup>
import QuiztitleDisplay from '@/components/quizzesPage/QuiztitleDisplay.vue';
import PublicquizzesView from '@/components/quizzesPage/PublicquizzesView.vue';
import QuizFullScreen from '@/components/quizzesPage/FullscreenquizView.vue'; // Adjust path as necessary
import { ref } from 'vue';

/**
 * Selected quiz
 * @type {import('vue').Ref<Object>}
 */
const selectedQuiz = ref(null);
</script>

<template>
  <QuiztitleDisplay />
  <h1>Public Quizzes</h1>
  <PublicquizzesView @quiz-selected="quiz => selectedQuiz = quiz" />
  <QuizFullScreen v-if="selectedQuiz" :quiz="selectedQuiz" @close="selectedQuiz = null" />
</template>

<style scoped>

h1 {
  font-family: 'DM Sans', sans-serif;
  font-size: 3rem;
  text-align: Left;
  margin-top: 20px;
  margin-left: 40px;
}

</style>
