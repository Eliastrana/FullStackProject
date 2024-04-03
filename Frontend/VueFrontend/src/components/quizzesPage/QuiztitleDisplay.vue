<template>
  <div class="titleContainer">
    <p>
      <span class="first-part">Learn something new about </span>
      <span :style="{ fontFamily: getFont(selectedWord) }">{{ selectedWord }}</span>
    </p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fonts: {
        'History': 'Eagle Lake, sans-serif',
        'Math': 'Times New Roman',
        'Science': 'Felipa',
        'Technology': 'Courier New',
        'Nature': 'Chicle, serif',
        'Society': 'Times New Roman'
      },
      words: ['History', 'Math', 'Science', 'Technology', 'Nature', 'Society'],
      selectedWordIndex: 0
    };
  },
  computed: {
    selectedWord() {
      return this.words[this.selectedWordIndex];
    }
  },
  methods: {
    getFont(word) {
      return this.fonts[word] || 'Arial'; // Default to Arial if font not specified for a word
    }
  },
  mounted() {
    this.interval = setInterval(() => {
      this.selectedWordIndex = (this.selectedWordIndex + 1) % this.words.length;
    }, 1500); // Change every 2 seconds, adjust as needed
  },
  beforeUnmount() {
    clearInterval(this.interval);
  }
};
</script>


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Chicle&family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&family=Eagle+Lake&family=Felipa&family=Oi&display=swap');

.first-part {
  color: #171616;
  font-family: 'DM Sans', sans-serif;
}

.titleContainer {
  height: 15vh; /* Use viewport height for better scalability */
  margin-top: 20vh;
  margin-bottom: 7vh;
  display: flex;
  align-items: center;
  justify-content: center; /* Center horizontally */
  padding-bottom: 5%;
}

p {
  font-size: 8vw; /* Use viewport width for scalability */
  margin: 0; /* Adjusted to remove unnecessary top margin */
  text-align: left;
  margin-left: 2%;
  color: #3232ff;
  line-height: 1.2;
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .titleContainer {
    font-size: 20vw;
    margin-top: 5vh; /* Slightly reduced for better appearance on tablets */
    margin-left: 2%;
  }
}

/* Additional breakpoints for extra small devices */
@media (max-width: 480px) {
  .titleContainer {
    margin-top: 5vh; /* Significantly reduced to minimize the gap on mobile devices */
    margin-left: 2%;

  }
}
</style>

