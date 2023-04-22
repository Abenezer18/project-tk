package et.tk.api.ticket;

import et.tk.api.ticket.Dto.TicketPost;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("tickets")
@Builder
public class Ticket {

    @Id
    private String id;
    private String userId;
    private String scheduleId;
    private List<String> seatIds;
    private String ticketPrice;
    private boolean ticketStatus;
    private String dateOfPublish;

    // POST
    public Ticket (TicketPost ticketPost){
        this.userId = ticketPost.getUserId();
        this.scheduleId = ticketPost.getScheduleId();
        this.ticketPrice = ticketPost.getTicketPrice();
        this.dateOfPublish = LocalDateTime.now().toString();
        this.ticketStatus = true;
    }
}
