package et.tk.api.movie.omdb;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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

    public String searchById (String id){
        String movieInfoResponseEntity;
        try {
            movieInfoResponseEntity = restTemplate.getForObject("http://www.omdbapi.com/?i="+id+"&apikey=70bdc566", String.class);
        } catch (HttpClientErrorException e) {
            return null;
        }
        return movieInfoResponseEntity;
    }
}


