package et.tk.api.venueManagement.seat;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {
    enum movieType{
        local,
        international
    }
    private String id;
    private String movieId;
    private movieType movieType; // local or international
    private String hallId;
    private String date;
    private String startTime;
    private String endTime;
    private String dateOfPublish;
    private String lastUpdated;
}
