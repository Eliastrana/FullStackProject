<template>
  <div class="center-container"></div>
  <div class="login-container">
    <h2>Contact Us</h2>
    <div>
      <div class="input-container">
        <input v-model="name" type="text" id="name" placeholder=" " required>
        <label for="name">Name</label>
      </div>
      <div class="input-container">
        <input v-model="subject" type="text" id="subject" placeholder=" " required>
        <label for="subject">Subject</label>
      </div>
      <div class="input-container">
        <textarea id="content" v-model="content" placeholder=" " required></textarea>
        <label for="content">Content</label>
      </div>
      <div id="button-container">
        <button @click="openMailClient" id="register-user" type="submit">Send</button>
      </div>
    </div>
  </div>
</template>




<script setup>
import { ref } from 'vue';

/**
 * Name for the contact form
 * @type {import('vue').Ref<string>}
 */
const name = ref('');

/**
 * Subject for the contact form
 * @type {import('vue').Ref<string>}
 */
const subject = ref('');

/**
 * Content for the contact form
 * @type {import('vue').Ref<string>}
 */
const content = ref('');

/**
 * Function to open the mail client
 * This function creates a mailto link with the subject, name, and content from the form.
 * It then sets the window location to this link, which opens the default mail client with a new email populated with this data.
 */
const openMailClient = () => {
  const mailtoLink = `mailto:support@qanda.no?subject=${encodeURIComponent(subject.value)}&body=Name: ${encodeURIComponent(name.value)}%0D%0A%0D%0AContent:%0D%0A${encodeURIComponent(content.value)}`;
  window.location.href = mailtoLink;

  name.value = '';
  subject.value = '';
  content.value = '';

};

</script>


<style scoped>
.center-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 10vh;
}

.login-container {
  margin: auto;
  background-color: rgb(249, 249, 249);
  padding: 20px;
  border-radius: 8px;
  width: 40%;
  max-width: 500px;
  box-sizing: border-box;
  min-width: 400px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.input-container {
  position: relative;
  margin-bottom: 10px;
  padding-right: 20px;
}

#button-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: center;
  max-width: 300px;
  margin: 0 auto;
}

input {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: none;
  border-bottom: 2px solid #343434;
  background-color: transparent;
  color: #343434;
}

input::placeholder {
  color: transparent;
}

input:focus {
  outline: none;
}

input:focus + label,
input:not(:placeholder-shown) + label {
  transform: translateY(-20px);
  font-size: 12px;
  color: #000000;
}

label {
  position: absolute;
  left: 10px;
  top: 10px;
  transition: all 0.3s ease;
  pointer-events: none;
}

#register-user, #create-user-link {
  padding: 10px 30px;
  margin-top: 20px;
  border: none;
  border-radius: 5px;
  background-color: #0056b3;
  color: #ffffff;
  cursor: pointer;
  transition: background-color 0.3s, box-shadow 0.2s, transform 0.2s;
  box-shadow: 0 4px 8px rgba(0,0,0,0.2);
  font-size: 20px;
}

#register-user:hover, #create-user-link:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0,0,0,0.3);
  background-color: #007bff;
}

#register-user:active, #create-user-link:active {
  background-color: #3232ff;
  transform: translateY(2px);
}

#create-user-link {
  background-color: transparent;
  color: #424242;
  padding: 0;
  border: none;
  cursor: pointer;
  text-decoration: none;
  font-size: 15px;
  margin-top: 10px;
}

#create-user-link:hover {
  color: #0056b3;
  text-decoration: underline;
}

input::placeholder, textarea::placeholder {
  color: transparent;
}

input:focus, textarea:focus {
  outline: none;
}

input:focus + label, textarea:focus + label,
input:not(:placeholder-shown) + label, textarea:not(:placeholder-shown) + label {
  transform: translateY(-20px);
  font-size: 12px;
  color: #000000;
}

textarea {
  width: 100%;
  height: 100px;
  padding: 10px;
  border: none;
  border-bottom: 2px solid #343434;
  background-color: transparent;
  color: #343434;
  resize: vertical;
}


</style>
