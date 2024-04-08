<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';

const quizzes = ref([]);

const flaggedQuizzes = computed(() => {
  return quizzes.value.filter(quiz => quiz.flagged && quiz.flaggingreason != null);
});


const deleteQuiz = (id) => {
  alert(`Delete quiz with ID: ${id}`);
};

const downloadQuiz = (id) => {
  alert(`Download quiz with ID: ${id}`);
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
      return '';
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
  right: 70px;
  fill: #4a5568;
}

.download-icon {
  cursor: pointer;
  position: absolute;
  top: 20px;
  right: 45px;
  fill: #4a5568;
}

.block-icon {
  cursor: pointer;
  position: absolute;
  top: 20px;
  right: 20px;
  fill: #4a5568;
}

.quizzes {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
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
  height: fit-content;
  border-radius: 8px;
}

.quiz-text {
  flex: 1;
}

.category-badge {
  display: inline-block;
  background-color: rgb(23, 22, 22);
  color: #ffffff;
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  margin: 0;
}

.quiz {
  flex: 1;
  min-width: calc(50% - 20px);
  max-width: calc(50% - 20px);
  background-color: #fafafa;
  padding: 20px;
  margin-bottom: 20px;
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
  align-self: stretch;
  text-align: left;
}

.severity-low {
  background-color: #d4edda;
}

.severity-medium {
  background-color: #fff3cd;
}

.severity-high {
  background-color: #f8d7da;
}

@media (max-width: 768px) {
  .quizzes-container {
    width: 100%;
    margin: 2% auto;
    padding: 10px;
  }

  .quiz {
    min-width: 100%;
    max-width: 100%;
    margin-bottom: 15px;
  }

  .quiz-info {
    flex-direction: column;
    align-items: center;
  }

  .quiz-image {
    width: 80px;
    height: auto;
  }

  .action-icons {
    right: 10px;
    top: auto;
    bottom: -30px;
  }

  .quiz-text, .category-badge, h3, h4 {
    text-align: center;
  }
}

</style>
