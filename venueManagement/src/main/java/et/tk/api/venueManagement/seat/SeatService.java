package et.tk.api.venueManagement.seat;

import et.tk.api.venueManagement.hall.Hall;
import et.tk.api.venueManagement.hall.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
        seat.setScheduleIds(null);
        Optional<Hall> hallOptional = hallRepository.findById(hallId);
        List<Seat> nameCheck = seatRepository.findByHallId(hallId);
        CollectionUtils.filter(nameCheck, o -> ((Seat) o).getRow().equals(seat.getRow()));
        CollectionUtils.filter(nameCheck, o -> ((Seat) o).getNumber() == seat.getNumber());


        OptionalDouble seatPriceOptional = OptionalDouble.of(seat.getPrice()); // checks if seat price is null

        // check if the seat price is empty, if it is set the hall price (base price)
        if (seatPriceOptional.getAsDouble() <= 0) {
            
            // check if the hall is present
            if (hallOptional.isPresent()){

                Double hallPrice = hallOptional.get().getPrice(); // gets the price of the hall
                
                // check if the hall price is empty. if it is not, then set the seat price equal to the hall price
                if (hallPrice <= 0){
                    return "hall price";
                } else {
                    seat.setPrice(hallPrice);
                }
            } else {
                return "hall";
            }
        }
    
        if (nameCheck.isEmpty()){
            if (hallOptional.isPresent()) {
                seat.setHallId(hallId);

                // making schedule id list not null by adding 1 data
                List<String> bla = new ArrayList<String>();
                bla.add("scheduleIds");
                seat.setScheduleIds(bla);
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
        seat.setScheduleIds(backup.getScheduleIds());
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

    public String updateScheduleIdList (String id, String scheduleId) {

        Optional<Seat> seatOptional = seatRepository.findById(id);
        if (seatOptional.isEmpty())
            return "not found";

        // check if the ticket exists
        try {
            ResponseEntity<ScheduleDto> schedule = restTemplate
                    .getForEntity("http://localhost:8082/api/schedules/" + scheduleId, ScheduleDto.class);
            if (schedule.getBody() == null)
                return "schedule";
        } catch (HttpClientErrorException e) {
            return "schedule service";
        }

        Seat seat = seatOptional.get();
        List<String> scheduleIds = seat.getScheduleIds();
        for (String ticketId1 : scheduleIds) {
            if (Objects.equals(ticketId1, scheduleId))
                return "seat";
        }

        scheduleIds.add(scheduleId);
        seat.setScheduleIds(scheduleIds);
        seatRepository.save(seat);
        return "updated";
    }
}
