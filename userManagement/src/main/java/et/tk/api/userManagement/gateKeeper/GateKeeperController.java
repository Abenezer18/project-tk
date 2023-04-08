package et.tk.api.userManagement.gateKeeper;

import et.tk.api.userManagement.systemAdmin.SystemAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gatekeeper")
public class GateKeeperController {

    @Autowired
    private GateKeeperService gateKeeperService;

    @PostMapping
    public ResponseEntity<String> createGateKeeper(@RequestBody GateKeeper gateKeeper) {
        String status = gateKeeperService.createGateKeeper(gateKeeper);
        //            case "name" -> {
        //                return new ResponseEntity<>("name is already in use!", HttpStatus.BAD_REQUEST);
        //            }

        // elegant code!!
        return switch (status) {
            case "venue" -> new ResponseEntity<>("venue service error", HttpStatus.INTERNAL_SERVER_ERROR);
            case "name" -> new ResponseEntity<>("Change name", HttpStatus.BAD_REQUEST);
            case "created" -> new ResponseEntity<>("Created", HttpStatus.OK);
            default -> new ResponseEntity<>("Unknown error, BUG", HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @GetMapping
    public ResponseEntity<List<GateKeeper>> getGateKeepers() {
        return new ResponseEntity<>(gateKeeperService.getGateKeepers(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GateKeeper> getGateKeeperById(@PathVariable String id){
        return new ResponseEntity<>(gateKeeperService.getGateKeeperById(id),HttpStatus.OK);
    }

    @GetMapping("/venue/{venueId}")
    public ResponseEntity<List<GateKeeper>> getGateKeeperByVenueId(@PathVariable String venueId) {
        return new ResponseEntity<>(gateKeeperService.getGateKeeperByVenueId(venueId),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGateKeeper(@PathVariable String id, @RequestBody GateKeeper gateKeeper) {
        String status = gateKeeperService.updateGateKeeper(id, gateKeeper);

        switch (status) {
            case "not_found" ->{
                return new ResponseEntity<>("GateKeeper not found", HttpStatus.NOT_FOUND);
            }
            case "name" ->{
                return new ResponseEntity<>("Change name",HttpStatus.BAD_REQUEST);
            }
            case "updated" ->{
                return new ResponseEntity<>("Updated",HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Unknown error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGateKeeper(@PathVariable String id) {
        int status = gateKeeperService.deleteGateKeeper(id);
        if (status == 0)
            return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

}
