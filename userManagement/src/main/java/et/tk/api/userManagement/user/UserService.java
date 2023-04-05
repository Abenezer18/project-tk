package et.tk.api.userManagement.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String createUser(User user){
        user.setName(user.getName().toLowerCase());
        user.setEmail(user.getEmail().toLowerCase());

        if (userRepository.findByName(user.getName()).isPresent())
            return "name";
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            return "email";
        if (user.getPhoneNumber().length() != 9) // must start from 9...
            return "phone";
        List<String> sample = new ArrayList<>();
        sample.add("tickets");
        user.setTicketIdList(sample);
        userRepository.save(new User(user));
        return "added";
    }

    public List<User> getUsers(){
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

        Optional<User> checker;
        checker = userRepository.findByName(user.getName());
        if (checker.isPresent()) {
            userRepository.save(userTempo);
            return "name";
        }
        checker = userRepository.findByEmail(user.getEmail());
        if (checker.isPresent()) {
            userRepository.save(userTempo);
            return "email";
        }
        checker= userRepository.findByPhoneNumber(user.getPhoneNumber());
        if (checker.isPresent()) {
            userRepository.save(userTempo);
            return "phone";
        }
        userRepository.save(user); // add dto removing the ticket list
        return "updated";
    }

    public String updateTicketList(String id, String ticketId) {
        User userTempo = this.getUserById(id);
        if (userTempo == null)
            return "user";
        userRepository.deleteById(id);

        List<String> tickets = new ArrayList<>(userTempo.getTicketIdList());
        tickets.add(ticketId);
        userTempo.setTicketIdList(tickets);
        userRepository.save(userTempo);
        return "updated";
    }

    public int deleteUser(String id) {
        if (this.getUserById(id) == null)
            return 0;
        userRepository.deleteById(id);
        return 1;
    }
}
