package et.tk.api.userManagement.clientAdmin;

import et.tk.api.userManagement.venueAdmin.VenueAdmin;
import et.tk.api.userManagement.venueAdmin.VenueAdminRepository;
import et.tk.api.userManagement.venueAdmin.VenueInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ClientAdminService {

    @Autowired
    private ClientAdminRepository clientAdminRepository;
    @Autowired
    private RestTemplate restTemplate;

    public String createClientAdmin(ClientAdmin clientAdmin){
        clientAdmin.setName(clientAdmin.getName().toLowerCase());
        ResponseEntity<ClientInfo> clientInfoResponseEntity;
        clientAdmin.setId(null);
        try {
            clientInfoResponseEntity = restTemplate
                    .getForEntity("http://localhost:8081/api/clients/"+ clientAdmin.getClientId() , ClientInfo.class);
        } catch (HttpClientErrorException e){
            return "client";
        }
        if(clientInfoResponseEntity.getBody() == null)
            return "clientNotFound";
//        try {
//            restTemplate.put("http://localhost:8081/api/venues/admin/" + clientAdmin.getVenueId(), clientAdmin.getId());
//        } catch (HttpClientErrorException e){
//            return "venue";
//        }
        clientAdminRepository.save(clientAdmin);
        return "created";
    }

    public List<ClientAdmin> getClientAdmins(){
        return clientAdminRepository.findAll();
    }

    public ClientAdmin getClientAdminById(String id){
        return clientAdminRepository.findById(id).orElse(null);
    }

    public ClientAdmin getAdminByClientId(String clientId){
        return clientAdminRepository.findByClientId(clientId).orElse(null);
    }

    public String updateClientAdmin(String id, ClientAdmin clientAdmin) {
        ResponseEntity<ClientInfo> clientInfoResponseEntity;

        Optional<ClientAdmin> clientAdminTempo = clientAdminRepository.findById(id);
        if (clientAdminTempo.isEmpty())
            return "not_found";
        ClientAdmin clientAdmin1 = clientAdminTempo.get();

        try {
            clientInfoResponseEntity = restTemplate
                    .getForEntity("http://localhost:8081/api/clients/"+ clientAdmin.getClientId() , ClientInfo.class);
        } catch (HttpClientErrorException e){
            return "client";
        }

        if (this.getAdminByClientId(clientAdmin.getClientId()) != null)
            return "clientId";

        clientAdmin1.setName(clientAdmin.getName());
        clientAdmin1.setClientId(clientAdmin.getClientId());

        clientAdminRepository.deleteById(id);
        clientAdminRepository.save(clientAdmin1);
        return "updated";
    }

    public int deleteClientAdmin(String id){
        if (this.getClientAdminById(id) == null)
            return 0;
        clientAdminRepository.deleteById(id);
        return 1;
    }
}