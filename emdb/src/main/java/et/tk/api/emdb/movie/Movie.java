package et.tk.api.emdb.movie;

import java.util.List;

import et.tk.api.emdb.movie.dto.MovieMinimalistView;
import et.tk.api.emdb.movie.dto.MoviePostAndUpdate;
import et.tk.api.emdb.movie.dto.MovieDetailedResponse;
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

    public Movie (MovieDetailedResponse movieDetailedResponse){
        this.id = movieDetailedResponse.getId();
        this.title = movieDetailedResponse.getTitle();
        this.year = movieDetailedResponse.getYear();
        this.rated = movieDetailedResponse.getRated();
        this.released = movieDetailedResponse.getReleased();
        this.runtime = movieDetailedResponse.getRuntime();
        this.genre = movieDetailedResponse.getGenre();
        this.director = movieDetailedResponse.getDirector();
        this.writer = movieDetailedResponse.getWriter();
        this.actors = movieDetailedResponse.getActors();
        this.plot = movieDetailedResponse.getPlot();
        this.language = movieDetailedResponse.getLanguage();
    }
    public Movie (MovieMinimalistView movieMinimalistView){
        this.id = movieMinimalistView.getId();
        this.title = movieMinimalistView.getTitle();
        this.year = movieMinimalistView.getYear();
    }
    //POST
    public Movie (MoviePostAndUpdate moviePostAndUpdate){
        this.title = moviePostAndUpdate.getTitle();
        this.year = moviePostAndUpdate.getYear();
        this.rated = moviePostAndUpdate.getRated();
        this.released = moviePostAndUpdate.getReleased();
        this.runtime = moviePostAndUpdate.getRuntime();
        this.genre = moviePostAndUpdate.getGenre();
        this.director = moviePostAndUpdate.getDirector();
        this.writer = moviePostAndUpdate.getWriter();
        this.actors = moviePostAndUpdate.getActors();
        this.plot = moviePostAndUpdate.getPlot();
        this.language = moviePostAndUpdate.getLanguage();
    }
}
