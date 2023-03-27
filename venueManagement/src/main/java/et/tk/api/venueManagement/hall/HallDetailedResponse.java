package et.tk.api.venueManagement.hall;

import et.tk.api.venueManagement.seat.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HallDetailedResponse {

    private String id;
    private String name;
    private String venueId;
    private List<Seat> seats;

    public HallDetailedResponse(Hall hall) {
        this.id = hall.getId();
        this.name = hall.getName();
        this.venueId = hall.getVenueId();
    }
}