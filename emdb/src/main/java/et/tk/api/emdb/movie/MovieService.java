package et.tk.api.emdb.movie;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import et.tk.api.emdb.movie.dto.MovieMinimalistView;
import et.tk.api.emdb.movie.dto.MoviePostAndUpdate;
import et.tk.api.emdb.movie.repositories.MovieRepository;
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
    public String addMovie(MoviePostAndUpdate moviePostAndUpdate) {
        Optional<Movie> optionalMovie = movieRepository.findByTitle(moviePostAndUpdate.getTitle());
        Movie movie = new Movie(moviePostAndUpdate);

        if (optionalMovie.isEmpty()) {
            movieRepository.save(movie);
            return "added";
        }
        else {
            return "name";
        }
    }

    // GET all movies form DB
    public List<MovieMinimalistView> getAllMovies(){

        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty())
            return null;
        return movies.stream().map(MovieMinimalistView::new).toList();
    }

    // search by partial title
    public List<MovieMinimalistView> search(String partialString) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").regex(".*" + partialString + ".*"));
        List<Movie> result = mongoTemplate.find(query, Movie.class);
        List<MovieMinimalistView> minimal = result.stream().map(MovieMinimalistView::new).toList();
        return minimal;
    }

    //UPDATE movie using id
    public String updateMovie(String id, MoviePostAndUpdate moviePostAndUpdate){
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isEmpty())
            return "not found";
        Movie movie = new Movie(moviePostAndUpdate);
        Movie backUp = new Movie(new MoviePostAndUpdate(optionalMovie.get())); // saving a backup
        backUp.setId(id); // setting id
        movieRepository.deleteById(id);
        Optional<Movie> nameCheck = movieRepository.findByTitle(moviePostAndUpdate.getTitle());
        if(nameCheck.isEmpty()){
            movieRepository.save(movie);
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


