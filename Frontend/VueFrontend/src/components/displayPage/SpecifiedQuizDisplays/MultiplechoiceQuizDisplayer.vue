// MultiplechoiceQuizDisplayer.vue

<script setup>
import { ref, onMounted, defineProps } from 'vue';
import MultiplechoiceDisplayer from '@/components/displayPage/displayQuiz/MultiplechoiceDisplayer.vue';

/**
 * Props for the MultiplechoiceQuizDisplayer component
 * @property {string} jsonPath - The path to the JSON file containing the quiz data
 */
const props = defineProps({
  jsonPath: String,
});

/**
 * Title of the quiz
 * @type {import('vue').Ref<string>}
 */
const quizTitle = ref('');

/**
 * Array of questions
 * @type {import('vue').Ref<Array>}
 */
const questions = ref([]);

/**
 * Index of the current question
 * @type {import('vue').Ref<number>}
 */
const currentQuestionIndex = ref(0);

/**
 * Current score
 * @type {import('vue').Ref<number>}
 */
const score = ref(0);

onMounted(async () => {
  /**
   * On component mount, fetch quiz data
   */
  try {
    const response = await fetch(props.jsonPath);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    quizTitle.value = data.title;
    questions.value = data.questions;
  } catch (error) {
    console.error("Fetching error:", error.message);
  }
});

/**
 * Handles the answer selected by the user
 * @param {Object} answer - The answer object
 * @param {boolean} answer.isCorrect - Whether the answer is correct or not
 */
const handleAnswer = ({ isCorrect }) => {
  if (isCorrect) {
    score.value++;
  }
  if (currentQuestionIndex.value < questions.value.length - 1) {
    currentQuestionIndex.value++;
  } else {
    alert(`Quiz completed! Your score: ${score.value}/${questions.value.length}`);
  }
};

/**
 * Navigates to the next question
 */
const nextQuestion = () => {
  if (currentQuestionIndex.value < questions.value.length - 1) {
    currentQuestionIndex.value++;
  }
};

/**
 * Navigates to the previous question
 */
const prevQuestion = () => {
  if (currentQuestionIndex.value > 0) {
    currentQuestionIndex.value--;
  }
};
</script>


<template>
  <div class="quiz-container">
    <h1>{{ quizTitle }}</h1>
    <div v-if="questions.length > 0">
      <button @click="prevQuestion" :disabled="currentQuestionIndex === 0">
        Previous
      </button>
      <MultiplechoiceDisplayer
        :key="questions[currentQuestionIndex].id"
        :question="questions[currentQuestionIndex].questionText"
        :options="questions[currentQuestionIndex].options"
        :correctIndex="questions[currentQuestionIndex].correctIndex"
        @answer="handleAnswer"
      />
      <button @click="nextQuestion" :disabled="currentQuestionIndex === questions.length - 1">
        Next
      </button>
    </div>
  </div>
</template>

<style scoped>
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

.correct {
  background-color: #CAE9FF;
}

.incorrect {
  background-color: #e7e7e7;
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

.button {
  align-content: center;
  text-align: center;
  padding: 10px 20px;
  background-color: #BEE9E8;
  color: #000000;
  border: none;
  border-radius: 40px;
  cursor: pointer;
  min-width: 200px;
  margin: 0 5px;
  font-size: 2rem;
  font-family: 'DM Sans', sans-serif;
}

.button:hover {
  background-color: #A2D2CE;
}

.buttons-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
  margin-left: 100px;
  margin-right: 100px;
}
</style>
