package et.tk.api.movie.emdb;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import et.tk.api.movie.emdb.dto.MovieMinimalistView;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    public MovieService() {
    }

    //POST new movie to DB
    public String addMovie(Movie movie){
        Optional<Movie> optionalMovie = movieRepository.findByTitle(movie.getTitle());
        if (optionalMovie.isEmpty()) {
            movie.setId(null);
            movieRepository.save(movie);
            return "added";
        }
        else {
            return "name";
        }
    }

    // GET all movies form DB
    public List<MovieMinimalistView> getAllMovies(){
        return movieRepository.findAll().stream().map(MovieMinimalistView::new).toList();
    }

    // GET  movie by id form DB
    public Movie getMovieById(String id){
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.orElse(null);
    }

    // search by partial title
    public List<MovieMinimalistView> search(String partialString){
        Query query = new Query();
        query.addCriteria(Criteria.where("title").regex(".*" + partialString + ".*"));
        List<Movie> result = mongoTemplate.find(query, Movie.class);
        return result.stream().map(MovieMinimalistView::new).toList();
    }

    //UPDATE movie using id
    public String updateMovie(String id, Movie movie){
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isEmpty())
            return "not found";
        movie.setId(optionalMovie.get().getId()); // re entering the movie id
        Movie backUp = optionalMovie.get(); // saving a backup
        movieRepository.deleteById(id);
        Optional<Movie> nameCheck = movieRepository.findByTitle(movie.getTitle());
        if(nameCheck.isEmpty()){
            movieRepository.save(movie); // updated
            return "updated";
        }
        else {
            movieRepository.save(backUp); // saving old movie
            return "name";
        }
    }

    //DELETE movie by id
    public String deleteMovie(String id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if(optionalMovie.isPresent()){
            movieRepository.deleteById(id);
            return "deleted";
        }else {
            return "not found";
        }
    }

    //PATCH not working
//    public Movie patchMovie(String id, JsonPatch jsonPatch) throws JsonPatchException, JsonProcessingException {
//        Optional<Movie> movie = movieRepository.findById(id);
//        JsonNode patched = jsonPatch.apply(objectMapper.convertValue(movie, JsonNode.class));
//
//        return movieRepository.save(objectMapper.treeToValue(patched, Movie.class));
//    }
}


