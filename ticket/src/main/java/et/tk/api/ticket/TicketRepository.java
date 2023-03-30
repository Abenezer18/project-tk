package et.tk.api.ticket;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    public Optional<Ticket> findByScheduleId(String scheduleId);
    public Optional<Ticket> findByUserId(String userId);
}
