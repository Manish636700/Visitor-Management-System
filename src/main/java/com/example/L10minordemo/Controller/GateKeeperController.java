package com.example.L10minordemo.Controller;


import com.example.L10minordemo.Service.GetKeeperService;
import com.example.L10minordemo.dto.VisitDto;
import com.example.L10minordemo.dto.VisitorDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/Gate")
public class GateKeeperController {

    private static Logger logger = LoggerFactory.getLogger(GateKeeperController.class);

    @Autowired
    private GetKeeperService gateKeeperService;

    @PostMapping("/createVisitor")
    ResponseEntity<Long> createUser(@RequestBody @Valid VisitorDTO visitorDTO) {
        return ResponseEntity.ok(gateKeeperService.createVisitor(visitorDTO));
    }

    @GetMapping("/get")
    ResponseEntity<VisitorDTO>get(@RequestParam String idNumber)
    {
        VisitorDTO visitordto = gateKeeperService.getByIdNumber(idNumber);
        if(visitordto==null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(visitordto);
    }

    @PostMapping("/createVisit")
    ResponseEntity<Long>create(@RequestBody @Valid VisitDto visitDto)
    {
        return ResponseEntity.ok(gateKeeperService.createVisit(visitDto));
    }

    @PostMapping("/markEntry/{id}")
    ResponseEntity<String> markEntry(@PathVariable Long id){
        return ResponseEntity.ok(gateKeeperService.markEntry(id));
    }

    @PostMapping("/markExit/{id}")
    ResponseEntity<String> markExit(@PathVariable Long id){
        return ResponseEntity.ok(gateKeeperService.markExit(id));
    }

    @PostMapping("/image-upload")
    public ResponseEntity<String>uploadVisitorImage(@RequestParam("file")MultipartFile file)
    {
        String fileName = UUID.randomUUID()+"_"+file.getOriginalFilename();
        String publicUrl = "/content/"+fileName;
        String uploadPath = "D:/java gfg importent files for projects/temp/image/"+fileName;
        try {
            file.transferTo(new File(uploadPath));
        } catch (IOException e) {
            logger.error("Exception while uploading image: {}",e);
            return ResponseEntity.ok("Exception while uploading image");
        }
        return ResponseEntity.ok(publicUrl);
    }
}
