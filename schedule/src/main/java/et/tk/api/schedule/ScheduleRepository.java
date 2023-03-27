package et.tk.api.schedule;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ScheduleRepository extends MongoRepository <Schedule, String>{
    public Optional<Schedule> findByMovieId(String movieId);
    public Optional<Schedule> findByHallId(String hallId);
}
