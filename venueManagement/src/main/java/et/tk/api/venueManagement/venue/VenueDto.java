package et.tk.api.venueManagement.venue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VenueDto {

    private String id;

    private String name;

    private String address;

    private String email;

    public VenueDto(Venue venue) {
        this.id = venue.getId();
        this.name = venue.getName();
        this.address = venue.getAddress();
        this.email = venue.getEmail();
    }
}
