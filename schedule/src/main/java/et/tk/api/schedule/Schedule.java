package et.tk.api.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}