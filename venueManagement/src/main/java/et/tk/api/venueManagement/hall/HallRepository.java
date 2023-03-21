package et.tk.api.venueManagement.hall;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface HallRepository extends MongoRepository <Hall, String>{

    public List<Hall> findByVenueId(String venueId);
    public Optional<Hall> findByName(String name);

}
