<template>
  <div class="fill-in-the-blank-container">
    <p>{{ question.questionText }}</p>
    <input v-model="userAnswer" type="text" placeholder="Type your answer here" />
    <button @click="submitAnswer">Submit</button>
  </div>
</template>

<script setup>
// Importing necessary modules from Vue
import { defineProps, ref, defineEmits } from 'vue';

/**
 * Props for the FillInTheBlankQuizDisplayer component
 * @property {Object} question - The question object
 */
const props = defineProps({
  question: Object,
});

/**
 * User's answer
 * @type {import('vue').Ref<string>}
 */
const userAnswer = ref('');

/**
 * Emits custom events
 * @type {Function}
 */
const emit = defineEmits(['answered']);

/**
 * Submits the user's answer and checks if it's correct
 */
const submitAnswer = () => {
  const isCorrect = userAnswer.value.trim().toLowerCase() === props.question.correctAnswer.trim().toLowerCase();
  emit('answered', { isCorrect, userAnswer: userAnswer.value });
  userAnswer.value = '';
};
</script>

<style scoped>
.fill-in-the-blank-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.fill-in-the-blank-container p {
  margin: 0;
}

.fill-in-the-blank-container input {
  line-height: 1.5;
  padding: 0.5em;
  margin: 0.5em;
}

.fill-in-the-blank-container button {
  cursor: pointer;
  padding: 0.5em 1em;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
}
</style>
