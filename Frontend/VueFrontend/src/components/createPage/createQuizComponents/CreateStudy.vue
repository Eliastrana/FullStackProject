<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';

const props = defineProps({
  uuid: String,
  text: String,
  answers: Array
});
const emits = defineEmits(['submitData', 'removeQuestion']);
const question = ref(props.text);
const answers = ref(props.answers);

const coverImageFront = ref(null);
const coverImageBack = ref(null);

watch([question, answers], () => {
  emits('submitData', {
    uuid: props.uuid,
    text: question.value,
    questionType: 'STUDY',
    answers: answers.value.map(answer => ({ text: answer.text, correct: answer.correct }))
  });
}, { deep: true });

function handleFileUploadFront(event) {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      coverImageFront.value = e.target.result;
    };
    reader.readAsDataURL(file);
  }
}

function handleFileUploadBack(event) {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      coverImageBack.value = e.target.result;
    };
    reader.readAsDataURL(file);
  }
}

function removeImageFront() {
  coverImageFront.value = null;
}

function removeImageBack() {
  coverImageBack.value = null;
}

function removeQuestion() {
  emits('removeQuestion', props.uuid);
}
</script>


<template>
  <div class="question-container">
    <h2>Study Card</h2>
    <div class="remove-icon" @click="removeQuestion">
      <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
        <path
          d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41l5.59 5.59L5 17.59 6.41 19l5.59-5.59L17.59 19 19 17.59l-5.59-5.59L19 6.41z" />
      </svg>
    </div>
    <!-- Front Image Upload and Preview -->
    <div v-if="coverImageFront" class="image-preview">
      <img :src="coverImageFront" alt="Front Image Preview" />
      <div class="remove-image" @click="removeImageFront">X</div>
    </div>
    <div v-if="!coverImageFront">
      <input type="file" id="uploadFront" hidden @change="handleFileUploadFront" accept="image/*" />
      <label for="uploadFront" class="uploadimagebutton">Upload Front Image</label>
    </div>

    <input class="question-title" v-model="question" placeholder="Study card question" />

    <!-- Back Image Upload and Preview -->
    <div v-if="coverImageBack" class="image-preview">
      <img :src="coverImageBack" alt="Back Image Preview" />
      <div class="remove-image" @click="removeImageBack">X</div>
    </div>
    <div v-if="!coverImageBack">
      <input type="file" id="uploadBack" hidden @change="handleFileUploadBack" accept="image/*" />
      <label for="uploadBack" class="uploadimagebutton">Upload Back Image</label>
    </div>

    <textarea class="answer-text" v-model="answers[0].text" placeholder="Study card answer"></textarea>
  </div>
</template>



<style scoped>


.remove-icon {
  cursor: pointer;
  position: absolute;
  top: 10px;
  right: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 24px; /* Adjust based on your preference */
  height: 24px; /* Adjust based on your preference */
  color: #000000;
  z-index: auto;
}

.remove-icon svg {
  width: 100%; /* Ensure SVG fills the container */
  height: 100%; /* Ensure SVG fills the container */
  border-radius: 20px;
}

.question-container {
  position: relative; /* Needed for absolute positioning of the remove-icon */
  /* The rest of your styles */
}

.question-container {
  position: relative;
  display: flex;
  flex-direction: column;
  padding: 20px;
  padding-top: 40px; /* Increase top padding to prevent overlap */
  border-radius: 8px;
  border: none;
  min-width: 500px;
  max-width: 500px;
  background-color: #BEE9E8; /* Or adjust to match across components */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-family: 'DM Sans', sans-serif;
}


.question-box {
  margin-bottom: 20px;
  margin-right: 20px;
}

.question-title, .answer-text {
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
  border-radius: 4px;
  border: none;
  font-family: 'DM Sans', sans-serif;
}

.answer-text {
  min-height: 50px; /* Retained for the textarea */
  resize: none; /* Retained for the textarea */
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
