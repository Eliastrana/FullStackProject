<script setup>

import CreatequizView from '@/components/createPage/CreatequizView.vue'
import PublicquizzesView from '@/components/quizzesPage/PublicquizzesView.vue';
import QuizFullScreen from '@/components/quizzesPage/FullscreenquizView.vue'; // Adjust path as necessary
import HowtocreateView from '@/components/createPage/HowtocreateView.vue'


import { ref } from 'vue'

const selectedQuiz = ref(null);

</script>

<template>

  <h1>Create a quiz!</h1>

  <h2>Select what type of quiz you want to make!</h2>

  <CreatequizView />


  <HowtocreateView />


  <h2>If you need inspiration!</h2>

  <PublicquizzesView @quiz-selected="quiz => selectedQuiz = quiz" />
  <QuizFullScreen v-if="selectedQuiz" :quiz="selectedQuiz" @close="selectedQuiz = null" />






</template>

<style scoped>

h1 {
  font-family: 'DM Sans', sans-serif;
  font-size: 7rem;
  text-align: Left;
  margin-top: 40px;
  margin-left: 40px;
  margin-bottom: -20px;
}

h2 {
  font-family: 'DM Sans', sans-serif;
  font-size: 2rem;
  text-align: Left;
  margin-left: 40px;
  color: #3232ff;
}

</style>