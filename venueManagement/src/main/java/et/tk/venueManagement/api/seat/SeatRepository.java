package et.tk.venueManagement.api.seat;

import et.tk.venueManagement.api.venue.Venue;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends MongoRepository <Seat, String> {
    public List<Seat> findByHallId(String hallId);
}