package et.tk.api.emdb.omdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;



@RestController
@RequestMapping("/api/omdb")
public class OmdbController {

    @Autowired
    OmdbService omdbService;

    @GetMapping("/{title}")
    public ResponseEntity<String> getMovieByTitle(@PathVariable String title) {
        String response = omdbService.getMovieByTitle(title);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity.ok()
                .headers(headers)
                .body(omdbService.getMovieByTitle(title));
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<String> searchByTitle(@PathVariable String title) {
        String response = omdbService.searchByTitle(title);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity.ok()
                .headers(headers)
                .body(omdbService.getMovieByTitle(title));
    }
}

