<template>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
  <div class="squares-container">

    <div class="squareMultiple" @click="navigateToLogin">
      <i class="fas fa-question-circle"></i>
      <p>Multiple Choice</p>
    </div>

    <div class="squareFill" @click="navigateToLogin">
      <i class="fas fa-pencil-alt"></i>
      <p>Fill in the Blank</p>
    </div>

    <div class="squareStudy" @click="navigateToLogin">
      <i class="fas fa-book"></i>
      <p>Study Cards</p>
    </div>

    <div class="squareMixed" @click="navigateToLogin">
      <i class="fas fa-brush"></i>
      <p>Mixed</p>
    </div>

  </div>
</template>

<script setup>
// Importing router from Vue Router
import router from '@/router/index.js'

/**
 * Navigates to the QuizcreatorTool route
 * This function is currently set to navigate to the QuizcreatorTool route when invoked.
 * It should be updated to navigate to the login route when the backend is ready.
 */
const navigateToLogin = () => {
  router.push({ name: 'QuizcreatorTool' });
  // Navigate this to login maybe
};
</script>

<style scoped>
h1 {
  font-family: 'DM Sans', sans-serif;
  font-size: clamp(1.4rem, 10vw, 5rem);
  text-align: left;
  margin-top: 40px;
  margin-bottom: -20px;
}
.squares-container {
  margin-left: 40px;
  margin-right: 40px;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.squareFill {
  min-width: calc(25% - 20px);
  min-height: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 150px;
  height: 150px;
  margin: 10px;
  border-radius: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.3s;
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
  text-align: center;
  color: #333;
  background-color: #62B6CB;
}

.squareStudy {
  min-width: calc(25% - 20px);
  min-height: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 150px;
  height: 150px;
  margin: 10px;
  border-radius: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.3s;
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
  text-align: center;
  color: #333;
  background-color: #BEE9E8;
}

.squareMultiple {
  min-width: calc(25% - 20px);
  min-height: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 150px;
  height: 150px;
  margin: 10px;
  border-radius: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.3s;
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
  text-align: center;
  color: #333;
  background-color: #CAE9FF;
}

.squareMixed {
  min-width: calc(25% - 20px);
  min-height: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 150px;
  height: 150px;
  margin: 10px;
  border-radius: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.3s;
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
  text-align: center;
  color: #333;
  background-image: linear-gradient(to right, #CAE9FF, #BEE9E8, #62B6CB);
}

.squareFill:hover {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  transform: translateY(-2px);
}

.squareStudy:hover {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  transform: translateY(-2px);
}

.squareMultiple:hover {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  transform: translateY(-2px);
}

.squareMixed:hover {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  transform: translateY(-2px);
}

.square:hover {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}

.square i {
  margin-bottom: 10px;
}

.square p {
  margin: 0;
}

@media screen and (max-width: 760px) {
  .squares-container {
    flex-direction: column;
    align-items: center;
  }

  .squareFill, .squareStudy, .squareMultiple, .squareMixed {
    min-width: 100%;
    min-height: 100px;
    width: auto;
    padding-top: 20px;
    height: auto;
    margin: 10px 0;
  }

  .squareFill i, .squareStudy i, .squareMultiple i, .squareMixed i {
    font-size: 4rem;
  }

  .squareFill p, .squareStudy p, .squareMultiple p, .squareMixed p {
    font-size: 1rem;
  }
}
</style>
