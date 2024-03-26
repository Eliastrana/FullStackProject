<template>
  <div class="quiz-container">
    <h1>{{ quizTitle }}</h1>
    <div class="score-container">
      Score: {{ currentScore }}/{{ totalQuestionsForScore }}
    </div>

    <div class="navigation">
      <button class="navigation-button" @click="prevQuestion" :disabled="currentQuestionIndex === 0">←</button>

      <div class="progress-bar-container">
        <div class="progress-bar" :style="{ width: progressBarWidth }"></div>
      </div>

      <button class="navigation-button" @click="nextQuestion" :disabled="currentQuestionIndex === questions.length - 1">→</button>
    </div>
    <div v-if="questions.length > 0">
      <component :is="currentQuizComponent" :key="currentQuestion.id" :question="currentQuestion" @answered="handleAnswered"/>

    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, defineProps } from 'vue';
import FillintheblankDisplayer from '@/components/displayPage/displayQuiz/FillintheblankDisplayer.vue';
import MultiplechoiceDisplayer from '@/components/displayPage/displayQuiz/MultiplechoiceDisplayer.vue';
import StudycardDisplayer from '@/components/displayPage/displayQuiz/StudycardDisplayer.vue';

// Define props, including the jsonPath for the quiz data
const props = defineProps({
  jsonPath: String
});

const quizTitle = ref('');
const questions = ref([]);
const currentQuestionIndex = ref(0);
const currentScore = ref(0);
const totalQuestionsForScore = ref(0);



const quizComponents = {
  "Fill in the Blank": FillintheblankDisplayer,
  "Multiple Choice": MultiplechoiceDisplayer,
  "Study Card": StudycardDisplayer
};

const currentQuestion = computed(() => questions.value[currentQuestionIndex.value]);
const currentQuizComponent = computed(() => quizComponents[currentQuestion.value.type]);

const handleAnswered = (isCorrect) => {
  if (isCorrect) {
    currentScore.value++;
    console.log(currentScore.value);
  }
  //MAYBE TURN THIS ON LATER
  //nextQuestion();
};





const nextQuestion = () => {
  if (currentQuestionIndex.value < questions.value.length - 1) {
    currentQuestionIndex.value++;
  }
};

const prevQuestion = () => {
  if (currentQuestionIndex.value > 0) {
    currentQuestionIndex.value--;
  }
};

const progressBarWidth = computed(() => {
  const progress = ((currentQuestionIndex.value + 1) / questions.value.length) * 100;
  return `${progress}%`;
});

onMounted(async () => {
  const response = await fetch(props.jsonPath);
  const data = await response.json();
  quizTitle.value = data.title;
  questions.value = data.questions;

  // Count the total questions that are not "Study Card" for scoring
  totalQuestionsForScore.value = questions.value.filter(q => q.type !== "Study Card").length;
});

</script>


<!-- Add styles as needed -->
<style scoped>

.quiz-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.navigation {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;

}

.navigation-button {
  padding: 10px 20px;
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
  background-color: #6ec0b8;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
}

.navigation-button:hover {
  background-color: #a0a0a0;
}

.navigation-button:disabled {
  background-color: #f2f2f2;
  color: #a0a0a0;
  cursor: not-allowed;
}

h1 {
  text-align: center;
  font-size: 2rem;
  margin-bottom: 20px;
}


.progress-bar-container {
  width: 100%;
  background-color: #f0f0f0;
  border-radius: 20px;
  margin: 10px ;
  height: 10px;

}

.progress-bar {
  height: 10px;
  background-color: #4CAF50;
  border-radius: 20px;
  text-align: center;
  line-height: 20px;
  color: white;
}

.score-container {
  text-align: center;
  font-size: 1.5rem;
  margin-bottom: 20px;
}





</style>
```
