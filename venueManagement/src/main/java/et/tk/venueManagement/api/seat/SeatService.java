package et.tk.venueManagement.api.seat;

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

    public SeatDto updateSeat(String id, SeatDto seatDto) {
        seatDto.setRow(seatDto.getRow().toUpperCase());
        Optional<Seat> seatOptional = seatRepository.findById(id);
        if (seatOptional.isEmpty())
            return null;
        Seat seat = seatOptional.get();

        seatRepository.deleteById(id);

        List<Seat> check = seatRepository.findByHallId(seat.getHallId());
        CollectionUtils.filter(check, o -> ((Seat) o).getRow().equals(seatDto.getRow()));
        CollectionUtils.filter(check, o -> ((Seat) o).getNumber() == seatDto.getNumber());

        if (check.isEmpty()) {
            seat.setRow(seatDto.getRow());
            seat.setNumber(seatDto.getNumber());
            seat = seatRepository.save(seat);
            return new SeatDto(seat);
        } else {
            return null;
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
