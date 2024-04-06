<script setup>
import { ref, onMounted } from 'vue';
import { QuizService } from '@/services/QuizService.js'; // Import QuizService
import { UserService } from '@/services/UserService.js';
import user from '@/store/modules/user.js';
import ConfirmationModal from '@/components/util/ConfirmationModal.vue';

const quizzes = ref([]);
const showConfirmationModal = ref(false);
const pendingDeleteQuizId = ref(null);

// Fetch quizzes data from the API when the component is mounted
onMounted(async () => {
  try {
    quizzes.value = await QuizService.getAllQuizzes();
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

// Edit quiz function
const editQuiz = (id) => {
  // Your edit logic here
  console.log(`Edit quiz with ID: ${id}`);
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
          <img :src="quiz.image" :alt="quiz.title" class="quiz-image"/>
          <div class="quiz-text">
            <h3>{{quiz.title }}</h3>
            <p>Creator ID: {{ quiz.creatorId }}</p>
            <p>{{ quiz.description }}</p>
            <p class="category-badge">#{{ quiz.category }}</p>
<!--            <p>Questions: <strong>{{ quiz.questions.length }}</strong></p>-->
          </div>


          <div class="action-icons">

            <div @click="askDeleteQuiz(quiz.id)" class="delete-icon">
              <span class="material-icons">delete</span>
            </div>

            <div @click="disableUser(quiz.id)" class="icon download-icon">
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
  width: 20px; /* Adjust icon size as needed */
  height: 20px; /* Adjust icon size as needed */
  position: absolute;
  top: 20px;
  right: 20px;
  fill: #4a5568; /* Icon color */
}

.delete-icon:hover {
  fill: rgba(0, 0, 0, 0.8); /* Icon color on hover */
}

.download-icon {
  cursor: pointer;
  width: 20px; /* Adjust icon size as needed */
  height: 20px; /* Adjust icon size as needed */
  position: absolute;
  top: 20px;
  right: 50px;
  fill: #4a5568; /* Icon color */
}

.download-icon:hover {
  fill: rgba(0, 0, 0, 0.8); /* Icon color on hover */
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
  width: 100px; /* Adjust the image size as needed */
  height: fit-content;
  border-radius: 8px;
}

.quiz-text {
  flex: 1;
}


.category-badge {
  display: inline-block; /* Treat the <p> tag more like an inline element */
  background-color: rgb(23, 22, 22); /* Example background color */
  color: #ffffff; /* Text color */
  padding: 5px 15px; /* Vertical and horizontal padding */
  border-radius: 20px; /* Rounded corners */
  font-size: 0.8rem; /* Adjust font size as needed */
  margin: 0; /* Remove default <p> margin if needed */
}

.quiz {
  background-color: #fafafa;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  transition: transform 0.3s;
  position: relative; /* This is necessary to position the edit icon correctly */

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
  align-self: stretch; /* Makes the headings container take the full width */
  text-align: left; /* Aligns the text to the left */
}



</style>
