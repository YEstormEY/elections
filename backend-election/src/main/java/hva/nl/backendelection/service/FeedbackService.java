package hva.nl.backendelection.service;

import hva.nl.backendelection.model.Feedback;
import hva.nl.backendelection.model.FeedbackItem;
import hva.nl.backendelection.repository.FeedbackRepository;
import hva.nl.backendelection.repository.FeedbackItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackItemRepository feedbackItemRepository;

    /**
     * Save feedback along with its items.
     *
     * @param feedback The feedback to save.
     * @return The saved feedback.
     */
    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    /**
     * Retrieve all feedback submissions.
     *
     * @return A list of all feedback.
     */
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    /**
     * Retrieve feedback by ID.
     *
     * @param id The ID of the feedback.
     * @return An Optional containing the feedback if found.
     */
    public Optional<Feedback> getFeedbackById(Long id) {
        return feedbackRepository.findById(id);
    }

    /**
     * Delete feedback by ID.
     *
     * @param id The ID of the feedback to delete.
     */
    public void deleteFeedbackById(Long id) {
        feedbackRepository.deleteById(id);
    }

    /**
     * Save a single feedback item.
     *
     * @param feedbackItem The feedback item to save.
     * @return The saved feedback item.
     */
    public FeedbackItem saveFeedbackItem(FeedbackItem feedbackItem) {
        return feedbackItemRepository.save(feedbackItem);
    }

    /**
     * Retrieve all feedback items.
     *
     * @return A list of all feedback items.
     */
    public List<FeedbackItem> getAllFeedbackItems() {
        return feedbackItemRepository.findAll();
    }
}

