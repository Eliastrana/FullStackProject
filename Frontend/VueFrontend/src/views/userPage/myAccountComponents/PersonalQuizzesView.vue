<script setup>
/**
 * This component displays the quizzes created by the user.
 * The user can start, edit, or delete the quizzes.
 */
import { defineProps, onMounted, ref } from 'vue'
import { QuizService } from '@/services/QuizService.js'
import { UserService } from '@/services/UserService.js'
import ConfirmationModal from '@/components/util/ConfirmationModal.vue'
import { CategoryService } from '@/services/CategoryService.js'
import { useRouter } from 'vue-router'
import store from '@/store/index.js'

// Stateful references used by the component.
const allQuizzes = ref([]);
const displayLimit = ref(3);
const displayedQuizzes = ref([]);
const categories = ref({});
// eslint-disable-next-line
const difficulties = ref([]);
const router = useRouter();
const showConfirmationModal = ref(false);
const pendingDeleteQuizId = ref(null);

/**
 * Fetches quizzes created by the current user, along with their categories and any associated images,
 * upon component mount. Filters quizzes by creator ID and maps category IDs to category names for display.
 */
onMounted(async () => {
  try {
    const userDetails = await UserService.getUserDetails();
    const userId = userDetails.id;

    let quizzesData = await QuizService.getAllQuizzes();

    quizzesData = quizzesData.filter(quiz => quiz.creatorId === userId);

    const allCategories = await CategoryService.getAllCategories();
    categories.value = allCategories.reduce((acc, current) => {
      acc[current.id] = current.categoryName;
      return acc;
    }, {});

    allQuizzes.value = await Promise.all(quizzesData.map(async (quiz) => {
      if (quiz.imageId) {
        quiz.imageData = await loadImageData(quiz.imageId);
      } else {
        quiz.imageData = '';
      }
      return quiz;
    }));
    displayedQuizzes.value = allQuizzes.value.slice(0, displayLimit.value);
  } catch (error) {
    console.error('Failed to load quizzes:', error);
  }
});

/**
 * Updates the list of quizzes currently being displayed based on the display limit.
 */
function updateDisplayedQuizzes() {
  displayedQuizzes.value = allQuizzes.value.slice(0, displayLimit.value);
}

/**
 * Increases the display limit and updates the displayed quizzes accordingly.
 */

const loadMoreQuizzes = () => {
  displayLimit.value += 5;
  updateDisplayedQuizzes();
};

/**
 * Triggers the confirmation modal for deleting a quiz.
 * @param {number} quizId - The ID of the quiz to potentially delete.
 */

const askDeleteQuiz = (quizId) => {
  pendingDeleteQuizId.value = quizId;
  showConfirmationModal.value = true;
};

/**
 * Confirms the deletion of a quiz and removes it from the display.
 */

const confirmDelete = () => {
  deleteQuiz(pendingDeleteQuizId.value);
  showConfirmationModal.value = false;
};

/**
 * Cancels the deletion of a quiz.
 */

const cancelDelete = () => {
  showConfirmationModal.value = false;
};

/**
 * Navigates to the quiz creator tool with the selected quiz for editing.
 * @param {number} quizId - The ID of the quiz to edit.
 */

const editQuiz = async (quizId) => {
  await router.push({ name: 'QuizcreatorTool', params: { quizId } });
};

/**
 * Deletes a quiz from the database and removes it from the display.
 * @param {number} quizId - The ID of the quiz to delete.
 */

const deleteQuiz = async (quizId) => {
  const isConfirmed = confirm('Are you sure you want to delete this quiz?');

  if (isConfirmed) {
    try {
      await QuizService.deleteQuiz(quizId);
      allQuizzes.value = allQuizzes.value.filter(quiz => quiz.id !== quizId);
      updateDisplayedQuizzes();
    } catch (error) {
      console.error('Failed to delete quiz:', error);
      alert('Error deleting the quiz. Please try again.');
    }
  }
};

/**
 * Fetches the image data for a quiz.
 * @param {number} imageId - The ID of the image to fetch.
 * @returns {string} - The image data as a base64 string.
 */

