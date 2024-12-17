package hva.nl.backendelection.service;

import hva.nl.backendelection.model.NewsletterSubscriber;
import hva.nl.backendelection.repository.NewsletterSubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NewsletterService {

    @Autowired
    private NewsletterSubscriberRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    public String subscribe(String name, String email) {
        if (repository.existsByEmail(email)) {
            return "E-mailadres is al ingeschreven.";
        }

        NewsletterSubscriber subscriber = new NewsletterSubscriber();
        subscriber.setName(name);
        subscriber.setEmail(email);

        repository.save(subscriber);

        sendWelcomeEmail(name, email);
        return "Je bent succesvol ingeschreven!";
    }

    private void sendWelcomeEmail(String name, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("🎉 Welkom bij onze nieuwsbrief, " + name + "!");
        message.setText(
                "Hey " + name + "!\n\n" +
                        "Supertof dat je je hebt aangemeld voor onze nieuwsbrief! We zijn blij dat je mee wilt blijven denken over belangrijke onderwerpen in de politiek en samenleving. 🎓🌍\n\n" +
                        "Hier is wat je kunt verwachten:\n" +
                        "- 🌟 Exclusieve updates over politiek en jongeren\n" +
                        "- 🎥 Interessante video’s en podcasts die complexe thema’s simpel maken\n" +
                        "- 🗳️ Tips en info over hoe jouw stem invloed kan hebben\n\n" +
                        "We sturen je binnenkort onze eerste update, maar voel je vrij om alvast rond te kijken op onze website en mee te doen aan discussies. Heb je vragen, ideeën of feedback? Stuur ons dan gerust een bericht. 📩\n\n" +
                        "Volg ons ook op social media om direct updates en leuke content te zien:\n" +
                        "- 📸 Instagram: @NextGenPolitics\n" +
                        "- 🐦 Twitter: @NextGenPolitics\n" +
                        "- 🎵 TikTok: @NextGenPolitics\n\n" +
                        "We kijken ernaar uit om je op de hoogte te houden!\n\n" +
                        "Groetjes,\n" +
                        "Het NextGenPoliticsTeam 🎉"
        );

        mailSender.send(message);
    }
}


