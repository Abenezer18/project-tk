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
    public ResponseEntity<String> createTicket(@RequestBody TicketPost ticketPost){
        String status = ticketService.createTicket(ticketPost);

        if (Objects.equals(status, "user"))
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "schedule"))
            return new ResponseEntity<>("Schedule not found!", HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "created"))
            return new ResponseEntity<>("Ticket created!", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Unknown error!\n"+ status , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets(){
        List<Ticket> ticketList = ticketService.getAllTickets();
        if (ticketList == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ticketList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable String id){
        Ticket ticket = ticketService.getById(id);
        if (ticket == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ticket, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Ticket>> getTicketsByUserId(@PathVariable String userId){
        List<Ticket> ticketList = ticketService.getTicketsByUserId(userId);
        if (ticketList == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ticketList, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<List<Ticket>> getTicketsByScheduleId(@PathVariable String scheduleId){
        List<Ticket> ticketList = ticketService.getTicketsByScheduleId(scheduleId);
        if (ticketList == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ticketList, HttpStatus.NOT_FOUND);
    }

    // Tobe used only by the gatekeeper
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
