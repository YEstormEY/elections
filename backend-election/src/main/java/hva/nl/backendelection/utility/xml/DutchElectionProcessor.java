package hva.nl.backendelection.utility.xml;

import hva.nl.backendelection.utility.PathUtils;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DutchElectionProcessor<E> {
    private static final Logger LOG = Logger.getLogger(DutchElectionProcessor.class.getName());
    private final Transformer<E> transformer;

    public static final String REGISTERED_NAME = "RegisteredName";
    public static final String AFFILIATION_IDENTIFIER = "AffiliationIdentifier";
    public static final String VALID_VOTES = "ValidVotes";

    private final Map<String, Integer> totalVotesMap = new HashMap<>();


    public DutchElectionProcessor(Transformer<E> transformer) {
        this.transformer = transformer;
    }

    public E processResults(String electionId, String folderName) throws IOException, XMLStreamException {

        List<Path> files = PathUtils.findFilesToScan(folderName, "Totaaltelling_%s".formatted(electionId));

        for (Path file : files) {
            XMLParser parser = new XMLParser(new FileInputStream(file.toString()));
            processVotes(parser);
        }

        // Send the aggregated results
        for (Map.Entry<String, Integer> entry : totalVotesMap.entrySet()) {
            Map<String, String> result = new HashMap<>();
            result.put("PartyName", entry.getKey());
            result.put("TotalVotes", String.valueOf(entry.getValue()));
            transformer.registerVotes(result);
        }

        return transformer.retrieve();
    }


    private void processVotes(XMLParser parser) throws XMLStreamException {
        while (parser.findBeginTag("Selection")) {
            String partyName = null;
            Integer votes = null; // Use Integer to check if votes were already processed

            // Find the party name in <RegisteredName> within <AffiliationIdentifier>
            if (parser.findBeginTag(AFFILIATION_IDENTIFIER)) {
                if (parser.findBeginTag(REGISTERED_NAME)) {
                    partyName = parser.getElementText();
                    parser.findAndAcceptEndTag(REGISTERED_NAME);
                }
                parser.findAndAcceptEndTag(AFFILIATION_IDENTIFIER);
            }

            // If party name is found, fetch the first <ValidVotes> immediately following
            if (partyName != null && !totalVotesMap.containsKey(partyName)) {
                if (parser.findBeginTag(VALID_VOTES)) {
                    try {
                        votes = Integer.parseInt(parser.getElementText());
                        LOG.info("ValidVotes for " + partyName + ": " + votes);
                    } catch (NumberFormatException e) {
                        LOG.warning("Invalid vote count for party: " + partyName);
                    }
                    parser.findAndAcceptEndTag(VALID_VOTES);
                }

                // Add the first <ValidVotes> for the party to the map
                if (votes != null) {
                    totalVotesMap.put(partyName, votes);
                    LOG.info("Votes aggregated for " + partyName + ": " + votes);
                }
            }

            // Skip remaining selections for this <AffiliationIdentifier>
            parser.findAndAcceptEndTag("Selection");
        }
    }
}
