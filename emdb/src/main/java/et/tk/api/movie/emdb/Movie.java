package et.tk.api.movie.emdb;

import java.util.List;

import et.tk.api.movie.emdb.dto.MovieMinimalistView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("movies") // @Document(collection = "halls") if you are not using the @Builder annotation
@Builder
public class Movie {

    @Id
    private String id;
    private String title;
    private String year;
    private String rated; //PG 13
    //correct to time
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private List<String> actors;
    private String plot;
    private String language;

    public Movie (MovieMinimalistView movieMinimalistView){
        this.id = movieMinimalistView.getId();
        this.title = movieMinimalistView.getTitle();
        this.year = movieMinimalistView.getYear();
    }
}
