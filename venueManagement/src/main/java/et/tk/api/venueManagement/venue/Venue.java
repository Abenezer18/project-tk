package et.tk.api.venueManagement.venue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "venues")
public class Venue {
    @Id
    private String id;
    private String name;
    private String address;
    private String email;
    private int price;
    private String clientId;
//    private String venueAdminId;
}
