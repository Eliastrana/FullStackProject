<script setup>

import CreatequizView from '@/components/createPage/CreatequizView.vue'
import PublicquizzesView from '@/components/quizzesPage/PublicquizzesView.vue';
import QuizFullScreen from '@/components/quizzesPage/FullscreenquizView.vue'; // Adjust path as necessary
import HowtocreateView from '@/components/createPage/HowtocreateView.vue'


import { ref } from 'vue'

const selectedQuiz = ref(null);

</script>

<template>

  <h1 class="CAQ">Create a quiz!</h1>

  <h2>Select what type of quiz you want to make!</h2>

  <CreatequizView />
  <HowtocreateView />


  <h2 id="h2-2">If you need inspiration!</h2>

  <PublicquizzesView @quiz-selected="quiz => selectedQuiz = quiz" />
  <QuizFullScreen v-if="selectedQuiz" :quiz="selectedQuiz" @close="selectedQuiz = null" />


</template>

<style scoped>
h1 {
  font-family: 'DM Sans', sans-serif;
  font-size: clamp(3.4rem, 10vw, 5rem); /* Justerer størrelsen basert på skjermens bredde */
  text-align: left;
  margin-top: 40px;
  margin-bottom: -20px;
  margin-left: 10vh;
}
h2{
  font-family: 'DM Sans', sans-serif;
  font-size: clamp(1.4rem, 3vw, 2rem); /* Justerer størrelsen basert på skjermens bredde */
  text-align: left;
  margin-top: 40px;
  margin-left: 5vh;

}
.CAQ{
  margin-left: 5vh;
}
#h2-2{
  margin-left: 4vh;
}
@media screen and (max-width: 768px) {
  h1 {
    margin-top: -34px;
    margin-left: 10px;
  }
}



</style>