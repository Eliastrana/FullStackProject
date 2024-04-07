//MixedQuizDisplayer.vue
<template>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">


  <div class="quiz-container">
    <!-- Overlay for Quiz Completion -->
    <div v-if="quizCompleted" class="overlay"></div>

    <!-- Quiz Header -->
    <div class="quiz-header">
      <!-- Randomize Questions Button -->
      <button @click="randomizeQuestions" :disabled="quizStarted" class="icon-button">
        <i class="fas fa-random"></i>
      </button>

      <!-- Quiz Title -->
      <h1>{{ quizTitle }}</h1>

      <!-- Submit/Complete Quiz Button -->
      <button @click="openResults" class="icon-button" aria-label="Submit">
        <i class="fas fa-check"></i>
      </button>
    </div>

    <!-- Score Display -->
    <div class="score-container">
      Score: {{ currentScore }}/{{ totalQuestionsForScore }}
    </div>

    <!-- Navigation Buttons and Progress Bar -->
    <div class="navigation">
      <button class="navigation-button" @click="prevQuestion" :disabled="currentQuestionIndex === 0">
        <span class="material-icons">arrow_back</span>
      </button>
      <div class="progress-bar-container">
        <div class="progress-bar" :style="{ width: progressBarWidth }"></div>
      </div>
      <button class="navigation-button" @click="nextQuestion" :disabled="currentQuestionIndex === (questions.value?.length ?? 0) - 1">
        <span class="material-icons">arrow_forward</span>
      </button>

    </div>


    <!-- Questions Display -->
    <div v-if="questions.length > 0" class="questions">
      <transition name="slide" mode="out-in">
        <!-- Dynamically display the current question component -->
        <component :is="currentQuizComponent"
                   :key="currentQuestion.id || `fallback-${currentQuestionIndex}`"
                   :question="currentQuestion"
                   @answered="handleAnswered" />
      </transition>
    </div>



    <!-- Quiz Completion Display -->
    <div v-if="quizCompleted" class="results-window">
      <h2>Quiz Completed!</h2>
      <p>Your score: {{ currentScore }}/{{ totalQuestionsForScore }}</p>
      <ul class="answer-summary">
        <!-- Display each question's result -->
        <li v-for="(question, index) in questions" :key="index" :class="{'correct': question.correct, 'incorrect': question.answered && !question.correct}">
          Q{{ index + 1 }}: {{ question.text }} - <strong>
          {{ question.questionType === 'STUDY' ? 'Study Note' : (question.correct ? 'Correct' : 'Incorrect')}}

        </strong>
        </li>
      </ul>
      <div class="rating">
        <input v-model="selectedRating" value="5" name="rating" id="star5" type="radio" @change="rateQuiz(5)">
        <label for="star5"></label>
        <input v-model="selectedRating" value="4" name="rating" id="star4" type="radio" @change="rateQuiz(4)">
        <label for="star4"></label>
        <input v-model="selectedRating" value="3" name="rating" id="star3" type="radio" @change="rateQuiz(3)">
        <label for="star3"></label>
        <input v-model="selectedRating" value="2" name="rating" id="star2" type="radio" @change="rateQuiz(2)">
        <label for="star2"></label>
        <input v-model="selectedRating" value="1" name="rating" id="star1" type="radio" @change="rateQuiz(1)">
        <label for="star1"></label>
      </div>
      <div class="buttonbox">
        <!-- Restart and Profile Navigation Buttons -->
        <button @click="restartQuiz">Try Again</button>
        <button @click="goToProfile">Your profile</button>
      </div>
    </div>




  </div>
</template>


<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { useStore } from 'vuex';
import FillInTheBlankDisplayer from '@/components/displayPage/displayQuiz/FillintheblankDisplayer.vue';
import MultipleChoiceDisplayer from '@/components/displayPage/displayQuiz/MultiplechoiceDisplayer.vue';
import StudyCardDisplayer from '@/components/displayPage/displayQuiz/StudycardDisplayer.vue';
import router from '@/router/index.js';
import { AttemptService } from '@/services/AttemptService.js'
import { RatingService } from '@/services/RatingService.js';


const store = useStore();

