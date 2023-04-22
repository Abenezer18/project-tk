package et.tk.api.ticket;

import et.tk.api.ticket.Dto.TicketPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<String> createTicket(@RequestBody Ticket ticket){
        return ticketService.createTicket(ticket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets(){
        return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable String id){
        Ticket ticket = ticketService.getById(id);
        if (ticket == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ticket, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Ticket>> getTicketsByUserId(@PathVariable String userId){
        List<Ticket> ticketList = ticketService.getTicketsByUserId(userId);
        if (ticketList == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ticketList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<Ticket>> getTicketsByScheduleId(@PathVariable String scheduleId){
        List<Ticket> ticketList = ticketService.getTicketsByScheduleId(scheduleId);
        if (ticketList == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ticketList, HttpStatus.FOUND);
    }

    // To be used only by the gatekeeper
    @PutMapping("/gatekeeper/{id}")
    public ResponseEntity<String> updateTicketStatus(@PathVariable String id){
        String result = ticketService.updateTicketStatus(id);
        if (Objects.equals(result, "valid"))
            return new ResponseEntity<>(result, HttpStatus.OK);
        else if (Objects.equals(result, "invalid"))
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        else
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
