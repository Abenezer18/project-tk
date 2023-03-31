package et.tk.api.userManagement.gateKeeper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "gatekeepers")
public class GateKeeper {
    @Id
    private String id;
    private String name;
    private String venueId;
}