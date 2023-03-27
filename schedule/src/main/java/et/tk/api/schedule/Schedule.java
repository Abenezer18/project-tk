package et.tk.api.schedule;

import et.tk.api.schedule.dto.SchedulePost;
import et.tk.api.schedule.dto.ScheduleUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.lang.String;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("schedule")
@Builder
public class Schedule {

    @Id
    private String id;
    private String movieId;
    private String hallId;
    private String date;
    private String startTime;
    private String endTime;
    private String dateOfPublish;
    private String lastUpdated;
    private String all; // .find all is not working

    public Schedule (SchedulePost schedulePost){
        this.movieId = schedulePost.getMovieId();
        this.hallId = schedulePost.getHallId();
        this.date = schedulePost.getDate();
        this.startTime = schedulePost.getStartTime();
        this.endTime = schedulePost.getEndTime();
        this.dateOfPublish = LocalDateTime.now().toString();
        this.all = "0";
    }
    public Schedule (ScheduleUpdate scheduleUpdate){
        this.movieId = scheduleUpdate.getMovieId();
        this.hallId = scheduleUpdate.getHallId();
        this.date = scheduleUpdate.getDate();
        this.startTime = scheduleUpdate.getStartTime();
        this.endTime = scheduleUpdate.getEndTime();
        this.lastUpdated = LocalDateTime.now().toString();
    }
}
