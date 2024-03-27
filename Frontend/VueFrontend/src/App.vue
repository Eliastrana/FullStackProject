<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div id="app">
  <header>
    <div class="wrapper">
      <nav>
        <!-- Emoji on the left -->
        <RouterLink to="/">💻</RouterLink>

        <!-- Centered Links -->
        <div class="nav-center">
          <RouterLink to="/Quizzes">Quizzes</RouterLink>
          <h1> | </h1>
          <RouterLink to="/Create">Create</RouterLink>

        </div>

        <!-- Log in on the right -->
        <div class="nav-right">
          <RouterLink to="/Login">Log in</RouterLink>
        </div>

        <div class="nav-right">
          <RouterLink to="/MyAccount">🧑</RouterLink>
        </div>

      </nav>
    </div>
  </header>

    <div class="content">
      <RouterView />
    </div>

<!--  <FooterView />-->

  </div>

</template>






<style>

/* Include the Roboto font */
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

header {
  font-family: 'Roboto', sans-serif;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 20px;


}

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.content {
  flex: 1; /* This will make the content grow */
}


.wrapper {
  width: 100%;
  max-width: 1200px; /* Adjust based on your preference */
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  flex: 1;

}

nav {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;

}

nav a {
  font-size: 30px; /* Larger font size */
  text-decoration: none;
  color: #333; /* Adjust color based on your preference */
}

nav a:hover {
  color: #3232ff; /* Adjust hover color based on your preference */
}

.nav-center {
  display: flex;
  justify-content: center;
  flex: 1;
}

.nav-right {
  display: flex;
  justify-content: flex-end;
}

.nav-center {
  display: flex;
  justify-content: center;
  align-items: center; /* This ensures vertical alignment */
  flex: 1;
}

.nav-center h1 {
  margin: 0 10px; /* Add some horizontal spacing */
  font-size: 24px; /* Adjust the font size as needed */
}



</style>


