<template>
  <transition name="fade">
    <div class="quiz-fullscreen" v-if="quiz" @click="closeQuiz">
      <div class="quiz-content" @click.stop>
        <div class="image-container">
          <img :src="quiz.imageData" alt="Quiz Image" class="quiz-image">
        </div>
        <button class="close-btn" @click.stop="closeQuiz">
          <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24" width="24" height="24"><path d="M0 0h24v24H0V0z" fill="none"/><path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"/></svg>
        </button>
        <button @click.stop="startQuiz" class="start-quiz-btn">Start Quiz</button>
        <!-- Header and Button Container -->
        <div class="header-container">
          <h1>{{ quiz.title }}</h1>
        </div>

        <div class="quiz-info">
        <!-- Displaying Category -->
        <p class="category-badge">#{{ categories[quiz.categoryId] }}</p>
        <!-- Displaying Difficulty -->
          <p :class="['difficulty-badge', difficultyClass(quiz.difficulty)]">{{ quiz.difficulty }}</p>
        </div>



        <p>{{ quiz.description }}</p>
        <h2>Questions</h2>
        <!-- Questions Container -->
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

const props = defineProps({
  quiz: Object,
});
const emits = defineEmits(['close']);
const questions = ref([]);
const store = useStore(); // Use the store
const router = useRouter(); // Use router for navigation
const categories = ref({});

watch(() => props.quiz, async (newQuiz, oldQuiz) => {
  if (newQuiz && (!oldQuiz || newQuiz.id !== oldQuiz.id)) {
    try {
      questions.value = await QuestionService.getQuestionsByQuizId(newQuiz.id);
    } catch (error) {
      console.error('Failed to fetch questions for the quiz', error);
    }
  }
}, { immediate: true });

onMounted(async () => {
  const allCategories = await CategoryService.getAllCategories();
  categories.value = allCategories.reduce((acc, current) => {
    acc[current.id] = current.categoryName;
    return acc;
  }, {});
})

const closeQuiz = () => {
  emits('close');
};

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
  position: relative; /* Needed for absolute positioning of the close button */
  background-color: white; /* White background */
  color: black; /* Adjust text color for better readability */
  text-align: left;
  max-width: 600px;
  padding: 20px;
  border-radius: 10px; /* Optional: adds rounded corners */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Optional: adds a subtle shadow */

  max-height: 80vh; /* Set the maximum height to the viewport height */
  overflow-y: auto; /* Enable vertical scrolling */
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
  margin-bottom: 20px; /* Adjust as needed */
}

.quiz-image {

    object-fit: cover;
    border-radius: 8px;

    max-width: 500px;
    min-width: 500px;
    max-height: 200px; /* Set a max-height   to prevent images from stretching */


}



.difficulty-easy {
  background-color: #28a745; /* Green for easy */
  display: inline-block;
  color: #ffffff;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  margin: 5px 0;
}

.difficulty-medium {
  background-color: #f3dc5e; /* Yellow for medium */
  display: inline-block;
  color: #ffffff;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  margin: 5px 0;
}

.difficulty-hard {
  background-color: #dc3545; /* Red for hard */
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
.fade-enter, .fade-leave-to /* Starting and ending state for enter/leave transitions */ {
  opacity: 0;
}

.questions-container {
  display: flex;
  flex-wrap: wrap; /* Allows items to wrap as needed */
  gap: 10px; /* Spacing between question cards */
  margin-top: 20px; /* Spacing above the question list */
  font-family: 'DM Sans', sans-serif;

  max-height: 100vh; /* Set the maximum height to the viewport height */
  overflow-y: auto; /* Enable vertical scrolling */
}



.question-card {
  flex: 1; /* Flex grow to fill available space */
  min-width: 200px; /* Minimum width for each question card */
  padding: 10px;
  background-color: #ffffff; /* Light grey background for visibility */
  border-radius: 5px; /* Rounded corners */
  box-shadow: 0 2px 4px rgba(0,0,0,0.2); /* Soft shadow for depth */
  font-family: 'DM Sans', sans-serif;
}

.category-badge {
  display: inline-block; /* Treat the <p> tag more like an inline element */
  background-color: #007bff;
  color: #ffffff; /* Text color */
  padding: 5px 15px; /* Vertical and horizontal padding */
  border-radius: 20px; /* Rounded corners */
  font-size: 0.8rem; /* Adjust font size as needed */
  margin: 0; /* Remove default <p> margin if needed */
}

.close-btn {
  position: absolute;
  background-color: #007bff;
  top: 10px;
  right: 10px;
  border: none;
  cursor: pointer;
  padding: 5px; /* Adjust as needed */
  display: flex; /* Helps center the icon if it's not filling the button */
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

@media (max-width: 600px) { /* Example breakpoint */
  .header-container {
    flex-direction: column; /* Stack items vertically */
    align-items: flex-start; /* Align items to the start */
  }

  .quiz-content {
    position: relative;
    background-color: white;
    color: black;
    text-align: left;
    max-width: 600px;
    width: 100%; /* Ensure it takes up to 100% of its parent width */
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    max-height: 80vh;
    overflow-y: auto;
    overflow-x: hidden; /* Prevents horizontal scrolling */

    margin: 20px; /* This helps ensure there's a bit of margin around the content */
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
    padding: 10px; /* Ensure padding does not cause overflow */
    box-sizing: border-box; /* Include padding in width calculation */
    overflow: hidden;

  }





}


</style>
