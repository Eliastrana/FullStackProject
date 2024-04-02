<script setup>


import router from '@/router/index.js'


const navigateToCreateQuiz = () => {
  router.push({ name: 'QuizcreatorTool' });
};


</script>

<template>

  <div class="container">
    <h1>Take learning to new heights</h1>
    <img src="/images/displayPage/corpcorewoman.png" alt="illustration">
  </div>

  <div class="container">
    <button @click="navigateToCreateQuiz">Create Quiz </button>
  </div>

</template>

<style scoped>

@import url('https://fonts.googleapis.com/css2?family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&display=swap');

body {
  font-family: 'DM Sans', sans-serif;
  margin: 0;
  padding: 0;
  box-sizing: border-box;

}

h1 {
  font-size: 8rem;
  margin: 0; /* Adjust or remove margin as necessary */
  font-family: 'DM Sans', sans-serif;
}


.container {
  display: flex;
  justify-content: center; /* Centers horizontally */
  align-items: center; /* Centers vertically */
  text-align: center; /* Ensures text within the container is centered */
}

img {
  width: 30%;
  height: auto;
  display: block;
  margin: 0 auto;
  margin-bottom: 180px; /* Adjust margin as necessary */
  margin-right: 5%;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s, transform 0.2s;
  cursor: pointer;
  color: #171616; /* Ensure text is readable on colored backgrounds */
  font-family: 'DM Sans', sans-serif;
  font-size: 4rem;
  margin-right: 10px;
  text-align: center;
  background-color: #FFD700;
}

button:hover {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
  background: #b9a824;

}



</style>