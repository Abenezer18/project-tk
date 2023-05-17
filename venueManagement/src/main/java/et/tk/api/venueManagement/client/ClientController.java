package et.tk.api.venueManagement.client;

import et.tk.api.venueManagement.hall.HallService;
import et.tk.api.venueManagement.venue.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @PostMapping
    public ResponseEntity<Client> createClient (@RequestBody Client client) {
        Client savedClient = clientService.createClient(client);
        if (savedClient == null)
            return new ResponseEntity<>(HttpStatus.FOUND);

        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        return new ResponseEntity<>(clientService.getClients(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable String id){
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClient(@PathVariable String id, @RequestBody Client client) {
        String status = clientService.updateClient(id, client);
        if (Objects.equals(status, "not found"))
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "name"))
            return new ResponseEntity<>("Name already exists",HttpStatus.FOUND);
        else if (Objects.equals(status, "updated"))
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        else
            return new ResponseEntity<>("unknown error!",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable String id) {
        String status = clientService.deleteClient(id);
        if (Objects.equals(status, "client"))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

//    @PutMapping("/client_admin/{id}")
//    public ResponseEntity<String> updateClientAdmin(@PathVariable String id, @RequestBody String venueAdminId) {
//        String status = clientService.updateClientAdmin(id, venueAdminId);
//
//        if (Objects.equals(status, "venue"))
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
