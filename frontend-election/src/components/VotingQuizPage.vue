<script setup lang="ts">
import { ref , onMounted } from 'vue';

// Reactive state variables
const quizStarted = ref(false);
const questions = ref([
  { title: 'Fake News', text: 'Social media companies should be able to decide for themselves what to do with fake news.' },
  { title: 'Climate Change', text: 'Governments should take urgent action to combat climate change.' },
  { title: 'Education', text: 'Education should be free for all citizens.' }
]);

const currentQuestionIndex = ref(0);
const questionTitle = ref('');
const question = ref('');
const progress = ref(0);
const userAnswers = ref([]);

// Function to handle starting the quiz
const startQuiz = () => {
  quizStarted.value = true;
  loadQuestion();
  updateProgress();
};

const loadQuestion = () => {
  const currentQuestion = questions.value[currentQuestionIndex.value];
  questionTitle.value = currentQuestion.title;
  question.value = currentQuestion.text;
};

const updateProgress = () => {
  progress.value = ((currentQuestionIndex.value + 1) / questions.value.length) * 100;
};

// Function to handle answering a question
const answer = (option) => {
  console.log('Answer chosen:', option);
  userAnswers.value[currentQuestionIndex.value] = option;
  nextQuestion();
};

const nextQuestion = () => {
  if (currentQuestionIndex.value < questions.value.length - 1) {
    currentQuestionIndex.value++;
    loadQuestion();
    updateProgress();
  } else {
    console.log('Quiz finished!');
    quizStarted.value = false;
    updateProgress();
  }
};
// Function to handle skipping the question
const skip = () => {
  console.log('Question skipped');
  nextQuestion()
};
onMounted(() => {
  startQuiz(); // Start the quiz immediately when the component loads
});
</script>

<template>
    <div class="welcome-container">
      <div class="welcome-message">
        <h1>Welcome to the voting guide</h1>
        <p>
          NextGenPolitics has created a quiz to help you decide what political
          party fits your views and morals the best.
        </p>
        <p>After the quiz, you will receive 3 political parties that best suit your answers.</p>
      </div>
      <div class="welcome-image">
        <img src="../../../backend-election/src/main/resources/static/img/VotingGuide.png" alt="Voting illustration" />
      </div>
    </div>

    <div class="quiz-container">
    <div class="progress-bar" :style="{ width: progress + '%' }"></div>
    <div class="quiz-question">
      <div v-if="quizStarted">
        <h2>{{ questionTitle }}</h2>
        <p>{{ question }}</p>
        <div class="buttons">
          <button class="btn disagree" @click="answer('disagree')">disagree</button>
          <button class="btn neither" @click="answer('neither')">neither</button>
          <button class="btn agree" @click="answer('agree')">agree</button>
        </div>
        <button class="btn skip" @click="skip">Skip →</button>
      </div>
      <div v-else>
        <h2>Quiz Finished!</h2>
        <p>Thank you for completing the quiz.</p>
        <div class="results">
          <h3>Your Answers:</h3>
          <ul>
            <li v-for="(answer, index) in userAnswers" :key="index">
              {{ questions[index].title }}: {{ answer }}
            </li>
          </ul>
        </div>
      </div>
  </div>
    </div>

</template>

<style scoped>
.welcome-container {
  background-color: #24786E99;
  padding: 20px;
  height: 60vh;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.welcome-message {
  max-width: 60%;
  text-align: left;
}

.welcome-message h1 {
  color: #ffffff;
}

.welcome-message p {
  color: #ffffff;
}

.welcome-image img {
  max-width: 200px;
}

.quiz-container {
  text-align: left;
  padding: 20px;
}

.progress-bar {
  width: 0% ;
  height: 10px;
  background-color: #FF6F61;
  margin-bottom: 20px;
  transition: width 0.3s ease;
}

.quiz-question {
  margin: 20px;
}

.buttons {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-bottom: 20px;
}

.btn {
  padding: 10px 20px;
  border: #2c3e50;
  cursor: pointer;
}

.disagree {
  background-color: #ff4d4d;
}

.neither {
  background-color: #d3d3d3;
}

.agree {
  background-color: #66ff66;
}

.skip {
  background-color: #f0f0f0;
  align-items: center;
  margin-top: 10px;
}

.results {
  margin-top: 20px;
}

@media screen and (max-width: 768px) {
  .buttons {
    flex-direction: column;
    gap: 10px;
  }
}

</style>