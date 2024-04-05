<script setup>
import { v4 as uuidv4 } from 'uuid';
import { computed, onMounted, ref, nextTick } from 'vue'
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


function scrollToBottom() {
  nextTick(() => {
    window.scrollTo({
      top: document.body.scrollHeight,
      behavior: 'smooth',
    });
  });
}

function scrollToTop() {
  window.scrollTo({
    top: 700,
    behavior: 'smooth',
  });
}

function addQuestionType(type) {
  console.log('Adding question type:', type);
  const uuid = uuidv4(); // Generate a unique identifier for the question

  nextTick(scrollToBottom);


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
      questionTemplate.answers = [{ text: '', correct: false }];
      break;
    case 'fillInTheBlank':
      questionTemplate.questionType = 'FILL_IN_BLANK';
      // Fill in the blank might only need one correct answer
      questionTemplate.answers = [{ text: '', correct: true }];
      break;
    case 'study':
      questionTemplate.questionType = 'STUDY';
      // Study card might not have traditional answers but handled as one correct concept or explanation
      questionTemplate.answers = [{ text: '', correct: true }];
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

// Inside your parent component's <script setup>
function removeQuestionFromStore(uuid) {
  // Assuming you have a Vuex action or mutation named 'removeQuestion'
  store.dispatch('quizzes/removeQuestion', uuid);
  // Or use store.commit if directly committing a mutation
}

function handleFileUpload(event) {
  const file = event.target.files[0]; // Get the uploaded file

  if (file) {
    const reader = new FileReader(); // Create a FileReader to read the file

    reader.onload = (e) => {
      coverImage.value = e.target.result; // Set the coverImage ref to the data URL
    };

    reader.readAsDataURL(file); // Read the file as a Data URL
  }
}

function removeImage() {
  coverImage.value = null; // Clears the image, effectively removing it
}


function moveQuestionUp(index) {
  if (index > 0) {
    const questionToMove = questions.value.splice(index, 1)[0];
    questions.value.splice(index - 1, 0, questionToMove);
    // Trigger Vuex store update
    store.dispatch('quizzes/updateQuestionsOrder', questions.value);
  }
}

function moveQuestionDown(index) {
  if (index < questions.value.length - 1) {
    const questionToMove = questions.value.splice(index, 1)[0];
    questions.value.splice(index + 1, 0, questionToMove);
    // Trigger Vuex store update
    store.dispatch('quizzes/updateQuestionsOrder', questions.value);
  }
}
</script>


<template>

  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">


  <div class="app-container">


    <div class="top-container">
      <h1>Quiz Creator Tool</h1>
      <!-- Image Preview -->
      <!-- Image Preview with Remove Button -->
      <div v-if="coverImage" class="image-preview">
        <img :src="coverImage" alt="Cover Image Preview" />
        <div class="remove-image" @click="removeImage">&times;</div>
      </div>

      <div v-if="!coverImage">
        <input type="file" id="upload" hidden @change="handleFileUpload" accept="image/*"/>
        <label class="uploadimagebutton" for="upload">Upload Cover Image</label>
      </div>

      <input id="quiz-title-input" v-model="quizTitle" @blur="updateQuizDetails" placeholder="Enter title of your quiz" />
      <textarea v-model="quizDescription" @blur="updateQuizDetails" placeholder="Enter description of your quiz"></textarea>

      <div id="bottom-container">
        <!-- Quiz Category Dropdown -->
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




      <div v-for="(question, index) in questions" :key="question.uuid" class="question-container">
        <!-- Move Buttons -->
        <div class="move-buttons">

          <button @click="moveQuestionUp(index)" :disabled="index === 0" class="move-button">
            <span class="material-icons">arrow_upward</span>
          </button>

          <button @click="moveQuestionDown(index)" :disabled="index === questions.length - 1" class="move-button">
            <span class="material-icons">arrow_downward</span>
          </button>

        </div>
        <!-- Question Editor -->
        <div class="question-editor">
          <component
            :is="getComponent(question.questionType)"
            :uuid="question.uuid"
            @submitData="handleQuizData"
            @removeQuestion="removeQuestionFromStore"
            v-bind="question"
          />
        </div>
      </div>



      <!--      <button class="compileButton" @click="compileQuizToJson">Compile Quiz to JSON</button>-->
      <button class="compileButton" @click="createQuiz">Create Quiz</button>
    </div>

    <button class="scroll-to-top" @click="scrollToTop">
      <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M4 12l1.41 1.41L12 6.83l6.59 6.58L20 12l-8-8-8 8z"/></svg>
    </button>

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


.top-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: rgb(249, 249, 249);
  border-radius: 20px;
  max-width: 70vw;
  text-align: center;
  padding: 20px;
  margin-top: 30px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.top-container select {
  padding: 10px;
  margin: 10px 0;
  margin-right: 20px;
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
  border: none;
  border-radius: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  cursor: pointer;
  width: auto; /* Adjusted to fit content */
}

.top-container select:focus {
  outline: none; /* Removes default focus outline */
  box-shadow: 0 0 0 2px #62B6CB; /* Adds a custom focus style */
}

#bottom-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 20px; /* Adjust as needed for spacing */
}


