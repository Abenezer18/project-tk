package et.tk.api.venueManagement.hall;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halls")
public class HallController {

    @Autowired
    private HallService hallService;

    @GetMapping("/{hallId}")
    public ResponseEntity<HallDto> getHall(@PathVariable String hallId) {
        HallDto hall = hallService.getHall(hallId);
        if (hall == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(hall, HttpStatus.OK);
    }

    @PutMapping("/{hallId}")
    public ResponseEntity<String> updateHall(@PathVariable String hallId, @RequestBody HallDto hallDto) {
        String status = hallService.updateHall(hallId, hallDto);
        if (status == "hall")
            return new ResponseEntity<>("hall dose not exist", HttpStatus.NOT_FOUND);
        else if (status == "name")
            return new ResponseEntity<>("hall name exists", HttpStatus.FOUND);
        else
            return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    @DeleteMapping("/{hallId}")
    public ResponseEntity<Void> deleteHall(@PathVariable String hallId) {
        String status = hallService.deleteHall(hallId);
        if (status == "deleted")
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

