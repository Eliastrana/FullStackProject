<script setup>
import { ref, defineProps } from 'vue';

/**
 * Props for the FillInTheBlankQuizDisplayer component
 * @property {Object} question - The question object
 */
const props = defineProps({
  question: Object
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
 * Boolean value to check if the question has been answered
 * @type {import('vue').Ref<boolean>}
 */
const answered = ref(false);

/**
 * Checks the user's answer and emits an event with the result
 */
const checkAnswer = () => {
  // If the question has already been answered, do nothing
  if (answered.value) return;

  // Check if the user's answer matches any of the correct answers (case insensitive)
  const isCorrect = props.question.answers.some(answer =>
    userAnswer.value.trim().toLowerCase() === answer.text.toLowerCase()
  );

  // Emit the 'answered' event with the result
  emit('answered', isCorrect);
  // Mark the question as answered
  answered.value = true;
};
</script>


<template>
  <div class="question-container">
    <img v-if="question.imageName" :src="question.imageName" alt="Question Image" class="question-image">
    <div class="question">
      {{ question.text }}
      <input v-model="userAnswer"
             @keyup.enter="checkAnswer"
             :class="{ 'correct-answer': isCorrect === true, 'incorrect-answer': isCorrect === false }"
             placeholder="Answer here"
             class="blank"
             :disabled="answered">
    </div>
    <button class="checkAnswerButton" @click="checkAnswer" :disabled="answered">Check Answer</button>
  </div>
</template>



<style scoped>
.question-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  border-radius: 20px;
  font-family: 'DM Sans', sans-serif;
  font-size: 1rem;
  background-color: #FFFFFF;
  border: 5px solid #62B6CB;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.question {
  margin-bottom: 20px;
  font-size: 2rem;
  font-family: 'DM Sans', sans-serif;
}

.question-image {
  max-width: 100%;
  max-height: 200px;
  border-radius: 8px;
  margin-top: 20px;
}

.checkAnswerButton {
  padding: 10px 20px;
  font-family: 'DM Sans', sans-serif;
  font-size: 1rem;
  border: none;
  background-color: #62B6CB;
  border-radius: 5px;
  cursor: pointer;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.checkAnswerButton:hover {
  background-color: #4A9AA5;
}

.blank {
  margin: 0 5px;
  line-height: 1.5;
  font-size: 1.5rem;
  font-family: 'DM Sans', sans-serif;
  padding: 5px;
  border: none;
  border-bottom: 2px solid #62B6CB;
}

.feedback {
  margin-top: 20px;
}

.correct-answer {
  border-bottom: 5px solid #4CAF50;
}

.incorrect-answer {
  border-bottom: 5px solid #e17474;
}

.correct-answer::after, .incorrect-answer::after {
  content: '';
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
}

</style>
