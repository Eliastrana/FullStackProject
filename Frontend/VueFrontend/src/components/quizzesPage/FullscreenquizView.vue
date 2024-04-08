<template>
  <transition name="fade">
    <div class="quiz-fullscreen" v-if="quiz" @click="closeQuiz">
      <div class="quiz-content" @click.stop>
        <div class="image-container">
          <img :src="quiz.imageData || '/images/default.png'" alt="Quiz Image" class="quiz-image" />
        </div>
        <button class="close-btn" @click.stop="closeQuiz">
          <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24" width="24" height="24"><path d="M0 0h24v24H0V0z" fill="none"/><path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/></svg>
        </button>
        <button @click.stop="startQuiz" class="start-quiz-btn">Start Quiz</button>
        <div class="header-container">
          <h1>{{ quiz.title }}</h1>
        </div>
        <div class="quiz-info">
        <p class="category-badge">#{{ categories[quiz.categoryId] }}</p>
          <p :class="['difficulty-badge', difficultyClass(quiz.difficulty)]">{{ quiz.difficulty }}</p>
        </div>
        <p>{{ quiz.description }}</p>
        <h2>Questions</h2>
        <div v-if="questions.length" class="questions-container">
          <div class="question-card" v-for="question in questions" :key="question.id">
            <p>{{ question.text }}</p>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>


<script setup>
import { onMounted, ref, watch } from 'vue'
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { QuestionService } from '@/services/QuestionService.js';
import { QuizService } from '@/services/QuizService.js';
import { defineProps, defineEmits } from 'vue';
import { CategoryService } from '@/services/CategoryService.js'

/**
 * Props for the FullscreenQuizView component
 * @property {Object} quiz - The quiz object
 */

const props = defineProps({
  quiz: Object,
});

/**
 * Emits custom events
 * @type {Function}
 */

const emits = defineEmits(['close']);
const questions = ref([]);
const store = useStore();
const router = useRouter();
const categories = ref({});

/**
 * Watches for changes in the quiz prop and fetches questions for the quiz
 */

watch(() => props.quiz, async (newQuiz, oldQuiz) => {
  if (newQuiz && (!oldQuiz || newQuiz.id !== oldQuiz.id)) {
    try {
      questions.value = await QuestionService.getQuestionsByQuizId(newQuiz.id);
    } catch (error) {
      console.error('Failed to fetch questions for the quiz', error);
    }
  }
}, { immediate: true });

/**
 * Fetches all categories when the component is mounted
 */

onMounted(async () => {
  const allCategories = await CategoryService.getAllCategories();
  categories.value = allCategories.reduce((acc, current) => {
    acc[current.id] = current.categoryName;
    return acc;
  }, {});
})


/**
 * Closes the quiz
 */
const closeQuiz = () => {
  emits('close');
};

/**
 * Starts the quiz
 */

const startQuiz = async () => {
  try {
    const quizData = await QuizService.getQuizById(props.quiz.id);
    // Include the quiz ID in the payload
    await store.dispatch('quizAttempt/setQuizData', { quizData, quizId: props.quiz.id });
    await router.push({ name: 'QuizDisplayer' });
  } catch (error) {
    console.error('Failed to fetch and store quiz data:', error);
  }
};


/**
 * Returns the class name for the difficulty badge
 * @param {string} difficulty - The difficulty level
 * @returns {string} - The class name
 */
function difficultyClass(difficulty) {
  switch (difficulty.toLowerCase()) {
    case 'easy':
      return 'difficulty-easy';
    case 'medium':
      return 'difficulty-medium';
    case 'hard':
      return 'difficulty-hard';
    default:
      return '';
  }
}

</script>

<style scoped>
.quiz-fullscreen {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;

}

.quiz-content {
  position: relative;
  background-color: white;
  color: black;
  text-align: left;
  max-width: 600px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  max-height: 80vh;
  overflow-y: auto;
}

button {
  padding: 10px 20px;
  font-size: 1rem;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

.image-container {
  position: relative;
  text-align: center;
  margin-bottom: 20px;
}

.quiz-image {
    object-fit: cover;
    border-radius: 8px;
    max-width: 500px;
    min-width: 500px;
    max-height: 200px;
}

.difficulty-easy {
  background-color: #28a745;
  display: inline-block;
  color: #ffffff;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  margin: 5px 0;
}

.difficulty-medium {
  background-color: #f3dc5e;
  display: inline-block;
  color: #ffffff;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  margin: 5px 0;
}

.difficulty-hard {
  background-color: #dc3545;
  display: inline-block;
  color: #ffffff;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  margin: 5px 0;
}

button {
  padding: 10px 20px;
  font-size: 1.1rem;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 20px;
  cursor: pointer;
}


button:hover {
  background-color: #5d9ff3;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}

.questions-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 20px;
  font-family: 'DM Sans', sans-serif;
  max-height: 100vh;
  overflow-y: auto;
}

.question-card {
  flex: 1;
  min-width: 200px;
  padding: 10px;
  background-color: #ffffff;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  font-family: 'DM Sans', sans-serif;
}

.category-badge {
  display: inline-block;
  background-color: #007bff;
  color: #ffffff;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  margin: 0;
}

.close-btn {
  position: absolute;
  background-color: #007bff;
  top: 10px;
  right: 10px;
  border: none;
  cursor: pointer;
  padding: 5px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close-btn:hover {
  background-color: rgba(0,0,0,0.1);
  background-color: #5d9ff3;

}

.quiz-info {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 10px;
}

@media (max-width: 600px) {
  .header-container {
    flex-direction: column;
    align-items: flex-start;
  }

  .quiz-content {
    position: relative;
    background-color: white;
    color: black;
    text-align: left;
    max-width: 600px;
    width: 100%;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    max-height: 80vh;
    overflow-y: auto;
    overflow-x: hidden;
    margin: 20px;
  }

  .quiz-fullscreen {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.8);
    display: flex;
    justify-content: center;
    align-items: center;
    color: white;
    padding: 10px;
    box-sizing: border-box;
    overflow: hidden;

  }

}

</style>
