package et.tk.api.schedule.dto;

import et.tk.api.schedule.Schedule;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleGet {

    private String id;
    private String movieId;
    private String hallId;
    private String date;
    private String startTime;
    private String endTime;
    private VenueInfo venueInfo;
    private HallInfo hallInfo;
    private MovieInfo movieInfo;


    public ScheduleGet(Schedule schedule, VenueInfo venueInfo, HallInfo hallInfo) {
        this.id = schedule.getId();
        this.movieId = schedule.getMovieId();
        this.hallId = schedule.getHallId();
        this.date = schedule.getDate();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.venueInfo = venueInfo;
        this.hallInfo = hallInfo;
    }

    // for get by id
    public ScheduleGet(Schedule schedule, VenueInfo venueInfo, HallInfo hallInfo, MovieInfo movieInfo) {
        this.id = schedule.getId();
        this.movieId = schedule.getMovieId();
        this.hallId = schedule.getHallId();
        this.date = schedule.getDate();
        this.startTime = schedule.getStartTime();
        this.endTime = schedule.getEndTime();
        this.venueInfo = venueInfo;
        this.hallInfo = hallInfo;
        this.movieInfo = movieInfo;
    }

    public ScheduleGet(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
    }
}