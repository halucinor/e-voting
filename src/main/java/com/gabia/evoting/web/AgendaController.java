package com.gabia.evoting.web;

import com.gabia.evoting.domain.AgendaModel;
import com.gabia.evoting.schedule.TaskDefinition;
import com.gabia.evoting.schedule.TaskDefinitionBean;
import com.gabia.evoting.schedule.TaskSchedulingService;
import com.gabia.evoting.service.AgendaService;
import com.gabia.evoting.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AgendaController extends AbstractController{

    private final AgendaService agendaService;
    private final TaskSchedulingService taskSchedulingService;
    private final TaskDefinitionBean taskDefinitionBean;


    @Operation(summary = "안건 조회"
            , description = "id 로 넘겨 받은 안건에 대해 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping("/agendas/{id}")
    public ResponseMessageDto<AgendaResponseDto> findAgendaById(@PathVariable("id") Long id){
        return successMessage(agendaService.findById(id));
    }

    @Operation(summary = "안건 리스트조회"
            , description = "모든 안건에 대해 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })
    @GetMapping("/agendas")
    public ResponseMessageDto<List<AgendaResponseDto>> findAllAgenda(){
        return successMessage(agendaService.findAll());
    }

    @Operation(summary = "안건 생성"
            , description = "안건을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "OK !!"),
    })
    @PostMapping("/agenda")
    public ResponseMessageDto<AgendaResponseDto> createAgenda(AgendaRequestDto requestDto){
        return successMessage(new AgendaResponseDto(agendaService.save(requestDto)));
    }

    @Operation(summary = "안건 상태 변경"
            , description = "안건의 상태를 시작/종료로 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
    })

    @PatchMapping("/agendas")
    public ResponseMessageDto<AgendaResponseDto> updateAgenda(@RequestBody AgendaChangeRequestDto requestDto){
        AgendaModel.Status status = requestDto.getStatus();
        Long agendaId = requestDto.getId();

        AgendaModel changedAgenda = null;

        if(status == AgendaModel.Status.START){
            changedAgenda = agendaService.startAgenda(agendaId);
        } else if (status == AgendaModel.Status.END) {
            changedAgenda = agendaService.endAgenda(agendaId);
        }
        return successMessage(changedAgenda);
    }

    @Operation(summary = "안건의 종료 시점을 예약합니다."
            , description = "안건의 상태를 주어진 시간에 종료하도록 스케쥴합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Scheduled"),
    })
    @PatchMapping(path="/agendas/schedule")
    public void scheduleTask(@RequestBody AgendaEndRequestDto requestDto) {
        taskDefinitionBean.setAgendaEndRequestDto(requestDto);
        taskSchedulingService.scheduleATask(UUID.randomUUID().toString(), taskDefinitionBean, requestDto.getEndDate());
    }

    @GetMapping(path="/remove/{jobid}")
    public void removeJob(@PathVariable String jobId) {
        taskSchedulingService.removeScheduledTask(jobId);
    }
}
