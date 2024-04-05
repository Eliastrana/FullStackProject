<script setup>
import { ref, computed, defineProps, defineEmits, watch } from 'vue'
import CreateTags from '@/components/createPage/createQuizComponents/CreateTags.vue'

/**
 * Props for the CreateMultiplechoice component
 * @property {String} uuid - The unique identifier for the question
 * @property {String} text - The text of the question
 * @property {Array} answers - The array of answers
 */
const props = defineProps({
  uuid: String,
  text: String,
  answers: Array
})

/**
 * Emits custom events
 * @type {Function}
 */
const emits = defineEmits(['submitData'])

/**
 * The question title
 * @type {import('vue').Ref<string>}
 */
const title = ref(props.text)

/**
 * The answers for the question
 * @type {import('vue').Ref<Array>}
 */
const answers = ref(props.answers)

/**
 * The cover image for the question
 * @type {import('vue').Ref<string>}
 */
const coverImage = ref(null)

/**
 * Adds a new answer to the answers array
 */
function addAnswer() {
  answers.value.push({ text: '', correct: false })
}

/**
 * Computed property to check if more answers can be added
 * @type {import('vue').ComputedRef<boolean>}
 */
const canAddMoreAnswers = computed(() => answers.value.length < 4)

/**
 * Watches for changes in the title and answers and emits the updated data
 */
watch([title, answers], () => {
  emits('submitData', {
    uuid: props.uuid,
    text: title.value,
    questionType: 'MULTIPLE_CHOICE',
    answers: answers.value
  })
}, { deep: true })

/**
 * Removes an answer from the answers array at the specified index
 * @param {number} index - The index of the answer to remove
 */
function removeAnswer(index) {
  answers.value.splice(index, 1)
}

/**
 * Emits the 'removeQuestion' event with the question's uuid
 */
function removeQuestion() {
  emits('removeQuestion', props.uuid)
}

/**
 * Handles the file upload for the question
 * @param {Event} event - The file upload event
 */
function handleFileUpload(event) {
  const file = event.target.files[0]

  if (file) {
    const reader = new FileReader()

    reader.onload = (e) => {
      coverImage.value = e.target.result
    }

    reader.readAsDataURL(file)
  }
}

/**
 * Removes the cover image for the question
 */
function removeImage() {
  coverImage.value = null
}

</script>

<template>
  <div class="question-container">
    <h2>Multiple Choice</h2>
    <div class="remove-icon" @click="removeQuestion" title="Remove">
      <svg viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">
        <path d="M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
        <path d="M6 18L18 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" />
      </svg>
    </div>
    <div v-if="coverImage" class="image-preview">
      <img :src="coverImage" alt="Cover Image Preview" />
      <div class="remove-image" @click="removeImage">&times;</div>
    </div>
    <div v-if="!coverImage">
      <input type="file" id="upload" hidden @change="handleFileUpload" accept="image/*" />
      <label class="uploadimagebutton" for="upload">Upload Cover Image</label>
    </div>
    <input class="question-input" v-model="title" placeholder="Your question" />
    <div class="answer-group" v-for="(answer, index) in answers" :key="index">
      <button class="remove-answer" @click="removeAnswer(index)" aria-label="Remove answer">
        <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" fill="currentColor">
          <path
            d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41l5.59 5.59L5 17.59 6.41 19l5.59-5.59L17.59 19 19 17.59l-5.59-5.59L19 6.41z" />
        </svg>
      </button>
      <input class="question-answer" v-model="answer.text" placeholder="Answer text" />
      <input class="question-checkbox" type="checkbox" v-model="answer.correct" />
    </div>
    <button id="add-answers" @click="addAnswer" :disabled="!canAddMoreAnswers" aria-label="Add answer">
      <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" fill="currentColor">
        <path d="M19 13H13V19H11V13H5V11H11V5H13V11H19V13Z" />
      </svg>
    </button>
    <CreateTags />
  </div>
</template>


<style scoped>
.question-container {
  position: relative;
  display: flex;
  flex-direction: column;
  padding: 20px;
  padding-top: 40px;
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
  top: 10px;
  right: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 24px;
  height: 24px;
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
  fill: #f44336;
}

.remove-answer:hover svg {
  fill: #d32f2f;
}

.answer-group {
  display: flex;
  align-items: center;
  gap: 10px;
  border: none;
  border-radius: 10px;
}

.question-input{
  margin: 10px 0 30px 0;
  font-size: 20px;
  min-width: 100%;
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

#add-answers {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  padding: 0;
  border: none;
  background-color: #007bff;
  border-radius: 50%;
  cursor: pointer;
  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
}

#add-answers svg {
  width: 24px;
  height: 24px;
  fill: white;
}

#add-answers:disabled {
  background-color: #9E9E9E;
  cursor: not-allowed;
}

#add-answers:hover:not(:disabled) {
  background-color: #47a0d5;
}

.image-preview img {
  max-width: 100%;
  max-height: 200px;
  object-fit: cover;
}

.image-preview {
  position: relative;
  margin: 20px 0;
}

.remove-image {
  max-width: 100%;
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

</style>


