package com.gabia.evoting.web;

import com.gabia.evoting.domain.AgendaModel;
import com.gabia.evoting.service.AgendaService;
import com.gabia.evoting.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AgendaController extends AbstractController{

    private final AgendaService agendaService;
    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @Operation(summary = "안건 조회"
            , description = "id 로 넘겨 받은 안건에 대해 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    @GetMapping("/agendas/{id}")
    public ResponseEntity<AgendaResponseDto> findAgendaById(@PathVariable("id") Long id){
        AgendaResponseDto responseDto = new AgendaResponseDto(agendaService.findById(id));
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "안건 리스트조회"
            , description = "모든 안건에 대해 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping("/agendas")
    public ResponseEntity<List<AgendaResponseDto>> findAllAgenda(){
        return new ResponseEntity(agendaService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "안건 생성"
            , description = "안건을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created !!"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/agenda")
    public ResponseEntity<AgendaResponseDto> createAgenda(AgendaRequestDto requestDto){
        AgendaResponseDto responseDto = new AgendaResponseDto(agendaService.save(requestDto));
        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "안건 상태 변경"
            , description = "안건의 상태를 시작/종료로 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content !!"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found !!"),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/agendas/{id}")
    public ResponseEntity<Void> updateAgenda(@PathVariable("id") Long id, AgendaChangeRequestDto requestDto){
        AgendaModel.Status status = requestDto.getStatus();
        Long agendaId = id;

        if(status == AgendaModel.Status.START){
            agendaService.startAgenda(agendaId);
        } else if (status == AgendaModel.Status.END) {
            agendaService.endAgenda(agendaId);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @Operation(summary = "안건의 종료 예약"
            , description = "안건의 종료시간을 예약합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content !!"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found !!"),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/agendas/{id}/schedule/{time}")
    public ResponseEntity<Void> endSchedule(@PathVariable("id") Long id,
                                            @PathVariable("time")
                                            @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
                                            LocalDateTime endTime){

        Instant instant = endTime.toInstant(ZoneOffset.UTC);
        long delay = Date.from(instant).getTime() - (System.currentTimeMillis() + 32400000); //9시간 차이

        if(delay <= 0){
            throw new RuntimeException("종료 예약 시간이 현재 시간보다 빠릅니다 EndTime :" + endTime);
        }

        AgendaModel agenda = agendaService.findById(id);

        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                agendaService.endAgenda(id);
                System.out.println("agenda " + id + " is ended!");
                System.out.println("Task executed at : " + new Date());
            }
        }, delay, TimeUnit.MILLISECONDS);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }
}
