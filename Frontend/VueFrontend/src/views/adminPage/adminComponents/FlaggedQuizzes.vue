<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';

const quizzes = ref([]);

const flaggedQuizzes = computed(() => {
  // Filter quizzes to include only those that are flagged and where the flagging reason is not null.
  return quizzes.value.filter(quiz => quiz.flagged && quiz.flaggingreason != null);
});


const deleteQuiz = (id) => {
  alert(`Delete quiz with ID: ${id}`);
  // Implementation for deleting a quiz goes here
};

const downloadQuiz = (id) => {
  alert(`Download quiz with ID: ${id}`);
  // Implementation for downloading a quiz goes here
};

const getSeverityClass = (severity) => {
  switch (severity?.toLowerCase()) {
    case 'low':
      return 'severity-low';
    case 'medium':
      return 'severity-medium';
    case 'high':
      return 'severity-high';
    default:
      return ''; // Default class or no class
  }
};

onMounted(async () => {
  try {
    const response = await axios.get('/mockJSON/flagged.json');
    quizzes.value = response.data;
  } catch (error) {
    console.error('Failed to load quizzes:', error);
  }
});
</script>

<template>
  <div class="quizzes-container">
    <div class="headings">
      <h1>Flagged quizzes</h1>
      <h2>Most urgent issues</h2>
    </div>
    <div class="quizzes">
      <!-- Iterate over flaggedQuizzes instead of quizzes -->
      <div
        v-for="(quiz, index) in flaggedQuizzes"
        :key="quiz.id"
        class="quiz"
        :class="getSeverityClass(quiz.severity)"
      >
        <div class="quiz-info">
          <img :src="quiz.image" :alt="quiz.title" class="quiz-image"/>
          <div class="quiz-text">
            <h3>Title: {{ quiz.title }}</h3>
            <h4>Flagging reason: {{ quiz.flaggingreason }}</h4>
            <h4>Sentence/word: {{ quiz.flaggedword }}</h4>
            <h3>Creator: {{ quiz.creator }}</h3>
            <p class="category-badge">#{{ quiz.category }}</p>
            <p>Questions: <strong>{{ quiz.questions?.length || 0 }}</strong></p>
          </div>

          <div class="action-icons">
            <div @click="deleteQuiz(quiz.id)" class="icon delete-icon">
              <span class="material-symbols-outlined">delete</span>
            </div>
            <div @click="downloadQuiz(quiz.id)" class="icon download-icon">
              <span class="material-symbols-outlined">download</span>
            </div>
            <div @click="disableUser(user.userId)" class="block-icon">
              <span class="material-symbols-outlined">do_not_disturb</span>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>




<style scoped>



h4 {
  font-size: 1rem;
  font-weight: normal;
}

.quizzes-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 96%;
  padding: 20px;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  background-color: #ececec;
  margin: 2% auto;
}



.delete-icon {
  cursor: pointer;
  position: absolute;
  top: 20px;
  right: 70px; /* Adjusted to make space for three icons */
  fill: #4a5568;
}

.download-icon {
  cursor: pointer;
  position: absolute;
  top: 20px;
  right: 45px; /* Positioned between delete and block icons */
  fill: #4a5568;
}

.block-icon {
  cursor: pointer;
  position: absolute;
  top: 20px;
  right: 20px; /* Keeps this icon closest to the right edge */
  fill: #4a5568;
}


.quizzes {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around; /* Ensures even spacing and centering */
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
  flex: 1; /* Allows quizzes to grow */
  min-width: calc(50% - 20px); /* Sets minimum width for two quizzes minus gap */
  max-width: calc(50% - 20px); /* Ensures no more than two quizzes per row */
  background-color: #fafafa;
  padding: 20px;
  margin-bottom: 20px; /* Adds space between rows */
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
  position: relative;
  transition: transform 0.3s;
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



/* Severity-based background colors */
.severity-low {
  background-color: #d4edda; /* Light green */
}

.severity-medium {
  background-color: #fff3cd; /* Light yellow */
}

.severity-high {
  background-color: #f8d7da; /* Light red */
}



@media (max-width: 768px) {
  .quizzes-container {
    width: 100%;
    margin: 2% auto; /* Adjust if you want different margins on mobile */
    padding: 10px; /* Adjust padding for smaller screens */
  }

  .quiz {
    /* On smaller screens, make each quiz take full width minus the gaps */
    min-width: 100%;
    max-width: 100%;
    margin-bottom: 15px; /* Adjust spacing between quizzes for tighter layout */
  }

  .quiz-info {
    flex-direction: column; /* Stack quiz information vertically on small screens */
    align-items: center; /* Center-align the content for a neater appearance */
  }

  .quiz-image {
    width: 80px; /* Adjust image size for smaller screens */
    height: auto; /* Maintain aspect ratio */
  }

  .action-icons {
    right: 10px; /* Adjust position for smaller screens if needed */
    top: auto; /* Optionally adjust or remove if positioning needs change */
    bottom: -30px; /* Example to move icons below the content */
  }

  .quiz-text, .category-badge, h3, h4 {
    text-align: center; /* Center-align text for a cleaner look on small screens */
  }
}

</style>
