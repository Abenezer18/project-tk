package et.tk.api.venueManagement.seat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;

    public SeatDto getSeat(String id) {
        Optional<Seat> seatOptional = seatRepository.findById(id);
        if (seatOptional.isEmpty())
            return null;
        Seat seat = seatOptional.get();
        return new SeatDto(seat);
    }

    public String updateSeat(String id, SeatDto seatDto) {
        seatDto.setRow(seatDto.getRow().toUpperCase());
        Optional<Seat> seatOptional = seatRepository.findById(id);
        if (seatOptional.isEmpty())
            return "not exist";
        Seat seat = seatOptional.get();
        Seat backUp = seat;
        seatRepository.deleteById(id);

        List<Seat> check = seatRepository.findByHallId(seat.getHallId());
        CollectionUtils.filter(check, o -> ((Seat) o).getRow().equals(seatDto.getRow()));
        CollectionUtils.filter(check, o -> ((Seat) o).getNumber() == seatDto.getNumber());

        if (check.isEmpty()) {
            seat.setRow(seatDto.getRow());
            seat.setNumber(seatDto.getNumber());
            seatRepository.save(seat);
            return "updated";
        } else {
            seatRepository.save(backUp);
            return "name";
        }
    }

    public String deleteSeat(String id) {
        Optional<Seat> seatOptional = seatRepository.findById(id);
        if (seatOptional.isEmpty())
            return "not found";
        seatRepository.deleteById(id);
        return "deleted";
    }
}
