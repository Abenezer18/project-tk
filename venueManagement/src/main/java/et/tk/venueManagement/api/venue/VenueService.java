package et.tk.venueManagement.api.venue;

import et.tk.venueManagement.api.hall.Hall;
import et.tk.venueManagement.api.hall.HallDto;
import et.tk.venueManagement.api.hall.HallRepository;
import et.tk.venueManagement.api.seat.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public VenueInfoView createVenue(VenueDto venueDto) {
        venueDto.setName(venueDto.getName().toLowerCase());
        venueDto.setAddress(venueDto.getAddress().toLowerCase());
        venueDto.setEmail(venueDto.getEmail().toLowerCase());
        Optional<Venue> checkName = venueRepository.findByName(venueDto.getName()); // checking name
        if(checkName.isPresent()) // checking name
            return null;
        Venue venue = new Venue(venueDto); // mapping to Venue class
        Venue savedVenue = venueRepository.save(venue);
        return new VenueInfoView(savedVenue);
    }

    public List<VenueInfoView> getVenues() {
        List<Venue> venues = venueRepository.findAll();
        return venues.stream().map(VenueInfoView::new).toList();
    }

    public VenueInfoView getVenueById(String id) {
        Optional<Venue> venueOptional = venueRepository.findById(id);
        if (venueOptional.isEmpty()) // check if optional is empty
            return null;
        return new VenueInfoView(venueOptional.get());
    }

    public VenueInfoView updateVenue(String id, VenueDto venueDto) {
        Optional<Venue> venueOptional = venueRepository.findById(id);
        System.out.println(venueOptional); // display old data
        if (venueOptional.isPresent()) {
            Venue venue = new Venue(id,venueDto);
            Venue updatedVenue = venueRepository.save(venue);
            return new VenueInfoView(updatedVenue);
        } else {
            return null;
        }
    }

    public String deleteVenue(String id) {
        Optional<Venue> venueOptional = venueRepository.findById(id);
        if (venueOptional.isPresent()) {
            venueRepository.deleteById(id);
            return "deleted";
        } else {
            return "not found";
        }
    }

    public List<HallDto> getHallsByVenueId(String id) {
        List<Hall> halls = hallRepository.findByVenueId(id);
        if (halls.isEmpty())
            return null;
        return halls.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public String createHall(String venueId, HallDto hallDto) {
        hallDto.setName(hallDto.getName().toLowerCase());
        Optional<Venue> venueOptional = venueRepository.findById(venueId);
        List<Hall> nameCheck = hallRepository.findByVenueId(venueId);
        CollectionUtils.filter(nameCheck, o -> ((Hall) o).getName().equals(hallDto.getName()));

        if (nameCheck.isEmpty()) {
            if (venueOptional.isPresent()) {
                Venue venue = venueOptional.get(); // use this later
                Hall hall = new Hall(hallDto, venueId);
                Hall savedHall = hallRepository.save(hall);
                return "created";
            } else {
                return "venue";
            }
        } else
            return "name";
    }

    private VenueDto convertToDto(Venue venue) {
        VenueDto venueDto = new VenueDto();
        venueDto.setId(venue.getId());
        venueDto.setName(venue.getName());
        venueDto.setAddress(venue.getAddress());
        venueDto.setEmail(venue.getEmail());
        return venueDto;
    }

    private HallDto convertToDto(Hall hall) {
        HallDto hallDto = new HallDto();
        hallDto.setId(hall.getId());
        hallDto.setName(hall.getName());
        hallDto.setVenueId(hall.getVenueId());
        return hallDto;
    }
}