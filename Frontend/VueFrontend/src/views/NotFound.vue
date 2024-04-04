<template>
  <div class="not-found-container">
    <h1 class="error-code">404</h1>
    <h3 class="error-message">Page not found.</h3>
    <p class="description">It seems we  <span class="highlight">Qand't</span>  find the page you're looking for!</p>
    <img class="error-image" src="/images/404/404.png" alt="404 Error">
    <router-link class="home-link" to="/">Go back home</router-link>
  </div>
</template>

<style scoped>
.not-found-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  text-align: center;
}

.error-code {
  font-family: 'DM Sans', sans-serif;
  font-size: 5rem;
  font-weight: bold;
  color: #007bff;
  margin-top: -70px;
  margin-bottom: -50px;
}

.error-message {
  font-family: 'DM Sans', sans-serif;
  font-size: 2.5rem;
  color: #333;
}

.description {
  font-family: 'DM Sans', sans-serif;
  font-size: 1.5rem;
}

.highlight {
  color: #FFDD00; /* This sets the color of 'Qand't' to yellow. Adjust the color code as needed. */
  font-size: 3rem;
}

.error-image {
  margin: 20px 0;
  max-height: 300px;
  max-width: 300px;
}

.home-link {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border-radius: 20px;
  text-decoration: none;
  font-size: 1.2rem;
}

.home-link:hover {
  background-color: #0056b3;
}
</style>
