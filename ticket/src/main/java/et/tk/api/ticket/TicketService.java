package et.tk.api.ticket;

import et.tk.api.ticket.Dto.ScheduleInfo;
import et.tk.api.ticket.Dto.SeatInfo;
import et.tk.api.ticket.Dto.TicketPost;
import et.tk.api.ticket.Dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private RestTemplate restTemplate;

    public UserInfo userInfo (String userId){
        ResponseEntity<UserInfo> userInfoResponseEntity = restTemplate
                .getForEntity("http://localhost:8084/api/users/" + userId, UserInfo.class);
        UserInfo userInfo = userInfoResponseEntity.getBody();
        System.out.println(userInfoResponseEntity.getStatusCode());
        return userInfo;
    }
    public ScheduleInfo scheduleInfo (String scheduleId){

        ResponseEntity<ScheduleInfo> scheduleInfoResponseEntity = restTemplate
                .getForEntity("http://localhost:8082/api/schedules/ticket/" + scheduleId, ScheduleInfo.class);
        ScheduleInfo scheduleInfo = scheduleInfoResponseEntity.getBody();
        System.out.println(scheduleInfoResponseEntity.getStatusCode());
        return scheduleInfo;
    }
    public SeatInfo seatInfo (String seatId){

        ResponseEntity<SeatInfo> seatInfoResponseEntity = restTemplate
                .getForEntity("http://localhost:8081/api/seats/" + seatId, SeatInfo.class);
        SeatInfo seatInfo = seatInfoResponseEntity.getBody();
        System.out.println(seatInfoResponseEntity.getStatusCode());
        return seatInfo;
    }

    public String createTicket(TicketPost ticketPost){
        UserInfo userInfo = this.userInfo(ticketPost.getUserId()); // check if user exists
        if (userInfo == null)
            return "user";

        ScheduleInfo scheduleInfo = this.scheduleInfo(ticketPost.getScheduleId()); // check if schedule exists
        if (scheduleInfo == null)
            return "schedule";

        for (String seat : ticketPost.getSeatIds()) {
            SeatInfo seatInfo = this.seatInfo(seat);
            if (seatInfo.isSeatStatus())
                return ("seat : " + seatInfo.getRow() + seatInfo.getNumber() + " is unavailable");
            else {          // setting the seat status true
                seatInfo.setSeatStatus(true);
                System.out.println("\n\n"+ seatInfo.isSeatStatus() +"\n\n");
                restTemplate.put("http://localhost:8081/api/seats/" + seatInfo.getId(), seatInfo);
            }
        }
        ticketRepository.save(new Ticket(ticketPost));
        return "created";
    }

    public List<Ticket> getAllTickets(){

        List<Ticket> ticketList = ticketRepository.findAll();
        if (ticketList.isEmpty())
            return null;
        return ticketList;
    }

    public Ticket getById(String id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        return ticketOptional.orElse(null);
    }

    public List<Ticket> getTicketsByUserId(String userId) {
        return ticketRepository.findByUserId(userId);
    }

    public List<Ticket> getTicketsByScheduleId(String scheduleId) {
        return ticketRepository.findByScheduleId(scheduleId);
    }

    // Tobe used only by the gatekeeper
    public String updateTicketStatus(String id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);

        if (ticketOptional.isEmpty()) // checking if the ticket exists
            return "not found";

        if (ticketOptional.get().isTicketStatus()) {
            ticketRepository.deleteById(id);
            Ticket updatedTicket = ticketOptional.get();
            updatedTicket.setTicketStatus(false);
            ticketRepository.save(updatedTicket);
            return "valid";
        } else {
            return "invalid";
        }
    }
}
