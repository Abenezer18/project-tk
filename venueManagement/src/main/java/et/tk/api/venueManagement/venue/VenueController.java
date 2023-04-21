package et.tk.api.venueManagement.venue;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @Operation(summary = "Create Venue", description = "Creates new venue")
    @ApiResponse(responseCode = "201", description = "New venue created",
                    content = {@Content(mediaType = "application/json",
                        schema = @Schema (implementation = Venue.class))})
    @ApiResponse(responseCode = "302", description = "Name Already exists", content = @Content)
    @PostMapping("/{clientId}")
    public ResponseEntity<String> createVenue(@PathVariable String clientId, @RequestBody Venue venue) {
        String status = venueService.createVenue(clientId, venue);
        if (Objects.equals(status, "name"))
            return new ResponseEntity<>("Name exists! Change name", HttpStatus.FOUND);
        else if (Objects.equals(status, "client"))
            return new ResponseEntity<>("client dose not exist!", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Venue created", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Venue>> getVenues() {
        return new ResponseEntity<>(venueService.getVenues(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable String id) {
        Venue venue = venueService.getVenueById(id);

        if (venue == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(venue,HttpStatus.FOUND);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Venue>> getVenueByClientId(@PathVariable String clientId) {
        return new ResponseEntity<>(venueService.getVenuesByClientId(clientId),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVenue(@PathVariable String id, @RequestBody Venue venue) {
        String status = venueService.updateVenue(id, venue);

        if (Objects.equals(status, "venue"))
            return new ResponseEntity<>("Venue not found", HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "client id"))
            return new ResponseEntity<>("Client ID does not match, if you want to change client you must delete and create the venue again.", HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "name"))
            return new ResponseEntity<>("Name already exists",HttpStatus.FOUND);
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVenue(@PathVariable String id) {
        String status = venueService.deleteVenue(id);

        if (Objects.equals(status, "venue"))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>("Deleted",HttpStatus.OK);

    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<String> updateVenueAdmin(@PathVariable String id, @RequestBody String venueAdminId) {
        String status = venueService.updateVenueAdmin(id, venueAdminId);

        if (Objects.equals(status, "venue"))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
