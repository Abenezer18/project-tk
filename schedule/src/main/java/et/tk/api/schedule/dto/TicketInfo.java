package et.tk.api.schedule.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketInfo {
    private String id;
    private String userId;
    private String scheduleId;
    private List<String> seatIds;
    private String ticketPrice;
    private boolean ticketStatus;
    private String dateOfPublish;
}
