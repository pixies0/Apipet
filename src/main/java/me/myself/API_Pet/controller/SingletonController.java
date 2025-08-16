package me.myself.API_Pet.controller;


import me.myself.API_Pet.shared.AuditorLog;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/design/singleton")
public class SingletonController {

    @PostMapping("/log")
    public String log(@RequestParam String who, @RequestParam String action) {
        AuditorLog.INSTANCE.log(who, action, "ok");
        return "logged";
    }

    @GetMapping("/events")
    public Object events() {
        return AuditorLog.INSTANCE.getEvents();
    }
}
