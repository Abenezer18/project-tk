package et.tk.api.venueManagement.seat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDto {
    private String id;
    private String row;
    private int number;
    private String hallId;
    private boolean seatStatus;


    public SeatDto(Seat seat) {
        this.id = seat.getId();
        this.row = seat.getRow();
        this.number = seat.getNumber();
        this.hallId = seat.getHallId();
        this.seatStatus = seat.isSeatStatus();
    }
}