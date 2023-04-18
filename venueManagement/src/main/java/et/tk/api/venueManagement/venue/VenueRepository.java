package et.tk.api.venueManagement.venue;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VenueRepository extends MongoRepository <Venue, String> {
    public Optional<Venue> findByName(String name);
    public Optional<Venue> findByEmail(String email);
    public Optional<Venue> deleteByClientId(String clientId);
}