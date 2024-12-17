package hva.nl.backendelection.demo;

import hva.nl.backendelection.utility.PathUtils;
import lombok.Getter;
import hva.nl.backendelection.utility.xml.DutchElectionProcessor;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Map;

@Getter
@Component
public class TotalVotesProcessor {

    private Map<String, String> electionData;

    // Process the data and store it in the field
    public void processElectionData() throws IOException, XMLStreamException {
        System.out.println("Processing files...");

        // We need a Transformer that has knowledge of your classes.
        DutchElectionTransformer creator = new DutchElectionTransformer();

        // And the election processor that traverses the folders and processes the XML files.
        DutchElectionProcessor<Election> electionProcessor = new DutchElectionProcessor<>(creator);

        // Process the XML files and store the data in electionData
        Election election = electionProcessor.processResults("TK2021", PathUtils.getResourcePath("/totaal_votes"));
        this.electionData = election.data; // Correct access to the 'data' field

        System.out.println("All files are processed.\n");
        System.out.println(election);
    }

}
