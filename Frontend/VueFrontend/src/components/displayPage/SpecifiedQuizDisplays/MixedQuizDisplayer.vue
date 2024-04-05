//MixedQuizDisplayer.vue
<template>
  <div class="quiz-container">
    <div v-if="quizCompleted" class="overlay"></div>
    <div class="quiz-header">
      <button @click="randomizeQuestions" :disabled="quizStarted" class="icon-button">
        <i class="fas fa-random"></i>
      </button>
      <h1>{{ quizTitle }}</h1>
      <button @click="openResults" class="icon-button" aria-label="Submit" :disabled="!quizStarted">
        <i class="fas fa-check"></i>
      </button>
    </div>
    <div class="score-container">
      Score: {{ currentScore }}/{{ totalQuestionsForScore }}
    </div>
    <div class="navigation">
      <button class="navigation-button" @click="prevQuestion" :disabled="currentQuestionIndex === 0">← Previous</button>
      <div class="progress-bar-container">
        <div class="progress-bar" :style="{ width: progressBarWidth }"></div>
      </div>
      <button class="navigation-button"
              @click="nextQuestion"
              :disabled="currentQuestionIndex === (questions.value?.length ?? 0) - 1">
        Next →
      </button>
    </div>

    <div v-if="questions.length > 0">
      <transition name="slide" mode="out-in">
        <component :is="currentQuizComponent"
                   :key="currentQuestion.id || `fallback-${currentQuestionIndex}`"
                   :question="currentQuestion"
                   @answered="handleAnswered" />
      </transition>
    </div>

    <div v-if="quizCompleted" class="results-window">
      <h2>Quiz Completed!</h2>
      <p>Your score: {{ currentScore }}/{{ totalQuestionsForScore }}</p>
      <ul class="answer-summary">
        <li v-for="(question, index) in questions" :key="index" :class="{'correct': question.correct, 'incorrect': question.answered && !question.correct}">
          Q{{ index + 1 }}: {{ question.text }} - <strong>
          {{ question.questionType === 'STUDY' ? 'Study Note' : (question.correct ? 'Correct' : 'Incorrect') }}
        </strong>
        </li>
      </ul>
      <div class="buttonbox">
        <button @click="restartQuiz">Try Again</button>
        <button @click="goToProfile">More Quizzes</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { useStore } from 'vuex';
import FillInTheBlankDisplayer from '@/components/displayPage/displayQuiz/FillintheblankDisplayer.vue';
import MultipleChoiceDisplayer from '@/components/displayPage/displayQuiz/MultiplechoiceDisplayer.vue';
import StudyCardDisplayer from '@/components/displayPage/displayQuiz/StudycardDisplayer.vue';
import router from '@/router/index.js';

/**
 * Vuex Store instance
 * @type {import('vuex').Store}
 */
const store = useStore();

/**
 * Current question data
 * @type {import('vue').ComputedRef<Object>}
 */
const currentQuestion = computed(() => questions.value[currentQuestionIndex.value] || {});

/**
 * Quiz data from the Vuex store
 * @type {import('vue').ComputedRef<Object>}
 */
const quizData = computed(() => store.state.quizAttempt.quizData);

/**
 * Title of the quiz
 * @type {import('vue').ComputedRef<string>}
 */
const quizTitle = computed(() => quizData.value.title);

/**
 * Array of questions
 * @type {import('vue').Ref<Array>}
 */
const questions = ref([...quizData.value.questions]);

/**
 * Index of the current question
 * @type {import('vue').Ref<number>}
 */
const currentQuestionIndex = ref(0);

/**
 * Current score
 * @type {import('vue').Ref<number>}
 */
const currentScore = ref(0);

/**
 * Boolean value to check if the quiz has started
 * @type {import('vue').Ref<boolean>}
 */
const quizStarted = ref(false);

/**
 * Boolean value to check if the quiz is completed
 * @type {import('vue').Ref<boolean>}
 */
const quizCompleted = ref(false);

/**
 * Total number of questions for score calculation
 * @type {import('vue').ComputedRef<number>}
 */
const totalQuestionsForScore = computed(() =>
  questions.value.filter(q => q.questionType !== "STUDY").length
);

/**
 * Map of quiz components
 * @type {Object}
 */
