<script setup>
import { ref } from 'vue';

const question = ref({
  title: '',
  answers: Array.from({ length: 4 }, () => ({ text: '' })), // Creates 4 separate answer objects
  correctAnswer: null // Index of the correct answer
});

// Define a function directly in the script setup scope
function getData() {
  // Assuming you want to return a deep copy of `question` instead of `this.questions`
  // since `this` is not used in <script setup> and `questions` is not defined.
  return JSON.parse(JSON.stringify(question.value));
}



</script>




<template>
  <div class="question-container">
    <h3>Multiple Choice</h3>
    <div class="question-box">
      <input v-model="question.title" type="text" placeholder="Enter your question here" class="question-title"/>

      <div class="answers-container">
        <div class="answer-row" v-for="n in 2" :key="n">
          <div class="answer-group" v-for="index in 2" :key="index + (n-1)*2 - 1">
            <input v-model="question.answers[index + (n-1)*2 - 1].text" type="text" :placeholder="'Answer ' + (index + (n-1)*2)" class="answer-text"/>
            <input type="radio" :value="index + (n-1)*2 - 1" v-model="question.correctAnswer" class="correct-answer-checkbox"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>





<style scoped>
.question-container {
  display: flex;
  flex-direction: column;
  padding: 20px;
  border-radius: 8px;
  border: none;
  min-width: 500px;
  max-width: 500px;
  background-color: #CAE9FF;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-family: 'DM Sans', sans-serif;
}

.question-box {
  margin-right: 20px;
}

.question-title {
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
  border-radius: 10px;
  border: none;
  font-family: 'DM Sans', sans-serif;


}

.answers-container {
  display: flex;
  flex-direction: column;
  gap: 10px; /* Space between rows */
  font-family: 'DM Sans', sans-serif;
  border: none;
  border-radius: 10px;



}

.answer-row {
  display: flex;
  justify-content: space-between;
  border: none;

}

.answer-group {
  display: flex;
  align-items: center;
  gap: 10px; /* Space between checkbox and input */
  border: none;
  border-radius: 10px;


}

.answer-text {
  flex-grow: 1;
  padding: 8px;
  border: none;
  border-radius: 10px;


}

.correct-answer-checkbox {
  /* Adjustments might not be necessary, but ensure they align well */
}
</style>


