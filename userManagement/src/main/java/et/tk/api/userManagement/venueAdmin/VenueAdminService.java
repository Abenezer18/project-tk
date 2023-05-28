package et.tk.api.userManagement.venueAdmin;
//package et.tk.api.venueManagement.venue.Venue;

import et.tk.api.userManagement.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VenueAdminService {
    @Autowired
    private VenueAdminRepository venueAdminRepository;
    @Autowired
    private RestTemplate restTemplate;

    public String createVenueAdmin(VenueAdmin venueAdmin){
        venueAdmin.setName(venueAdmin.getName().toLowerCase());
        ResponseEntity<VenueInfo> venueInfoResponseEntity;
        venueAdmin.setId(null);
        try {
            venueInfoResponseEntity = restTemplate
                    .getForEntity("http://localhost:8085/api/venues/"+ venueAdmin.getVenueId() , VenueInfo.class);
        } catch (HttpClientErrorException e){
            return "venue";
        }
        if(venueInfoResponseEntity.getBody() == null)
            return "venueNotFound";
        try {
            restTemplate.put("http://localhost:8085/api/venues/admin/" + venueAdmin.getVenueId(), venueAdmin.getId());
        } catch (HttpClientErrorException e){
            return "venue";
        }
        venueAdminRepository.save(venueAdmin);
        return "created";
    }

    public List<VenueAdmin> getVenueAdmins(){
        return venueAdminRepository.findAll();
    }

    public VenueAdmin getVenueAdminById(String id){
        return venueAdminRepository.findById(id).orElse(null);
    }

    public VenueAdmin getAdminByVenueId(String venueId){
        return venueAdminRepository.findByVenueId(venueId).orElse(null);
    }

    public String updateVenueAdmin(String id, VenueAdmin venueAdmin) {
        Optional<VenueAdmin> venueAdminTempo = venueAdminRepository.findById(id);
        if (venueAdminTempo.isEmpty())
            return "not_found";
        VenueAdmin venueAdmin1 = venueAdminTempo.get();
        venueAdmin1.setName(venueAdmin.getName());

        venueAdminRepository.deleteById(id);
        venueAdminRepository.save(venueAdmin1);
        return "updated";
    }

    public int deleteVenueAdmin(String id){
        if (this.getVenueAdminById(id) == null)
            return 0;
        venueAdminRepository.deleteById(id);
        return 1;
    }
}
