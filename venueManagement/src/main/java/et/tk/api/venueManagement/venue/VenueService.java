package et.tk.api.venueManagement.venue;

import et.tk.api.venueManagement.hall.Hall;
import et.tk.api.venueManagement.hall.HallRepository;
import et.tk.api.venueManagement.seat.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private SeatRepository seatRepository;

    public Venue createVenue(Venue venue) {
        venue.setId(null);
        venue.setName(venue.getName().toLowerCase());
        venue.setAddress(venue.getAddress().toLowerCase());
        venue.setEmail(venue.getEmail().toLowerCase());

        Optional<Venue> checkName = venueRepository.findByName(venue.getName()); // checking name
        if(checkName.isPresent()) // checking name
            return null;

        return venueRepository.save(venue);
    }

    public List<Venue> getVenues() {
        return venueRepository.findAll();
    }

    public Venue getVenueById(String id) {
        return venueRepository.findById(id).orElse(null);
    }

    public List<Venue> getVenuesByClientId(String id) {

        List<Venue> venues = venueRepository.findAll();
        CollectionUtils.filter(venues, o -> ((Venue) o).getClientId().equals(id));

        if (venues.isEmpty())
            return null;

        return venues;
    }

    public String updateVenue(String id, Venue venue) {
        Optional<Venue> venueOptional = venueRepository.findById(id);

        if (venueOptional.isEmpty())
            return "venue";

        if (!Objects.equals(venue.getClientId(), venueOptional.get().getClientId()))
            return "client id";

        Venue backUp = venueOptional.get();
        venue.setId(backUp.getId());

        venueRepository.deleteById(id);

        List<Venue> nameCheck = venueRepository.findAll();
        CollectionUtils.filter(nameCheck, o -> ((Venue) o).getName().equals(venue.getName()));

        if (nameCheck.isEmpty()) {
            venueRepository.save(venue);
            return "updated";
        }
        venueRepository.save(backUp);
        return "name";
    }

    public String deleteVenue(String id) {
        Optional<Venue> venueOptional = venueRepository.findById(id);
        if (venueOptional.isPresent()) {
            venueRepository.deleteById(id);
            List<Hall> halls = hallRepository.findByVenueId(id);
            if (halls == null){
                return "deleted";
            }
            List<String> hallIds = halls.stream().map(Hall::getId).collect(Collectors.toList());
            hallRepository.deleteAllById(hallIds);

            return "deleted";
        } else {
            return "not found";
        }
    }

    // to be used only by system admin
    public String updateVenueAdmin(String id, String venueAdminId) {
        Optional<Venue> venueOptional = venueRepository.findById(id);
        if (venueOptional.isEmpty())
            return "venue";
        Venue venue = venueOptional.get();
        venue.setVenueAdminId(venueAdminId);
        venueRepository.deleteById(id);
        venueRepository.save(venue);
        return "updated";
    }
}