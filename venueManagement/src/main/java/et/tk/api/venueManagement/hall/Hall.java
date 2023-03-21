package et.tk.api.venueManagement.hall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "halls")
public class Hall {
    @Id
    private String id;
    private String name;

    //  private int capacity;
    @DBRef
    private String venueId;

    // for POST
    public Hall(String name, String venueId) {
        this.name = name;
        this.venueId = venueId;
    }
    public Hall(HallDto hallDto, String venueId){
        this.name = hallDto.getName();
        this.venueId = venueId;
    }
    public Hall(HallDto hallDto){
        this.name = hallDto.getName();
    }
}
