<script setup>
import { ref, onMounted } from 'vue';
import { QuizService } from '@/services/QuizService.js'; // Import QuizService
import { UserService } from '@/services/UserService.js';
import user from '@/store/modules/user.js';
import ConfirmationModal from '@/components/util/ConfirmationModal.vue'

const allQuizzes = ref([]); // Stores all quizzes
const displayLimit = ref(3); // Initial display limit
const displayedQuizzes = ref([]); // Quizzes to be displayed

// Fetch quizzes data from the API when the component is mounted

onMounted(async () => {
  try {
    // Fetch user details to get the userId
    const userDetails = await UserService.getUserDetails();
    const userId = userDetails.id; // Adjust based on the actual structure of userDetails

    // Fetch all quizzes
    const quizzesData = await QuizService.getAllQuizzes();

    console.log('Quizzes data loaded:', quizzesData)

    // Filter quizzes based on the userId
    allQuizzes.value = quizzesData.filter(quiz => quiz.creatorId === userId);

    console.log('Filtered quizzes:', allQuizzes.value)

    // Initially display all (or a subset of) filtered quizzes
    displayedQuizzes.value = allQuizzes.value; // You can also implement pagination or a "View More" button
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
const editQuiz = (id) => {
  alert(`Edit quiz with ID: ${id} `);

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
          <img :src="quiz.image" :alt="quiz.title" class="quiz-image"/>
          <div class="quiz-text">
            <h3>{{ quiz.title }}</h3>
            <p>{{ quiz.description }}</p>
            <p>Category: <strong>{{ quiz.category }}</strong></p>
<!--            <p>Questions: <strong>{{ quiz.questions.length }}</strong></p>-->
          </div>

          <div class="action-icons">
            <div @click="editQuiz(quiz.id)" class="delete-icon">
              <span class="material-icons">edit</span>
            </div>

            <div @click="askDeleteQuiz(quiz.id)" class="delete-icon">
              <span class="material-icons">delete</span>
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
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 800px;
  padding: 20px;
  margin: 5% auto;
  margin-right: 2%;
  margin-left: 2%;

  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  background-color: #ececec;
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

