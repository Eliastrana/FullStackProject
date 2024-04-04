// PublicquizzesView.vue
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
  font-size: 3rem; /* Base size for larger screens */
  text-align: left;
  margin-top: 20px;
  margin-left: 40px;
}

/* Responsive adjustments */
@media (max-width: 768px) {
  h1 {
    font-size: 2.5rem; /* Slightly smaller font size for tablets */
    margin-top: 15px;
    margin-left: 20px;
  }
}

/* Extra small devices (phones) */
@media (max-width: 480px) {
  h1 {
    font-size: 2rem; /* Smaller font size for mobile phones */
    margin-top: 10px;
    margin-left: 10px;
  }
}
</style>
