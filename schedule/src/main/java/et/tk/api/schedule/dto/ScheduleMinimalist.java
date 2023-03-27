package et.tk.api.schedule.dto;

import et.tk.api.schedule.Schedule;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleMinimalist {

    private String id;
    private String movieId;
    private String hallId;

    // for Get all Schedules
    public ScheduleMinimalist (Schedule schedule){
        this.id = schedule.getId();
        this.movieId = schedule.getMovieId();
        this.hallId =  schedule.getHallId();
    }
}