const currentQuestion = computed(() => questions.value[currentQuestionIndex.value] || {});
const quizData = computed(() => store.state.quizAttempt.quizData);
const quizId = computed(() => store.state.quizAttempt.quizId);
const quizTitle = computed(() => quizData.value.title);
const questions = ref([...quizData.value.questions]);
const currentQuestionIndex = ref(0);
const currentScore = ref(0);
const quizStarted = ref(false);
const quizCompleted = ref(false);
const selectedRating = ref('');

// Maintain a separate ref for the total number of questions for scoring,
// excluding study cards since they don't count towards the score.
const totalQuestionsForScore = computed(() =>
  questions.value.filter(q => q.questionType !== "STUDY").length
);

// Component mapping based on the question type
const quizComponents = {
  STUDY: StudyCardDisplayer,
  MULTIPLE_CHOICE: MultipleChoiceDisplayer,
  FILL_IN_BLANK: FillInTheBlankDisplayer,
};

const currentQuizComponent = computed(() =>
  quizComponents[questions.value[currentQuestionIndex.value]?.questionType]
);

watch(quizData, (newQuizData) => {
  if (newQuizData) {
    questions.value = [...newQuizData.questions];
  }
});

const handleAnswered = (isCorrect) => {
  const currentIndex = currentQuestionIndex.value;
  const currentQuestion = questions.value[currentIndex];

  // Check if the question has already been answered to prevent re-processing
  if (!currentQuestion.answered) {
    // Mark as answered and set correctness
    currentQuestion.answered = true;
    currentQuestion.correct = isCorrect;

    // Update the score if the answer is correct
    if (isCorrect) {
      currentScore.value++;
    }

    // Handle navigation or completion after a brief pause
    if (currentIndex < questions.value.length - 1) {
      setTimeout(() => currentQuestionIndex.value++, 500);
    } else {
      quizCompleted.value = true;
    }
  }
};


const randomizeQuestions = () => {
  if (!quizStarted.value) {
    questions.value.sort(() => 0.5 - Math.random());
  }
};

const nextQuestion = () => {
  if (currentQuestionIndex.value < questions.value.length - 1) {
    quizStarted.value = true;
    currentQuestionIndex.value++;
  }
};

const prevQuestion = () => {
  if (currentQuestionIndex.value > 0) {
    quizStarted.value = true;
    currentQuestionIndex.value--;
  }
};

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

const goToProfile = () => {
  router.push({ name: 'MyAccount' });
};

const openResults = () => {
  quizCompleted.value = true;
};

const progressBarWidth = computed(() => {
  return `${(currentQuestionIndex.value + 1) / questions.value.length * 100}%`;
});

watch(quizCompleted, async (newValue) => {
  if (newValue === true) {
    await submitAttempt();
  }
});

const submitAttempt = async () => {
  const attemptDetails = {
    quizId: quizId.value,
    userId: store.getters['user/userId'],
    score: currentScore.value,
    totalQuestions: questions.value.length,
    correctAnswers: questions.value.filter(q => q.correct).length,
  };


  try {
    await AttemptService.create(attemptDetails);
  } catch (error) {
    console.error('Failed to save quiz attempt:', error);
  }
};


onMounted(() => {
  window.scrollTo(0, 0);
});

async function rateQuiz(rating) {
  const ratingDTO = {
    userId: store.getters['user/userId'],
    quizId: quizId.value,
    rating: rating
  };

  try {
    await RatingService.saveOrUpdateRating(ratingDTO);
  } catch (error) {
    console.error('Failed to save quiz rating:', error);
  }
}

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
  z-index: 101;
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

.rating {
  display: inline-block;
}

.rating input {
  display: none;
}

.rating label {
  float: right;
  cursor: pointer;
  color: #ccc;
  transition: color 0.3s;
}

.rating label:before {
  content: '\2605';
  font-size: 30px;
}

.rating input:checked ~ label,
.rating label:hover,
.rating label:hover ~ label {
  color: #dcb21f;
  transition: color 0.3s;
}


@media (max-width: 600px) {
  .quiz-header h1 {
    font-size: 1.5rem;
  }

  .quiz-header button {
    padding: 8px 16px;
    font-size: 1rem;
  }

  .questions {
    max-width: 100%;
    width: 100%;
    overflow-x: auto;
    box-sizing: border-box;
  }

  .results-window {
    min-width: 80%;
    max-height: 50%;
  }

}
</style>
