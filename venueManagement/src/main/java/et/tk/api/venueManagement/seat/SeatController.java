package et.tk.api.venueManagement.seat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/{id}")
    public ResponseEntity<SeatDto> getSeat(@PathVariable String id) {
        System.out.println("\n\n gygy \n\n");
        SeatDto seat = seatService.getSeat(id);
        if (seat == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(seat, HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSeat(@PathVariable String id, @RequestBody SeatDto seatDto) {
        String status = seatService.updateSeat(id, seatDto);
        if (status == "not exist")
            return new ResponseEntity<>("Seat does not exist in hall", HttpStatus.NOT_FOUND);
        else if (status == "name") {
            return new ResponseEntity<>("seat exists, change name.", HttpStatus.FOUND);
        }
        return new ResponseEntity<>("Updated", HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeat(@PathVariable String id) {
        String status = seatService.deleteSeat(id);
        if (status == "not found")
            return new ResponseEntity<>(status,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}