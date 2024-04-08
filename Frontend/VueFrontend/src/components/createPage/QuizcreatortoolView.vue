//QuiacreatortoolView.vue
<script setup>
/**
 * A component for creating and updating quizzes. It allows users to input quiz details,
 * add questions of various types, upload a cover image, and set the quiz as public or private.
 * The component makes use of Vuex for state management and Vue Router for navigation.
 *
 * @component
 */

import { v4 as uuidv4 } from 'uuid';
import { computed, onMounted, ref, nextTick } from 'vue'
import { useStore } from 'vuex';
import CreateMultipleChoice from '@/components/createPage/createQuizComponents/CreateMultiplechoice.vue';
import CreateFillInTheBlank from '@/components/createPage/createQuizComponents/CreateFillintheblank.vue';
import CreateStudyCard from '@/components/createPage/createQuizComponents/CreateStudy.vue';
import { QuizService } from '@/services/QuizService.js'
import router from '@/router/index.js'
import { CategoryService } from '@/services/CategoryService.js'
import { useRoute } from 'vue-router'

// Stateful references and their initial states.
const store = useStore();
const formSubmitted = ref(false);
const route = useRoute();
const quizTitle = ref('');
const quizDescription = ref('');
const quizCategory = ref('');
const quizDifficulty = ref('');
const coverImage = ref(null);
const categories = ref([]);
const showMessage = ref(false);


// Computed properties to validate form fields.
const isTitleValid = computed(() => /^.{1,100}$/.test(quizTitle.value));
const isDescriptionValid = computed(() => quizDescription.value.trim().length > 0);
const isCategoryValid = computed(() => quizCategory.value.trim().length > 0);
const isDifficultyValid = computed(() => ['EASY', 'MEDIUM', 'HARD'].includes(quizDifficulty.value));
const isFormValid = computed(() => isTitleValid.value && isDescriptionValid.value && isCategoryValid.value && isDifficultyValid.value);
const questions = computed(() => store.state.quizzes.quizDetails.questions);


/**
 * Fetches quiz details and categories upon component mount. It also prepares the quiz for editing if a quizId is provided.
 */
onMounted(async () => {
  const quizId = route.params.quizId;
  if (quizId) {
    await store.dispatch('quizzes/addImageToQuiz', { quizId });
    const quizDetails = await QuizService.getQuizById(quizId);
    quizTitle.value = quizDetails.title;
    quizDescription.value = quizDetails.description;
    quizCategory.value = quizDetails.categoryId;
    quizDifficulty.value = quizDetails.difficulty;
    if (quizDetails.imageName) {
      coverImage.value = `data:image/${quizDetails.imageType};base64,${quizDetails.imageData}`;
    }
    store.commit('quizzes/SET_QUIZ_DETAILS', quizDetails);
  } else {
  try {
    const quizDetails = store.state.quizzes.quizDetails;
    quizTitle.value = quizDetails.title;
    quizDescription.value = quizDetails.description;
    quizCategory.value = quizDetails.categoryId;
    quizDifficulty.value = quizDetails.difficulty;
    if (quizDetails.coverImage) {
      coverImage.value = `data:image/${quizDetails.imageType};base64,${quizDetails.imageData}`;
    }
  } catch (error) {
    console.error('Error fetching categories:', error);
  }
  }
  categories.value = await CategoryService.getAllCategories();

});

// Definitions of quiz types and their properties.
const quizTypes = ref([
  { id: 'multipleChoice', name: 'Multiple Choice', color: "#CAE9FF" },
  { id: 'fillInTheBlank', name: 'Fill in the Blank', color: "#62B6CB" },
  { id: 'study', name: 'Study', color: "#BEE9E8" }
]);

// Difficulty levels for the quiz.
const difficulties = ref([
  { value: 'EASY', text: 'Easy' },
  { value: 'MEDIUM', text: 'Medium' },
  { value: 'HARD', text: 'Hard' },
]);

/**
 * Scrolls the window to the bottom of the page. Used after adding a new question to the quiz.
 */
function scrollToBottom() {
  nextTick(() => {
    window.scrollTo({
      top: document.body.scrollHeight,
      behavior: 'smooth',
    });
  });
}

/**
 * Scrolls the window to the top of the page.
 */

function scrollToTop() {
  window.scrollTo({
    top: 0,
    behavior: 'smooth',
  });
}

/**
 * Adds a new question to the quiz. The question type is determined by the type parameter.
 * @param {string} type - The type of question to add.
 */

