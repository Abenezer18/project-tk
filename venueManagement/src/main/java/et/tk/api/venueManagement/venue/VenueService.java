package et.tk.api.venueManagement.venue;

import et.tk.api.venueManagement.hall.Hall;
import et.tk.api.venueManagement.hall.HallDetailedResponse;
import et.tk.api.venueManagement.hall.HallDto;
import et.tk.api.venueManagement.hall.HallRepository;
import et.tk.api.venueManagement.seat.Seat;
import et.tk.api.venueManagement.seat.SeatDto;
import et.tk.api.venueManagement.seat.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return venueRepository.findAll().stream().map(VenueInfoView::new).toList();
    }

    public VenueDetailedResponse getDetailedVenueResponse(String id){
        Optional<Venue> venueOptional = venueRepository.findById(id);
        if (venueOptional.isEmpty())
            return null;
        VenueDetailedResponse venueDetailedResponse = new VenueDetailedResponse(venueOptional.get());


        venueDetailedResponse.setHallDetailedResponse(this.getHallsDetailedResponse(id));

        return venueDetailedResponse;
    }

    public VenueInfoView getVenueById(String id) {
        Optional<Venue> venueOptional = venueRepository.findById(id);
        // check if optional is empty
        return venueOptional.map(VenueInfoView::new).orElse(null);
    }

    public List<Venue> getVenuesByClientId(String id) {
        List<Venue> venues = venueRepository.findAll();
        CollectionUtils.filter(venues, o -> ((Venue) o).getClientId().equals(id));
        if (venues.isEmpty())
            return null;
        return venues;
    }

    public String updateVenue(String id, VenueDto venueDto) {
        Optional<Venue> venueOptional = venueRepository.findById(id);
        System.out.println(venueOptional); // display old data
        if (venueOptional.isEmpty())
            return "not found";
        Venue backUp = venueOptional.get();
        venueRepository.deleteById(id);
        List<Venue> nameCheck = venueRepository.findAll();
        CollectionUtils.filter(nameCheck, o -> ((Venue) o).getName().equals(venueDto.getName()));
        if (nameCheck.isEmpty()) {
            Venue venue = new Venue(id, venueDto);
            Venue updatedVenue = venueRepository.save(venue);
            return "updated";
        }
        venueRepository.save(backUp);
        return "name";
    }

    public String deleteVenue(String id) {
        Optional<Venue> venueOptional = venueRepository.findById(id);
        if (venueOptional.isPresent()) {
            venueRepository.deleteById(id);
            List<HallDto> getHalls = this.getHallsByVenueId(id);
            if (getHalls == null)
                return "deleted";
            for (HallDto hallDto:getHalls) {  // deleting all the halls and seats inside a venue
                seatRepository.deleteByHallId(hallDto.getId());
                hallRepository.deleteById(hallDto.getId());
            }
            return "deleted";
        } else {
            return "not found";
        }
    }

    public List<HallDto> getHallsByVenueId(String id) {
        List<Hall> halls = hallRepository.findAll();
        CollectionUtils.filter(halls, o -> ((Hall) o).getVenueId().equals(id));
        if (halls.isEmpty())
            return null;
        return halls.stream().map(HallDto::new).collect(Collectors.toList());
    }

    public List<HallDetailedResponse> getHallsDetailedResponse(String id) {
        List<Hall> halls = hallRepository.findAll();
        CollectionUtils.filter(halls, o -> ((Hall) o).getVenueId().equals(id));
        if (halls.isEmpty())
            return null;
        List<HallDetailedResponse> hallDetailedResponses = halls.stream().map(HallDetailedResponse::new).toList();

        for (HallDetailedResponse hallDetailedResponse : hallDetailedResponses) {
            String hallId = hallDetailedResponse.getId();
            List<Seat> seatsInHall = seatRepository.findByHallId(hallId);
            hallDetailedResponse.setSeats(seatsInHall);
        }
        return hallDetailedResponses;
    }

    public String createHall(String venueId, HallDto hallDto) {
        if (this.getVenueById(venueId) == null)
            return "venue";
        hallDto.setName(hallDto.getName().toLowerCase());
        List<Hall> nameCheck = hallRepository.findAll();
        CollectionUtils.filter(nameCheck, o -> ((Hall) o).getVenueId().equals(venueId));
        CollectionUtils.filter(nameCheck, o -> ((Hall) o).getName().equals(hallDto.getName()));

        if (nameCheck.isEmpty()) {
            Hall hall = new Hall(hallDto, venueId);
            Hall savedHall = hallRepository.save(hall);
            return "created";
        } else
            return "name";
    }


//    // to be used only by system admin
//    public String updateVenueAdmin(String id, String venueAdminId) {
//        Optional<Venue> venueOptional = venueRepository.findById(id);
//        if (venueOptional.isEmpty())
//            return "venue";
//        Venue venue = venueOptional.get();
//        venue.setVenueAdminId(venueAdminId);
//        venueRepository.deleteById(id);
//        venueRepository.save(venue);
//        return "updated";
//    }
}