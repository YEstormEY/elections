<script setup lang="ts">

</script>

<template>
    <div class="voting-guide-content">
      <div class="voting-guide-text">
        <h2>Welcome to the voting guide</h2>
        <p>NextGenPolitics has created a quiz to help you decide what political party fits your views and morals the best.</p>
        <p>After the quiz, you will receive 3 political parties that best suit your answers.</p>
      </div>

      <div class="voting-guide-image">
        <img src="../assets/VotingGuide.png" alt="Voting Guide Illustration">
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
        <img src="../assets/PoliticalParties.png" alt="Political parties display">
      </div>
    </div>
  </div>



</template>

<style scoped>
.voting-guide-content {
  background-color: #24786E99;
  padding: 20px;
  height: 60vh;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.voting-guide-text {
  flex: 1;
  text-align: left;
}

.voting-guide-text h2 {
  font-size: 28px;
  color: #ffffff;
}

.voting-guide-text p {
  font-size: 16px;
  margin-top: 10px;
  color: #ffffff;
}

.start-button {
  margin-top: 20px;
  margin-bottom: 20px;
  margin-left: 230px;
  padding: 15px 30px;
  background-color: #7fcc72;
  color: white;
  border: none;
  font-size: 1.2rem;
  border-radius: 5px;
  cursor: pointer;
  display: inline-block;
}

.voting-guide-image {
  flex: 1;
  display: flex;
  justify-content: center;
}

.voting-guide-image img {
  max-width: 300px;
  border-radius: 10px;
}
.guide-and-parties-container {
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: flex-start;
  gap: 20px;
  margin-bottom: 40px;
}


.why-guide-section {
    display: flex;
    flex: 1;
    flex-direction: column;
    max-width: 600px;
    width: 45%;
  }

.pink-container {
  background-color: #fbe3e4;
  flex-direction: column;
  padding: 20px;
  flex: 1;
  border-radius: 10px;
  width: 180%;
  margin-bottom: 40px;
  margin-left: 190px;
  margin-right: 100px;
  text-align: left;

}

.why-guide-section h3 {
  font-size: 1.5rem;
  margin-bottom: 10px;
  color: #333;
}

.why-guide-section p {
  font-size: 1.1rem;
  line-height: 1.6;
  color: #555;
}


.political-parties {
  width: 50%;
  position: relative;
  right: 30px;
  left: 500px;
  top: 10px;
  flex: 1;
  text-align: center;
  margin-bottom: 60px;
}

.political-parties h4 {
  font-size: 18px;
  margin-bottom: 20px;
  line-height: 1.5;
  text-align: left;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.party-logos {
   display: grid;
    grid-template-columns: repeat(auto-fit, minmax(100px, 1fr));
    gap: 20px;
    justify-items: left;
    margin-top: 20px;
  }

  .political-parties {
    width: 50px;
    height: 50px;
  }

</style>