function addQuestionType(type) {
  const uuid = uuidv4();
  nextTick(scrollToBottom);
  let questionTemplate = {
    uuid,
    text: '',
    questionType: '',
    tags: [],
    answers: [],
    image: null,
    imageFront: null,
    imageBack: null,
  };

  switch (type) {
    case 'multipleChoice':
      questionTemplate.questionType = 'MULTIPLE_CHOICE';
      questionTemplate.answers = [{ text: '', correct: false }];
      break;
    case 'fillInTheBlank':
      questionTemplate.questionType = 'FILL_IN_BLANK';
      questionTemplate.answers = [{ text: '', correct: true }];
      break;
    case 'study':
      questionTemplate.questionType = 'STUDY';
      questionTemplate.answers = [{ text: '', correct: true }];
      break;
  }
  store.dispatch('quizzes/addOrUpdateQuestion', questionTemplate);
}


/**
 * Handles the data submitted by a question component and updates the store with the new question.
 * @param {Object} data - The data submitted by the question component.
 */
function handleQuizData(data) {
  store.dispatch('quizzes/addOrUpdateQuestion', data);
}


/**
 * Returns the component corresponding to the question type.
 * @param {string} type - The type of question.
 * @returns {Object} - The component corresponding to the question type.
 */
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

/**
 * Updates the quiz details in the store with the current values of the form fields.
 */

function updateQuizDetails() {
  store.commit('quizzes/SET_QUIZ_DETAILS', {
    title: quizTitle.value,
    description: quizDescription.value,
    categoryId: quizCategory.value,
    difficulty: quizDifficulty.value,
    coverImage: coverImage.value,
  });
  formSubmitted.value = true;
}


/**
 * Saves the quiz to the database. If a quizId is provided, the quiz is updated, otherwise a new quiz is created.
 */
async function saveQuiz() {
  formSubmitted.value = true;
  if (!isTitleValid.value) {
    console.error('The quiz title is invalid.');
    return;
  }
  const quizDetails = store.state.quizzes.quizDetails;
  const quizId = route.params.quizId;

  try {
    if (quizId) {
      await QuizService.updateQuiz(quizId, quizDetails);
    } else {
      await QuizService.create(quizDetails);
    }

    store.commit('quizzes/CLEAR_QUIZZES');
    showMessage.value = true;
    setTimeout(() => {
      showMessage.value = false;
      router.push('/MyAccount');
    }, 1500);

  } catch (error) {
    console.error('Error processing quiz:', error);
  }
}


/**
 * Removes a question from the store.
 * @param {string} uuid - The UUID of the question to remove.
 */
function removeQuestionFromStore(uuid) {
  store.dispatch('quizzes/removeQuestion', uuid);
}

/**
 * Handles the file upload event and updates the store with the image data.
 * @param {Event} event - The file upload event.
 */
function handleFileUpload(event) {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      coverImage.value = e.target.result;
      const [imageType, imageData] = e.target.result.split(';base64,');
      store.commit('quizzes/SET_QUIZ_DETAILS', {
        ...store.state.quizzes.quizDetails,
        imageName: file.name,
        imageType: imageType.split(':')[1],
        imageData: imageData,
      });
    };
    reader.readAsDataURL(file);
  }
}


/**
 * Removes the cover image from the quiz.
 */
function removeImage() {
  coverImage.value = null;
}

/**
 * Moves a question up in the list of questions.
 * @param {number} index - The index of the question to move.
 */
function moveQuestionUp(index) {
  if (index > 0) {
    const questionToMove = questions.value.splice(index, 1)[0];
    questions.value.splice(index - 1, 0, questionToMove);
    store.dispatch('quizzes/updateQuestionsOrder', questions.value);
  }
}

/**
 * Moves a question down in the list of questions.
 * @param {number} index - The index of the question to move.
 */
function moveQuestionDown(index) {
  if (index < questions.value.length - 1) {
    const questionToMove = questions.value.splice(index, 1)[0];
    questions.value.splice(index + 1, 0, questionToMove);
    store.dispatch('quizzes/updateQuestionsOrder', questions.value);
  }
}

/**
 * Toggles the public/private status of the quiz.
 */
const isPublicCheckbox = computed({
  get: () => store.state.quizzes.quizDetails.isPublic,
  set: (value) => {
    store.commit('quizzes/SET_QUIZ_DETAILS', { ...store.state.quizzes.quizDetails, isPublic: value });
  },
});

</script>

