<template>
  <div class="star-rating">
    <div class="star-rating-back">
      <span class="star" v-for="n in 5" :key="n">&#9733;</span>
    </div>
    <div class="star-rating-front" :style="{ width: filledWidth }">
      <span class="star" v-for="n in 5" :key="n">&#9733;</span>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
const props = defineProps({
  rating: {
    type: Number,
    default: 0,
  },
});

const filledWidth = computed(() => {
  const maxRating = 5;
  return `${(props.rating / maxRating) * 100}%`;
});
</script>

<style scoped>
.star-rating {
  position: relative;
  display: inline-block;
  font-size: 30px;
  color: #ccc;
}

.star-rating-back .star {
  display: inline-block;
}

.star-rating-front {
  display: flex;
  overflow: hidden;
  position: absolute;
  top: 0;
  left: 0;
  white-space: nowrap;
  color: #dcb21f;
}
</style>
