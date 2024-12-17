package hva.nl.backendelection.demo;

import java.util.HashMap;
import java.util.Map;

public class Election {
    public Map<String, String> data = new HashMap<>(); // Ensure it's initialized

    @Override
    public String toString() {
        return "The last information received is: %s".formatted(data);
    }
}
