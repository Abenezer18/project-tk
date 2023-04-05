package et.tk.api.userManagement.venueAdmin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VenueInfo {
    private String id;
    private String name;
    private String address;
    private String email;
}
