<script setup>

</script>

<template>
  <div class="container">
    <h1>How to create a quiz</h1>
    <h2>Creating a quiz is easy! Just follow these steps:</h2>
    <div class="steps-container">
      <div class="step-container">
        <div class="step">Select what type of quiz you want to make. Choose one of our modes, or combine them in mixed mode!</div>
      </div>
      <div class="arrow">→</div>
      <div class="step-container">
        <div class="step">Add your questions and answers. Make sure the question and answer matches!</div>
      </div>
      <div class="arrow">→</div>
      <div class="step-container">
        <div class="step">Give the quiz a name, and save it. Now you can test yourself or others!</div>
      </div>
    </div>
  </div>
</template>


<style scoped>
.container {
  margin-top: 20px;
}

h1 {
  font-family: 'DM Sans', sans-serif;
  font-size: clamp(2.5rem, 8vw, 5rem);
  text-align: left;
  margin-top: 20px;
  margin-left: 40px;
}

h2 {
  font-family: 'DM Sans', sans-serif;
  font-size: clamp(1.5rem, 3vw, 2rem);
  text-align: left;
  margin-left: 40px;
  color: #3232ff;
}

p {
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
  text-align: left;
  margin-left: 40px;
}

.steps-container {
  display: flex;
  align-items: center;
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
  text-align: left;

  margin: 20px 40px;

}

.step-container {
  background-color: #f5f5f5; /* Light gray background */
  border-radius: 15px; /* Rounded corners */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Box shadow */
  padding: 20px; /* Padding inside each step container */
  margin: 10px 0; /* Margin around each step container */
}

.arrow {
  font-size: 2rem; /* Arrow size */
  padding: 0 20px; /* Padding around arrows */
}

.step {
  margin: 0; /* Remove default margin */
}

@media (max-width: 760px) {
  .steps-container {
    flex-direction: column;
    align-items: center; /* Sentrerer elementene horisontalt */
  }

  .step-container {
    text-align: center; /* Sørger for at teksten inni hver container er sentrert */
    width: 100%; /* Juster bredde etter behov for å gi mer plass */
  }

  .arrow {
    transform: rotate(90deg);
    padding: 10px 0;
    align-self: center; /* Sentrerer pilene */
  }
}

</style>

