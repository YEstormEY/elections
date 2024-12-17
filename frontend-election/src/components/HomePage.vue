<template>
  <div class="background">
    <!-- Left-side text content directly on the background -->
    <div class="left-text">
      <h1>Welcome to NextGenPolitics</h1>
      <p>
        NextGenPolitics gives young people a voice in democracy. We explain how the political system works and show how you can influence your future, even without the right to vote.
      </p>
      <p>
        From local decisions to European politics – you have more power than you think!
      </p>
    </div>

    <!-- Carousel and right-side content -->
    <div id="carouselExampleControls" class="carousel slide right-content" data-bs-ride="carousel" data-bs-interval="3000">
      <div class="carousel-inner">
        <!-- First Slide -->
        <div class="carousel-item active">
          <div class="slide-content">
            <div class="gray-box">
              <div class="text-content">
                <h2>Take the quiz to see what party fits you!</h2>
                <p>
                  This quiz will help you discover which political party best suits your views and values. Answer a few questions and immediately gain insight into which party shares your vision.
                </p>
                <button class="start-button">
                  <router-link to="/VotingQuizPage" :style="{ color: '#ffffff' }">Start quiz!</router-link>
                </button>
              </div>
              <div class="image-and-buttons">
                <img class="inside-image" src="../assets/VotingGuide.png" alt="First slide image">
                <div class="navigationButtons">
                  <button class="nav-button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                    <img src="../assets/previous.webp" alt="previous">
                  </button>
                  <button class="nav-button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                    <img src="../assets/Next.png" alt="next">
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Second Slide -->
        <div class="carousel-item">
          <div class="slide-content">
            <div class="gray-box">
              <div class="text-content">
                <h2>Vote for the Shadow Elections!</h2>
                <p>
                  Participating in these shadow elections gives you the opportunity to experience what it is like to vote, even if you are not yet 18! Only young people between the ages of 14 and 18 participate, so it is your time to make your voice heard. Discover which parties appeal to young people like you the most and compare the results with the real election results!
                </p>
                <button class="start-button">Vote now!</button>
                <button class="result-button">View results!</button>
              </div>
              <div class="image-and-buttons">
                <img class="inside-image" src="../assets/Vote.png" alt="Second slide image">
                <div class="navigationButtons">
                  <button class="nav-button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                    <img src="../assets/previous.webp" alt="previous">
                  </button>
                  <button class="nav-button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                    <img src="../assets/Next.png" alt="next">
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>



      </div>
    </div>
  </div>

  <section class="latest-articles">
    <h2>Latest Articles</h2>
    <div class="article-container">
      <!-- First Article -->
      <div class="article">
        <img src="../assets/News.png" alt="Importance of Voting">
        <h3>Importance of Voting</h3>
        <p>Discover why your vote matters</p>
      </div>

      <!-- Second Article -->
      <div class="article">
        <img src="../assets/News.png" alt="Rutte NATO boss">
        <h3>As the new NATO boss, Rutte lashes out at China and is not worried about Trump</h3>
      </div>

      <!-- Third Article -->
      <div class="article">
        <img src="../assets/News.png" alt="Political Terminology">
        <h3>Political Terminology</h3>
        <p>Learn key terms used in politics</p>
      </div>
    </div>
  </section>
</template>

<style scoped>
/* Green background remains visible */
.background {
  background-color: #24786E99;
  padding: 20px;
  min-height: 50vh;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: wrap;
}

@media (max-width: 768px) {
  .background {
    flex-direction: column;
    height: auto;
  }
}


.left-text {
  width: 35%;
  color: white;
  padding: 20px;
}

@media (max-width: 768px) {
  .left-text {
    width: 100%;
    text-align: center;
  }
}

.left-text h1 {
  font-size: 36px;
  margin-bottom: 20px;
}

@media (max-width: 768px) {
  .left-text h1 {
    font-size: 28px;
  }
}

.left-text p {
  font-size: 18px;
  line-height: 1.5;
  margin-bottom: 20px;
}

@media (max-width: 768px) {
  .left-text p {
    font-size: 16px;
  }
}


.right-content {
  width: 65%;
}

@media (max-width: 768px) {
  .right-content {
    width: 100%;
  }
}


.slide-content {
  display: flex;
  justify-content: flex-end;
  width: 100%;
  padding: 0 50px;
}

@media (max-width: 768px) {
  .slide-content {
    justify-content: center;
    padding: 0 20px;
  }
}


.gray-box {
  background-color: #f0f0f0;
  padding: 20px;
  width: 100%;
  max-width: 1000px;
  border-radius: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

@media (max-width: 768px) {
  .gray-box {
    flex-direction: column;
  }
}

/* Image and buttons */
.image-and-buttons {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: flex-end;
  width: 45%;
  height: auto;
  margin-left: 20px;
}

@media (max-width: 768px) {
  .image-and-buttons {
    width: 100%;
    margin-left: 0;
    margin-top: 20px;
  }
}

.inside-image {
  width: 100%;
  height: auto;
  object-fit: contain;
}

/* Navigation buttons */
.navigationButtons {
  display: flex;
  justify-content: flex-end;
  width: 100%;
  margin-top: 10px;
}

.nav-button {
  background: none;
  border: none;
  cursor: pointer;
}

.nav-button img {
  width: 30px;
  height: auto;
  margin-left: 10px;
}

/* Text content inside gray box */
.text-content {
  color: #333;
  width: 50%;
}

@media (max-width: 768px) {
  .text-content {
    width: 100%;
    text-align: center;
  }
}

.text-content h2 {
  color: red;
  margin-bottom: 10px;
}

.text-content p {
  color: #333;
  margin-bottom: 20px;
}

/* Button styles */
.start-button, .result-button {
  background-color: #28a745;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
  margin-top: 10px;
}

.start-button {
  margin-left: 30px;
}

.start-button:hover {
  background-color: #218838;
}

.result-button {
  margin-left: 80px;
  background-color: orangered;
}

.result-button:hover {
  background-color: darkred;
}


.latest-articles {
  background-color: #fff;
  padding: 40px;
  text-align: center;
}

.latest-articles h2 {
  font-size: 28px;
  margin-bottom: 20px;
}

@media (max-width: 768px) {
  .latest-articles h2 {
    font-size: 24px;
  }
}

.article-container {
  display: flex;
  justify-content: space-around;
  align-items: stretch;
  flex-wrap: wrap;
}

.article {
  background-color: #f1f1f1;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 30%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
}

@media (max-width: 768px) {
  .article {
    width: 100%;
    max-width: 300px;
  }
}

.article img {
  width: 200px;
  height: auto;
  margin-bottom: 15px;
}

.article h3 {
  font-size: 18px;
  margin-bottom: 10px;
}

.article p {
  font-size: 14px;
  color: #666;
}

</style>
