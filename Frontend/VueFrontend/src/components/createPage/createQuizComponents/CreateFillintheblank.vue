<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';

const props = defineProps({
  uuid: String,
  text: String,
  answers: Array // Assuming you are handling it in a similar way to multiple choice
});
const emits = defineEmits(['submitData']);

const title = ref(props.text);
const answers = ref(props.answers);
const coverImage = ref(null);


watch([title, answers], () => {
  console.log('Submitting data for fill in the blank question');
  emits('submitData', {
    uuid: props.uuid,
    text: title.value,
    questionType: 'FILL_IN_BLANK',
    answers: [{ text: answers.value, correct: true }]
  });
}, { deep: true });

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
    <h2>Fill in the Blank</h2>
    <div class="remove-icon" @click="removeQuestion">
      <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
        <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41l5.59 5.59L5 17.59 6.41 19l5.59-5.59L17.59 19 19 17.59l-5.59-5.59L19 6.41z"/>
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

    <input class="question-title" v-model="title" placeholder="Question title" />

    <input class="answer-text" v-model="answers[0].text" placeholder="Correct answer for the blank" />
  </div>
</template>


<style scoped>

.question-container {
  position: relative; /* Establishes a positioning context */
  display: flex;
  flex-direction: column;
  align-content: center;
  padding: 20px;
  padding-top: 40px; /* Increase padding-top to avoid overlapping with the remove icon */
  border-radius: 10px;
  border: none;
  min-width: 500px;
  max-width: 500px;
  background-color: #62B6CB;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-family: 'DM Sans', sans-serif;
  margin-right: 20px;
  margin-left: 20px;
}

.remove-icon {
  cursor: pointer;
  position: absolute;
  top: 10px; /* Adjusted for visual consistency */
  right: 10px;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.remove-icon svg {
  width: 100%; /* SVG fills the container */
  height: 100%;
  border-radius: 20px; /* Rounded corners for the background */
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
  margin-right: 20px;

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
