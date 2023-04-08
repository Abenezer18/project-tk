package et.tk.api.userManagement.systemAdmin;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SystemAdminRepository extends MongoRepository <SystemAdmin, String> {
    public Optional<SystemAdmin> findByName(String name);
}
