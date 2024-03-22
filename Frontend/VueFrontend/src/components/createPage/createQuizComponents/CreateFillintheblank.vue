<script setup>
import { ref } from 'vue';

const question = ref({
  title: '',
  answer: '' // Only one answer for fill in the blank
});

</script>


<template>
  <div class="question-container">
    <h3>Fill in the Blank</h3>
    <div class="question-box">
      <input v-model="question.title" type="text" placeholder="Enter your question here" class="question-title"/>
      <input v-model="question.answer" type="text" placeholder="Answer" class="answer-text"/>
    </div>
  </div>
</template>



<style scoped>
.question-container {

  display: flex;
  flex-direction: column;
  align-content: center;
  padding: 20px;
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
</style>
