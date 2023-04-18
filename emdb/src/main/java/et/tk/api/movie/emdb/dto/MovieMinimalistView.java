package et.tk.api.movie.emdb.dto;

import et.tk.api.movie.emdb.Movie;
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
public class MovieMinimalistView {

    private String id;
    private String title;
    private String year;

    public MovieMinimalistView (Movie movie){
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
    }
}
