//MixedQuizDisplayer.vue
<template>

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">


  <div class="quiz-container">
    <div v-if="quizCompleted" class="overlay"></div>


    <div class="quiz-header">
      <button @click="randomizeQuestions" :disabled="quizStarted" class="icon-button">
        <i class="fas fa-random"></i>
      </button>

      <h1>{{ quizTitle }}</h1>
      <button @click="openResults" type="submit" class="icon-button" aria-label="Submit">
        <i class="fas fa-check"></i>
      </button>
    </div>

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
      <transition name="slide" mode="out-in">
        <component :is="currentQuizComponent"
                   :key="currentQuestion.id"
                   :question="currentQuestion"
                   :updateScore="handleAnswered"
                   @answered="handleFillinBlankAnswer" />
      </transition>
    </div>

    <div v-if="quizCompleted" class="results-window">
      <h2>Quiz Completed!</h2>
      <p>Your score: {{ currentScore }}/{{ totalQuestionsForScore }}</p>
      <ul class="answer-summary">
        <li v-for="(question, index) in questions" :key="index" :class="{'correct': question.correct, 'incorrect': question.answered && !question.correct}">
          Q{{ index + 1 }}: {{ question.questionText }} - <strong>
          <!-- Check if it's a study card or if it has been correctly or incorrectly answered -->
          {{ question.type === 'Study Card' ? 'Study Card' : (question.correct ? 'Correct' : 'Incorrect') }}
        </strong>
        </li>
      </ul>
      <div class="buttonbox">
      <button @click="restartQuiz">Try Again</button>
      <button @click="goToprofile">More Quizzes</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, defineProps } from 'vue';
import FillintheblankDisplayer from '@/components/displayPage/displayQuiz/FillintheblankDisplayer.vue';
import MultiplechoiceDisplayer from '@/components/displayPage/displayQuiz/MultiplechoiceDisplayer.vue';
import StudycardDisplayer from '@/components/displayPage/displayQuiz/StudycardDisplayer.vue';
import router from '@/router/index.js'

// Define props, including the jsonPath for the quiz data
const props = defineProps({
  jsonPath: String
});

const quizTitle = ref('');
const questions = ref([]);
const currentQuestionIndex = ref(0);
const currentScore = ref(0);
const totalQuestionsForScore = ref(0);

const handleFillinBlankAnswer = (answerData) => {
  handleAnswered(answerData); // Forward the received data to handleAnswered
};


const quizComponents = {
  "Fill in the Blank": FillintheblankDisplayer,
  "Multiple Choice": MultiplechoiceDisplayer,
  "Study Card": StudycardDisplayer
};

const currentQuestion = computed(() => questions.value[currentQuestionIndex.value]);
const currentQuizComponent = computed(() => quizComponents[currentQuestion.value.type]);
const quizCompleted = ref(false);

const restartQuiz = () => {
  currentQuestionIndex.value = 0;
  currentScore.value = 0;
  quizCompleted.value = false;
  quizStarted.value = false;
  questions.value.forEach(question => {
    question.answered = false;
  });
};

const randomizeQuestions = () => {
  questions.value.sort(() => Math.random() - 0.5);
};


const openResults = () => {
  quizCompleted.value = true;
};

const goToprofile = () => {
  router.push({ name: 'MyAccount' });
};

const handleAnswered = (isCorrect) => {
  if (!currentQuestion.value.answered) {
    currentQuestion.value.answered = true; // Mark as answered
    currentQuestion.value.correct = isCorrect; // Set whether it was answered correctly

    if (isCorrect) {
      currentScore.value++;
    }

    if (currentQuestionIndex.value === questions.value.length - 1) {
      // Last question has been answered
      quizCompleted.value = true;
    } else {
      // Not the last question, move to the next one
      setTimeout(nextQuestion, 500);
    }
  }
};

const navigationDirection = ref('next'); // default to 'next'



const quizStarted = ref(false);


const nextQuestion = () => {
  if (currentQuestionIndex.value < questions.value.length - 1) {
    quizStarted.value = true;
    navigationDirection.value = 'next';
    currentQuestionIndex.value++;
  }
};

const prevQuestion = () => {
  if (currentQuestionIndex.value > 0) {
    quizStarted.value = true;
    navigationDirection.value = 'previous';
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

  // Initialize questions with 'answered', 'correct', and adjust for study cards
  questions.value = data.questions.map(question => {
    // For study cards, consider them as 'answered' but not applicable for 'correct' or 'incorrect'
    if (question.type === "Study Card") {
      return {
        ...question,
        answered: true, // Study cards are considered as 'answered'
        correct: null // 'correct' is not applicable for study cards
      };
    } else {
      return {
        ...question,
        answered: false, // Other questions are not answered initially
        correct: null // 'correct' state is null initially
      };
    }
  });
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


/* Enter and leave-active transitions */
.slide-enter-active, .slide-leave-active {
  transition: transform 0.4s ease;
}

/* When a new question enters, it should come from the right */
.slide-enter-from {
  transform: translateX(100%);
}

/* When the current question leaves, it should move to the left */
.slide-leave-to {
  transform: translateX(-100%);
}

/* Final state for entering and starting state for leaving (usually centered) */
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
  overflow: hidden; /* Prevent scrolling when results window is open */
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
  color: #4CAF50; /* Green for correct answers */
}

.incorrect {
  color: #F44336; /* Red for incorrect answers */
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
  background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent black */
  z-index: 100; /* Ensure it's below the results window but above other content */
}


.quiz-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.quiz-header h1 {
  margin: 0;
  /* Adjust the font size as needed to fit your design */
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
```
