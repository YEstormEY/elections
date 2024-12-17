package hva.nl.backendelection.service;

import hva.nl.backendelection.model.Vote;
import hva.nl.backendelection.model.request.VoteRequest;
import hva.nl.backendelection.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    // Method to cast a vote
    public String castVote(VoteRequest voteRequest) {
        try {
            // Check if the user has already voted
            Optional<Vote> existingVote = voteRepository.findByUserId(voteRequest.getUserId());

            if (existingVote.isPresent()) {
                return "You have already voted."; // User has already voted
            }

            // Create and save a new Vote object
            Vote vote = new Vote();
            vote.setUserId(voteRequest.getUserId());
            vote.setPartyId(voteRequest.getPartyId());
            voteRepository.save(vote);

            return "Vote cast successfully."; // Return success message
        } catch (Exception e) {
            // Log the exception (you might use a logging framework)
            // logger.error("Error casting vote: ", e); // Uncomment if using a logger
            return "Error casting vote."; // Indicate failure
        }
    }

    // Method to get all votes
    public List<Vote> getAllVotes() {
        return voteRepository.findAll(); // Return all votes from the repository
    }

    // Method to get vote counts by party
    public Map<Long, Long> getVoteCountsByParty() {
        List<Vote> votes = getAllVotes(); // Retrieve all votes
        Map<Long, Long> voteCounts = new HashMap<>();

        // Count votes for each party
        for (Vote vote : votes) {
            Long partyId = vote.getPartyId();
            voteCounts.put(partyId, voteCounts.getOrDefault(partyId, 0L) + 1);
        }

        return voteCounts; // Return the aggregated vote counts
    }
}
