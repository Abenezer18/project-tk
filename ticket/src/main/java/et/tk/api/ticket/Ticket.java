package et.tk.api.ticket;

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
    private double ticketPrice;
    private boolean ticketStatus;
    private String dateOfPublish;
    private String paymentToken;
}
