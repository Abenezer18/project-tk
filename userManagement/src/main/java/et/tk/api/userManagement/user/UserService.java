package et.tk.api.userManagement.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String createNewUser(User user){
        user.setName(user.getName().toLowerCase());
        user.setEmail(user.getEmail().toLowerCase());

        if (user.getPhoneNumber().length() != 9) // must start from 9...
            return "phone";
        if (userRepository.findByName(user.getName()).isPresent())
            return "name";
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            return "email";

        userRepository.save(new User(user));
        return "added";
    }

    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public User getUserById(String id){
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public User getUserByPhone(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    public String updateUser(String id, User user) {
        User userTempo = this.getUserById(id);
        if (userTempo == null)
            return "user";
        if (user.getPhoneNumber().length() != 9) // must start from 9...
            return "phone";
        userRepository.deleteById(id);

        Optional<User> nameCheck = userRepository.findByName(user.getName());
        if (nameCheck.isPresent()) {
            userRepository.save(userTempo);
            return "name";
        }
        Optional<User> emailCheck = userRepository.findByEmail(user.getEmail());
        if (emailCheck.isPresent()) {
            userRepository.save(userTempo);
            return "email";
        }
        Optional<User> phoneCheck = userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (phoneCheck.isPresent()) {
            userRepository.save(userTempo);
            return "phone";
        }
        userRepository.save(user);
        return "updated";
    }

    public String deleteUser(String id) {
        if (this.getUserById(id) == null)
            return "not_found";
        userRepository.deleteById(id);
        return "deleted";
    }
}
