package hva.nl.backendelection.api;

import hva.nl.backendelection.model.Feedback;
import hva.nl.backendelection.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitFeedback(@RequestBody Feedback feedback) {
        // Set feedback reference for each feedback item
        feedback.getFeedbackItems().forEach(item -> item.setFeedback(feedback));

        feedbackService.saveFeedback(feedback);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Feedback submitted successfully!");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbackList = feedbackService.getAllFeedback();
        return ResponseEntity.ok(feedbackList);
    }
}
