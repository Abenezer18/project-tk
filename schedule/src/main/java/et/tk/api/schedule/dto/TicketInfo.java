package et.tk.api.schedule.dto;

import org.springframework.data.annotation.Id;

import java.util.List;

public class TicketInfo {
    private String id;
    private String userId;
    private String scheduleId;
    private List<String> seatIds;
    private String ticketPrice;
    private boolean ticketStatus;
    private String dateOfPublish;
}
