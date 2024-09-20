package com.example.L10minordemo.Service;


import com.example.L10minordemo.Exception.BadRequestException;
import com.example.L10minordemo.Exception.NotFoundException;
import com.example.L10minordemo.dto.AddressDto;
import com.example.L10minordemo.dto.VisitDto;
import com.example.L10minordemo.dto.VisitorDTO;
import com.example.L10minordemo.entity.*;
import com.example.L10minordemo.enums.VisitStatus;
import com.example.L10minordemo.repo.AddressRepo;
import com.example.L10minordemo.repo.FlatRepo;
import com.example.L10minordemo.repo.VisitRepo;
import com.example.L10minordemo.repo.VisitorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class GetKeeperService
{

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private VisitorRepo visitorRepo;

    @Autowired
    private FlatRepo flatRepo;

    @Autowired
    private VisitRepo visitRepo;

    @Autowired
    private RedisTemplate<String,VisitorDTO>redisTemplate;

    public Long createVisitor(VisitorDTO visitorDTO){
        Visitor visitor = Visitor.builder()
                .email(visitorDTO.getEmail())
                .name(visitorDTO.getName())
                .phone(visitorDTO.getPhone())
                .idNumber(visitorDTO.getIdNumber())
                .build();
        if(visitorDTO.getAddress() != null){
            AddressDto addressDto = visitorDTO.getAddress();
            Address address = Address.builder()
                    .line1(addressDto.getLine1())
                    .line2(addressDto.getLine1())
                    .pincode(addressDto.getPincode())
                    .city(addressDto.getCity())
                    .country(addressDto.getCountry())
                    .build();
            visitor.setAddress(address);
        }
//        entityManager.persist(visitor);
        visitor = visitorRepo.save(visitor);
        return visitor.getId();
    }

    public VisitorDTO getByIdNumber(String idNumber){
        String key = "visitor:"+idNumber;
        VisitorDTO visitorDTO = redisTemplate.opsForValue().get(key);
        if(visitorDTO!=null)
        {
            return visitorDTO;
        }
        Visitor visitor = visitorRepo.findByidNumber(idNumber);
        if(visitor != null){
            long id = visitor.getAddress().getId();
            Address address = addressRepo.findById(id);
            AddressDto addressDto = AddressDto.builder()
                    .line1(address.getLine1())
                    .line2(address.getLine2())
                    .pincode(address.getPincode())
                    .city(address.getCity())
                    .country(address.getCountry()).build();

            visitorDTO = VisitorDTO.builder()
                    .email(visitor.getEmail())
                    .name(visitor.getName())
                    .phone(visitor.getPhone())
                    .idNumber(visitor.getIdNumber())
                    .address(addressDto)
                    .build();
            redisTemplate.opsForValue().set(key,visitorDTO,60, TimeUnit.MINUTES);
        }
        else {

            throw new NotFoundException();
        }
        return visitorDTO;
    }


    public Long createVisit(VisitDto visitDto){
        Visitor visitor = visitorRepo.findByidNumber(visitDto.getIdNumber());
        if(visitor == null){
            throw new BadRequestException("Visitor does not exist");
        }
        Flat flat = flatRepo.findByNumber(visitDto.getFlatNumber());
        if(flat == null){
            throw new BadRequestException("Flat does not Exist");
        }
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Visit visit = Visit.builder()
                .imageUrl(visitDto.getUrlOfImage())
                .noOfPeople(visitDto.getNoOfPeople())
                .purpose(visitDto.getPurpose())
                .visitor(visitor)
                .flat(flat)
                .status(VisitStatus.WAITING)
               .createdBy(user)
                .build();
        visit = visitRepo.save(visit);
        return visit.getId();
    }

    public String markEntry(Long id){
        Visit visit = visitRepo.findById(id).get();
        if(visit == null){
            throw new BadRequestException("Visit does not exist");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(visit.getCreatedBy()))
        {
            throw new BadRequestException("This GateKeeper is Different");
        }

        if(visit.getStatus().equals(VisitStatus.APPROVED)){
            visit.setInTime(new Date());
            visitRepo.save(visit);
            return "Done";
        }
        else {
            throw new BadRequestException("can not mark this Entry");

        }

    }

    public String markExit(Long id){
        Visit visit = visitRepo.findById(id).get();
        if(visit == null){
            throw new BadRequestException("Visit does not exist");
        }
        if(visit.getStatus().equals(VisitStatus.APPROVED) && visit.getInTime() != null){
            visit.setOutTime(new Date());
            visit.setStatus(VisitStatus.COMPLETED);
            visitRepo.save(visit);
            return "Done";
        }
        else {
            throw new BadRequestException("can not mark this exit");

        }
    }
}