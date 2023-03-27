package et.tk.api.schedule.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdate {

    private String movieId;
    private String hallId;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;
    private String startTime;
    private String endTime;
}