const loadImageData = async (imageId) => {
  try {
    const imageData = await QuizService.getImageById(imageId);
    return imageData;
  } catch (error) {
    console.error('Failed to load image:', error);
    return '';
  }
};

/**
 * Starts a quiz attempt by fetching the quiz data and navigating to the quiz displayer.
 * @param {Object} quiz - The quiz object to start.
 */
// eslint-disable-next-line
const props = defineProps({
  quiz: Object,
});


/**
 * Starts a quiz session by fetching quiz data and navigating to the Quiz Displayer.
 * @param {Object} quiz - The quiz object to start.
 */

const startQuiz = async (quiz) => {
  try {
    const quizData = await QuizService.getQuizById(quiz.id);
    await store.dispatch('quizAttempt/setQuizData', { quizData, quizId: quiz.id });
    await router.push({ name: 'QuizDisplayer' });
  } catch (error) {
    console.error('Failed to fetch and store quiz data:', error);
  }
};

</script>

<template>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <div class="quizzes-container">
    <h1>
      Personal Quizzes</h1>
    <h2>These are your personal quizzes</h2>
    <div class="quizzes">
      <div class="quiz" v-for="(quiz) in displayedQuizzes" :key="quiz.id">
        <div class="quiz-info">
          <img v-if="quiz.imageData" :src="quiz.imageData" alt="Quiz Image" class="quiz-image"/>
          <div class="quiz-text">
            <h3>{{ quiz.title }}</h3>
            <p>{{ quiz.description }}</p>
            <p class="category-badge">#{{ categories[quiz.categoryId] }}</p>
          </div>
          <div class="action-icons">
            <div @click="startQuiz(quiz)" class="delete-icon">
              <span class="material-icons play-icon">play_arrow</span>
            </div>
            <div @click="editQuiz(quiz.id)" class="delete-icon">
              <span class="material-icons">edit</span>
            </div>
            <div @click="askDeleteQuiz(quiz.id)" class="delete-icon">
              <span class="material-icons delete-quiz-icon">delete</span>
            </div>
            </div>
        </div>
      </div>
      <button v-if="displayedQuizzes.length < allQuizzes.length" @click="loadMoreQuizzes" class="view-more-button">View More</button>

      <ConfirmationModal
        :isVisible="showConfirmationModal"
        message="Are you sure you want to delete this quiz?"
        @confirm="confirmDelete"
        @cancel="cancelDelete"
      />

    </div>
  </div>
</template>

<style scoped>

.quizzes-container {
  max-width: 800px;
  margin-right: auto;
  margin-left: auto;
  display: block;
  padding: 20px;
}

.edit-icon {
  cursor: pointer;
  width: 20px;
  height: 20px;
  position: absolute;
  top: 20px;
  right: 20px;
  fill: #4a5568;
}

.edit-icon:hover {
  fill: rgba(0, 0, 0, 0.8);
}

.quizzes {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 100%;
}

.quiz-info {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 20px;
}

.quiz-image {
  width: 100px;
  height: auto;
  border-radius: 8px;
}

.quiz-text {
  flex: 1;
  min-width: 50%;
}

.quiz {
  background-color: #fafafa;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  transition: transform 0.3s;
  position: relative;
}


.action-icons {
  display: flex;
  align-items: center;
  gap: 8px;
  position: absolute;
  top: 10px;
  right: 10px;
}

.material-icons {
  font-size: 24px;
  cursor: pointer;
}

.material-icons:hover {
  color: #666;
}

.quiz:hover {
  transform: translateY(-5px);
  background-color: #f0f0f0;
}

h1, h2 {
  font-family: 'DM Sans', sans-serif;
  text-align: center;
}

h2 {
  color: #3232ff;
  margin-bottom: 20px;
}

.action-icons {
  position: absolute;
  top: 10px;
  right: 10px;
  display: flex;
  gap: 10px;
}

.view-more-button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #007bff;
  max-width: 20%;

  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.view-more-button:hover {
  background-color: #0056b3;
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

.play-icon {
  color: #3ad83a;
}

.delete-quiz-icon {
  color: #f40404;
}

@media (max-width: 768px) {
  .quiz-info {
    flex-direction: column;
  }

  .quiz-image {
    width: 80%;
    max-width: 200px;
    margin: 0 auto;
  }
}

</style>

