<script setup>
import { defineProps, ref } from 'vue';

// Update the props to accept a single question object
const props = defineProps({
  question: Object,
});

const selectedOptionIndex = ref(-1);

// Emit an event when an option is selected
const emit = defineEmits(['answer']);

const selectOption = (index) => {
  selectedOptionIndex.value = index;
  const isCorrect = index === props.question.correctIndex;
  emit('answered', { isCorrect });
};


const getOptionClasses = (index) => {
  const classes = [];
  if (selectedOptionIndex.value !== -1) {
    // An option has been selected
    if (index === props.question.correctIndex) {
      classes.push('correct'); // Apply 'correct' class to the correct option
    } else if (index === selectedOptionIndex.value) {
      classes.push('selected'); // Apply 'selected' class to the selected option
    } else {
      classes.push('incorrect'); // Apply 'incorrect' class to all other options
    }
  }
  return classes;
};
</script>

<template>
  <div class="quiz-container">
    <div class="question-title">{{ props.question.questionText }}</div>
    <div class="answers-grid">
      <button
        v-for="(option, index) in props.question.options"
        :key="index"
        class="answer"
        :class="getOptionClasses(index)"
        @click="selectOption(index)"
        :disabled="selectedOptionIndex !== -1"
      >
        {{ option }}
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
  width: 100%; /* Ensure the container takes full width */
  justify-content: center; /* Center content vertically */
}

.answers-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  width: 100%;
  max-width: 400px; /* Adjust this if necessary */
  justify-content: center; /* Center grid items horizontally */
  margin: auto; /* This helps in centering the grid itself if it's smaller than its container */
}



.question-title {
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
  position: relative; /* Added for pseudo-elements */
}

.answer:hover {
  //background-color: #f0f0f0;
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
  /* Add a new style for the selected option */
  background-color: #e7e7e7; /* A light color, for example, khaki */
  border: 5px solid #e17474; /* A darker color, for example, darkkhaki */

  content: '✕';
  color: #000000; /* Adjust as needed for visibility */
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
  color: white; /* Adjust as needed for visibility */
}

.incorrect::before {
  content: '✕';
  color: white; /* Adjust as needed for visibility */
}

.answer:active {
  transform: translateY(0);
}
</style>
