package et.tk.api.ticket.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketPost {
    private String userId;
    private String scheduleId;
    private List<String> seatIds;
    private String ticketPrice;
}
