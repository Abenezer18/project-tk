package et.tk.api.userManagement.systemAdmin;

import et.tk.api.userManagement.venueAdmin.VenueAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system_admin")
public class SystemAdminController {

    @Autowired
    private SystemAdminService systemAdminService;

    @PostMapping
    public ResponseEntity<String> createSystemAdmin(@RequestBody SystemAdmin systemAdmin) {
        String status = systemAdminService.createSystemAdmin(systemAdmin);
        //            case "name" -> {
        //                return new ResponseEntity<>("name is already in use!", HttpStatus.BAD_REQUEST);
        //            }
        if (status.equals("created")) {
            return new ResponseEntity<>("Created", HttpStatus.OK);
        }
        return new ResponseEntity<>("Unknown error, BUG", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<List<SystemAdmin>> getSystemAdmins() {
        return new ResponseEntity<>(systemAdminService.getSystemAdmins(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemAdmin> getSystemAdminById(@PathVariable String id){
        return new ResponseEntity<>(systemAdminService.getSystemAdminById(id),HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<SystemAdmin> getAdminByName(@PathVariable String name){
        return new ResponseEntity<>(systemAdminService.getSystemAdminByName(name),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSystemAdmin(@PathVariable String id, @RequestBody SystemAdmin systemAdmin) {
        String status = systemAdminService.updateSystemAdmin(id, systemAdmin);

        switch (status) {
            case "not_found" ->{
                return new ResponseEntity<>("System Admin not found", HttpStatus.NOT_FOUND);
            }
            case "updated" ->{
                return new ResponseEntity<>("Updated",HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Unknown error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSystemAdmin(@PathVariable String id) {
        int status = systemAdminService.deleteSystemAdmin(id);
        if (status == 0)
            return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

}
