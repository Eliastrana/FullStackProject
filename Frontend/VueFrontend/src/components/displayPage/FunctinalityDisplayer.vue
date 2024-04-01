<script setup>

</script>

<template>
  <div class="functionality">


    <div class="content-wrapper">
      <img class="functionality-image" src="/images/dalle/corpcore.png">

      <div class="text-content">
        <h1>Functionality Displayer</h1>
        <p>Here you can see the functionality of the app</p>
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
  margin-top: -100px;
  margin-right: 40px;
  margin-left: 40px;
  display: flex; /* Use flex display */
  flex-direction: column; /* Stack content vertically */
  align-items: center; /* Center-align items */
  height: 100%;

}

.content-wrapper {
  display: flex; /* Enables flexbox for direct children, i.e., text-content and functionality-image */
  align-items: center; /* Vertically aligns the flex items */
  justify-content: center; /* Horizontally centers the flex items in the container */
  text-align: left; /* Aligns the text to the left */
  width: 100%; /* Ensures the wrapper takes the full width of its parent */
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
