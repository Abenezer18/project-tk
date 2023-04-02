package et.tk.api.ticket.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatInfo {
    private String id;
    private String Row;
    private Integer number;
    private Integer Number;
    private boolean seatStatus;
}