const quizComponents = {
  STUDY: StudyCardDisplayer,
  MULTIPLE_CHOICE: MultipleChoiceDisplayer,
  FILL_IN_BLANK: FillInTheBlankDisplayer,
};

/**
 * Current quiz component based on the question type
 * @type {import('vue').ComputedRef<Object>}
 */
const currentQuizComponent = computed(() =>
  quizComponents[questions.value[currentQuestionIndex.value]?.questionType]
);

/**
 * Watcher for quizData changes
 */
watch(quizData, (newQuizData) => {
  console.log('new quiz data: ')
  console.log(newQuizData)
  if (newQuizData) {
    questions.value = [...newQuizData.questions];
  }
});

/**
 * Handles the answer selected by the user
 * @param {boolean} isCorrect - Whether the answer is correct or not
 */
const handleAnswered = (isCorrect) => {
  const currentIndex = currentQuestionIndex.value;
  const currentQuestion = questions.value[currentIndex];

  if (!currentQuestion.answered) {
    currentQuestion.answered = true;
    currentQuestion.correct = isCorrect;
    if (isCorrect) {
      currentScore.value++;
    }
    if (currentIndex < questions.value.length - 1) {
      setTimeout(() => currentQuestionIndex.value++, 500);
    } else {
      quizCompleted.value = true;
    }
  }
};

/**
 * Randomizes the order of questions
 */
const randomizeQuestions = () => {
  if (!quizStarted.value) {
    questions.value.sort(() => 0.5 - Math.random());
  }
};

/**
 * Navigates to the next question
 */
const nextQuestion = () => {
  if (currentQuestionIndex.value < questions.value.length - 1) {
    quizStarted.value = true;
    currentQuestionIndex.value++;
  }
};

/**
 * Navigates to the previous question
 */
const prevQuestion = () => {
  if (currentQuestionIndex.value > 0) {
    quizStarted.value = true;
    currentQuestionIndex.value--;
  }
};

/**
 * Restarts the quiz
 */
const restartQuiz = () => {
  currentQuestionIndex.value = 0;
  currentScore.value = 0;
  quizCompleted.value = false;
  quizStarted.value = false;
  questions.value.forEach(question => {
    question.answered = false;
    question.correct = null;
  });
  randomizeQuestions();
};

/**
 * Navigates to the profile page
 */
const goToProfile = () => {
  router.push({ name: 'MyAccount' });
};

/**
 * Opens the results of the quiz
 */
const openResults = () => {
  quizCompleted.value = true;
};

/**
 * Calculates the width of the progress bar
 * @type {import('vue').ComputedRef<string>}
 */
const progressBarWidth = computed(() => {
  return `${(currentQuestionIndex.value + 1) / questions.value.length * 100}%`;
});

</script>


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

.slide-enter-active, .slide-leave-active {
  transition: transform 0.4s ease;
}

.slide-enter-from {
  transform: translateX(100%);
}

.slide-leave-to {
  transform: translateX(-100%);
}

.slide-enter-to, .slide-leave-from {
  transform: translateX(0);
}

.results-window {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  padding: 20px;
  min-width: 40%;
  min-height: 40%;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.2);
  z-index: 101; /* Above other content */
  text-align: center;
}

body {
  overflow: hidden;
}

.results-window h2 {
  margin: 0 0 20px 0;
}

.results-window p {
  margin: 0 0 20px 0;
}

.results-window button {
  padding: 10px 20px;
  font-size: 1.1rem;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 20px;
  cursor: pointer;
}

.results-window button:hover {
  background-color: #5d9ff3;
}

.answer-summary {
  text-align: left;
  list-style: none;
  padding: 0;
}

.answer-summary li {
  margin: 5px 0;
}

.correct {
  color: #4CAF50;
}

.incorrect {
  color: #F44336;
}

.buttonbox {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 100;
}

.quiz-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.quiz-header h1 {
  margin: 0;
  font-size: 2rem;
}

.quiz-header button {
  padding: 10px 20px;
  font-size: 1.1rem;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 20px;
  cursor: pointer;
}

.quiz-header button:hover {
  background-color: #5d9ff3;
}

.quiz-header button:disabled {
  background-color: #f2f2f2;
  color: #a0a0a0;
  cursor: not-allowed;
}

</style>
