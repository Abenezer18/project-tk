package et.tk.api.venueManagement.hall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HallDto {
    private String id;
    private String name;
    //private int capacity;
    private String venueId;

    // for GET
    public HallDto(Hall hall) {
        this.id = hall.getId();
        this.name = hall.getName();
        this.venueId = hall.getVenueId();
    }

    // for PUT and POST
    public HallDto(Hall hall, String venueId) {
        this.id = hall.getId();
        this.name = hall.getName();
        this.venueId = venueId;
    }
}
