package et.tk.api.venueManagement.seat;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private String id;
    private String userId;
    private String scheduleId;
    private List<String> seatIds;
    private String ticketPrice;
    private boolean ticketStatus;
    private String dateOfPublish;
}
