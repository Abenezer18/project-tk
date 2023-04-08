package et.tk.api.userManagement.systemAdmin;

import et.tk.api.userManagement.venueAdmin.VenueAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SystemAdminService {

    @Autowired
    private SystemAdminRepository systemAdminRepository;

    public String createSystemAdmin(SystemAdmin systemAdmin){
        systemAdmin.setId(null);
        systemAdmin.setName(systemAdmin.getName().toLowerCase());
//        if (systemAdminRepository.findByName(systemAdmin.getName()).isPresent())
//            return "name";
        systemAdminRepository.save(systemAdmin);
        return "created";
    }

    public List<SystemAdmin> getSystemAdmins(){
        return systemAdminRepository.findAll();
    }

    public SystemAdmin getSystemAdminById(String id){
        return systemAdminRepository.findById(id).orElse(null);
    }

    public SystemAdmin getSystemAdminByName(String name){
        return systemAdminRepository.findByName(name).orElse(null);
    }

    public String updateSystemAdmin(String id, SystemAdmin systemAdmin){
        Optional<SystemAdmin> systemAdminOptional = systemAdminRepository.findById(id);
        if (systemAdminOptional.isEmpty())
            return "not_found";
        SystemAdmin systemAdmin1 = systemAdminOptional.get();
        systemAdmin1.setName(systemAdmin.getName().toLowerCase());
        systemAdminRepository.deleteById(id);
        systemAdminRepository.save(systemAdmin1);
        return "updated";
    }

    public int deleteSystemAdmin(String id){
        if (this.getSystemAdminById(id) == null)
            return 0;
        systemAdminRepository.deleteById(id);
        return 1;
    }
}
