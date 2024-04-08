<script setup>
import { ref, onMounted } from 'vue';
import { QuizService } from '@/services/QuizService.js'; // Import QuizService
import { UserService } from '@/services/UserService.js';
import user from '@/store/modules/user.js';
import ConfirmationModal from '@/components/util/ConfirmationModal.vue';
import { CategoryService } from '@/services/CategoryService.js'
import { DifficultyService } from '@/services/DifficultyService.js'

const quizzes = ref([]);
const showConfirmationModal = ref(false);
const pendingDeleteQuizId = ref(null);

// Fetch quizzes data from the API when the component is mounted
const loadImageData = async (imageId) => {
  try {
    const imageData = await QuizService.getImageById(imageId);
    return imageData; // This could be a URL or base64-encoded data
  } catch (error) {
    console.error('Failed to load image:', error);
    return ''; // Return a fallback or empty string if the image fails to load
  }
};

const categories = ref({});
const difficulties = ref([]);

// Example of how you might use it within your component
onMounted(async () => {
  try {
    const quizzesFetched = await QuizService.getAllQuizzes();

    const allCategories = await CategoryService.getAllCategories();
    categories.value = allCategories.reduce((acc, current) => {
      acc[current.id] = current.categoryName;
      return acc;
    }, {});

    difficulties.value = await DifficultyService.getAllDifficulties();
    for (const quiz of quizzesFetched) {
      if (quiz.imageId) {
        quiz.imageData = await loadImageData(quiz.imageId);
      }
    }
    quizzes.value = quizzesFetched;
  } catch (error) {
    console.error('Failed to load quizzes:', error);
  }
});


// Function to ask for quiz deletion
const askDeleteQuiz = (quizId) => {
  pendingDeleteQuizId.value = quizId;
  showConfirmationModal.value = true;
};

// Function to delete a quiz after confirmation
const confirmDelete = async () => {
  try {
    await QuizService.deleteQuiz(pendingDeleteQuizId.value);
    quizzes.value = quizzes.value.filter(quiz => quiz.id !== pendingDeleteQuizId.value);
    showConfirmationModal.value = false;
  } catch (error) {
    console.error('Failed to delete quiz:', error);
    // Optionally handle the error, e.g., show an error message to the user
  }
};

// Function to cancel deletion
const cancelDelete = () => {
  showConfirmationModal.value = false;
};



const downloadQuiz = async (quizId) => {
  try {
    const quiz = await QuizService.getQuizById(quizId);
    const quizData = JSON.stringify(quiz);
    const blob = new Blob([quizData], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `${quiz.title}.json`;
    a.click();
    URL.revokeObjectURL(url);
  } catch (error) {
    console.error('Failed to download quiz:', error);
    // Optionally handle the error, e.g., show an error message to the user
  }
};



</script>



<template>

  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

  <div class="quizzes-container">
    <div class="headings">
      <h1>All Quizzes</h1>
      <h2>Monitor all users' quizzes</h2>
    </div>
    <div class="quizzes">
      <div class="quiz" v-for="(quiz, index) in quizzes" :key="quiz.id">
        <div class="quiz-info">


          <img :src="quiz.imageData || '/images/default.png'" alt="Quiz Image" class="quiz-image" />


          <div class="quiz-text">
            <h3>{{quiz.title }}</h3>
            <p>Creator ID: {{ quiz.creatorId }}</p>
            <p>{{ quiz.description }}</p>
            <p class="category-badge">#{{ categories[quiz.categoryId] }}</p>
<!--            <p>Questions: <strong>{{ quiz.questions.length }}</strong></p>-->
          </div>


          <div class="action-icons">

            <div @click="askDeleteQuiz(quiz.id)" class="delete-icon">
              <span class="material-icons">delete</span>
            </div>

            <div @click="downloadQuiz(quiz.id)" class="icon download-icon">
              <span class="material-symbols-outlined">download</span>
            </div>

          </div>
        </div>
      </div>
    </div>

<!--    <button v-if="displayedQuizzes.length < allQuizzes.length" @click="loadMoreQuizzes" class="view-more-button">View More</button>-->


    <ConfirmationModal
      :isVisible="showConfirmationModal"
      message="Are you sure you want to delete this quiz?"
      @confirm="confirmDelete"
      @cancel="cancelDelete"
    />
  </div>
</template>

<style scoped>
.quizzes-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: fit-content;
  margin-right: 1%;
  min-width: 45%;
  padding: 20px;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  background-color: #ececec;
}

.delete-icon {
  cursor: pointer;
  width: 20px;
  height: 20px;
  position: absolute;
  top: 20px;
  right: 20px;
  fill: #4a5568;
}

.delete-icon:hover {
  fill: rgba(0, 0, 0, 0.8);
}

.download-icon {
  cursor: pointer;
  width: 20px;
  height: 20px;
  position: absolute;
  top: 20px;
  right: 50px;
  fill: #4a5568;
}

.download-icon:hover {
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
  align-items: center;
  gap: 20px;
  margin-bottom: -10px;
}

.quiz-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
}


.quiz-text {
  flex: 1;
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

.quiz {
  background-color: #fafafa;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  transition: transform 0.3s;
  position: relative;

}

.quiz:hover {
  transform: translateY(-5px);
  background-color: #f0f0f0;
}

h1 {
  font-family: 'DM Sans', sans-serif;
}

h2 {
  font-family: 'DM Sans', sans-serif;
  color: #3232ff;
}

.headings {
  align-self: stretch;
  text-align: left;
}



</style>
