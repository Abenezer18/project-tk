package et.tk.api.venueManagement.venue;

import et.tk.api.venueManagement.hall.Hall;
import et.tk.api.venueManagement.hall.HallDetailedResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VenueDetailedResponse {

    private String id;

    private String name;

    private String address;

    private String email;

    private List<HallDetailedResponse> hallDetailedResponse;

    public VenueDetailedResponse(Venue venue) {
        this.id = venue.getId();
        this.name = venue.getName();
        this.address = venue.getAddress();
        this.email = venue.getEmail();
    }
}