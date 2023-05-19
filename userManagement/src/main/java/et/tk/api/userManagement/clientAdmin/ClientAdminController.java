package et.tk.api.userManagement.clientAdmin;

// import et.tk.api.userManagement.venueAdmin.VenueAdmin;
// import et.tk.api.userManagement.venueAdmin.VenueAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client_admin")
public class ClientAdminController {
    @Autowired
    private ClientAdminService clientAdminService;

    @PostMapping
    public ResponseEntity<String> createClientAdmin(@RequestBody ClientAdmin clientAdmin) {
        String status = clientAdminService.createClientAdmin(clientAdmin);
        switch (status) {
            case "client" -> {
                return new ResponseEntity<>("Venue management service is returning an error!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            case "ClientNotFound" -> {
                return new ResponseEntity<>("Client not found!", HttpStatus.BAD_REQUEST);
            }
            case "created" -> {
                return new ResponseEntity<>("Created", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Unknown error, BUG", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<List<ClientAdmin>> getClientAdmins() {
        return new ResponseEntity<>(clientAdminService.getClientAdmins(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientAdmin> getClientAdminById(@PathVariable String id){
        return new ResponseEntity<>(clientAdminService.getClientAdminById(id),HttpStatus.OK);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<ClientAdmin> getAdminByClientId(@PathVariable String clientId){
        return new ResponseEntity<>(clientAdminService.getAdminByClientId(clientId),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClientAdmin(@PathVariable String id, @RequestBody ClientAdmin clientAdmin) {
        String status = clientAdminService.updateClientAdmin(id, clientAdmin);

        switch (status) {
            case "not_found" ->{
                return new ResponseEntity<>("Venue Admin not found.", HttpStatus.NOT_FOUND);
            }
            case "client" ->{
                return new ResponseEntity<>("Venue Management error.",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            case "clientId" ->{
                return new ResponseEntity<>("Client admin exists fot the client.",HttpStatus.BAD_REQUEST);
            }
            case "updated" ->{
                return new ResponseEntity<>("Updated",HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Unknown error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVenueAdmin(@PathVariable String id) {
        int status = clientAdminService.deleteClientAdmin(id);
        if (status == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
