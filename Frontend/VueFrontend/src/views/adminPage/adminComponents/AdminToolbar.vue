<script setup>
import { QuizService } from '@/services/QuizService.js';
import { ref } from 'vue' // Ensure this path is correct

const exportAllQuizzes = async () => {
  try {

    // Fetch summaries of all quizzes
    const quizzesSummary = await QuizService.getAllQuizzes();

    // Fetch complete details for each quiz in parallel
    const fetchDetailsPromises = quizzesSummary.map(quiz => QuizService.getCompleteQuizDetails(quiz.id));
    const quizzesDetails = await Promise.all(fetchDetailsPromises);

    // Convert the detailed quizzes data into a JSON string
    const quizzesBlob = new Blob([JSON.stringify(quizzesDetails)], { type: 'application/json' });

    // Create a Blob URL
    const quizzesUrl = URL.createObjectURL(quizzesBlob);

    // Create an anchor element for downloading
    const link = document.createElement('a');
    link.href = quizzesUrl;
    link.download = 'quizzes_complete.json'; // Set the download filename

    // Trigger the download
    document.body.appendChild(link);
    link.click();

    // Clean up
    document.body.removeChild(link);
    URL.revokeObjectURL(quizzesUrl);
  } catch (error) {
    console.error('Failed to export quizzes:', error);
    alert('Failed to export quizzes. Please check the console for details.');
  }
};

const fileInputRef = ref(null);

const triggerFileInput = () => {
  if (fileInputRef.value) {
    fileInputRef.value.click();
  }
};


const importQuiz = async (event) => {
  try {
    // Ensure a file was selected
    if (!event.target.files.length) {
      alert('Please select a file to import.');
      return;
    }

    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = async (e) => {
      try {
        // Parse the file content to JSON
        const quizzes = JSON.parse(e.target.result);

        // Ensure the file contains quizzes
        if (!quizzes || !quizzes.length) {
          alert('The file does not contain valid quizzes.');
          return;
        }

        alert(`Importing ${quizzes.length} quizzes...`);

        // Use Promise.all to import all quizzes in parallel
        const importPromises = quizzes.map(quiz => QuizService.create(quiz));
        await Promise.all(importPromises);

        alert('All quizzes have been successfully imported.');
      } catch (error) {
        console.error('Error importing quizzes:', error);
        alert('Error importing quizzes. Please check the console for details.');
      }
    };

    reader.onerror = (error) => {
      console.error('Error reading file:', error);
      alert('Failed to read the file.');
    };

    // Read the file content as text
    reader.readAsText(file);
  } catch (error) {
    console.error('Error during import:', error);
    alert('An error occurred during import. Please check the console for details.');
  }
};



</script>


<template>
  <div class="admin-toolbar">
    <!-- Hidden file input -->
    <input type="file" ref="fileInputRef" @change="importQuiz" hidden>

    <!-- Triggers the hidden file input -->
    <div class="admin-toolbar__button" @click="triggerFileInput">
      + Import Quiz
    </div>

    <!-- Export all quizzes -->
    <div class="admin-toolbar__button" @click="exportAllQuizzes">
      Export All Quizzes
    </div>
  </div>
</template>

<style scoped>
.admin-toolbar {
  display: flex;
  align-items: center;
  padding: 10px;
  gap: 20px;
  margin-left: 20px;
}

.admin-toolbar__button {
  cursor: pointer;
  background-color: #f0f0f0;
  padding: 10px 20px;
  min-height: 60px;
  min-width: 150px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s;
  font-family: 'DM Sans', sans-serif;
  font-size: 2rem;
}

.admin-toolbar__button:hover {
  background-color: #e0e0e0;
}

@media (max-width: 600px) {

  .admin-toolbar {
    flex-direction: column;
    align-items: stretch;
    margin-left: 0;
    gap: 10px;
  }

  .admin-toolbar__button {
    min-width: auto;
    font-size: 1.5rem;
    padding: 8px 16px;
  }
}

</style>
