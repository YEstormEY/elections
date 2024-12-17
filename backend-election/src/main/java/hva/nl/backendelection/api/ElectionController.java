package hva.nl.backendelection.api;


import hva.nl.backendelection.demo.TotalVotesProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Map;

@RestController
public class ElectionController {

    @Autowired
    private TotalVotesProcessor totalVotesProcessor;

    /**
     * Endpoint to fetch the election data.
     */
    @GetMapping("/api/election-data")
    public Map<String, String> getElectionData() {
        try {
            // Process the data if not already processed
            if (totalVotesProcessor.getElectionData() == null) {
                totalVotesProcessor.processElectionData();
            }
            return totalVotesProcessor.getElectionData();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
            return null;
        }
    }
}
