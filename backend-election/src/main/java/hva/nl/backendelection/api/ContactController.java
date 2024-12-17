package hva.nl.backendelection.api;

import hva.nl.backendelection.model.Contact;
import hva.nl.backendelection.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact") // replace with your frontend URL if different
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/submit")
    public ResponseEntity<Contact> submitContactForm(@RequestBody Contact contact) {
        Contact savedContact = contactRepository.save(contact);
        return ResponseEntity.ok(savedContact);
    }
}
