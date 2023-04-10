package et.tk.api.schedule;

import et.tk.api.schedule.dto.ScheduleGet;
import et.tk.api.schedule.dto.ScheduleMinimalist;
import et.tk.api.schedule.dto.SchedulePost;
import et.tk.api.schedule.dto.ScheduleUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/{movieId}/{hallId}")
    public ResponseEntity<String> createSchedule(@PathVariable String movieId, @PathVariable String hallId, @RequestBody SchedulePost schedulePost) {
        String status = scheduleService.createSchedule(movieId, hallId, schedulePost);
        if (status == "movie")
            return new ResponseEntity<>("Movie does not exist", HttpStatus.NOT_FOUND);
        else if (status == "hall")
            return new ResponseEntity<>("Hall does not exist", HttpStatus.NOT_FOUND);
        else if (status == "booked")
            return new ResponseEntity<>("Hall already booked for the specified date and time", HttpStatus.CONFLICT);
        else
            return new ResponseEntity<>("Schedule created",HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <List<ScheduleMinimalist>> getAllSchedules() {
        List<ScheduleMinimalist> scheduleMinimalists = scheduleService.getAllSchedules();
        if (scheduleMinimalists == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(scheduleMinimalists, HttpStatus.FOUND);
    }

    @GetMapping("/hall/{hallId}")
    public ResponseEntity<List<ScheduleGet>> getSchedulesInHall(@PathVariable String hallId) {
        List<ScheduleGet> result = scheduleService.getSchedulesInHall(hallId);
        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ScheduleGet>> getSchedulesByMovie(@PathVariable String movieId) {
        List<ScheduleGet> result = scheduleService.getSchedulesByMovie(movieId);
        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleGet> getScheduleById(@PathVariable String id) {
        ScheduleGet result = scheduleService.getScheduleById(id);
        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity<Schedule> getScheduleByIdForTicket(@PathVariable String id) {
        Schedule result = scheduleService.getScheduleByIdForTicket(id);
        if (result == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSchedule(@PathVariable String id, @RequestBody ScheduleUpdate scheduleUpdate) {
        String status = scheduleService.updateSchedule(id, scheduleUpdate);
        if (status == "schedule")
            return new ResponseEntity<>("Schedule not found", HttpStatus.NOT_FOUND);
        else if (status == "hallDoesNotExist")
            return new ResponseEntity<>("Hall does not exist", HttpStatus.NOT_FOUND);
        else if (status == "movieDoesNotExist")
            return new ResponseEntity<>("Movie does not exist", HttpStatus.NOT_FOUND);
        else if (status == "hall")
            return new ResponseEntity<>("Hall occupied by another schedule at that specific time", HttpStatus.FOUND);
        else
            return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable String id) {
        String status = scheduleService.deleteSchedule(id);
        if (status == "deleted")
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
