package et.tk.api.schedule.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VenueInfo {
    private String id;

    private String name;

    private String address;
}
