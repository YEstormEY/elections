//package hva.nl.backendelection.api;
//
//import hva.nl.backendelection.model.Partyscore;
//import hva.nl.backendelection.service.PartyscoreService;
//import hva.nl.backendelection.service.QuizService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//
//public class PartyscoreController {
//    public PartyscoreController() {super();}
//
//    /**
//     * The getAllPartyscore method retrieves all parties scores from the database.
//     *
//     * @return a list of all partyscore
//     */
//    @GetMapping("/party_score")
//    public ResponseEntity<Iterable<Partyscore>> getPartyScore() {
//       return ResponseEntity.ok(PartyscoreService());
//  }
//}
