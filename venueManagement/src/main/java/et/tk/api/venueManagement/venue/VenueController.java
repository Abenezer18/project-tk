package et.tk.api.venueManagement.venue;

import et.tk.api.venueManagement.hall.HallDto;
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
                        schema = @Schema (implementation = VenueDto.class))})
    @ApiResponse(responseCode = "302", description = "Name Already exists", content = @Content)
    @PostMapping
    public ResponseEntity<VenueInfoView> createVenue(@RequestBody VenueDto venueDto) {
        VenueInfoView savedVenueDto = venueService.createVenue(venueDto);
        if (savedVenueDto == null) // checking if name exists
            return new ResponseEntity<>(HttpStatus.FOUND);
        return new ResponseEntity<>(savedVenueDto, HttpStatus.CREATED);
    }


    @GetMapping("/detailed/{id}")
    public ResponseEntity<VenueDetailedResponse> getDetailedVenueResponse(@PathVariable String id){
        VenueDetailedResponse venueDetailedResponses = venueService.getDetailedVenueResponse(id);
        if (venueDetailedResponses == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(venueDetailedResponses,HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<List<VenueInfoView>> getVenues() {
        List<VenueInfoView> venueInfoView = venueService.getVenues();
        if (venueInfoView.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(venueInfoView,HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueInfoView> getVenueById(@PathVariable String id) {
        System.out.println("in venue controller");
        VenueInfoView venueDto = venueService.getVenueById(id);
        if (venueDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(venueDto,HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVenue(@PathVariable String id, @RequestBody VenueDto venueDto) {
        String status = venueService.updateVenue(id, venueDto);
        if (status == "not found")
            return new ResponseEntity<>("Venue not found", HttpStatus.NOT_FOUND);
        else if (status == "name") {
            return new ResponseEntity<>("Name already exists",HttpStatus.FOUND);
        }
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVenue(@PathVariable String id) {
        String status = venueService.deleteVenue(id);
        if (status == "not found")
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>("Deleted",HttpStatus.OK);

    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<String> updateVenueAdmin(@PathVariable String id, @RequestBody String venueAdminId) {
        String status = venueService.updateVenueAdmin(id, venueAdminId);
        if (status == "venue")
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
