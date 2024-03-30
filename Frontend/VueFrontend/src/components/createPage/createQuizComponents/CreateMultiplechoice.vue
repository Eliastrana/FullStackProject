<script setup>
  import { ref, watch, defineEmits } from 'vue';
  import { debounce } from 'lodash-es'; // Anta at lodash er installert, ellers kan du implementere din egen debounce-funksjon

  const title = ref('');
  const answers = ref([{ text: '' }, { text: '' }, { text: '' }, { text: '' }]);
  const correctAnswer = ref(null);
  const emits = defineEmits(['submitData']);


  // Debounce-funksjonen sørger for at emitCurrentData kun blir kalt
  // hvis det ikke har vært noen endringer i de overvåkede dataene på 500 millisekunder


  const debouncedEmitData = debounce(() => {
  emits('submitData', {
    type: 'multipleChoice',
    question: title.value,
    options: answers.value.map(a => a.text),
    correctOption: correctAnswer.value,
  });
}, 5000); // Juster tidsforsinkelsen etter behov

  // Overvåker endringer i title, answers, og correctAnswer,
  // og bruker den debounced versjonen av emit funksjonen ved endringer.
  watch([title, answers, correctAnswer], debouncedEmitData, { deep: true });
</script>



<template>
  <div class="question-container">
    <h3>Multiple Choice</h3>
    <div class="question-box">
      <input v-model="title" type="text" placeholder="Enter your question here" class="question-title"/>
      <div class="answers-container">
        <div class="answer-row" v-for="(answer, index) in answers" :key="index">
          <input v-model="answer.text" type="text" :placeholder="'Answer ' + (index + 1)" class="answer-text"/>
          <input type="radio" :value="index" v-model="correctAnswer" class="correct-answer-checkbox"/>
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


