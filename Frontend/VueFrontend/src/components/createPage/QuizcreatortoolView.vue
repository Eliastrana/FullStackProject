<script setup>
import { v4 as uuidv4 } from 'uuid';
import { computed, onMounted, ref } from 'vue'
import { useStore } from 'vuex';
import CreateMultipleChoice from '@/components/createPage/createQuizComponents/CreateMultiplechoice.vue';
import CreateFillInTheBlank from '@/components/createPage/createQuizComponents/CreateFillintheblank.vue';
import CreateStudyCard from '@/components/createPage/createQuizComponents/CreateStudy.vue';
import { QuizService } from '@/services/QuizService.js'

const store = useStore();

const quizTitle = ref('');
const quizDescription = ref('');
const quizCategory = ref('');
const quizDifficulty = ref('');
const coverImage = ref(null);

const questions = computed(() => store.state.quizzes.quizDetails.questions);

onMounted(() => {
  const quizDetails = store.state.quizzes.quizDetails;
  quizTitle.value = quizDetails.title;
  quizDescription.value = quizDetails.description;
  quizCategory.value = quizDetails.category;
  quizDifficulty.value = quizDetails.difficulty;
  coverImage.value = quizDetails.coverImage;
});

const quizTypes = ref([
  { id: 'multipleChoice', name: 'Multiple Choice', color: "#CAE9FF" },
  { id: 'fillInTheBlank', name: 'Fill in the Blank', color: "#62B6CB" },
  { id: 'study', name: 'Study', color: "#BEE9E8" }
]);

const difficulties = ref([
  { value: 'EASY', text: 'Easy' },
  { value: 'MEDIUM', text: 'Medium' },
  { value: 'HARD', text: 'Hard' },
]);

// async function createQuiz(quizData) {
//   try {
//     const response = await axios.post('https://api/completeQuiz', quizData);
//     console.log('Quiz opprettet:', response.data);
//   } catch (error) {
//     console.error('Feil ved oppretting av quiz:', error);
//   }
// }

function addQuestionType(type) {
  console.log('Adding question type:', type);
  const uuid = uuidv4(); // Generate a unique identifier for the question

  let questionTemplate = {
    uuid,
    text: '', // Default text for the question
    questionType: '', // To be set based on type
    tags: [], // Assuming tags might be optional or provided later
    answers: [] // Default answers array
  };

  // Customize the question template based on the type
  switch (type) {
    case 'multipleChoice':
      questionTemplate.questionType = 'MULTIPLE_CHOICE';
      // Initialize with one default answer option
      questionTemplate.answers = [{ text: 'New Answer', correct: false }];
      break;
    case 'fillInTheBlank':
      questionTemplate.questionType = 'FILL_IN_BLANK';
      // Fill in the blank might only need one correct answer
      questionTemplate.answers = [{ text: '', correct: true }];
      break;
    case 'study':
      questionTemplate.questionType = 'STUDY';
      // Study card might not have traditional answers but handled as one correct concept or explanation
      questionTemplate.answers = [{ text: 'Study note or detail', correct: true }];
      break;
  }

  // Dispatch the action to add this new question to the Vuex store
  store.dispatch('quizzes/addOrUpdateQuestion', questionTemplate);
  console.log(questions)
}


function handleQuizData(data) {
  store.dispatch('quizzes/addOrUpdateQuestion', data);
}

function getComponent(type) {
  switch (type) {
    case 'MULTIPLE_CHOICE':
      return CreateMultipleChoice;
    case 'FILL_IN_BLANK':
      return CreateFillInTheBlank;
    case 'STUDY':
      return CreateStudyCard;
    default:
      return null;
  }
}

function updateQuizDetails() {
  console.log('Updating quiz details')
  store.commit('quizzes/SET_QUIZ_DETAILS', {
    title: quizTitle.value,
    description: quizDescription.value,
    category: quizCategory.value,
    difficulty: quizDifficulty.value,
    coverImage: coverImage.value,
  });
}

async function createQuiz() {
  const quizDetails = store.state.quizzes.quizDetails;
  try {
    console.log('Creating quiz:', quizDetails)
    const response = await QuizService.create(quizDetails);
    console.log('Quiz created successfully:', response);
  } catch (error) {
    console.error('Error creating quiz:', error);
  }
}



async function compileQuizToJson() {
  // Assuming store dispatch or commit has already been done to update quizDetails
  const quizData = store.state.quizzes.quizDetails; // Adjust path as necessary

  try {
    const response = await QuizService.create(quizData);
    console.log('Quiz created successfully', response);
  } catch (error) {
    console.error('Error creating quiz', error);
  }
}


</script>


<template>
  <div class="app-container">
    <div class="top-container">
      <h1>Quiz Creator Tool</h1>
      <input id="quiz-title-input" v-model="quizTitle" @blur="updateQuizDetails" placeholder="Enter title of your quiz" />
      <textarea v-model="quizDescription" @blur="updateQuizDetails" placeholder="Enter description of your quiz"></textarea>

      <div id="bottom-container">
        <select v-model="quizCategory" @change="updateQuizDetails">
          <option disabled value="">Choose category</option>
          <option v-for="category in categories" :key="category.value" :value="category.value">
            {{ category.text }}
          </option>
        </select>

        <!-- Quiz Difficulty Dropdown -->
        <select v-model="quizDifficulty" @change="updateQuizDetails">
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
        :is="getComponent(question.questionType)"
        :uuid="question.uuid"
        @submitData="handleQuizData"
        v-bind="question"
      />
    </div>

    <button class="compileButton" @click="compileQuizToJson">Compile Quiz to JSON</button>
    <button class="compileButton" @click="createQuiz">Create Quiz</button>
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