package et.tk.api.userManagement.clientAdmin;

// import et.tk.api.userManagement.venueAdmin.VenueAdmin;
// import org.springframework.beans.propertyeditors.ClassEditor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClientAdminRepository extends MongoRepository<ClientAdmin, String> {

    public Optional<ClientAdmin> findByName(String name);
    public Optional<ClientAdmin> findByClientId(String clientId);

}
