package et.tk.api.emdb.omdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmdbService {

    @Autowired
    RestTemplate restTemplate;

    public String getMovieByTitle (String title){
        String movieInfoResponseEntity;
        try {
            movieInfoResponseEntity = restTemplate.getForObject("http://www.omdbapi.com/?t="+title+"&apikey=70bdc566", String.class);
        } catch (HttpClientErrorException e) {
            return null;
        }
        return movieInfoResponseEntity;
    }

    public String searchByTitle (String title){
        String movieInfoResponseEntity;
        try {
            movieInfoResponseEntity = restTemplate.getForObject("http://www.omdbapi.com/?s="+title+"&apikey=70bdc566", String.class);
        } catch (HttpClientErrorException e) {
            return null;
        }
        return movieInfoResponseEntity;
    }
}


