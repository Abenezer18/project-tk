package et.tk.api.userManagement.venueAdmin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

public class VenueInfo {

    private String id;
    private String name;
    private String address;
    private String email;
}
