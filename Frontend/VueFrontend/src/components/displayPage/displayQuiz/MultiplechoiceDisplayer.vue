//MultiplechoiceDisplayer.vue
<script setup>
import { defineProps, ref } from 'vue';

const props = defineProps({
  question: Object,
});

const selectedOptionIndex = ref(-1);
const emit = defineEmits(['answered']);

const selectOption = (index) => {
  selectedOptionIndex.value = index;
  // Determine if the selected answer is correct
  const isCorrect = props.question.answers[index].correct;
  emit('answered', isCorrect);
};

const getOptionClasses = (index) => {
  const classes = [];
  // Only modify classes if an option has been selected
  if (selectedOptionIndex.value !== -1) {
    if (props.question.answers[index].correct) {
      classes.push('correct');
    } else if (index === selectedOptionIndex.value) {
      classes.push('selected');
    } else {
      classes.push('incorrect');
    }
  }
  return classes;
};
</script>


<template>
  <div class="quiz-container">
    <div class="question-title">{{ props.question?.text }}</div>
    <img v-if="props.question.multimediaLink" :src="props.question.multimediaLink" alt="Question Image" class="question-image">
    <div class="answers-grid">
      <button v-for="(answer, index) in props.question.answers" :key="index" class="answer" :class="getOptionClasses(index)" @click="selectOption(index)" :disabled="selectedOptionIndex !== -1">
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