<template>
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <div class="app-container">
    <div class="top-container">
      <h1>Add your information</h1>
      <div>
        <input type="checkbox" id="lock" v-model="isPublicCheckbox" />
        <label for="lock" class="lock-label">
      <span class="lock-wrapper">
        <span class="shackle"></span>
        <svg class="lock-body" width="" height="" viewBox="0 0 28 28" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path fill-rule="evenodd" clip-rule="evenodd" d="M0 5C0 2.23858 2.23858 0 5 0H23C25.7614 0 28 2.23858 28 5V23C28 25.7614 25.7614 28 23 28H5C2.23858 28 0 25.7614 0 23V5ZM16 13.2361C16.6137 12.6868 17 11.8885 17 11C17 9.34315 15.6569 8 14 8C12.3431 8 11 9.34315 11 11C11 11.8885 11.3863 12.6868 12 13.2361V18C12 19.1046 12.8954 20 14 20C15.1046 20 16 19.1046 16 18V13.2361Z" fill="white"></path>
        </svg>
      </span>
      </label>
      </div>
      <div v-if="coverImage" class="image-preview">
        <img :src="coverImage" alt="Cover Image Preview" />
        <div class="remove-image" @click="removeImage">&times;</div>
      </div>
      <div v-if="!coverImage">
        <input type="file" id="upload" hidden @change="handleFileUpload" accept="image/*"/>
        <label class="uploadimagebutton" for="upload">Upload Cover Image</label>
      </div>
      <input id="quiz-title-input"
             v-model="quizTitle"
             placeholder="Enter title of your quiz"
             @blur="updateQuizDetails" />
      <p v-if="!isTitleValid && formSubmitted" style="color: red;">
        Title must be between 1 and 100 characters long.
      </p>
      <textarea v-model="quizDescription"
                @blur="updateQuizDetails"
                placeholder="Enter description of your quiz"></textarea>
      <p v-if="!isDescriptionValid && formSubmitted" style="color: red;">
        Description is required.
      </p>
      <div id="bottom-container">
        <select v-model="quizCategory"
                @change="updateQuizDetails"
                @blur="updateQuizDetails"
                :class="{'select-invalid': !isCategoryValid && formSubmitted}">
          <option disabled value="">Choose category</option>
          <option v-for="category in categories" :key="category.id" :value="category.id">
            {{ category.categoryName }}
          </option>
        </select>
        <select v-model="quizDifficulty"
                @change="updateQuizDetails"
                @blur="updateQuizDetails"
                :class="{'select-invalid': !isDifficultyValid && formSubmitted}">
          <option disabled value="">Choose difficulty</option>
          <option v-for="difficulty in difficulties" :key="difficulty.value" :value="difficulty.value">
            {{ difficulty.text }}
          </option>
        </select>
      </div>
    </div>
    <div class="quiz-container">

      <div class="quiz-type-selector" v-if="questions.length === 0">
        <h2>Choose question type:</h2>
        <div class="quiz-type-buttons" >
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
      <transition-group name="fade" tag="div" class="quiz-type-buttons">
      <div v-for="(question, index) in questions" :key="question.uuid" class="question-container">
        <div class="move-buttons">
          <button @click="moveQuestionUp(index)" :disabled="index === 0" class="move-button">
            <span class="material-icons">arrow_upward</span>
          </button>
          <button @click="moveQuestionDown(index)" :disabled="index === questions.length - 1" class="move-button">
            <span class="material-icons">arrow_downward</span>
          </button>
        </div>
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
      </transition-group>
      <div class="quiz-type-selector" v-if="questions.length > 0">
        <h2>Add another question!</h2>

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
      <button class="compileButton" @click="saveQuiz" :disabled="!isFormValid">Save Quiz</button>
    </div>

    <transition name="fade">
      <div class="success-message" v-if="showMessage">
        Your quiz has been created!
      </div>
    </transition>


    <button class="scroll-to-top" @click="scrollToTop">
      <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><path d="M4 12l1.41 1.41L12 6.83l6.59 6.58L20 12l-8-8-8 8z"/></svg>
    </button>
  </div>
</template>

<style scoped>

.select-invalid {
  border: 2px solid red;
}

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
  color: #171616;
  font-family: 'DM Sans', sans-serif;
  font-size: 3rem;
  margin-right: 10px;
  text-align: center;
  margin-bottom: 20px;

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
  color: #ffffff;
  font-family: 'DM Sans', sans-serif;
  font-size: 1rem;
  margin-right: 10px;
  text-align: center;
  margin-top: 20px;
  margin-bottom: 20px;
  background-color: #007bff;
}

