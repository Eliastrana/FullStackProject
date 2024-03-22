<template>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
  <div class="squares-container">
    <div class="square" @click="navigateToLogin">
      <i class="fas fa-book"></i>
      <p>Study</p>
    </div>
    <div class="square" @click="navigateToLogin">
      <i class="fas fa-question-circle"></i>
      <p>Multiple Choice</p>
    </div>
    <div class="square" @click="navigateToLogin">
      <i class="fas fa-pencil-alt"></i>
      <p>Fill in the Blank</p>
    </div>
    <div class="square" @click="navigateToLogin">
      <i class="fas fa-brush"></i>
      <p>Mixed</p>
    </div>
  </div>
</template>

<script setup>
// import { useRouter } from 'vue-router';

// const router = useRouter();


import router from '@/router/index.js'



const navigateToLogin = () => {
  //SET THIS TO GO TO LOGIN WHEN BACKEND IS READY
  router.push({ name: 'QuizcreatorTool' });


};
</script>

<style scoped>
.squares-container {
  margin-left: 40px;
  margin-right: 40px;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.square {
  min-width: calc(25% - 20px);
  min-height: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 150px; /* Adjust based on your needs */
  height: 150px; /* Adjust based on your needs */
  margin: 10px;
  border-radius: 15px; /* Rounded corners */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* Initial shadow */
  transition: box-shadow 0.3s; /* Smooth transition for the shadow */
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
  text-align: center;
  color: #333;
  background-color: #fffff7;
}

.square:hover {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3); /* Increased shadow on hover */
}

.square i {
  margin-bottom: 10px; /* Space between icon and text */
}

.square p {
  margin: 0; /* Remove default margin */
}
</style>
