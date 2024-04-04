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
    default: '#CAE9FF', // A default background color
  },
});

import FrontpageFlipcard from '@/components/displayPage/displayQuiz/FrontpageFlipcard.vue'


</script>


<template>
  <div class="functionality" :style="{ backgroundColor: props.backgroundColor }">
    <!-- Wrap both the card and text content in a flex container -->
    <div class="content-wrapper">
      <!-- Card container with explicit width -->
      <div class="card-container">
        <FrontpageFlipcard />
      </div>
      <!-- Text content -->
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
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  text-align: center;
  width: 90%;
  margin-top: 2%;
  margin-right: auto; /* Sentrerer komponenten horisontalt */
  margin-left: auto; /* Sentrerer komponenten horisontalt */
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px; /* Legger til mellomrom mellom bildet og teksten */
}

.content-wrapper {
  font-size: 1rem;
  display: flex;
  flex-direction: row; /* Standard layout */
  align-items: center;
  justify-content: center;
  gap: 20px; /* Legger til mellomrom mellom bildet og teksten */
  width: 100%; /* Bruker full bredde til å håndtere layout */
}

.content-wrapper.image-right {
  flex-direction: row-reverse; /* Bytter rekkefølge for høyreorientert bilde */
}

.text-content {
  flex: 1;
  padding: 20px;
  max-width: 60%; /* Setter en maksbredde for å begrense bildets størrelse */
  height: auto; /* Beholder bildets aspektforhold */
  line-height: 1.3; /* Gir bedre lesbarhet */

}

.functionality-image {
  flex: 1; /* Gjør bildet fleksibelt innenfor containerens tilgjengelige plass */
  max-width: 60%; /* Setter en maksbredde for å begrense bildets størrelse */
  height: auto; /* Beholder bildets aspektforhold */
  border-radius: 10px;
}
.text-content h1 {
  font-size: clamp(0.9rem, 2.6vw, 20rem); /* Juster disse verdiene etter behov */
}

.text-content p {
  font-size: clamp(1.2rem, 1vw, 10rem);
}


.content-wrapper {
  display: flex;
  flex-direction: row; /* Ensures items are side by side */
  align-items: center;
  justify-content: start; /* Aligns items to the start, putting the card on the left */
  width: 100%;
  gap: 20px; /* Adjusts spacing between the card and the text */
}

.card-container {
  flex: 1; /* Allows the card container to grow */
  max-width: 40%; /* Adjusts the maximum width of the card to prevent it from being too large */
  /* Set a min-width if the card becomes too small at smaller viewport sizes */
  min-width: 300px; /* Example min-width */
}

.text-content {
  flex: 2; /* Allows the text content to take up more space */
  padding: 20px;
  /* Existing text content styles... */
}



@media (max-width: 868px) {
  .content-wrapper, .content-wrapper.image-right {
    flex-direction: column;
    text-align: center;
  }
  .text-content, .functionality-image {
    max-width: 100%;
    padding: 10px 0; /* Reduserer padding på mindre skjermer */
    border-radius: 30px;
  }
  .text-content h1 {
    font-size: clamp(1.7rem, 2.6vw, 20rem); /* Juster disse verdiene etter behov */
  }

  .text-content p {
    font-size: clamp(1.2rem, 1vw, 10rem);
  }
  .content-wrapper {
    flex-direction: column; /* Stacks items on top of each other on smaller screens */
  }
  .card-container, .text-content {
    max-width: 100%;
    min-width: auto; /* Resets min-width for smaller screens */
  }
}




</style>
