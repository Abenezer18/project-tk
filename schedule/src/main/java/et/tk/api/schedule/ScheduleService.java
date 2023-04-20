package et.tk.api.schedule;

import et.tk.api.schedule.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private RestTemplate restTemplate;

    public VenueInfo venueInfo (String venueId){
        ResponseEntity<VenueInfo> venueInfoResponseEntity;

        try {
            venueInfoResponseEntity = restTemplate
                    .getForEntity("http://localhost:8081/api/venues/" + venueId, VenueInfo.class);
        } catch (HttpStatusCodeException e) {
            return null;
        }
        return venueInfoResponseEntity.getBody();
    }
    public HallInfo hallInfo (String hallId){
        ResponseEntity<HallInfo> hallInfoResponseEntity;

        try {
            hallInfoResponseEntity = restTemplate
                    .getForEntity("http://localhost:8081/api/halls/" + hallId, HallInfo.class);
        } catch (HttpStatusCodeException e) {
            return null;
        }
        return hallInfoResponseEntity.getBody();
    }
    public MovieInfo movieInfo (String movieId){
        ResponseEntity<MovieInfo> movieInfoResponseEntity;
        try {
            movieInfoResponseEntity = restTemplate
                    .getForEntity("http://localhost:8080/movies/" + movieId, MovieInfo.class);
        } catch (HttpStatusCodeException e) {
            return null;
        }
        return movieInfoResponseEntity.getBody();
    }
    public String ticketInfo (String scheduleId){
        ResponseEntity<TicketInfo> ticketInfoResponseEntity;
        try {
            ticketInfoResponseEntity = restTemplate
                    .getForEntity("http://localhost:8083/api/tickets/schedule/" + scheduleId, TicketInfo.class);
        } catch (HttpStatusCodeException e) {
            return "internal";
        }
        if (ticketInfoResponseEntity.getStatusCode().equals(HttpStatus.FOUND)){
            return "found";
        } else {
            return "not found";
        }
    }

    public String createSchedule(String movieId, String hallId, Schedule schedule){
        HallInfo hallInfo = this.hallInfo(hallId); // checking if hall exists
        if (hallInfo == null)
            return "hall";

        MovieInfo movieInfo = this.movieInfo(movieId);
        if (movieInfo == null)
            return "movie";

        List <Schedule> bookCheck = scheduleRepository.findAll();
        CollectionUtils.filter(bookCheck, o -> ((Schedule) o).getHallId().equals(hallId));
        CollectionUtils.filter(bookCheck, o -> ((Schedule) o).getDate().equals(schedule.getDate()));
        CollectionUtils.filter(bookCheck, o -> ((Schedule) o).getStartTime().equals(schedule.getStartTime()));

        if (bookCheck.isEmpty()){
            schedule.setId(null);
            schedule.setDateOfPublish(LocalDateTime.now().toString());
            schedule.setLastUpdated(null);
            schedule.setHallId(hallId);
            schedule.setMovieId(movieId);
            scheduleRepository.save(schedule);
            return null;
        } else {
            return "booked";
        }
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesInHall(String hallId) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findByHallId(hallId);
        if (scheduleOptional.isEmpty())
            return null;
        return scheduleOptional.stream().toList();
    }

    public List<Schedule> getSchedulesByMovie(String movieId) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findByMovieId(movieId);
        if (scheduleOptional.isEmpty())
            return null;
        return scheduleOptional.stream().toList();
    }

    public Schedule getScheduleById(String id){
        return scheduleRepository.findById(id).orElse(null);
    }

    public String updateSchedule(String id, Schedule schedule){
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (scheduleOptional.isEmpty())
            return "schedule";
        Schedule oldSchedule = scheduleOptional.get();

        HallInfo hallInfo = this.hallInfo(schedule.getHallId()); // checking if hall exists
        if (hallInfo == null)
            return "hallDoesNotExist";

        MovieInfo movieInfo = this.movieInfo(schedule.getMovieId());
        if (movieInfo == null)
            return "movieDoesNotExist";

        scheduleRepository.deleteById(id);

        List<Schedule> bookCheck = scheduleRepository.findByHallId(schedule.getHallId()).stream().toList();
        CollectionUtils.filter(bookCheck, o -> ((Schedule) o).getDate().equals(schedule.getDate()));
        CollectionUtils.filter(bookCheck, o -> ((Schedule) o).getStartTime().equals(schedule.getStartTime()));

        if (bookCheck.isEmpty()){
            schedule.setId(oldSchedule.getId());
            schedule.setDateOfPublish(oldSchedule.getDateOfPublish());
            schedule.setLastUpdated(LocalDateTime.now().toString());
            scheduleRepository.save(schedule);
            return null;
        } else {
            scheduleRepository.save(oldSchedule);
            return "hall";
        }
    }

    public String deleteSchedule(String id){
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (schedule.isEmpty())
            return null;

        String ticket = this.ticketInfo(id);
        if (Objects.equals(ticket, "found"))
            return "ticket";
        else if (Objects.equals(ticket, "internal"))
            return "ticket info";

        scheduleRepository.deleteById(id);
        return "deleted";
    }
}