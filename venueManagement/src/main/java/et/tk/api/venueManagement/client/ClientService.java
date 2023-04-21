package et.tk.api.venueManagement.client;

import et.tk.api.venueManagement.hall.Hall;
import et.tk.api.venueManagement.hall.HallRepository;
import et.tk.api.venueManagement.seat.Seat;
import et.tk.api.venueManagement.seat.SeatRepository;
import et.tk.api.venueManagement.seat.Ticket;
import et.tk.api.venueManagement.venue.Venue;
import et.tk.api.venueManagement.venue.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private  ClientRepository clientRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private SeatRepository seatRepository;

    public Client createClient(Client client) {
        client.setId(null);
        client.setClientAdminId(null);
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
        return clientRepository.findById(id).orElse(null);
    }

    public String updateClient(String id, Client client) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty())
            return "not found";

        client.setId(id);

        Client backUp = clientOptional.get();
        client.setId(backUp.getId());
        client.setClientAdminId(backUp.getClientAdminId());

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
        if (clientOptional.isEmpty())
            return "client";

        List<Venue> venues = venueRepository.findByClientId(id);
        if (venues.isEmpty()) {
            clientRepository.deleteById(id);
            return "deleted"; // if this does not work revert to the old method
        }

        for (Venue venue:venues) {
            List<Hall> halls = hallRepository.findByVenueId(venue.getId());
            for (Hall hall : halls) {
                List<Seat> seats = seatRepository.findByHallId(hall.getId());
                for (Seat seat:seats) {
                    seatRepository.deleteById(seat.getId());
                }
                hallRepository.deleteById(hall.getId());
            }
            venueRepository.deleteById(venue.getId());
        }
        clientRepository.deleteById(id);

        return "deleted";
    }

    // to be used only by system admin
    public String updateClientAdmin(String id, String clientAdminId) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty())
            return "client";

//        // check if the client admin exists
//        try {
//            ResponseEntity<Ticket> ticket = restTemplate
//                    .getForEntity("http://localhost:8083/api/users/" + clientAdminId, Ticket.class);
//            if (ticket.getBody() == null)
//                return "ticket";
//        } catch (HttpClientErrorException e) {
//            return "ticket service";
//        }

        Client client = clientOptional.get();
        client.setClientAdminId(clientAdminId);

        clientRepository.deleteById(id);

        clientRepository.save(client);
        return "updated";
    }
}
