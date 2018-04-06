package com.mycompany.airportservice;

import com.mycompany.airportservice.exceptions.WebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")

public class ScheduleController{
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    AirplaneRepository airplaneRepository;

    @GetMapping("/schedules")
    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    @GetMapping("/schedules/{id}")
    public Schedule getScheduleById(@PathVariable(value = "id") Long postId){
        return scheduleRepository.findById(postId).orElseThrow(() -> new WebException("GET api/schedules/id", "- no schedule found"));
    }

    @PostMapping("/schedules")
    public Schedule createSchedule(@Valid @RequestBody Schedule schedule, HttpServletResponse response){
        if (scheduleRepository.existsById(schedule.getId())){
            throw new WebException("POST api/schedules", "- schedule exists with this id: " + schedule.getId());
        }
        if (schedule.getStartPort() == null || schedule.getDestination() == null ||schedule.getAirplaneId() == 0){
            throw new WebException("POST api/schedules", "- missing fields");
        }
        airplaneRepository.findById(schedule.getAirplaneId()).orElseThrow(() -> new WebException("POST api/schedules", "- no such airplane with id: " + schedule.getAirplaneId()));
        response.setStatus(201);
        Schedule scheduleNew = scheduleRepository.save(schedule);
        response.addHeader("Location", "api/schedules/" + scheduleNew.getId());
        return scheduleNew;
    }

    @PutMapping("/schedules/{id}")
    public Schedule updateSchedule(@PathVariable(value = "id") Long scheduleId, @Valid @RequestBody Schedule newSchedule, HttpServletResponse response){
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new WebException("PUT api/schedules/id", "- no such schedule"));
        if (newSchedule.getAirplaneId() == 0 || newSchedule.getStartPort() == null || newSchedule.getDestination() == null){
            throw new WebException("PUT api/schedules", "- missing fields");
        }
        airplaneRepository.findById(schedule.getAirplaneId()).orElseThrow(() -> new WebException("PUT api/schedules", "- airplane with id: " + schedule.getAirplaneId() + " does not exist."));
        schedule.setStartPort(newSchedule.getStartPort());
        schedule.setDestination(newSchedule.getDestination());
        schedule.setAirplaneId(newSchedule.getAirplaneId());
        response.setStatus(201);
        response.addHeader("Location", "api/schedules/" + scheduleId);
        return scheduleRepository.save(schedule);
    }
    @PatchMapping("/schedules/{id}")
    public Schedule patchSchedule(@PathVariable(value = "id") Long scheduleId, @Valid @RequestBody Schedule newSchedule, HttpServletResponse response) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new WebException("PATCH api/sechedules/id", "- no such schedule"));
            if (newSchedule.getStartPort() == null && newSchedule.getDestination() == null && newSchedule.getAirplaneId() == 0) {
                throw new WebException("PATCH api/schedules", "empty path request body");
            }
            if(newSchedule.getStartPort() != null){
                schedule.setStartPort(newSchedule.getStartPort());
            }
            if(newSchedule.getDestination() != null){
                schedule.setDestination(newSchedule.getDestination());
            }
            if(newSchedule.getAirplaneId() != 0){
                schedule.setAirplaneId(newSchedule.getAirplaneId());
            }
            response.setStatus(202);
            response.addHeader("Location", "api/schedules/" + scheduleId);
            return scheduleRepository.save(schedule);
    }
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable(value = "id") Long scheduleId){
        Schedule post = scheduleRepository.findById(scheduleId).orElseThrow(() -> new WebException("DELETE api/schedules/id", "- airplane with id: " + scheduleId + " does not exist."));
        scheduleRepository.delete(post);
        return ResponseEntity.noContent().build();
    }
}