<script lang="ts">
import { defineComponent } from 'vue'

type Question = {
  title: string;
  content: string;
};
type Results = {
  userId: string;
  score: number;
};

export default defineComponent({
  data() {
    return {
      quizStarted: false,
      questions: [] as Question[], // Questions array
      currentQuestionIndex: 0, // Index of the current question
      questionTitle: '', // Title of the current question
      question: '', // Content of the current question
      progress: 0, // Progress percentage
      userAnswers: {} as Record<number, string | number>, // User's answers
      results: null as Results | null, // Quiz results
      userId: null as string | null, // User ID
    };
  },
  mounted() {
    this.startQuiz(); // Start the quiz immediately when the component loads
  },
  methods: {
    async startQuiz() {
      try {
        const response = await fetch("http://localhost:8080/api/quiz/questions");
        this.questions = await response.json();
        this.quizStarted = true;
        this.loadQuestion();
        this.updateProgress();
      } catch (error) {
        console.error("Error fetching quiz data:", error);
      }
    },
    loadQuestion() {
      console.log(this.questions);
      const currentQuestion = this.questions[this.currentQuestionIndex];
      this.questionTitle = currentQuestion.title;
      this.question = currentQuestion.content;
    },
    updateProgress() {
      this.progress = ((this.currentQuestionIndex + 1) / this.questions.length) * 100;
    },
    nextQuestion() {
      if (this.currentQuestionIndex < this.questions.length - 1) {
        this.currentQuestionIndex++;
        this.loadQuestion();
        this.updateProgress();
      } else {
        console.log("Quiz completed!");
        this.quizStarted = false;
      }
    },
    skip() {
      console.log("Question skipped");
      this.nextQuestion();
    },
    async answer(option: string | number) {
      try {
        console.log("Answer chosen:", option);
        this.userAnswers[this.currentQuestionIndex] = option;

        await new Promise((resolve) => setTimeout(resolve, 100)); // Simulate delay
        console.log("Mock fetch call - no request sent");
        // Send the answer to the backend
        // temporarily commented out to test the rest of the code
        // await fetch("http://localhost:8080/api/quiz/results", {
        //   method: "POST",
        //   headers: {
        //     "Content-Type": "application/json",
        //   },
        //   body: JSON.stringify({
        //     answers: this.userAnswers,
        //     userId: this.userId,
        //     questionIndex: this.currentQuestionIndex,
        //     answer: option,
        //   }),

        // Check if the quiz is finished
        if (this.currentQuestionIndex === this.questions.length - 1) {
          await this.finishQuiz();
          console.log("User answers before finishing quiz:", this.userAnswers);
        } else {
          this.nextQuestion();
        }
      } catch (error) {
        console.error("Error sending answer:", error);
      }
    },

    async finishQuiz() {
      try {
        console.log("Quiz finished!");
        this.quizStarted = false;

        await new Promise((resolve) => setTimeout(resolve, 500)); // Simulate network delay
        const mockResponse = {
          success: true,
          message: "Quiz results processed successfully",
          data: {
            userId: this.userId || "mock-user-id",
            answers: this.userAnswers,
            score: Math.floor(Math.random() * 100), // Mock score generation
          },
        };
        console.log("Mock results from backend:", mockResponse);
        // Optionally send all answers at the end
        // await fetch("http://localhost:8080/api/quiz/results", {
        //   method: "POST",
        //   headers: {
        //     "Content-Type": "application/json",
        //   },
        //   body: JSON.stringify({
        //     answers: this.userAnswers,
        //   }),
        // });
        if (!mockResponse.success) {
          console.error("Failed to fetch results:", mockResponse.message);
          return;
        }
        this.results = mockResponse.data;
        console.log("Quiz results:", this.results);
        console.log("All answers submitted to the backend");
      } catch (error) {
        // console.error("Error finishing quiz:", error);
      }
    },

  },
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
        <img src="../assets/VotingGuide.png" alt="Voting illustration" />
      </div>
    </div>

    <div class="quiz-container">
    <div class="progress-bar" :style="{ width: progress + '%' }"></div>
    <div class="quiz-question">
      <div startQuiz>
        <h2>{{ questionTitle }}</h2>
        <p>{{ question }}</p>
        <div class="buttons">
          <button class="btn disagree" @click="answer('disagree')">disagree</button>
          <button class="btn neither" @click="answer('neither')">neither</button>
          <button class="btn agree" @click="answer('agree')">agree</button>
        </div>
        <button class="btn skip" @click="skip">Skip →</button>
      </div>
      <div v-if="results">
        <h2>Your Quiz Results</h2>
        <p>Your final score is: {{ results.score }}</p>
        <ul>
<!--          <li v-for="(score, index) in results.scores" :key="index">-->
<!--            Question {{ index + 1 }}: {{ score }}-->
<!--          </li>-->
        </ul>
      </div>
  </div>
    </div>

</template>

<style scoped>
.welcome-container {
  background-color: #24786E99;
  padding: 20px;
  min-height: 50vh;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: wrap;
}
@media (max-width: 768px) {
  .welcome-container {
    flex-direction: column;
    height: auto;
  }
}
.welcome-message {
  flex: 1;
  text-align: left;
  margin-bottom: 20px;
}
@media (max-width: 768px) {
  .welcome-message {
    width: 100%;
    text-align: center;
  }
}
.welcome-message h1 {
  color: #ffffff;
  font-size: 28px;
  margin-bottom: 20px;
}

.welcome-message p {
  color: #ffffff;
  line-height: 1.5;
  margin-bottom: 20px;
  font-size: 16px;
  margin-top: 10px;
}
@media (max-width: 768px) {
  .welcome-message p {
    font-size: 16px;
  }
}
.welcome-image {
  flex: 1;
  max-width: 400px;
  display: flex;
  margin-right: 40px;
  justify-content: center;
}

.welcome-image img {
  max-width: 100%; /* Make the image responsive */
  height: auto;
  border-radius: 10px;
}

.quiz-container {
  text-align: left;
  padding: 20px;
}

.progress-bar {
  width: 0 ;
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
  background-color: #28a745;
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