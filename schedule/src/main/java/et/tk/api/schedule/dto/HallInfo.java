package et.tk.api.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallInfo {
    private String id;
    private String name;
    private String venueId;
}
