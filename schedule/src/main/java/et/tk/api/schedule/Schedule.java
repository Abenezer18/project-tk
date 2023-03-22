package et.tk.api.schedule;

import et.tk.api.schedule.dto.SchedulePost;
import et.tk.api.schedule.dto.ScheduleUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "schedules")
public class Schedule {

    @Id
    private String id;
    @DBRef
    private String movieId;
    @DBRef
    private String hallId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String startTime;
    private String endTime;
    private LocalDateTime dateOfPublish;
    private LocalDateTime lastUpdated;

    Schedule (SchedulePost schedulePost){
        this.movieId = schedulePost.getMovieId();
        this.hallId = schedulePost.getHallId();
        this.date = schedulePost.getDate();
        this.startTime = schedulePost.getStartTime();
        this.endTime = schedulePost.getEndTime();
        this.dateOfPublish = LocalDateTime.now();
    }
    Schedule (ScheduleUpdate scheduleUpdate){
        this.movieId = scheduleUpdate.getMovieId();
        this.hallId = scheduleUpdate.getHallId();
        this.date = scheduleUpdate.getDate();
        this.startTime = scheduleUpdate.getStartTime();
        this.endTime = scheduleUpdate.getEndTime();
        this.lastUpdated = LocalDateTime.now();
    }
}
