package et.tk.api.venueManagement.client;

import et.tk.api.venueManagement.hall.Hall;
import et.tk.api.venueManagement.hall.HallDetailedResponse;
import et.tk.api.venueManagement.hall.HallDto;
import et.tk.api.venueManagement.hall.HallRepository;
import et.tk.api.venueManagement.seat.Seat;
import et.tk.api.venueManagement.seat.SeatRepository;
import et.tk.api.venueManagement.venue.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private VenueService venueService;

    public Client createClient(Client client) {
        client.setId(null);
        client.setName(client.getName().toLowerCase());
        client.setAddress(client.getAddress().toLowerCase());
        client.setEmail(client.getEmail().toLowerCase());
        Optional<Client> checkName = clientRepository.findByName(client.getName()); // checking name
        if(checkName.isPresent()) // checking name
            return null;
        clientRepository.save(client);
        return client;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(String id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        return clientOptional.orElse(null);
    }

    public String updateClient(String id, Client client) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty())
            return "not found";
        Client backUp = clientOptional.get();
        clientRepository.deleteById(id);
        List<Client> nameCheck = clientRepository.findAll();
        CollectionUtils.filter(nameCheck, o -> ((Client) o).getName().equals(client.getName()));
        if (nameCheck.isEmpty()) {
            clientRepository.save(client);
            return "updated";
        }
        clientRepository.save(backUp);
        return "name";
    }

    public String deleteClient(String id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            return "not found";
        }
        List<Venue> venues = venueService.getVenuesByClientId(id);
        if (venues == null){
            clientRepository.deleteById(id);
            return "deleted";
        }

        for (Venue venue:venues) {
            List<HallDto> getHalls = venueService.getHallsByVenueId(venue.getId());
            for (HallDto hallDto : getHalls) {  // deleting all the halls and seats inside a venue
                seatRepository.deleteByHallId(hallDto.getId());
            }
            hallRepository.deleteByVenueId(venue.getId());
        }

        venueRepository.deleteByClientId(id);
        return "deleted";
    }

//
//    // to be used only by system admin
//    public String updateClientAdmin(String id, String clientAdminId) {
//        Optional<Client> clientOptional = clientRepository.findById(id);
//        if (clientOptional.isEmpty())
//            return "client";
//        Client client = clientOptional.get();
//        client.setClientAdminId(clientAdminId);
//        clientRepository.deleteById(id);
//        clientRepository.save(client);
//        return "updated";
//    }
}