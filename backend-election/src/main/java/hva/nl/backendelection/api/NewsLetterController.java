package hva.nl.backendelection.api;

import hva.nl.backendelection.repository.NewsLetterRepository;
import hva.nl.backendelection.model.NewsLetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsletter")
public class NewsLetterController {

    @Autowired
    private NewsLetterRepository newsLetterRepository;

    @PostMapping("/submit")
    public ResponseEntity<NewsLetter> submitNewsLetterForm(@RequestBody NewsLetter newsLetter) {
        NewsLetter savedNewsLetter = newsLetterRepository.save(newsLetter);
        return ResponseEntity.ok(savedNewsLetter);
    }
}
