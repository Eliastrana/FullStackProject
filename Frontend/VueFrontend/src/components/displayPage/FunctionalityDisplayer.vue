<script setup>
const props = defineProps({
  title: String,
  description: String,
  imageUrl: String,
  imagePosition: {
    type: String,
    default: 'left', // Default to left if not specified
  },
  backgroundColor: {
    type: String,
    default: '#fffff7', // A default background color
  },
});
</script>




<template>
  <div class="functionality" :style="{ backgroundColor: props.backgroundColor }">
    <div class="content-wrapper" :class="{ 'image-right': props.imagePosition === 'right' }">
      <img class="functionality-image" :src="props.imageUrl" alt="Functionality Image">
      <div class="text-content">
        <h1>{{ props.title }}</h1>
        <p>{{ props.description }}</p>
      </div>
    </div>
  </div>
</template>





<style scoped>

.functionality {
  font-family: 'DM Sans', sans-serif;
  padding: 20px;
  border: none;
  border-radius: 20px;
  background-color: #fffff7;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  text-align: center;
  width: 90%;
  margin-top: 2%;
  margin-right: 40px;
  margin-left: 40px;
  display: flex; /* Use flex display */
  flex-direction: column; /* Stack content vertically */
  align-items: center; /* Center-align items */
  height: 100%;

}

.content-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: left;
  /* Ensure the default layout has the image on the left */
}

.content-wrapper.image-right {
  flex-direction: row-reverse; /* Flips the order when image is supposed to be on the right */
}

.text-content {
  flex: 1; /* Allows the text content to fill the available space */
  padding: 40px; /* Adds padding around the text content */
  margin-left: 40px;
  font-family: 'DM Sans', sans-serif;
  font-size: 1.7rem; /* Adjust font size as necessary */

}

.functionality-image {

  max-height: 600px;


  border-radius: 10px; /* Optional: Rounds the corners of the image */
}

</style>
