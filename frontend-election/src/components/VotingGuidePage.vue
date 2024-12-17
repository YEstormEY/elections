<script setup lang="ts">
import votingGuideImage from '../assets/VotingGuide.png';
import politicalParties from '../assets/politicalParties.png';

</script>

<template>
    <div class="voting-guide-content">
      <div class="voting-guide-text">
        <h2>Welcome to the voting guide</h2>
        <p>NextGenPolitics has created a quiz to help you decide what political party fits your views and morals the best.</p>
        <p>After the quiz, you will receive 3 political parties that best suit your answers.</p>
      </div>
      <div class="voting-guide-image-container">
      <div class="voting-guide-image">
        <img :src="votingGuideImage" alt="Voting Guide Illustration">
      </div>
      </div>
    </div>
    <div class="guide-and-parties-container">
    <div class="why-guide-section">
      <button class="start-button">
        <router-link to="/VotingQuizPage" :style="{ color: '#ffffff' }">Start the voting guide</router-link>
      </button>
      <div class="pink-container">
      <h3>Why a Voting Guide?</h3>
      <p>It's hard to decide who to vote for when you turn 18. The voting guide ensures that you have more insight into what exactly the political parties stand for, and which one suits you the best. Of course, you always have free will and can vote for any political party regardless of your results.</p>
      </div>
      </div>


    <div class="political-parties">
      <h4>These are the political parties that are participating in this voting guide:</h4>
      <div class="party-logos">
        <img :src="politicalParties" alt="Political parties display">
      </div>
    </div>
  </div>



</template>

<style scoped>
.voting-guide-content {
    background-color: #24786E99;
    padding: 20px;
    min-height: 50vh;
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    flex-wrap: wrap;
  }
  @media (max-width: 768px) {
    .voting-guide-content {
      flex-direction: column;
      height: auto;
    }
  }
  .voting-guide-text {
    flex: 1;
    text-align: left;
    margin-bottom: 20px;
    color: #ffffff;
  }
  @media (max-width: 768px) {
    .voting-guide-text {
      width: 100%;
      text-align: center;
    }
  }
  .voting-guide-text h1 {
    color: #ffffff;
    font-size: 28px;
    margin-bottom: 20px;
  }
  @media (max-width: 768px) {
    .voting-guide-text h1 {
      font-size: 28px;
    }
  }
  .voting-guide-text p {
    color: #ffffff;
    line-height: 1.5;
    margin-bottom: 20px;
    font-size: 16px;
    margin-top: 10px;
  }
  @media (max-width: 768px) {
    .voting-guide-text p {
      font-size: 16px;
    }
  }
  .voting-guide-image {
    flex: 1;
    max-width: 400px;
    display: flex;
    margin-right: 40px;
    justify-content: left;
  }

  .voting-guide-image img {
    max-width: 100%; /* Make the image responsive */
    height: auto;
    border-radius: 10px;
  }


.start-button {
  margin-top: 20px;
  margin-bottom: 20px;
  margin-left: 200px;
  padding: 15px 30px;
  background-color: #28a745;
  color: white;
  border: none;
  font-size: 1.2rem;
  border-radius: 5px;
  cursor: pointer;
  display: inline-block;
}

.start-button:hover {
  background-color: #218838;
}

@media (max-width: 768px) {
  .start-button {
    justify-content: center;
  }
}

.guide-and-parties-container {
  display: flex;
  flex-direction: row; /* Allow wrapping for smaller screens */
  gap: 20px;
  justify-content: space-between; /* Center content on smaller screens */
  align-items: flex-start;
  margin-bottom: 0;
}

@media (max-width: 768px) {
  .guide-and-parties-container {
    flex-direction: column; /* Stack items vertically */
    gap: 20px;
  }
}
.why-guide-section {
  order: 1;
  flex: 1 1 45%;
  max-width: 600px;
  margin-top: 0;
  margin-left: 60px;
  width: 100%; /* Allow full width on small screens */
}
@media (max-width: 768px) {
  .why-guide-section{
    flex: 1 1 100%; /* Full width for both sections */
    max-width: 100%;
    margin-left: 0;
    margin-right: 0;
  }
}
.pink-container {
  background-color: #fbe3e4;
  padding: 20px;
  flex: 1;
  border-radius: 20px;
  width: 100%; /* Allow full width on small screens */
  margin-bottom: 20px;
  text-align: left;
}
@media (max-width: 768px) {
  .pink-container {
    padding: 15px; /* Reduce padding */
  }
}

.why-guide-section h3 {
  font-size: 28px;
  margin-bottom: 10px;
  color: #333;
}

.why-guide-section p {
  font-size: 16px;
  line-height: 1.6;
  color: #555;
}

.political-parties {
  order: 2;
  flex: 1 1 45%;
  width: 100%; /* Allow full width on small screens */
  text-align: left;
  margin-top: 20px;

}

.political-parties h4 {
  font-size: 20px;
  margin-bottom: 20px;
  line-height: 1.5;
}

.party-logos {
  display: grid;
  margin-left: 50px;
  grid-template-columns: repeat(auto-fit, minmax(100px, 1fr)); /* Adjust grid for responsiveness */
  gap: 20px;
  justify-items: center;
}

.party-logos img {
  width: 300px;
  height: auto;
  border-radius: 5px;
}

/* Responsive adjustments for smaller screens */
@media (max-width: 768px) {
  .guide-and-parties-container {
    flex-direction: column; /* Stack items vertically */
    gap: 20px;
  }

  .start-button {
    margin-left: 40px; /* Center the button */
    padding: 15px 20px; /* Adjust padding for smaller buttons */
    font-size: 1rem; /* Reduce font size */
  }
  .pink-container {
    width: 100%; /* Full width on small screens */
    margin-left: 0; /* Center container */
    margin-right: 0;
  }

  .political-parties {
    width: 100%; /* Full width */
    margin-bottom: 20px;
  }

  .party-logos {
    grid-template-columns: repeat(auto-fit, minmax(70px, 1fr)); /* Smaller logos for tight spaces */
  }

  .party-logos img {
    max-width: 200px; /* Smaller image on mobile */
    max-height: 400px;
  }
}
</style>