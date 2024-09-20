package com.example.L10minordemo.Controller;

import com.example.L10minordemo.Service.ResidentService;
import com.example.L10minordemo.dto.AllVisitByResponseBody;
import com.example.L10minordemo.dto.VisitDto;
import com.example.L10minordemo.entity.User;
import com.example.L10minordemo.enums.VisitStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resident")
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @PostMapping("/actOnVisit/{id}")
    public ResponseEntity<String> actOnVisit(@PathVariable Long id, @RequestParam VisitStatus visitStatus) throws Exception {
        return ResponseEntity.ok(residentService.updateVisit(visitStatus,id));
    }
    @GetMapping("/pendingVisits")
    public ResponseEntity<List<VisitDto>> getPendingVisits(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(residentService.getPendingVisits(user.getId()));
    }
    @GetMapping("/allVisitsPage")
    public ResponseEntity<AllVisitByResponseBody> getPendingVisits(@AuthenticationPrincipal User user, @RequestParam Integer pageNo, @RequestParam Integer pageSize){
        Pageable pageable = Pageable.ofSize(pageSize)
                .withPage(pageNo);
        return ResponseEntity.ok(residentService.getAllVisits(user.getId(),pageable));
    }
    /*

    @GetMapping("/pendingVisits")
    public ResponseEntity<List<VisitDto>> getPendingVisits(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(residentService.getPendingVisits(user.getId()));
    }
    @GetMapping("/allVisitsPage")
    public ResponseEntity<AllVisitByResponseBody> getPendingVisits(@AuthenticationPrincipal User user, @RequestParam Integer pageNo, @RequestParam Integer pageSize){
        Pageable pageable = Pageable.ofSize(pageSize)
                .withPage(pageNo);
        return ResponseEntity.ok(residentService.getAllVisits(user.getId(),pageable));
    }
    */

}