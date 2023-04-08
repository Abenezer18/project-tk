package et.tk.api.userManagement.venueAdmin;

import et.tk.api.userManagement.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venue_admin")
public class VenueAdminController {
    @Autowired
    private VenueAdminService venueAdminService;

    @PostMapping
    public ResponseEntity<String> createVenueAdmin(@RequestBody VenueAdmin venueAdmin) {
        String status = venueAdminService.createVenueAdmin(venueAdmin);
        switch (status) {
            case "venue" -> {
                return new ResponseEntity<>("Venue management service is returning an error!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            case "venueNotFound" -> {
                return new ResponseEntity<>("Venue not found!", HttpStatus.BAD_REQUEST);
            }
            case "created" -> {
                return new ResponseEntity<>("Created", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Unknown error, BUG", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<List<VenueAdmin>> getVenueAdmins() {
        return new ResponseEntity<>(venueAdminService.getVenueAdmins(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueAdmin> getVenueAdminById(@PathVariable String id){
        return new ResponseEntity<>(venueAdminService.getVenueAdminById(id),HttpStatus.OK);
    }

    @GetMapping("/venue/{id}")
    public ResponseEntity<VenueAdmin> getAdminByVenueId(@PathVariable String venueId){
        return new ResponseEntity<>(venueAdminService.getAdminByVenueId(venueId),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVenueAdmin(@PathVariable String id, @RequestBody VenueAdmin venueAdmin) {
        String status = venueAdminService.updateVenueAdmin(id, venueAdmin);

        switch (status) {
            case "not_found" ->{
                return new ResponseEntity<>("Venue Admin not found", HttpStatus.NOT_FOUND);
            }
            case "updated" ->{
                return new ResponseEntity<>("Updated",HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Unknown error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVenueAdmin(@PathVariable String id) {
        int status = venueAdminService.deleteVenueAdmin(id);
        if (status == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
