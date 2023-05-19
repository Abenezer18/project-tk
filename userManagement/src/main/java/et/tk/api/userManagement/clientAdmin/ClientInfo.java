package et.tk.api.userManagement.clientAdmin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfo {
    private String id;
    private String name;
    private String address;
    private String email;
}
