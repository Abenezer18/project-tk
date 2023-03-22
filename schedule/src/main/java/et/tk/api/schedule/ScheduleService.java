package et.tk.api.schedule;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import et.tk.api.schedule.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScheduleService {
    private ScheduleRepository scheduleRepository;
    private RestTemplate restTemplate;

    public VenueInfo venueInfo (String venueId){

        ResponseEntity<VenueInfo> venueInfoResponseEntity = restTemplate
                .getForEntity("http://localhost:8080/api/venues/" + venueId, VenueInfo.class);
        VenueInfo venueInfo = venueInfoResponseEntity.getBody();

        System.out.println(venueInfoResponseEntity.getStatusCode());
        return venueInfo;
    }
    public HallInfo hallInfo (String hallId){

        ResponseEntity<HallInfo> hallInfoResponseEntity = restTemplate
                .getForEntity("http://localhost:8080/api/halls/" + hallId, HallInfo.class);
        HallInfo hallInfo = hallInfoResponseEntity.getBody();

        System.out.println(hallInfoResponseEntity.getStatusCode());
        return hallInfo;
    }
    public MovieInfo movieInfo (String movieId){

        ResponseEntity<MovieInfo> movieInfoResponseEntity = restTemplate
                .getForEntity("http://localhost:8081/api/movies/" + movieId, MovieInfo.class);
        MovieInfo movieInfo = movieInfoResponseEntity.getBody();

        System.out.println(movieInfoResponseEntity.getStatusCode());
        return movieInfo;
    }

    public String createSchedule(String movieId, String hallId, SchedulePost schedulePost){
        HallInfo hallInfo = this.hallInfo(hallId); // checking if hall exists
        if (hallInfo == null)
            return "hall";
        VenueInfo venueInfo = this.venueInfo(hallInfo.getVenueId());
        MovieInfo movieInfo = this.movieInfo(movieId);
        if (movieInfo == null)
            return "movie";
        List <Schedule> bookCheck = scheduleRepository.findByHallId(hallId).stream().toList();
        CollectionUtils.filter(bookCheck, o -> ((Schedule) o).getDate().equals(schedulePost.getDate()));
        CollectionUtils.filter(bookCheck, o -> ((Schedule) o).getStartTime().equals(schedulePost.getStartTime()));

        if (bookCheck.isEmpty()){
            Schedule newSchedule = new Schedule(schedulePost);
            scheduleRepository.save(newSchedule);
            return null;
        } else {
            return "booked";
        }
    }

    public List<ScheduleMinimalist> getAllSchedules(){
        List<Schedule> scheduleList = scheduleRepository.findAll();
        return scheduleList.stream().map(ScheduleMinimalist::new).toList();
    }

    public List<ScheduleGet> getSchedulesInHall(String hallId) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findByHallId(hallId);
        if (scheduleOptional.isEmpty())
            return null;
        List<Schedule> scheduleList = scheduleOptional.stream().toList();
        HallInfo hallInfo = this.hallInfo(hallId);
        VenueInfo venueInfo = this.venueInfo(hallInfo.getVenueId());
        List<ScheduleGet> scheduleGets = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            scheduleGets.add(new ScheduleGet(schedule, venueInfo, hallInfo));
        }
        return scheduleGets;
    }

    public List<ScheduleGet> getSchedulesByMovie(String movieId) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findByMovieId(movieId);
        if (scheduleOptional.isEmpty())
            return null;
        List<Schedule> scheduleList = scheduleOptional.stream().toList();
        List<ScheduleGet> scheduleGets = new ArrayList<>();
        MovieInfo movieInfo = this.movieInfo(movieId);

        for (Schedule schedule : scheduleList) {
            HallInfo hallInfo = this.hallInfo(schedule.getHallId());
            VenueInfo venueInfo = this.venueInfo(hallInfo.getVenueId());
            scheduleGets.add(new ScheduleGet(schedule, venueInfo, hallInfo));
        }
        return scheduleGets.stream().toList();
    }

    public ScheduleGet getScheduleById(String id){
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (scheduleOptional.isEmpty())
            return null;
        Schedule schedule = scheduleOptional.get();
        HallInfo hallInfo = this.hallInfo(schedule.getHallId());
        VenueInfo venueInfo = this.venueInfo(hallInfo.getVenueId());
        MovieInfo movieInfo = this.movieInfo(schedule.getMovieId());

        return new ScheduleGet(schedule, venueInfo, hallInfo, movieInfo);
    }

    public String updateSchedule(String id, ScheduleUpdate scheduleUpdate){
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if (scheduleOptional.isEmpty())
            return "schedule";
        Schedule oldSchedule = scheduleOptional.get();
        HallInfo hallInfo = this.hallInfo(scheduleUpdate.getHallId()); // checking if hall exists
        if (hallInfo == null)
            return "hallDoesNotExist";
        MovieInfo movieInfo = this.movieInfo(scheduleUpdate.getMovieId());
        if (movieInfo == null)
            return "movieDoesNotExist";
        scheduleRepository.deleteById(id);
        List<Schedule> bookCheck = scheduleRepository.findByHallId(scheduleUpdate.getHallId()).stream().toList();
        CollectionUtils.filter(bookCheck, o -> ((Schedule) o).getDate().equals(scheduleUpdate.getDate()));
        CollectionUtils.filter(bookCheck, o -> ((Schedule) o).getStartTime().equals(scheduleUpdate.getStartTime()));

        if (bookCheck.isEmpty()){
            Schedule newSchedule = new Schedule(scheduleUpdate);
            scheduleRepository.save(newSchedule);
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
        scheduleRepository.deleteById(id);
        return "deleted";
    }

}
