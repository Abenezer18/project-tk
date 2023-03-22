package et.tk.api.schedule.dto;

import et.tk.api.schedule.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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