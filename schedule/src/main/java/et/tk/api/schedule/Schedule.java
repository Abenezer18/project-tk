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
    enum movieType{
        local,
        international
    }

    @Id
    private String id;
    private String movieId;
    private movieType movieType; // local or international
    private String hallId;
    private String date;
    private String startTime;
    private String endTime;
    private String dateOfPublish;
    private String lastUpdated;

    public Schedule (SchedulePost schedulePost){
        this.movieId = schedulePost.getMovieId();
        this.hallId = schedulePost.getHallId();
        this.date = schedulePost.getDate();
        this.startTime = schedulePost.getStartTime();
        this.endTime = schedulePost.getEndTime();
        this.dateOfPublish = LocalDateTime.now().toString();
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