package et.tk.venueManagement.api.venue;

import et.tk.venueManagement.api.hall.HallDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @Autowired
    private VenueRepository venueRepository;

    @PostMapping
    public ResponseEntity<VenueInfoView> createVenue(@RequestBody VenueDto venueDto) {
        VenueInfoView savedVenueDto = venueService.createVenue(venueDto);
        if (savedVenueDto == null) // checking if name exists
            return new ResponseEntity<>(HttpStatus.FOUND);
        return new ResponseEntity<>(savedVenueDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VenueInfoView>> getVenues() {
        List<VenueInfoView> venueInfoView = venueService.getVenues();
        if (venueInfoView == null)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(venueInfoView,HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueInfoView> getVenueById(@PathVariable String id) {
        VenueInfoView venueDto = venueService.getVenueById(id);
        if (venueDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(venueDto,HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueInfoView> updateVenue(@PathVariable String id, @RequestBody VenueDto venueDto) {
        VenueInfoView updatedVenueDto = venueService.updateVenue(id, venueDto);
        if (updatedVenueDto == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updatedVenueDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVenue(@PathVariable String id) {
        String status = venueService.deleteVenue(id);
        if (status == "not found")
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>("Deleted",HttpStatus.OK);

    }

    @PostMapping("/{id}/halls")
    public ResponseEntity<String> createHall(@PathVariable String id, @RequestBody HallDto hallDto) {
        String status = venueService.createHall(id, hallDto);
        if (status == "name")
            return new ResponseEntity<>("name exists!", HttpStatus.FOUND);
        else if (status == "venue")
            return new ResponseEntity<>("venue dose not exist!", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Hall created", HttpStatus.CREATED);
    }

    @GetMapping("/{id}/halls")
    public ResponseEntity<List<HallDto>> getHallsByVenueId(@PathVariable String id) {
        List<HallDto> hallDtos = venueService.getHallsByVenueId(id);
        if (hallDtos == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(hallDtos,HttpStatus.FOUND);
    }
}
