package et.tk.api.venueManagement.seat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "seats")
public class Seat {

    @Id
    private String id;

    private String row;

    private int number;

    @DBRef
    private String hallId;

    public Seat(String row, int number, String hallId) {
        this.row = row;
        this.number = number;
        this.hallId = hallId;
    }
    public Seat(SeatDto seatDto, String hallId){
        this.setRow(seatDto.getRow());
        this.setNumber(seatDto.getNumber());
        this.setHallId(hallId);
    }
}
