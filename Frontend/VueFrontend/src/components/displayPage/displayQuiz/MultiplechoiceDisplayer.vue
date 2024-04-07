//MultiplechoiceDisplayer.vue
<script setup>
import { defineProps, ref } from 'vue';

/**
 * Props for the MultiplechoiceDisplayer component
 * @property {Object} question - The question object
 */
const props = defineProps({
  question: Object,
});

/**
 * Index of the selected option
 * @type {import('vue').Ref<number>}
 */
const selectedOptionIndex = ref(-1);

/**
 * Emits custom events
 * @type {Function}
 */
const emit = defineEmits(['answered']);

/**
 * Selects an option and checks if it's correct
 * @param {number} index - The index of the selected option
 */
const selectOption = (index) => {
  selectedOptionIndex.value = index;
  const isCorrect = props.question.answers[index].correct;
  emit('answered', isCorrect);
};

/**
 * Returns the classes for an option based on its state
 * @param {number} index - The index of the option
 * @returns {Array<string>} The classes for the option
 */
const getOptionClasses = (index) => {
  const classes = [];
  if (selectedOptionIndex.value !== -1) {
    if (props.question.answers[index].correct) {
      classes.push('correct')
    } else if (index === selectedOptionIndex.value) {
      classes.push('selected')
    } else {
      classes.push('incorrect')
    }
  }
  return classes
};
</script>

<template>
  <div class="quiz-container">
    <div class="question-title">{{ props.question?.text }}</div>
    <img v-if="props.question.multimediaLink" :src="props.question.multimediaLink" alt="Question Image"
         class="question-image">
    <div class="answers-grid">
      <button v-for="(answer, index) in props.question.answers" :key="index" class="answer"
              :class="getOptionClasses(index)" @click="selectOption(index)" :disabled="selectedOptionIndex !== -1">
        {{ answer.text }}
      </button>
    </div>
  </div>
</template>

<style scoped>

ul {
  list-style-type: none;
  padding: 0;
}

li {
  cursor: pointer;
  padding: 10px;
  margin: 5px;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.option {
  cursor: pointer;
  padding: 10px;
  margin: 5px;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.quiz-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  width: 100%;
  justify-content: center;
}

.answers-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  width: 100%;
  max-width: 400px;
  justify-content: center;
  margin: auto;
}

.question-title {
  margin-bottom: 20px;
  font-size: 2rem;
  font-family: 'DM Sans', sans-serif;
}

.question-image {
  max-width: 100%;
  max-height: 150px;
  border-radius: 8px;
  margin-top: 20px;
  margin-bottom: 20px;
}

.answer {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 150px;
  width: 400px;
  background-color: #CAE9FF;
  border-radius: 10px;
  font-family: 'DM Sans', sans-serif;
  font-size: 2rem;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
  position: relative;
}

.answer:hover {
  transform: translateY(-2px);
}

.selected {
}

.correct {
  background-color: #CAE9FF;
  color: #000000;
}

.incorrect {
  background-color: #e7e7e7;
}

.selected {
  background-color: #e7e7e7;
  border: 5px solid #e17474;
  content: '✕';
  color: #000000;
}

.correct::before,
.incorrect::before {
  content: '';
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 20px;
}

.correct::before {
  content: '✓';
  color: white;
}

.incorrect::before {
  content: '✕';
  color: white;
}

.answer:active {
  transform: translateY(0);
}


@media (max-width: 768px) {
  .answers-grid {
    grid-template-columns: 1fr;
  }

  .answer {
    width: 100%;
  }

  quiz-container {
    padding: 10px;
  }

}
</style>
