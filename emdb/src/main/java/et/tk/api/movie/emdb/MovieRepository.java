package et.tk.api.movie.emdb;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface MovieRepository extends MongoRepository <Movie, String>{
    public Optional<Movie> findByTitle(String title);
}
