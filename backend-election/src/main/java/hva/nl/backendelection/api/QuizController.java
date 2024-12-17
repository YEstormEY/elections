package hva.nl.backendelection.api;
import hva.nl.backendelection.model.Partyscore;
import hva.nl.backendelection.model.Questions;
import hva.nl.backendelection.model.request.QuizRequest;
import hva.nl.backendelection.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/quiz/questions")
    public List <Questions> getQuizQuestions() {
        return quizService.getQuestions();
    }

    @PostMapping("/quiz/results")
    public List<Partyscore> getQuizResults(@RequestBody QuizRequest request) {
        System.out.println("POST request received for /quiz/results");
        System.out.println("Request body: " + request);
        // Calculate top 3 parties
        List<Partyscore> topParties = quizService.getTopMatchingParties(request.getAnswers());

        // Save results to the database
        quizService.saveQuizResults(request.getUserId(), topParties);

        return topParties;
    }
}


