package hva.nl.backendelection.api;

import hva.nl.backendelection.model.request.VoteRequest;
import hva.nl.backendelection.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap; // Import HashMap
import java.util.Map; // Import Map
import hva.nl.backendelection.model.Vote; // Import Vote
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000") // Allow CORS for this controller
public class VoteController {

    @Autowired
    private VoteService voteService;



    @PostMapping("/shadowElections")
    public ResponseEntity<Map<String, String>> castVote(@RequestBody VoteRequest voteRequest) {
        String message = voteService.castVote(voteRequest);
        Map<String, String> response = new HashMap<>(); // Initialize HashMap
        response.put("message", message);

        if (message.equals("Vote cast successfully.")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/votes") // Updated endpoint for getting all votes
    public List<Vote> getAllVotes() {
        return voteService.getAllVotes();
    }

    @GetMapping("/votes/counts") // Updated endpoint for getting vote counts by party
    public ResponseEntity<Map<Long, Long>> getVoteCounts() {
        Map<Long, Long> voteCounts = voteService.getVoteCountsByParty(); // Fetch vote counts from the service
        return ResponseEntity.ok(voteCounts); // Return the vote counts in the response
    }
}
