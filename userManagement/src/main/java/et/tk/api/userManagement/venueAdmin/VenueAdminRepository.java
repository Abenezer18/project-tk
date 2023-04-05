package et.tk.api.userManagement.venueAdmin;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VenueAdminRepository extends MongoRepository<VenueAdmin, String> {
    public Optional<VenueAdmin> findByName(String name);
    public Optional<VenueAdmin> findByVenueId(String venueId);
}