package et.tk.venueManagement.api.hall;

import et.tk.venueManagement.api.venue.VenueRepository;
import et.tk.venueManagement.api.seat.Seat;
import et.tk.venueManagement.api.seat.SeatDto;
import et.tk.venueManagement.api.seat.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HallService {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private VenueRepository venueRepository;

    public HallDto getHall(String hallId) {
        Optional<Hall> hallOptional = hallRepository.findById(hallId);

        if (hallOptional.isPresent())
            return new HallDto(hallOptional.get());
        else
            return null;
    }

    public String updateHall(String hallId, HallDto hallDto) {
        Optional<Hall> hallOptional = hallRepository.findById(hallId);
        if (hallOptional.isEmpty())
            return "hall";

        Hall hall = hallOptional.get();

        hallRepository.deleteById(hallId);

        List<Hall> nameCheck = hallRepository.findByVenueId(hall.getVenueId()); // checking name
        CollectionUtils.filter(nameCheck, o -> ((Hall) o).getName().equals(hallDto.getName()));

        if (nameCheck.isEmpty()) {
            Hall updatedHall = new Hall(hallDto);
            updatedHall.setId(hall.getId());
            updatedHall.setVenueId(hall.getVenueId());
            hallRepository.save(updatedHall);
            return "updated";
        } else {
            hallRepository.save(hall);
            return "name";
        }
    }
    public String deleteHall(String hallId) {
        Optional<Hall> hallOptional = hallRepository.findById(hallId);

        if (hallOptional.isPresent()) {
            hallRepository.deleteById(hallId);
            return "deleted";
        } else {
            return "not found";
        }
    }

    public String createSeat(String hallId, SeatDto seatDto) {
        seatDto.setRow(seatDto.getRow().toUpperCase());
        Optional<Hall> hallOptional = hallRepository.findById(hallId);
        List<Seat> nameCheck = seatRepository.findByHallId(hallId);
        CollectionUtils.filter(nameCheck, o -> ((Seat) o).getRow().equals(seatDto.getRow()));
        CollectionUtils.filter(nameCheck, o -> ((Seat) o).getNumber() == seatDto.getNumber());

        if (nameCheck.isEmpty()){
            if (hallOptional.isPresent()) {
                Hall hall = hallOptional.get(); // use this later
                Seat seat = new Seat(seatDto, hallId);
                seatRepository.save(seat);
                return "created";
            } else {
                return "hall";
            }
        } else {
            return "name";
        }
    }

    public List<SeatDto> getAllSeats(String id) {
        List<Seat> seats = seatRepository.findByHallId(id);
        if (seats.isEmpty())
            return null;
        return seats.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    private SeatDto convertToDto(Seat seat) {
        SeatDto seatDto = new SeatDto();
        seatDto.setId(seat.getId());
        seatDto.setRow(seat.getRow());
        seatDto.setNumber(seat.getNumber());
        seatDto.setHallId(seat.getHallId());
        return seatDto;
    }
}

