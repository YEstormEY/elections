<script lang="ts">

</script>

<template>
  <div class="background">
    <div class="text">
      <h1>Learn about the creators of this wonderful site </h1>
      <p>
        This is the page where you learn about why we made this site and what we hope to achieve with it.
      </p>
    </div>
  </div>

    <!-- About Us Section -->
    <section class="about-us-section">
      <div class="container">
        <h2>Who We Are</h2>
        <p>
          NextGenPolitics is a dedicated platform designed to help young people like you learn about the political landscape, understand current issues, and make informed choices. We’re all about empowering youth to shape the future through knowledge and action.
        </p>
        <p>
          We are a group of students from the Hogeschool van Amsterdam who are assigned to make a website for a project. But we not only hope to get a great score but also to launch this website so the younger generation of the netherlands will be more informed about the political landscape of the netherlands.
        </p>
      </div>
    </section>

    <!-- Our Mission Section -->
    <section class="mission-section">
      <div class="container text-center">
        <h2>Our Mission</h2>
        <p>
          To inspire a generation of informed, motivated, and active young citizens who believe in making a positive impact through politics. Whether you’re interested in climate change, education, human rights, or local governance, NextGenPolitics is here to guide you.
        </p>
        <p>
          We believe that every young person has the potential to be a change maker. By providing you with the tools, resources, and support you need, we aim to help you become an active participant in the democratic process and a force for good in your community.
        </p>
      </div>
    </section>

    <!-- Why Join Us Section -->
    <section class="why-join-section">
      <div class="container">
        <h2>Why Join Us?</h2>
        <div class="benefits">
          <div class="benefit">
            <img src="../../assets/community.jpg" alt="Community" />
            <h3>Community</h3>
            <p>Meet and interact with other young people passionate about change.</p>
          </div>
          <div class="benefit">
            <img src="../../assets/knowledge.jpg" alt="Knowledge" />
            <h3>Knowledge</h3>
            <p>Get easy-to-understand info on political topics that matter to you.</p>
          </div>
          <div class="benefit">
            <img src="../../assets/action.jpg" alt="Action" />
            <h3>Action</h3>
            <p>Find out how to take real action and make a difference in your community.</p>
          </div>
        </div>
      </div>
    </section>

    <!-- Get Involved Section -->
    <section class="get-involved-section">
      <div class="container text-center">
        <h2>Get Involved</h2>
        <p>
          Stay updated with our latest initiatives, join discussions, attend events, and find volunteering opportunities. We believe that change starts with you!
        </p>
        <button class="btn btn-primary mt-3">Join Us</button>
      </div>
    </section>

</template>

<style scoped>

.background {
  background-color: #24786E99;
  padding: 20px;
  height: 60vh;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}


.text {
  width: 100%;
  color: white;
  padding: 20px;
  text-align: center;
}

.text h1 {
  font-size: 36px;
  margin-bottom: 60px;
}

.text p {
  font-size: 18px;
  line-height: 1.5;
  margin-bottom: 20px;
  text-align: center;
  width: 50%;
  margin-left: 25%;
}

/* Basic reset */
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

.hero-section h1 {
  font-size: 2.5rem;
  margin-bottom: 0.5rem;
}

.hero-section p {
  font-size: 1.2rem;
}

/* About Us Section */
.about-us-section {
  background-color: #f4f4f4;
  padding: 3rem 1rem;
  text-align: center;
}

.about-us-section h2 {
  color: #24786E99;
  margin-bottom: 1rem;
}

/* Mission Section */
.mission-section {
  padding: 3rem 1rem;
}

.mission-section h2 {
  color: #24786E99;
  margin-bottom: 1rem;
}

/* Why Join Us Section */
.why-join-section {
  background-color: #f9f9f9;
  padding: 3rem 1rem;
  text-align: center;
}

.why-join-section h2 {
  color: #24786E99;
  margin-bottom: 2rem;
}

.benefits {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

.benefit {
  width: 30%;
  text-align: center;
  padding: 1rem;
}

.benefit img {
  width: 250px;
  height: auto;
  margin-bottom: 1rem;
}

.benefit h3 {
  color: #24786E99;
}

/* Get Involved Section */
.get-involved-section {
  padding: 3rem 1rem;
  background: #24786E99;
  color: white;
}

.get-involved-section h2 {
  margin-bottom: 1rem;
}

.btn {
  background-color: #24786E99;
  color: white;
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  border: none;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.btn:hover {
  background-color: black;
}

/* Responsive Design */
@media (max-width: 768px) {
  .hero-section h1 {
    font-size: 2rem;
  }

  .benefits {
    flex-direction: column;
  }

  .benefit {
    width: 100%;
    margin-bottom: 1.5rem;
  }
}
</style>
