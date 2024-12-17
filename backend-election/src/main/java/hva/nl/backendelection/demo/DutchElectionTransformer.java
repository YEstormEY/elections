package hva.nl.backendelection.demo;

import hva.nl.backendelection.utility.xml.Transformer;
import java.util.Map;

/**
 * A dummy {@link Transformer} that just prints the election data so you can get an understanding of what
 * information is available within each method.
 * <br>
 * <b>Please do NOT include this code in you project!</b>
 */
public class DutchElectionTransformer implements Transformer<Election> {
    private final Election election = new Election();

    @Override
    public void registerElection(Map<String, String> electionData) {
        election.data = electionData;
        System.out.printf("Found election information: %s\n", electionData);
    }

    @Override
    public void registerContest(Map<String, String> contestData) {
        election.data = contestData;
        System.out.printf("Found contest information: %s\n", contestData);
    }

    @Override
    public void registerAffiliation(Map<String, String> affiliationData) {
        election.data = affiliationData;
        System.out.printf("Found affiliation information: %s\n", affiliationData);
    }

    @Override
    public void registerCandidate(Map<String, String> candidateData) {
        election.data = candidateData;
        System.out.printf("Found candidate information: %s\n", candidateData);
    }

    @Override
    public void registerVotes(Map<String, String> votesData) {
        if (votesData != null && !votesData.isEmpty()) {
            System.out.printf("Registering votes: %s\n", votesData);
            election.data.put(votesData.get("PartyName"), votesData.get("TotalVotes"));
        } else {
            System.out.println("No votes data received.");
        }
    }



    @Override
    public Election retrieve() {
        System.out.println("Election data: " + election.data);
        return election;
    }

}
