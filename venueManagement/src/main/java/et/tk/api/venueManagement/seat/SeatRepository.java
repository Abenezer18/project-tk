package et.tk.api.venueManagement.seat;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SeatRepository extends MongoRepository <Seat, String> {
    public List<Seat> findByHallId(String hallId);
    public void deleteByHallId(String hallId);
}