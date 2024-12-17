package hva.nl.backendelection.service;

import hva.nl.backendelection.model.Contact;
import hva.nl.backendelection.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    /**
     * Save a new contact form submission.
     *
     * @param contact The contact form data to be saved.
     * @return The saved Contact entity.
     */
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    /**
     * Retrieve a contact form submission by ID.
     *
     * @param id The ID of the contact form submission.
     * @return An Optional containing the Contact entity if found.
     */
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    /**
     * Delete a contact form submission by ID.
     *
     * @param id The ID of the contact form submission to delete.
     */
    public void deleteContactById(Long id) {
        contactRepository.deleteById(id);
    }

    /**
     * Retrieve all contact form submissions.
     *
     * @return An iterable list of all Contact entities.
     */
    public Iterable<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
}
