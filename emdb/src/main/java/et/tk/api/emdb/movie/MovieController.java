package et.tk.api.emdb.movie;

import java.util.List;

import et.tk.api.emdb.movie.dto.MovieDetailedResponse;
import et.tk.api.emdb.movie.dto.MovieMinimalistView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import et.tk.api.emdb.movie.dto.MoviePostAndUpdate;

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
    public ResponseEntity<String> addMovie(@RequestBody MoviePostAndUpdate m){
        String status =  movieService.addMovie(m);

        if (status=="name")
            return new ResponseEntity<>("Title already exists", HttpStatus.FOUND);
        else
            return new ResponseEntity<>("Movie added", HttpStatus.OK);
    }

    //GET all movies
    @GetMapping("/movies")
    public ResponseEntity<List<MovieMinimalistView>> getAllMovies() {
        List<MovieMinimalistView> result = movieService.getAllMovies();

        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK) ;
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDetailedResponse> getMovieById(@PathVariable String id) {
        MovieDetailedResponse result = movieService.getMovieById(id);

        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK) ;
    }

    //GET movie by title using search repository
    @GetMapping("/movies/title/{title}")
    public ResponseEntity<List<MovieMinimalistView>> search(@PathVariable String title){

        List<MovieMinimalistView> searchResult = movieService.search(title);
        if (searchResult.isEmpty())
            return new ResponseEntity<>(searchResult,HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    //UPDATE movie by id
    @PutMapping("/movies/{id}")
    public ResponseEntity<String> updateMovie(@RequestBody MoviePostAndUpdate mov, @PathVariable String id){
        String status = movieService.updateMovie(id, mov);

        if (status == "not found")
            return new ResponseEntity<>("Movie not found!", HttpStatus.NOT_FOUND);
        else if (status == "name")
            return new ResponseEntity<>("Title already exists", HttpStatus.FOUND);
        else
            return new ResponseEntity<>("Updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/movies/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable String id){
        String status = movieService.deleteMovie(id);
        if (status == "not found")
            return new ResponseEntity<>("Movie not found!", HttpStatus.NOT_FOUND);
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
