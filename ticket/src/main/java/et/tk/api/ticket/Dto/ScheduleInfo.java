package et.tk.api.ticket.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleInfo {
    private String id;
    private String movieId;
    private String hallId;
    private String date;
    private String startTime;
    private String endTime;
}
