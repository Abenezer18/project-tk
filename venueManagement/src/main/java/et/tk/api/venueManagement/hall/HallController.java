package et.tk.api.venueManagement.hall;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    @Autowired
    private HallService hallService;

    @PostMapping("/{venueId}/halls")
    public ResponseEntity<String> createHall(@PathVariable String venueId, @RequestBody Hall hall) {
        String status = hallService.createHall(venueId, hall);
        if (Objects.equals(status, "name"))
            return new ResponseEntity<>("name exists!", HttpStatus.FOUND);
        else if (Objects.equals(status, "venue"))
            return new ResponseEntity<>("venue dose not exist!", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Hall created", HttpStatus.CREATED);
    }

    @GetMapping("/{venueId}/halls")
    public ResponseEntity<List<Hall>> getHallsByVenueId(@PathVariable String venueId) {
        List<Hall> halls = hallService.getHallsByVenueId(venueId);
        if (halls == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(halls,HttpStatus.FOUND);
    }

    @GetMapping("/{hallId}")
    public ResponseEntity<Hall> getHallById(@PathVariable String hallId) {
        Hall hall = hallService.getHallById(hallId);
        if (hall == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(hall, HttpStatus.OK);
    }

    @PutMapping("/{hallId}")
    public ResponseEntity<String> updateHall(@PathVariable String hallId, @RequestBody Hall hall) {
        String status = hallService.updateHall(hallId, hall);
        if (Objects.equals(status, "hall"))
            return new ResponseEntity<>("hall dose not exist", HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "venue id"))
            return new ResponseEntity<>("Venue ID does not match, if you want to change venue you must delete and create the hall again.", HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "name"))
            return new ResponseEntity<>("hall name exists", HttpStatus.FOUND);
        else if (Objects.equals(status, "updated"))
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        return new ResponseEntity<>("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{hallId}")
    public ResponseEntity<Void> deleteHall(@PathVariable String hallId) {
        String status = hallService.deleteHall(hallId);
        if (Objects.equals(status, "deleted"))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

