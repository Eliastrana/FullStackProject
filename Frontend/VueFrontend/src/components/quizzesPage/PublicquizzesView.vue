<template>
  <div>
    <QuizContainer @select-quiz="handleQuizSelect" @update:quizzes="updateQuizzes" />
    <div v-if="!quizzes.length" class="no-quizzes">
      <img src="/images/404/noquizzes.png" alt="No Quizzes Available">
      <p>We weren't able find any quizzes for you.</p>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import QuizContainer from '@/components/quizzesPage/QuizContainer.vue';
import { QuizService } from '@/services/QuizService.js'

const emit = defineEmits(['quiz-selected']);
const quizzes = ref([]);

const handleQuizSelect = (quiz) => {
  emit('quiz-selected', quiz);
};

const updateQuizzes = (newQuizzes) => {
  quizzes.value = newQuizzes;
};

onMounted(async () => {
  try {
    quizzes.value = await QuizService.getPublicQuizzes();
  } catch (error) {
    console.error('Error while fetching quizzes', error);
  }
});
</script>

<style scoped>

.no-quizzes {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
}

.no-quizzes img {
  max-width: 30%;
  height: auto;
}

.no-quizzes p {
  font-family: 'DM Sans', sans-serif;
  margin-top: 20px;
  font-size: 1.5rem;
  margin-bottom: 100px;

}
</style>
