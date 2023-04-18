package et.tk.api.movie.emdb;

import java.util.List;
import java.util.Objects;

import et.tk.api.movie.emdb.dto.MovieMinimalistView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

//    @GetMapping("**")
//    public ResponseEntity<String> pathError (){
//        return new ResponseEntity<>("path invalid", HttpStatus.BAD_GATEWAY);
//    }

    //POST new movie
    @PostMapping("/movies")
    public ResponseEntity<String> addMovie(@RequestBody Movie movie){
        if (movieService.addMovie(movie).equals("name"))
            return new ResponseEntity<>("Title already exists", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>("Movie added", HttpStatus.OK);
    }

    //GET all movies
    @GetMapping("/movies")
    public ResponseEntity<List<MovieMinimalistView>> getAllMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK) ;
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) {
        Movie result = movieService.getMovieById(id);
        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK) ;
    }

    //GET movie by title using search repository
    @GetMapping("/movies/search/{title}")
    public ResponseEntity<List<MovieMinimalistView>> search(@PathVariable String title){

        List<MovieMinimalistView> searchResult = movieService.search(title);
        if (searchResult.isEmpty())
            return new ResponseEntity<>(searchResult,HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    //UPDATE movie by id
    @PutMapping("/movies/{id}")
    public ResponseEntity<String> updateMovie(@RequestBody Movie movie, @PathVariable String id){
        String status = movieService.updateMovie(id, movie);

        if (Objects.equals(status, "not found"))
            return new ResponseEntity<>("Movie not found!", HttpStatus.BAD_REQUEST);
        else if (Objects.equals(status, "name"))
            return new ResponseEntity<>("Title already exists", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable String id){
        String status = movieService.deleteMovie(id);
        if (status.equals("not found"))
            return new ResponseEntity<>("Movie not found!", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>("Movie deleted!", HttpStatus.OK);
    }

    //    PATCH movie by id (not working) , add ResponseEntity later
//    @RequestMapping("/movies/{id}")
//    public void patchMovie(@PathVariable String id,@RequestBody JsonPatch jsonPatch) throws JsonPatchException, JsonProcessingException {
//        movie.patchMovie(id, jsonPatch);
//    }
    //delete movie by id
}
