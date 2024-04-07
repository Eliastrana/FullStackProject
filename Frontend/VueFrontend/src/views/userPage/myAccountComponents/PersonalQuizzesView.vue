<script setup>
import { defineProps, onMounted, ref } from 'vue'
import { QuizService } from '@/services/QuizService.js' // Import QuizService
import { UserService } from '@/services/UserService.js'
import ConfirmationModal from '@/components/util/ConfirmationModal.vue'
import { CategoryService } from '@/services/CategoryService.js'
import { useRouter } from 'vue-router'
import store from '@/store/index.js'

const allQuizzes = ref([]); // Stores all quizzes
const displayLimit = ref(3); // Initial display limit
const displayedQuizzes = ref([]); // Quizzes to be displayed

const categories = ref({});
const difficulties = ref([]);

const router = useRouter();

// Fetch quizzes data from the API when the component is mounted

onMounted(async () => {
  try {
    // Fetch user details to get the userId
    const userDetails = await UserService.getUserDetails();
    const userId = userDetails.id; // Adjust based on the actual structure of userDetails

    // Fetch all quizzes
    let quizzesData = await QuizService.getAllQuizzes();

    // Filter quizzes based on the userId
    quizzesData = quizzesData.filter(quiz => quiz.creatorId === userId);

    const allCategories = await CategoryService.getAllCategories();
    categories.value = allCategories.reduce((acc, current) => {
      acc[current.id] = current.categoryName;
      return acc;
    }, {});


    // Load image data for each quiz
    allQuizzes.value = await Promise.all(quizzesData.map(async (quiz) => {
      if (quiz.imageId) {
        quiz.imageData = await loadImageData(quiz.imageId);
      } else {
        quiz.imageData = ''; // Fallback or default image
      }
      return quiz;
    }));
    // Initially display all (or a subset of) filtered quizzes
    displayedQuizzes.value = allQuizzes.value.slice(0, displayLimit.value);
  } catch (error) {
    console.error('Failed to load quizzes:', error);
  }
});


// Update the displayed quizzes based on the display limit
function updateDisplayedQuizzes() {
  displayedQuizzes.value = allQuizzes.value.slice(0, displayLimit.value);
}

// Function to load more quizzes
const loadMoreQuizzes = () => {
  displayLimit.value += 5; // Increase the limit
  updateDisplayedQuizzes(); // Update displayed quizzes
};

const showConfirmationModal = ref(false);
const pendingDeleteQuizId = ref(null);

const askDeleteQuiz = (quizId) => {
  pendingDeleteQuizId.value = quizId;
  showConfirmationModal.value = true;
};

const confirmDelete = () => {
  deleteQuiz(pendingDeleteQuizId.value);
  showConfirmationModal.value = false;
};

const cancelDelete = () => {
  showConfirmationModal.value = false;
};


// Edit quiz function
const editQuiz = async (quizId) => {
  await router.push({ name: 'QuizcreatorTool', params: { quizId } });
};

const deleteQuiz = async (quizId) => {
  // Display the confirmation dialog
  const isConfirmed = confirm('Are you sure you want to delete this quiz?');

  // Check if the user clicked 'OK'
  if (isConfirmed) {
    try {
      console.log('Deleting quiz with ID:', quizId);
      await QuizService.deleteQuiz(quizId);
      // Remove the quiz from the displayed quizzes after successful deletion
      allQuizzes.value = allQuizzes.value.filter(quiz => quiz.id !== quizId);
      updateDisplayedQuizzes();
    } catch (error) {
      console.error('Failed to delete quiz:', error);
      alert('Error deleting the quiz. Please try again.');
    }
  }
};


const loadImageData = async (imageId) => {
  try {
    const imageData = await QuizService.getImageById(imageId);
    return imageData; // This could be a URL or base64-encoded data
  } catch (error) {
    console.error('Failed to load image:', error);
    return ''; // Return a fallback or empty string if the image fails to load
  }
};


const props = defineProps({
  quiz: Object,
});

const startQuiz = async (quiz) => {
  try {
    const quizData = await QuizService.getQuizById(quiz.id);
    // Include the quiz ID in the payload
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
      <div class="quiz" v-for="(quiz, index) in displayedQuizzes" :key="quiz.id">
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
  max-width: 800px; /* or your desired width */
  margin-right: auto;
  margin-left: auto;
  display: block; /* Default, but explicitly stated for clarity */
  padding: 20px;
  /* other styles */
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
  align-items: center; /* Center items vertically within the container */
  gap: 8px; /* Adjust the gap between icons */
  position: absolute;
  top: 10px; /* Adjust as needed */
  right: 10px; /* Adjust as needed */
}

/* If icons appear too small or too large, adjust here */
.material-icons {
  font-size: 24px; /* Adjust icon size as necessary */
  cursor: pointer;
}

/* Optional: Add hover effect for better user interaction */
.material-icons:hover {
  color: #666; /* Change as per your theme */
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
  gap: 10px; /* Adjust gap between icons */
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
  display: inline-block; /* Treat the <p> tag more like an inline element */
  background-color: #007bff; /* Example background color */
  color: #ffffff; /* Text color */
  padding: 5px 15px; /* Vertical and horizontal padding */
  border-radius: 20px; /* Rounded corners */
  font-size: 0.8rem; /* Adjust font size as needed */
  margin: 0; /* Remove default <p> margin if needed */
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

