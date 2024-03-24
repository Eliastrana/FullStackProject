<template>
  <transition name="fade">
    <div class="quiz-fullscreen" v-if="quiz" @click="closeQuiz">
      <div class="quiz-content" @click.stop>
        <div class="image-container">
          <img :src="quiz.image" alt="Quiz Image" class="quiz-image">
        </div>

        <button @click.stop="startQuiz" class="start-quiz-btn">Start Quiz</button>

        <!-- Header and Button Container -->
        <div class="header-container">

          <h1>{{ quiz.title }}</h1>
        </div>

        <p class="category-badge">#{{ quiz.category }}</p>
        <p>{{ quiz.description }}</p>
        <h2>Questions</h2>

        <!-- Questions Container -->
        <div v-if="quiz.questions && quiz.questions.length" class="questions-container">
          <div class="question-card" v-for="(question, index) in quiz.questions" :key="index">
            <p>{{ question.questionText }}</p>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>





<script>
export default {
  props: {
    quiz: Object,
  },
  methods: {
    startQuiz() {
      alert('Quiz started!');
      // Frontend logic stops here
    },
    closeQuiz() {
      this.$emit('close'); // Emit an event to tell the parent to close the fullscreen view
    },
  },
};

</script>

<style scoped>
.quiz-fullscreen {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.8);
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;

}

.quiz-content {
  background-color: white; /* White background */
  color: black; /* Adjust text color for better readability */
  text-align: left;
  max-width: 600px;
  padding: 20px;
  border-radius: 10px; /* Optional: adds rounded corners */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Optional: adds a subtle shadow */

  max-height: 80vh; /* Set the maximum height to the viewport height */
  overflow-y: auto; /* Enable vertical scrolling */
}

.quiz-image {
  max-width: 100%;
  height: auto;
  border-radius: 8px;
  margin-bottom: 20px;
}

button {
  padding: 10px 20px;
  font-size: 1rem;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}


.image-container {
  position: relative;
  text-align: center;
  margin-bottom: 20px; /* Adjust as needed */
}

.quiz-image {

    object-fit: cover;
    border-radius: 8px;

    min-width: 600px;
    width: 100%; /* Ensure the image takes up the full width */
    max-height: 200px; /* Set a max-height   to prevent images from stretching */


}

button {
  padding: 10px 20px;
  font-size: 1.1rem;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 20px;
  cursor: pointer;
}


button:hover {
  background-color: #0056b3;
}


.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* Starting and ending state for enter/leave transitions */ {
  opacity: 0;
}

.questions-container {
  display: flex;
  flex-wrap: wrap; /* Allows items to wrap as needed */
  gap: 10px; /* Spacing between question cards */
  margin-top: 20px; /* Spacing above the question list */
  font-family: 'DM Sans', sans-serif;

  max-height: 100vh; /* Set the maximum height to the viewport height */
  overflow-y: auto; /* Enable vertical scrolling */
}



.question-card {
  flex: 1; /* Flex grow to fill available space */
  min-width: 200px; /* Minimum width for each question card */
  padding: 10px;
  background-color: #ffffff; /* Light grey background for visibility */
  border-radius: 5px; /* Rounded corners */
  box-shadow: 0 2px 4px rgba(0,0,0,0.2); /* Soft shadow for depth */
  font-family: 'DM Sans', sans-serif;
}

.category-badge {
  display: inline-block; /* Treat the <p> tag more like an inline element */
  background-color: rgb(23, 22, 22); /* Example background color */
  color: #ffffff; /* Text color */
  padding: 5px 15px; /* Vertical and horizontal padding */
  border-radius: 20px; /* Rounded corners */
  font-size: 0.8rem; /* Adjust font size as needed */
  margin: 0; /* Remove default <p> margin if needed */
}




.quiz-image {
  width: 100%;
  border-radius: 8px;
  margin-bottom: 20px; /* Adjust if necessary */
}

@media (max-width: 600px) { /* Example breakpoint */
  .header-container {
    flex-direction: column; /* Stack items vertically */
    align-items: flex-start; /* Align items to the start */
  }


}


</style>
