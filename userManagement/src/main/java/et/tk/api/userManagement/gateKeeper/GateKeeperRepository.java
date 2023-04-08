package et.tk.api.userManagement.gateKeeper;

import et.tk.api.userManagement.systemAdmin.SystemAdmin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GateKeeperRepository extends MongoRepository<GateKeeper, String> {
    public Optional<GateKeeper> findByName(String name);
    public List<GateKeeper> findByVenueId(String venueId);
}
