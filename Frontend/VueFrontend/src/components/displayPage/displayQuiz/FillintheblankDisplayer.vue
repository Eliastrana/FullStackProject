<script setup>
import { ref, defineProps } from 'vue';

// Define props
const props = defineProps({
  question: {
    type: Object,
    required: true
  }
});

const userAnswer = ref('');
const isCorrect = ref(null);

const emit = defineEmits(['answered']);


const answered = ref(false); // New reactive property to prevent re-answering

const checkAnswer = () => {
  if (answered.value) return; // Prevent checking the answer again if already answered

  isCorrect.value = userAnswer.value.trim().toLowerCase() === props.question.answer.toLowerCase();
  emit('answered', isCorrect.value);
  answered.value = true; // Mark as answered
};




</script>

<template>
  <div class="question-container">
    <div>
      <img v-if="question.image" :src="question.image" alt="Question Image" class="question-image">
    </div>
    <div class="question">
      {{ question.questionText }}
      <input v-model="userAnswer"
             @keyup.enter="checkAnswer"
             :class="{ 'correct-answer': isCorrect, 'incorrect-answer': isCorrect === false }"
             placeholder="Answer here"
             class="blank"
             :disabled="answered">
    </div>
    <button class="checkAnswerButton" @click="checkAnswer">Check Answer</button>
<!--    <div v-if="isCorrect !== null" class="feedback">-->
<!--      <p v-if="isCorrect" class="correct">Correct!</p>-->
<!--      <p v-else class="incorrect">Incorrect, try again.</p>-->
<!--    </div>-->
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
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1)

  ;
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

/* Styles for icons */
.correct-answer::after, .incorrect-answer::after {
  content: '';
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
}


</style>
