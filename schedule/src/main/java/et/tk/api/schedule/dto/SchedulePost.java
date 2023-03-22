package et.tk.api.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchedulePost {

    private String movieId;
    private String hallId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String startTime;
    private String endTime;
}