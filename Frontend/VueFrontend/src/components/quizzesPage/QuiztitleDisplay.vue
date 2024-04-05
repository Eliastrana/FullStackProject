<template>
  <div class="titleContainer">
    <p>
      <span class="first-part">Learn something new about </span>
      <span :style="{ fontFamily: getFont(selectedWord) }">{{ selectedWord }}</span>
    </p>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue';

/**
 * Fonts for different quiz categories
 * @type {Object}
 */
const fonts = {
  'History': 'Eagle Lake, sans-serif',
  'Math': 'Times New Roman',
  'Science': 'Felipa',
  'Technology': 'Courier New',
  'Nature': 'Chicle, serif',
  'Society': 'Times New Roman'
};

/**
 * Quiz categories
 * @type {Array}
 */
const words = ['History', 'Math', 'Science', 'Technology', 'Nature', 'Society'];

/**
 * Index of the currently selected quiz category
 * @type {import('vue').Ref<number>}
 */
const selectedWordIndex = ref(0);

/**
 * Currently selected quiz category
 * @type {import('vue').ComputedRef<string>}
 */
const selectedWord = computed(() => words[selectedWordIndex.value]);

/**
 * Get the font for a given quiz category
 * @param {string} word - The quiz category
 * @returns {string} The font for the quiz category
 */
function getFont(word) {
  return fonts[word] || 'Arial';
}

let interval;

/**
 * On component mount, start an interval to change the selected quiz category every 1.5 seconds
 */
onMounted(() => {
  interval = setInterval(() => {
    selectedWordIndex.value = (selectedWordIndex.value + 1) % words.length;
  }, 1500);
});

/**
 * On component unmount, clear the interval
 */
onUnmounted(() => {
  clearInterval(interval);
});
</script>


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Chicle&family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&family=Eagle+Lake&family=Felipa&family=Oi&display=swap');

.first-part {
  color: #171616;
  font-family: 'DM Sans', sans-serif;
}

.titleContainer {
  height: 15vh;
  margin-top: 20vh;
  margin-bottom: 7vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding-bottom: 5%;
}

p {
  font-size: 8vw;
  margin: 0;
  text-align: left;
  margin-left: 2%;
  color: #3232ff;
  line-height: 1.2;
}

@media (max-width: 768px) {
  .titleContainer {
    font-size: 20vw;
    margin-top: 5vh;
    margin-left: 2%;
  }
}

@media (max-width: 480px) {
  .titleContainer {
    margin-top: 5vh;
    margin-left: 2%;

  }
}
</style>

