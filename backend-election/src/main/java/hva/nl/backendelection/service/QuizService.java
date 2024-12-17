package hva.nl.backendelection.service;
import hva.nl.backendelection.model.Partyscore;
import hva.nl.backendelection.model.Questions;
import hva.nl.backendelection.model.QuizScore;
import hva.nl.backendelection.repository.PartyScoreRepository;
import hva.nl.backendelection.repository.QuizRepository;
import hva.nl.backendelection.repository.UserQuizResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private PartyScoreRepository partyScoreRepository;
    @Autowired
    private UserQuizResultsRepository userQuizResultsRepository;

    public List<Questions> getQuestions() {
        return quizRepository.findAll();
    }

    public List<Partyscore> getTopMatchingParties(List<QuizScore> answers) {
        return partyScoreRepository.findAll();
    }

    public List<QuizScore> saveQuizResults(Long userId, List<Partyscore> topParties) {
        return userQuizResultsRepository.findAll();
    }
    // Example parties and their stances
  /*  private List<Party> getParties() {
        Map<String, String> VVD = Map.of(
                "Fake News", "disagree",
                "Climate Change", "agree",
                "Education", "agree"
        );
        Map<String, String> PVDA = Map.of(
                "Fake News", "agree",
                "Climate Change", "neither",
                "Education", "disagree"
        );
        Map<String, String> PVV = Map.of(
                "Fake News", "neither",
                "Climate Change", "agree",
                "Education", "agree"
        );

        return Arrays.asList(
                new Party(),
                new Party(),
                new Party()
        );
    }
//*/
//    /**
//     * Calculate the top matching parties for the user based on their quiz answers.
//     *
//     * @param userAnswers List of user's quiz answers
//     * @return List of top 3 parties with their alignment percentages
//     */
//    public List<Partyscore> getTopMatchingParties(List<QuizResultsRepository> userAnswers) {
//        List<Party> parties = getParties();
//        Map<Party, Integer> scoreMap = new HashMap<>();
//        int totalQuestions = userAnswers.size();
//
//        // Calculate score for each party
//        for (Party party : parties) {
//            int score = 0;
//            for (QuizResultsRepository answer : userAnswers) {
//                String stance = party.get().get(answer.getQuestion());//make party stances on questions quiz....
//                if (stance != null && stance.equals(answer.getAnswer())) {
//                    score++;
//                }
//            }
//            scoreMap.put(party, score);
//        }

    // Convert scores to percentages
//        List<Partyscore> partyScores = new ArrayList<>();
//        for (Map.Entry<Party, Integer> entry : scoreMap.entrySet()) {
//            double percentage = (entry.getValue() / (double) totalQuestions) * 100;
//            partyScores.add(new Partyscore(getParty_name(), percentage));
//        }
//
//        // Sort parties by score percentage in descending order
//        partyScores.sort((a, b) -> Double.compare(b.getScorePercentage(), a.getScorePercentage()));
//
//        // Return the top 3 parties
//        return partyScores.stream().limit(3).toList();
//    }

//    public QuizResultsRepository getResultRepository() {
//        return resultRepository;
//    }

//    public void setResultRepository(QuizResultsRepository resultRepository) {
//        this.resultRepository = resultRepository;
//    }
//
//    public void saveQuizResults(Long userId, List<Partyscore> topParties) {
//    }
//}}
//
//
//
}