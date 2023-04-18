package et.tk.api.movie.omdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/omdb")
public class OmdbController {

    @Autowired
    OmdbService omdbService;

    @GetMapping("/{title}")
    public ResponseEntity<String> getMovieByTitle(@PathVariable String title) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return   ResponseEntity.ok()
                .headers(headers)
                .body(omdbService.getMovieByTitle(title));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<String> searchByTitle(@PathVariable String title) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity.ok()
                .headers(headers)
                .body(omdbService.searchByTitle(title));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<String> searchById(@PathVariable String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity.ok()
                .headers(headers)
                .body(omdbService.searchById(id));
    }
}

