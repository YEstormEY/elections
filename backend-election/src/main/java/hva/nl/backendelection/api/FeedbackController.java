package hva.nl.backendelection.api;

import hva.nl.backendelection.model.Feedback;
import hva.nl.backendelection.model.FeedbackItem;
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

    /**
     * Submit new feedback along with feedback items.
     *
     * @param feedback The feedback object from the request body.
     * @return A response indicating success.
     */
    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submitFeedback(@RequestBody Feedback feedback) {
        // Associate feedback with each feedback item
        feedback.getFeedbackItems().forEach(item -> item.setFeedback(feedback));

        feedbackService.saveFeedback(feedback);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Feedback submitted successfully!");

        return ResponseEntity.ok(response);
    }

    /**
     * Get all feedback submissions.
     *
     * @return A list of all feedback.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbackList = feedbackService.getAllFeedback();
        return ResponseEntity.ok(feedbackList);
    }

    /**
     * Get specific feedback by ID.
     *
     * @param id The feedback ID.
     * @return The feedback object or a 404 response if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete specific feedback by ID.
     *
     * @param id The feedback ID.
     * @return A response indicating success.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteFeedbackById(@PathVariable Long id) {
        feedbackService.deleteFeedbackById(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Feedback deleted successfully!");

        return ResponseEntity.ok(response);
    }

    /**
     * Add a single feedback item to existing feedback.
     *
     * @param feedbackId   The feedback ID to which the item belongs.
     * @param feedbackItem The feedback item to add.
     * @return The saved feedback item.
     */
    @PostMapping("/{feedbackId}/add-item")
    public ResponseEntity<FeedbackItem> addFeedbackItem(@PathVariable Long feedbackId, @RequestBody FeedbackItem feedbackItem) {
        return feedbackService.getFeedbackById(feedbackId)
                .map(feedback -> {
                    feedbackItem.setFeedback(feedback);
                    FeedbackItem savedItem = feedbackService.saveFeedbackItem(feedbackItem);
                    return ResponseEntity.ok(savedItem);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
