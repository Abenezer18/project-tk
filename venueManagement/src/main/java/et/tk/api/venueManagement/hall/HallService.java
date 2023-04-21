package et.tk.api.venueManagement.hall;

import et.tk.api.venueManagement.seat.Seat;
import et.tk.api.venueManagement.seat.SeatRepository;
import et.tk.api.venueManagement.venue.VenueRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public String createHall(String venueId, Hall hall) {
        hall.setId(null);
        hall.setName(hall.getName().toLowerCase());
        hall.setVenueId(venueId);
        if (venueRepository.findById(venueId).isEmpty())
            return "venue";

        List<Hall> nameCheck = hallRepository.findAll();
        CollectionUtils.filter(nameCheck, o -> ((Hall) o).getVenueId().equals(venueId));
        CollectionUtils.filter(nameCheck, o -> ((Hall) o).getName().equals(hall.getName()));

        if (nameCheck.isEmpty()) {
            hallRepository.save(hall);
            return "created";
        } else
            return "name";
    }

    public List<Hall> getHallsByVenueId(String id) {
        List<Hall> halls = hallRepository.findAll();
        CollectionUtils.filter(halls, o -> ((Hall) o).getVenueId().equals(id));
        if (halls.isEmpty())
            return null;
        return halls;
    }

    public Hall getHallById(String id) {
        return hallRepository.findById(id).orElse(null);
    }

    public String updateHall(String hallId, Hall hall) {
        Optional<Hall> hallOptional = hallRepository.findById(hallId);
        if (hallOptional.isEmpty())
            return "hall";

        if (!Objects.equals(hall.getVenueId(), hallOptional.get().getVenueId()))
            return "venue id";

        Hall backup = hallOptional.get();
        hall.setId(backup.getId());

        hallRepository.deleteById(hallId);

        List<Hall> nameCheck = hallRepository.findByVenueId(backup.getVenueId());
        CollectionUtils.filter(nameCheck, o -> ((Hall) o).getName().equals(hall.getName()));

        if (nameCheck.isEmpty()) {
            hall.setId(backup.getId());
            hallRepository.save(hall);
            return "updated";
        } else {
            hallRepository.save(backup);
            return "name";
        }
    }

    public String deleteHall(String id) {
        Optional<Hall> hallOptional = hallRepository.findById(id);
        if (hallOptional.isEmpty())
            return "hall";

        List<Seat> seats = seatRepository.findByHallId(id);
        for (Seat seat:seats) {
            seatRepository.deleteById(seat.getId());
        }
        hallRepository.deleteById(id);

        return "deleted";
    }
}

