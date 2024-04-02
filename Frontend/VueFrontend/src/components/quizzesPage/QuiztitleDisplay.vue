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


@import url('https://fonts.googleapis.com/css2?family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&family=Felipa&display=swap');

@import url('https://fonts.googleapis.com/css2?family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&family=Felipa&family=Oi&display=swap');

@import url('https://fonts.googleapis.com/css2?family=Chicle&family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&family=Felipa&family=Oi&display=swap');

@import url('https://fonts.googleapis.com/css2?family=Chicle&family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&family=Eagle+Lake&family=Felipa&family=Oi&display=swap');

.first-part {
  color: #171616; /* Keep your desired color */
  font-family: 'DM Sans', sans-serif;
}

.titleContainer {
  height: 150px;
  margin-top: 10%;
  margin-bottom: 70px;
  display: flex;
  align-items: center;
  padding-bottom: 5%;
}

p {
  font-size: 8rem;
  margin: 0;
  text-align: left;
  padding-left: 40px;
  color: #3232ff;
  line-height: 1.2; /* Adjust this value as needed */
}

span {
  position: relative;
  /* top: -20px; Removed or adjust as needed */
}



</style>
