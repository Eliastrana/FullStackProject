<template>
  <div v-if="props.isVisible" class="modal">
    <div class="modal-content">
      <h2>Create Category</h2>
      <input v-model="categoryName" placeholder="Category Name" />
      <input
        v-model="description"
        placeholder="Description"
        @keyup.enter="submitForm"
      />
      <button @click="submitForm">Submit</button>
      <button @click="close">Cancel</button>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';



const emits = defineEmits(['close', 'created']);
const isVisible = ref(true);

const categoryName = ref('');
const description = ref('');

const submitForm = () => {
  if (!categoryName.value || !description.value) {
    alert('Please fill in all fields.');
    return;
  }

  emits('created', categoryName.value, description.value);
  categoryName.value = '';
  description.value = '';
};


const close = () => {
  emits('close'); // This emits the close event
};


const props = defineProps({
  isVisible: Boolean
});


</script>

<style scoped>

.modal {

  position: fixed;
  z-index: 1000; /* High z-index to ensure it's above other content */
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.5); /* Dimmed background */
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  max-width: 500px; /* Or any appropriate size */
  width: 90%; /* Responsive width, adjusted for smaller screens */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Optional: Adds shadow for depth */
  display: flex;
  flex-direction: column; /* Ensures children are stacked vertically */
  gap: 10px; /* Creates space between child elements */
  align-items: stretch; /* Aligns children width to match the container */
  font-family: 'DM Sans', sans-serif;

}

/* Styles for inputs and textarea for consistency */
.modal-content input,
.modal-content textarea {
  font-family: 'DM Sans', sans-serif;
  padding: 10px;
  margin-top: 5px; /* Adds a bit of margin at the top for spacing */
  border: none; /* Gives a subtle border */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Adds a shadow for depth */
  border-radius: 4px; /* Rounds the corners */
}

/* Button styling for consistency and spacing */
.modal-content button {
  cursor: pointer;
  padding: 10px 20px;
  margin-top: 10px; /* Adds more space above the buttons */
  border: none;
  border-radius: 20px;
  max-width: 20%;
  background-color: #007bff; /* Example button color */
  color: white;
  font-weight: bold;
  align-items: center;
}

/* Additional styling for the cancel button to differentiate it */
.modal-content button:last-child {
  background-color: #a9a9a9; /* Different color for the cancel button */
}

/* Optional hover effect for buttons */
.modal-content button:hover {
  background-color: #0056b3; /* Darker color on hover */
}

/* Optional hover effect for cancel button */

.modal-content button:last-child:hover {
  background-color: #7a7a7a; /* Darker color on hover */
}


</style>
