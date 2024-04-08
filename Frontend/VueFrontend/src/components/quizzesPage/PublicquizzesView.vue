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

/**
 * Emits custom events
 * @type {Function}
 */

const emit = defineEmits(['quiz-selected']);

/**
 * Array of quizzes
 * @type {import('vue').Ref<Array>}
 */
const quizzes = ref([]);

/**
 * Handles the quiz selected event
 * @param {Object} quiz - The quiz object
 */
const handleQuizSelect = (quiz) => {
  emit('quiz-selected', quiz);
};

/**
 * Updates the quizzes array
 * @param {Array} newQuizzes - The new quizzes array
 */
const updateQuizzes = (newQuizzes) => {
  quizzes.value = newQuizzes;
};

/**
 * Fetches public quizzes when the component is mounted
 */

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
