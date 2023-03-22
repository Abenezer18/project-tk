package et.tk.api.emdb.movie.repositories;

import java.util.List;
import java.util.Optional;

import et.tk.api.emdb.movie.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface MovieRepository extends MongoRepository <Movie, String>{
    public Optional<Movie> findByTitle(String title);
}
