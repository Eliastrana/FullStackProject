<script setup>
import { ref, defineProps, defineEmits, watch } from 'vue';
import CreateTags from '@/components/createPage/createQuizComponents/CreateTags.vue'
import store from '@/store/index.js'

/**
 * Props for the CreateFillintheblank component
 * @property {String} uuid - The unique identifier for the question
 * @property {String} text - The text of the question
 * @property {Array} answers - The array of answers
 */
const props = defineProps({
  uuid: String,
  text: String,
  answers: {
    type: Array,
    default: () => [{ text: '' }] // Ensure this structure matches what you expect
  },
  tags: Array
});


/**
 * Emits custom events
 * @type {Function}
 */
const emits = defineEmits(['submitData', 'removeQuestion']);

/**
 * The question title
 * @type {import('vue').Ref<string>}
 */
const title = ref(props.text);

/**
 * The answers for the question
 * @type {import('vue').Ref<Array>}
 */
const answers = ref(props.answers);
const tags = ref(props.tags || []);

function handleTagUpdate(newTags) {
  store.dispatch('quizzes/updateQuestionTags', {
    uuid: props.uuid,
    newTags
  });
}

/**
 * Watches for changes in the title and answers and emits the updated data
 */
watch([title, answers, tags], () => {
  emits('submitData', {
    uuid: props.uuid,
    text: title.value,
    questionType: 'FILL_IN_BLANK',
    answers: answers.value,
    tags: tags.value // Include the updated tags in the emitted data
  });
}, { deep: true });

/**
 * Emits the 'removeQuestion' event with the question's uuid
 */
function removeQuestion() {
  emits('removeQuestion', props.uuid);
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
    <input class="question-title" v-model="title" placeholder="Question title" />
    <input class="answer-text" v-model="answers[0].text" placeholder="Correct answer for the blank" />
    <CreateTags :initialTags="tags" @update-tags="handleTagUpdate" />
  </div>
</template>


<style scoped>

.question-container {
  position: relative;
  display: flex;
  flex-direction: column;
  align-content: center;
  padding: 20px;
  padding-top: 40px;
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
  top: 10px;
  right: 10px;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.remove-icon svg {
  width: 100%;
  height: 100%;
  border-radius: 20px;
}

.question-box {
  margin-bottom: 20px;
  margin-right: 20px;
}

.question-title, .answer-text {
  width: 100%;
  padding: 8px;
  font-size: 1rem;
  margin-bottom: 10px;
  border-radius: 8px;
  border: none;
  font-family: 'DM Sans', sans-serif;
  margin-right: 20px;

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
