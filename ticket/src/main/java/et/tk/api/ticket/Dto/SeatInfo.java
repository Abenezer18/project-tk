package et.tk.api.ticket.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatInfo {
    private String id;
    private String Row;
    private Integer number;
    private Integer Number;
    private List<String> scheduleIds;
}
