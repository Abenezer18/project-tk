package et.tk.api.userManagement.venueAdmin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "venue_admin")
public class VenueAdmin {
    @Id
    private String id;
    private String name;
    private String venueId;
}