package et.tk.api.userManagement.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository <User, String> {
    public Optional<User> findByName(String name);
    public Optional<User> findByEmail(String email);
    public Optional<User> findByPhoneNumber(String phoneNumber);

}
