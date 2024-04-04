<script setup>


import router from '@/router/index.js'


const navigateToCreateQuiz = () => {
  router.push({ name: 'QuizcreatorTool' });
};

const scrollDown = () => {
  const newY = window.scrollY + 750; // Adjust the '100' value as needed for scrolling distance
  window.scrollTo({ top: newY, behavior: 'smooth' });
};



</script>

<template>

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />


  <div class="image-container">
        <img class="logo" src="/images/logo/quanda_logo.png" alt="Qanda logo">
  </div>

  <div class="container">
    <h1>Take learning to new heights</h1>
  </div>

  <div class="container">
    <button @click="navigateToCreateQuiz">Create Quiz </button>
  </div>

<!--  <div class="undertitle">-->
<!--    <h2>Explore</h2>-->
<!--</div>-->

<!--  <div class="arrow" @click="scrollDown">-->
<!--    <i class="fas fa-arrow-down"></i> &lt;!&ndash; Font Awesome arrow icon &ndash;&gt;-->
<!--  </div>-->



</template>

<style scoped>

@import url('https://fonts.googleapis.com/css2?family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&display=swap');

.arrow {
  font-size: 3rem;
}

.arrow:hover {
  cursor: pointer;
  text-shadow: 0 0 8px #FFFFAA, 0 0 10px #FFFFAA, 0 0 12px #FFFFAA; /* Glowing effect */

}

body {
  font-family: 'DM Sans', sans-serif;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  background-color: #b9a824;

}

h1 {
  font-size: clamp(2.5rem, 8vw, 10rem);
  margin: 0; /* Juster eller fjern margin etter behov */
  font-family: 'DM Sans', sans-serif;
  font-weight: bold;
  padding-left: 40px;
  padding-right: 40px;
}

h2 {
  font-size: 4rem;
  margin: 0; /* Adjust or remove margin as necessary */
  font-family: 'DM Sans', sans-serif;
  font-weight: bold;
}


.container {
  flex: 1;
  margin-bottom: 80px;
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
  font-size: 3rem;
  margin-right: 10px;
  text-align: center;
  background-color: #FFD700;
}

button:hover {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
  background: #b9a824;
}

.logo {
  width: 100%;
  height: auto;
  display: block;
  margin: 0 auto;

}

.image-container{
  display: flex;
  justify-content: center;
  align-items: center;
  max-width: 25%;
  margin-bottom: -40px;
}

@media (max-width: 768px) {
  .container {
    margin-bottom: 140px; /* Øker avstand til neste element */
  }

  button {
    font-size: 3rem; /* Reduserer skriftstørrelsen for knappen */
    padding: 8px 16px; /* Mindre padding for en mindre knapp */
    border-radius: 15px; /* Mindre avrundede hjørner */
    margin-top: 20px; /* Øker avstand fra toppen, juster etter behov */
    align-self: center; /* Sentrerer knappen hvis .container bruker flex */
  }

  .logo {
    width: 150%; /* Øker størrelsen på logoen i mobilvisning */
    margin-bottom: 20px; /* Øker avstand under logoen */
  }

  h1 {
    font-size: 4rem; /* Juster font-størrelse for h1 for konsistens */
    padding: 0 20px; /* Tilpass padding for å passe bedre i små skjermer */
  }
}

@media (max-width: 480px) {
  .container {
    margin-top: 50px;
    margin-bottom: 50px; /* Juster avstand for enda mindre skjermer */
  }

  button {
    font-size: 2.5rem; /* Ytterligere reduksjon av skriftstørrelse */
    margin-top: 50px; /* Juster avstand fra toppen etter behov */
  }

  .logo {
    width: 200%; /* Gjør logoen enda større for de minste skjermene */
  }
}

</style>