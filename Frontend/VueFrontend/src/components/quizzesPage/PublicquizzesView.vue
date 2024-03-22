<template>
  <div>
    <QuizContainer @select-quiz="handleQuizSelect" />
  </div>
</template>

<script setup>
import QuizContainer from '@/components/quizzesPage/QuizContainer.vue';
import { defineEmits } from 'vue';

const emit = defineEmits(['quiz-selected']);

const handleQuizSelect = (quiz) => {
  emit('quiz-selected', quiz);
};
</script>

<style scoped>
h1 {
  font-family: 'DM Sans', sans-serif;
  font-size: 3rem;
  text-align: Left;
  margin-top: 20px;
  margin-left: 40px;
}
</style>