.compileButton:hover {
  transform: translateY(-2px);
  background-color: #47a0d5;
}

.compileButton:disabled {
  background-color: #f0f0f0;
  color: #999;
  cursor: not-allowed;
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
  width: auto;
}

.top-container select:focus {
  outline: none;
  box-shadow: 0 0 0 2px #62B6CB;
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
  max-width: 100%;
  max-height: 200px;
  object-fit: cover;
}


#bottom-container{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background-color: rgb(249, 249, 249);
  max-width: 70vw;
  text-align: center;

}


.image-preview {
  position: relative;
  margin: 20px 0;
}

.remove-image {
  position: absolute;
  top: 0;
  right: 0;
  background-color: rgba(0,0,0,0.6);
  color: white;
  padding: 0 5px;
  cursor: pointer;
  border-radius: 0 0 0 10px;
}

.remove-image:hover {
  background-color: rgba(0,0,0,0.8);
}


.uploadimagebutton {
  display: inline-block;
  margin: 20px 0;
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
  background-color: rgba(0, 0, 0, 0.5);
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
  background-color: rgba(0, 0, 0, 0.8);
}

.scroll-to-top svg {
  fill: white;
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
  align-items: flex-start;
  gap: 10px;
  padding-bottom: 40px;
}

quiz-type-buttons {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 10px;
}

.move-buttons {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-right: 10px;
  border: none;

}

.question-editor {
  flex-grow: 1;
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
  font-size: 24px;
  color: #000;
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
.slide-enter, .slide-leave-to  {
  transform: translateY(50px);
  opacity: 0;
}

.fade-leave-active {
  transition: opacity 0.5s ease;
}
.fade-leave-to {
  opacity: 0;
}

.question-editor > * {
  box-sizing: border-box;
  margin: 0 auto;
  max-width: 100%;
}

#lock {
  display: none;
}
.lock-label {
  width: 45px;
  height: 45px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #e81717;
  border-radius: 15px;
  cursor: pointer;
  transition: all 0.3s;
}
.lock-wrapper {
  width: fit-content;
  height: fit-content;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.shackle {
  background-color: transparent;
  height: 9px;
  width: 14px;
  border-top-right-radius: 10px;
  border-top-left-radius: 10px;
  border-top: 3px solid white;
  border-left: 3px solid white;
  border-right: 3px solid white;
  transition: all 0.3s;
}
.lock-body {
  width: 15px;
}
#lock:checked + .lock-label .lock-wrapper .shackle {
  transform: rotateY(150deg) translateX(3px);
  transform-origin: right;
}
#lock:checked + .lock-label {
  background-color: #007bff;
}
.lock-label:active {
  transform: scale(0.9);
}

.select-invalid {
  border: 2px solid red;
}

.success-message {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.75);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000; /* Ensure it covers everything */
  font-size: 4rem;
}


.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
  opacity: 0;
}





@media (max-width: 768px) {
  .top-container,
  #bottom-container,
  .quiz-container {
    max-width: 90vw;
    padding: 10px;
    margin-top: 20px;
  }

  #bottom-container {
    flex-direction: column;
    align-items: stretch;
  }

  #bottom-container select {
    width: 100%;
    margin-bottom: 10px;
  }

  #quiz-title-input,
  textarea {
    width: 80vw;
    font-size: 16px;
  }

  .quiz-type-buttons .quiz-type-button {
    padding: 10px;
    font-size: 16px;
    margin: 5px;
  }

  .question-container {
    flex-direction: column;
  }

  .move-buttons,
  .question-editor {
    width: 100%;
    padding: 0 5px;
  }

  .scroll-to-top {
    width: 40px;
    height: 40px;
    right: 10px;
    bottom: 10px;
  }

  .image-preview img {
    max-width: 80vw;
    height: auto;
  }

  .uploadimagebutton {
    width: 100%;
    font-size: 16px;
  }

  .compileButton {
    width: 50%;
    font-size: 14px;
  }
}

@media (max-width: 480px) {


  .quiz-type-buttons .quiz-type-button {
    padding: 10px;
    font-size: 14px;
    margin: 5px;
  }

  .scroll-to-top {
    width: 30px;
    height: 30px;
    right: 5px;
    bottom: 5px;
  }

  .quiz-container {
    max-width: 90vw;
    padding: 10px;
    margin-top: 20px;
  }

}
</style>