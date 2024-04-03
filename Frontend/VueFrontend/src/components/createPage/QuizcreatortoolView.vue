<script setup>
import { v4 as uuidv4 } from 'uuid';
import { computed, ref } from 'vue';
import { useStore } from 'vuex';
import CreateMultipleChoice from '@/components/createPage/createQuizComponents/CreateMultiplechoice.vue';
import CreateFillInTheBlank from '@/components/createPage/createQuizComponents/CreateFillintheblank.vue';
import CreateStudyCard from '@/components/createPage/createQuizComponents/CreateStudy.vue';
import axios from 'axios';

const store = useStore();

const quizTitle = ref('');
const quizDescription = ref('');
const quizCategory = ref('');
const coverImage = ref(null);

const questions = computed(() => store.state.quizzes.questions);

const quizTypes = ref([
  { id: 'multipleChoice', name: 'Multiple Choice', color: "#CAE9FF" },
  { id: 'fillInTheBlank', name: 'Fill in the Blank', color: "#62B6CB" },
  { id: 'study', name: 'Study', color: "#BEE9E8" }
]);

async function createQuiz(quizData) {
  try {
    // Erstatt 'https://your-backend-api.com/quizzes' m ed URL-en til ditt faktiske endepunkt
    const response = await axios.post('https://api/completeQuiz', quizData);
    console.log('Quiz opprettet:', response.data);
    // Videre håndtering, for eksempel omdirigering eller visning av suksessmelding
  } catch (error) {
    console.error('Feil ved oppretting av quiz:', error);
    // Feilhåndtering, for eksempel visning av feilmelding
  }
}

function addQuestionType(type) {
  console.log('Adding question type:', type);
  const uuid = uuidv4();
  store.dispatch('quizzes/addOrUpdateQuestion', {
    uuid,
    type,
    title: '', // Initial title set as empty
    answers: [], // Initial answers set as empty array
  });
}

function handleQuizData(data) {
  store.dispatch('quizzes/addOrUpdateQuestion', data);
}

function getComponent(type) {
  switch (type) {
    case 'multipleChoice':
      return CreateMultipleChoice;
    case 'fillInTheBlank':
      return CreateFillInTheBlank;
    case 'study':
      return CreateStudyCard;
    default:
      return null;
  }
}


async function compileQuizToJson() {
  const quizData = {
    title: quizTitle.value,
    description: quizDescription.value,
    category: quizCategory.value,
    coverImage: coverImage.value,
    questions: questions.value,
  };

  const quizJson = JSON.stringify(quizData, null, 2);
  console.log(quizJson);

  // Kaller funksjonen for å sende quizdata til server
  await createQuiz(quizData);
}

</script>


<template>
  <div class="app-container">
    <div class="top-container">
      <h1>Quiz Creator Tool</h1>
      <input id="quiz-title-input" v-model="quizTitle" placeholder="Enter title of your quiz" />
      <textarea v-model="quizDescription" placeholder="Enter description of your quiz"></textarea>

      <div id="bottom-container">
        <select v-model="quizCategory">
          <option disabled value="">Choose category</option>
          <option v-for="category in categories" :key="category.value" :value="category.value">
            {{ category.text }}
          </option>
        </select>

        <!-- Quiz Difficulty Dropdown -->
        <select v-model="quizDifficulty">
          <option disabled value="">Choose difficulty</option>
          <option v-for="difficulty in difficulties" :key="difficulty.value" :value="difficulty.value">
            {{ difficulty.text }}
          </option>
        </select>

        <input type="file" id="upload" hidden @change="handleFileUpload" accept="image/*"/>
        <label for="upload">Choose file</label>


      <!-- Image Preview -->
      <div v-if="coverImage" class="image-preview">
        <img :src="coverImage" alt="Cover Image Preview" />
      </div>
        </div>
    </div>


  <div class="quiz-container">
    <div class="quiz-type-selector">
      <h2>Choose question type:</h2>
      <div class="quiz-type-buttons">
        <button
            v-for="quizType in quizTypes"
            :key="quizType.id"
            @click="addQuestionType(quizType.id)"
            :style="{ backgroundColor: quizType.color }"
            class="quiz-type-button"
        >
          + {{ quizType.name }}
        </button>
      </div>
    </div>

    <h2>Your Questions:</h2>

    <!-- Container for quiz components with dynamic border color -->
    <div v-for="question in questions" :key="question.uuid" class="question-editor">
      <component
        :is="getComponent(question.type)"
        :uuid="question.uuid"
        @submitData="handleQuizData"
      />
    </div>

    <button class="compileButton" @click="compileQuizToJson">Compile Quiz to JSON</button>
  </div>
  </div>
</template>




<style scoped>

body {
  font-family: 'DM Sans', sans-serif;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f0f0;
  text-align: center;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 20px;
}

.quiz-type-selector .quiz-type-buttons .quiz-type-button {
  padding: 10px 40px;
  border: none;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s, transform 0.2s;
  cursor: pointer;
  color: #171616; /* Ensure text is readable on colored backgrounds */
  font-family: 'DM Sans', sans-serif;
  font-size: 3rem;
  margin-right: 10px;
  text-align: center;
  //margin-bottom: 40px;

}

.quiz-type-button:hover {
  transform: translateY(-2px);
}

.quiz-container {
  display: flex;
  align-items: center;
  height: 100vh;
  width: 100vw;
  flex-direction: column;
  grid-gap: 30px;

}

.compileButton {
  padding: 10px 20px;
  border: none;
  border-radius: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s, transform 0.2s;
  cursor: pointer;
  color: #ffffff; /* Ensure text is readable on colored backgrounds */
  font-family: 'DM Sans', sans-serif;
  font-size: 1rem;
  margin-right: 10px;
  text-align: center;
  margin-top: 20px;
  margin-bottom: 40px;
  background-color: #007bff;
}

.compileButton:hover {
  transform: translateY(-2px);
  background-color: #47a0d5;
}
.top-container{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: rgb(249, 249, 249);
  border-radius: 20px;
  max-width: 70vw;
  text-align: center;
  padding: 20px 300px 0 300px;
  margin-top: 30px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border: 1px solid #ccc;
}
#quiz-title-input {
  margin: 0 0 0 0;
  font-size: 20px;
  padding: 10px;
  border-radius: 10px;
  border: 1px solid #ccc;

}
.app-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  width: 100%;
}
textarea {
  margin: 10px 0 10px 0;
  font-size: 20px;
  padding: 10px;
  border-radius: 10px;
  font-family:"DM Sans",serif;
  width: 400px;
  border: 1px solid #ccc;
}
.image-preview img {
  max-width: 100%; /* Begrense bredden til bildet for forhåndsvisning */
  max-height: 200px; /* Sett en maksimal høyde for forhåndsvisning */
  object-fit: cover; /* Sørge for at bildet dekker området proporsjonalt */
}
#bottom-container{
  display: flex;
  flex-direction: row;
  align-items: center;
  background-color: rgb(249, 249, 249);
  max-width: 70vw;
  text-align: center;
  margin-top: 30px;
}

</style>