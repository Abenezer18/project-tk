package et.tk.api.userManagement.gateKeeper;

import et.tk.api.userManagement.systemAdmin.SystemAdmin;
import et.tk.api.userManagement.venueAdmin.VenueInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class GateKeeperService {

    @Autowired
    GateKeeperRepository gateKeeperRepository;
    @Autowired
    private RestTemplate restTemplate;

    public String createGateKeeper(GateKeeper gateKeeper){
        ResponseEntity<VenueInfo> gateKeeperResponseEntity;
        try {
            gateKeeperResponseEntity = restTemplate
                    .getForEntity("http://localhost:8085/api/venues/"+ gateKeeper.getVenueId() , VenueInfo.class);
        } catch (HttpClientErrorException e){
            return "venue";
        }
        gateKeeper.setId(null);
        gateKeeper.setName(gateKeeper.getName().toLowerCase());

        List<GateKeeper> gateKeeperList = new java.util.ArrayList<>(this.getGateKeeperByVenueId(gateKeeper.getVenueId()));
        gateKeeperList.removeIf(o -> !((GateKeeper) o).getName().equals(gateKeeper.getName()));
        if (gateKeeperList.isEmpty()){
            gateKeeperRepository.save(gateKeeper);
            return "created";
        }
            return "name";
    }

    public List<GateKeeper> getGateKeepers(){
        return gateKeeperRepository.findAll();
    }

    public GateKeeper getGateKeeperById(String id){
        return gateKeeperRepository.findById(id).orElse(null);
    }

    public List<GateKeeper> getGateKeeperByVenueId(String venueId){
        List<GateKeeper> gateKeeperOptional = gateKeeperRepository.findByVenueId(venueId);
        if (gateKeeperOptional.isEmpty())
            return null;

        return gateKeeperOptional.stream().toList();
    }

    public String updateGateKeeper(String id, GateKeeper gateKeeper){
        Optional<GateKeeper> gateKeeperOptional = gateKeeperRepository.findById(id);
        if (gateKeeperOptional.isEmpty())
            return "not_found";
        GateKeeper gateKeeper1 = gateKeeperOptional.get();
        gateKeeper1.setName(gateKeeper.getName().toLowerCase());
        List<GateKeeper> gateKeeperList = new java.util.ArrayList<>(this.getGateKeeperByVenueId(gateKeeperOptional.get().getVenueId()));
        gateKeeperList.removeIf(o -> !((GateKeeper) o).getName().equals(gateKeeper.getName()));
        if (gateKeeperList.isEmpty()) {
            gateKeeperRepository.deleteById(id);
            gateKeeperRepository.save(gateKeeper1);
            return "updated";
        }
        return "name";
    }

    public int deleteGateKeeper(String id){
        if (this.getGateKeeperById(id) == null)
            return 0;
        gateKeeperRepository.deleteById(id);
        return 1;
    }
}