#quiz-title-input {
  margin: 0 0 0 0;
  font-size: 20px;
  padding: 10px;
  border-radius: 10px;
  border: none;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);


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
  border: none;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
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
}


.image-preview {
  position: relative; /* Required for absolute positioning of children */
  margin: 20px 0; /* Adjust margin as needed */
  /* Keep existing styles */
}

.remove-image {
  position: absolute;
  top: 0;
  right: 0;
  background-color: rgba(0,0,0,0.6); /* Semi-transparent black */
  color: white; /* White text color */
  padding: 0 5px;
  cursor: pointer;
  border-radius: 0 0 0 10px; /* Rounded corners on the top right */
}

/* Adjustments for better visibility */
.remove-image:hover {
  background-color: rgba(0,0,0,0.8); /* Slightly darker on hover */
}


.uploadimagebutton {
  display: inline-block; /* Change to inline-block for better control */
  margin: 20px 0; /* Increase margin to prevent overlap */
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.uploadimagebutton:hover {
  transform: translateY(-2px);
  background-color: #47a0d5;
}

.scroll-to-top {
  position: fixed;
  right: 20px;
  bottom: 20px;
  background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent black */
  color: white;
  border: none;
  border-radius: 50%;
  width: 48px;
  height: 48px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.3s;
}

.scroll-to-top:hover {
  background-color: rgba(0, 0, 0, 0.8); /* Darker on hover */
}

.scroll-to-top svg {
  fill: white; /* SVG icon color */
  width: 24px;
  height: 24px;
}


.move-buttons {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-bottom: 5px;
}

.move-buttons button {
  padding: 5px 10px;
  background-color: #f0f0f0;
  cursor: pointer;
  border-radius: 20px;
  transition: background-color 0.3s;
  border: none;
}

.move-buttons button:disabled {
  color: #999;
  cursor: not-allowed;
}


.question-container {
  display: flex;
  align-items: flex-start; /* Align items at the start of the container */
  gap: 10px; /* Space between move buttons and the question editor */
}

.move-buttons {
  display: flex;
  flex-direction: column;
  justify-content: center; /* Center the buttons vertically */
  align-items: center; /* Align buttons in the middle of the 'move-buttons' container */
  padding-right: 10px; /* Optional: Adds some space between buttons and question content */
  border: none;

}

.question-editor {
  flex-grow: 1; /* Ensure the question editor occupies the remaining space */
}


.move-button {
  border: none;

  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 5px;
  border-radius: 20px;
}

.material-icons {
  font-size: 24px; /* Adjust icon size as needed */
  color: #000; /* Icon color, change as required */
}

.move-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.move-button:hover {
  background-color: #a9a9a9;
}


.slide-enter-active, .slide-leave-active {
  transition: transform 0.5s ease;
}

/* Enter-to and leave-from styles */
.slide-enter, .slide-leave-to /* Starting state for enter/ending state for leave */ {
  transform: translateY(50px);
  opacity: 0;
}




</style>