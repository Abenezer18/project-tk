package et.tk.api.schedule.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchedulePost {

    private String movieId;
    private String hallId;
    private String date;
    private String startTime;
    private String endTime;
}