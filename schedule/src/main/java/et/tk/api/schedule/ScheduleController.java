package et.tk.api.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/{movieId}/{hallId}")
    public ResponseEntity<String> createSchedule(@PathVariable String movieId, @PathVariable String hallId, @RequestBody Schedule schedule) {
        String status = scheduleService.createSchedule(movieId, hallId, schedule);
        if (Objects.equals(status, "movie"))
            return new ResponseEntity<>("Movie does not exist", HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "hall"))
            return new ResponseEntity<>("Hall does not exist", HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "booked"))
            return new ResponseEntity<>("Hall already booked for the specified date and time", HttpStatus.CONFLICT);
        else
            return new ResponseEntity<>("Schedule created",HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<Schedule>> getAllSchedules() {
        return new ResponseEntity<>(scheduleService.getAllSchedules(), HttpStatus.OK);
    }

    @GetMapping("/hall/{hallId}")
    public ResponseEntity<List<Schedule>> getSchedulesInHall(@PathVariable String hallId) {
        List<Schedule> result = scheduleService.getSchedulesInHall(hallId);
        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Schedule>> getSchedulesByMovie(@PathVariable String movieId) {
        List<Schedule> result = scheduleService.getSchedulesByMovie(movieId);
        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable String id) {
        Schedule result = scheduleService.getScheduleById(id);
        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSchedule(@PathVariable String id, @RequestBody Schedule schedule) {
        String status = scheduleService.updateSchedule(id, schedule);
        if (Objects.equals(status, "schedule"))
            return new ResponseEntity<>("Schedule not found", HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "hallDoesNotExist"))
            return new ResponseEntity<>("Hall does not exist", HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "movieDoesNotExist"))
            return new ResponseEntity<>("Movie does not exist", HttpStatus.NOT_FOUND);
        else if (Objects.equals(status, "hall"))
            return new ResponseEntity<>("Hall occupied by another schedule at that specific time", HttpStatus.FOUND);
        else
            return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable String id) {
        String status = scheduleService.deleteSchedule(id);
        if (Objects.equals(status, "deleted"))
            return new ResponseEntity<>(HttpStatus.OK);
        else if (Objects.equals(status, "ticket"))
            return new ResponseEntity<>("Tickets are already associated with this schedule",HttpStatus.BAD_REQUEST);
        else if (Objects.equals(status, "ticket info"))
            return new ResponseEntity<>("Tickets service is not responding",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("Schedule not found",HttpStatus.NOT_FOUND);
    }

}
