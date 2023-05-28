package et.tk.api.ticket.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketPost {
    private String userId;
    private String scheduleId;
    private double ticketPrice;
}
