package et.tk.api.venueManagement.seat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping("/{hallId}/seats")
    public ResponseEntity<String> createSeat(@PathVariable String hallId, @RequestBody Seat seat) {
        String status = seatService.createSeat(hallId, seat);
        if (Objects.equals(status, "hall"))
            return new ResponseEntity<>("Hall does not exist",HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "name")) {
            return new ResponseEntity<>("Seat already exists",HttpStatus.FOUND);
        } else
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{hallId}/seats")
    public ResponseEntity<List<Seat>> getAllSeats(@PathVariable String hallId) {
        return new ResponseEntity<>(seatService.getAllSeats(hallId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeat(@PathVariable String id) {
        Seat seat = seatService.getSeat(id);
        if (seat == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(seat, HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSeat(@PathVariable String id, @RequestBody Seat seat) {
        String status = seatService.updateSeat(id, seat);
        if (Objects.equals(status, "not exist"))
            return new ResponseEntity<>("Seat does not exist in hall", HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "hall"))
            return new ResponseEntity<>("Hall ID does not match, if you want to change hall you must delete and create the seat again.", HttpStatus.FOUND);
        else if (Objects.equals(status, "name"))
            return new ResponseEntity<>("seat exists, change name.", HttpStatus.FOUND);
        return new ResponseEntity<>("Updated", HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeat(@PathVariable String id) {
        String status = seatService.deleteSeat(id);
        if (Objects.equals(status, "not found"))
            return new ResponseEntity<>(status,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}