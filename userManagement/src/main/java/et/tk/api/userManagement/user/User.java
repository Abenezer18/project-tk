package et.tk.api.userManagement.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<String> ticketIdList;
//    private List<Ticket> ticketList;     import ticket package later

    public User (User user){
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setPhoneNumber(user.getPhoneNumber());
    }

    // adding ticket to list
    public User (String ticketId){
        this.ticketIdList.add(ticketId);
    }
}