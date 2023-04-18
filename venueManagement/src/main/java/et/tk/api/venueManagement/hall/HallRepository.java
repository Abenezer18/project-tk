package et.tk.api.venueManagement.hall;

import et.tk.api.venueManagement.client.Client;
import et.tk.api.venueManagement.seat.Seat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface HallRepository extends MongoRepository <Hall, String>{

    public List<Hall> findByVenueId(String venueId);
    public Optional<Hall> findByName(String name);
    public Optional<Client> deleteByVenueId(String id);
}
