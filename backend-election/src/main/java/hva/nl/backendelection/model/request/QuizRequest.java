package hva.nl.backendelection.model.request;
import hva.nl.backendelection.model.Questions;
import hva.nl.backendelection.model.QuizScore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class QuizRequest {
    private Long userId; // User ID
    private List<QuizScore> answers;
    private List<Questions> questions;
}

