package et.tk.api.userManagement.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        String status = userService.createUser(user);
        switch (status) {
            case "name" -> {
                return new ResponseEntity<>("name", HttpStatus.BAD_REQUEST);
            }
            case "email" -> {
                return new ResponseEntity<>("email", HttpStatus.BAD_REQUEST);
            }
            case "phone" -> {
                return new ResponseEntity<>("Phone", HttpStatus.BAD_REQUEST);
            }
            case "added" -> {
                return new ResponseEntity<>("created", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Unknown error, BUG", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<User> getUserByPhone(@PathVariable String phone){
        return new ResponseEntity<>(userService.getUserByPhone(phone),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody User user) {
        String status = userService.updateUser(id, user);

        switch (status) {
            case "user" ->{
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            case "phone" ->{
                return new ResponseEntity<>("phone",HttpStatus.BAD_REQUEST);
            }
            case "name" ->{
                return new ResponseEntity<>("Username found",HttpStatus.BAD_REQUEST);
            }
            case "email" ->{
                return new ResponseEntity<>("matching email found",HttpStatus.BAD_REQUEST);
            }
            case "updated" ->{
                return new ResponseEntity<>("updated",HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Unknown error",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        int status = userService.deleteUser(id);
        if (status == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
