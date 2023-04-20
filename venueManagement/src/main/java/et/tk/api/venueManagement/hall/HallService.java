package et.tk.api.venueManagement.hall;

import et.tk.api.venueManagement.seat.SeatRepository;
import et.tk.api.venueManagement.venue.VenueRepository;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HallService {

    private SeatRepository seatRepository;
    private HallRepository hallRepository;
    private VenueRepository venueRepository;

    public HallDto getHall(String hallId) {
        Optional<Hall> hallOptional = hallRepository.findById(hallId);

        return hallOptional.map(HallDto::new).orElse(null);
    }

    public String updateHall(String hallId, HallDto hallDto) {
        Optional<Hall> hallOptional = hallRepository.findById(hallId);
        if (hallOptional.isEmpty())
            return "hall";

        Hall hall = hallOptional.get();

        hallRepository.deleteById(hallId);

        List<Hall> nameCheck = hallRepository.findByVenueId(hall.getVenueId());
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
}

