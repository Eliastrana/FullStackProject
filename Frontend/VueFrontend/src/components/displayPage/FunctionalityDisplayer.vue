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
}


</style>
