package et.tk.api.venueManagement.client;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClientRepository extends MongoRepository <Client, String> {
    public Optional<Client> findByName(String name);
    public Optional<Client> findByEmail(String email);
}
