package et.tk.api.ticket;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    public List<Ticket> findByScheduleId(String scheduleId);
    public List<Ticket> findByUserId(String userId);
}
