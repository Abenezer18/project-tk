package et.tk.api.venueManagement.seat;

import et.tk.api.venueManagement.hall.Hall;
import et.tk.api.venueManagement.hall.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private RestTemplate restTemplate;

    public String createSeat(String hallId, Seat seat) {
        seat.setRow(seat.getRow().toUpperCase());
        seat.setId(null);
        seat.setTicketIds(null);
        Optional<Hall> hallOptional = hallRepository.findById(hallId);
        List<Seat> nameCheck = seatRepository.findByHallId(hallId);
        CollectionUtils.filter(nameCheck, o -> ((Seat) o).getRow().equals(seat.getRow()));
        CollectionUtils.filter(nameCheck, o -> ((Seat) o).getNumber() == seat.getNumber());

        if (nameCheck.isEmpty()){
            if (hallOptional.isPresent()) {
                seat.setHallId(hallId);
                seatRepository.save(seat);
                return "created";
            } else {
                return "hall";
            }
        } else {
            return "name";
        }
    }

    public List<Seat> getAllSeats(String id) {
        List<Seat> seats = seatRepository.findByHallId(id);
//        CollectionUtils.filter(seats, o -> ((Seat) o).getHallId().equals(id));
        if (seats.isEmpty())
            return null;
        return seats;
    }

    public Seat getSeat(String id) {
        return seatRepository.findById(id).orElse(null);
    }

    public String updateSeat(String id, Seat seat) {
        seat.setRow(seat.getRow().toUpperCase());

        Optional<Seat> seatOptional = seatRepository.findById(id);
        if (seatOptional.isEmpty())
            return "not exist";

        if (!Objects.equals(seat.getHallId(), seatOptional.get().getHallId()))
            return "hall";

        Seat backup = seatOptional.get();
        seat.setId(backup.getId());
        seat.setTicketIds(backup.getTicketIds());
        seatRepository.deleteById(id);

        List<Seat> check = seatRepository.findByHallId(seat.getHallId());
        CollectionUtils.filter(check, o -> ((Seat) o).getRow().equals(seat.getRow()));
        CollectionUtils.filter(check, o -> ((Seat) o).getNumber() == seat.getNumber());

        if (check.isEmpty()) {
            seatRepository.save(seat);
            return "updated";
        } else {
            seatRepository.save(backup);
            return "name";
        }
    }

    public String deleteSeat(String id) {
        if (seatRepository.findById(id).isEmpty())
            return "not found";
        seatRepository.deleteById(id);
        return "deleted";
    }

    public String updateTicketIdList (String id, String ticketId) {

        Optional<Seat> seatOptional = seatRepository.findById(id);
        if (seatOptional.isEmpty())
            return "not found";

        // check if the ticket exists
        try {
            ResponseEntity<Ticket> ticket = restTemplate
                    .getForEntity("http://localhost:8083/api/tickets/" + ticketId, Ticket.class);
            if (ticket.getBody() == null)
                return "ticket";
        } catch (HttpClientErrorException e) {
            return "ticket";
        }

        Seat seat = seatOptional.get();
        List<String> ticketIds = seat.getTicketIds();
        for (String ticketId1 : ticketIds) {
            if (Objects.equals(ticketId1, ticketId))
                return "seat";
        }
        ticketIds.add(ticketId);
        seat.setTicketIds(ticketIds);
        seatRepository.save(seat);
        return "updated";
    }
}
