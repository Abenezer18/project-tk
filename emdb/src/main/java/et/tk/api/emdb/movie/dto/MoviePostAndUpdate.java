package et.tk.api.emdb.movie.dto;

import java.util.List;

import et.tk.api.emdb.movie.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoviePostAndUpdate {

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

    public MoviePostAndUpdate(Movie movie){
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.rated = movie.getRated();
        this.released = movie.getReleased();
        this.runtime = movie.getRuntime();
        this.genre = movie.getGenre();
        this.director = movie.getDirector();
        this.writer = movie.getWriter();
        this.actors = movie.getActors();
        this.plot = movie.getPlot();
        this.language = movie.getLanguage();
    }
}
