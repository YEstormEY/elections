<script lang="ts">

</script>

<template>
  <div class="d-flex flex-column min-vh-100">
    <div class="flex-grow-1"> <!-- Main content here --> </div>

    <footer class="py-4 bg-light w-100 mt-auto">
      <div class="container">
        <div class="row">
          <!-- Column 1: About NextGenPolitics -->
          <div class="col-md-4">
            <h5>Over NextGenPolitics</h5>
            <ul class="nav flex-column">
              <li class="nav-item mb-2"><router-link to="/AboutUs" class="nav-link p-0 text-muted">Over NextGenPolitics</router-link></li>
              <li class="nav-item mb-2"><router-link to="/AvFAQVa" class="nav-link p-0 text-muted">Vacatures</router-link></li>
              <li class="nav-item mb-2"><router-link to="/AvFAQVa" class="nav-link p-0 text-muted">FAQ</router-link></li>
              <li class="nav-item mb-2"><router-link to="/AvFAQVa" class="nav-link p-0 text-muted">Algemene voorwaarden</router-link></li>
            </ul>
          </div>

          <!-- Column 2: Wat we doen met: -->
          <div class="col-md-4">
            <h5>Wat we doen met:</h5>
            <ul class="nav flex-column">
              <li class="nav-item mb-2"><router-link to="/PoliticsInfo" class="nav-link p-0 text-muted">Politiek</router-link></li>
              <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Schaduw verkiezing</a></li>
              <li class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">De quiz</a></li>
            </ul>
          </div>

          <!-- Column 3: Contact -->
          <div class="col-md-4">
            <h5>Contact</h5>
            <ul class="nav flex-column">
              <router-link class="nav-item mb-2" to="/contact"><a href="#" class="nav-link p-0 text-muted">Contact</a></router-link>
              <router-link class="nav-item mb-2" to="/Nieuwsbrief"><a href="#" class="nav-link p-0 text-muted">Nieuwsbrieven</a></router-link>
              <router-link to="Feedback" class="nav-item mb-2"><a href="#" class="nav-link p-0 text-muted">Feedback</a></router-link>
            </ul>
          </div>
        </div>

        <div class="border-top pt-3 mt-3">
          <p class="text-center text-muted">© 2024 NextGenPolitics</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<style scoped>
/* Ensure the footer sticks to the bottom of the page */
html, body {
  height: 100%;
  margin: 0;
}

.d-flex.flex-column.min-vh-100 {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

footer {
  background-color: #f8f9fa; /* Light background */
  width: 100%;
}

footer h5 {
  font-weight: bold;
}

footer .nav-link {
  color: #6c757d; /* Text color for links */
  transition: color 0.3s ease;
}

footer .nav-link:hover {
  color: #0056b3; /* Hover color */
}

footer .border-top {
  border-top: 1px solid #e9ecef; /* Subtle border for footer separation */
}

footer p {
  margin: 0;
}
</style>
