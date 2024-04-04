<script setup>
import { ref, computed, defineProps, defineEmits, watch, onMounted } from 'vue'

const props = defineProps({
  uuid: String,
  text: String,
  answers: Array,
});
const emits = defineEmits(['submitData']);

const title = ref(props.text);
const answers = ref(props.answers);

const coverImage = ref(null);


function addAnswer() {
  answers.value.push({ text: '', correct: false });
}

const canAddMoreAnswers = computed(() => answers.value.length < 4);

// Whenever the title or answers change, emit the update to the parent.
watch([title, answers], () => {
  emits('submitData', {
    uuid: props.uuid,
    text: title.value,
    questionType: 'MULTIPLE_CHOICE',
    answers: answers.value,

  });
}, { deep: true });




function removeAnswer(index) {
  answers.value.splice(index, 1);
}

function removeQuestion() {
  emits('removeQuestion', props.uuid);
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


</script>

<template>
  <div class="question-container">
    <h2>Multiple Choice</h2>
    <div class="remove-icon" @click="removeQuestion" title="Remove">
      <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">
        <path d="M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
        <path d="M6 18L18 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>

    <div v-if="coverImage" class="image-preview">
      <img :src="coverImage" alt="Cover Image Preview" />
      <div class="remove-image" @click="removeImage">&times;</div>
    </div>


    <div v-if="!coverImage">
      <input type="file" id="upload" hidden @change="handleFileUpload" accept="image/*"/>
      <label class="uploadimagebutton" for="upload">Upload Cover Image</label>
    </div>

    <input class="question-input" v-model="title" placeholder="Your question" />
    <div class="answer-group" v-for="(answer, index) in answers" :key="index">

      <button class="remove-answer" @click="removeAnswer(index)" aria-label="Remove answer">
        <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" fill="currentColor">
          <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41l5.59 5.59L5 17.59 6.41 19l5.59-5.59L17.59 19 19 17.59l-5.59-5.59L19 6.41z"/>
        </svg>
      </button>

      <input class="question-answer" v-model="answer.text" placeholder="Answer text" />
      <input class="question-checkbox" type="checkbox" v-model="answer.correct" />
    </div>
    <button id="add-answers" @click="addAnswer" :disabled="!canAddMoreAnswers" aria-label="Add answer">
      <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" fill="currentColor">
        <path d="M19 13H13V19H11V13H5V11H11V5H13V11H19V13Z"/>
      </svg>
    </button>
  </div>

</template>


<style scoped>
.question-container {
  position: relative; /* Enables absolute positioning for children */
  display: flex;
  flex-direction: column;
  padding: 20px;
  padding-top: 40px; /* Increase padding-top to ensure content doesn't overlap with remove icon */
  border-radius: 8px;
  border: none;
  min-width: 500px;
  max-width: 500px;
  background-color: #CAE9FF;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-family: 'DM Sans', sans-serif;
}

.remove-icon {
  cursor: pointer;
  position: absolute;
  top: 10px; /* Adjust top and right values as needed */
  right: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 24px; /* Or size as desired */
  height: 24px; /* Or size as desired */
}

.remove-answer {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
  padding: 0;
  border: none;
  background-color: transparent;
  cursor: pointer;
  margin-bottom: 15px;
}

.remove-answer svg {
  width: 100%;
  height: 100%;
  fill: #f44336; /* Change this color as needed */
}

/* Hover state for better user interaction */
.remove-answer:hover svg {
  fill: #d32f2f; /* Darker shade of red for hover state */
}





.answer-group {
  display: flex;
  align-items: center;
  gap: 10px; /* Space between checkbox and input */
  border: none;
  border-radius: 10px;
}

.question-input{
  margin: 10px 0 30px 0;
  font-size: 20px;
  border: none;
  padding: 10px;
  border-radius: 10px;
}
.question-answer{
  margin: 0 0 15px 0;
  min-width: 70%;
  font-size: 15px;
  border: none;
  padding: 10px;
  border-radius: 10px;
}
.question-checkbox{
  margin: 0 0 15px 0;

  padding: 10px;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  text-align: center;
}
#add-answers {
  padding: 10px 20px;
  border: none;
  border-radius: 10px;
  background-color: #FFD700;
  color: #171616;
  font-family: 'DM Sans', sans-serif;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 10px;
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


#add-answers {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px; /* Adjust based on your preference */
  height: 40px; /* Adjust based on your preference */
  padding: 0;
  border: none;
  background-color: #007bff; /* Green background for visibility */
  border-radius: 50%; /* Circle shape */
  cursor: pointer;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2); /* Optional: Adds shadow for depth */
}

#add-answers svg {
  width: 24px; /* Icon size */
  height: 24px; /* Icon size */
  fill: white; /* Icon color */
}

#add-answers:disabled {
  background-color: #9E9E9E; /* Grey out button when disabled */
  cursor: not-allowed;
}

#add-answers:hover:not(:disabled) {
  background-color: #47a0d5; /* Darker green on hover for an active state */
}



.image-preview img {
  max-width: 100%; /* Begrense bredden til bildet for forhåndsvisning */
  max-height: 200px; /* Sett en maksimal høyde for forhåndsvisning */
  object-fit: cover; /* Sørge for at bildet dekker området proporsjonalt */
}

.image-preview {
  position: relative; /* Required for absolute positioning of children */
  margin: 20px 0; /* Adjust margin as needed */
  /* Keep existing styles */
}

.remove-image {
  max-width: 100%;
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



</style>


