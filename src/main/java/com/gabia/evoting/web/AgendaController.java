package com.gabia.evoting.web;

import com.gabia.evoting.domain.AgendaModel;
import com.gabia.evoting.service.AgendaService;
import com.gabia.evoting.web.dto.AgendaChangeRequestDto;
import com.gabia.evoting.web.dto.AgendaRequestDto;
import com.gabia.evoting.web.dto.AgendaResponseDto;
import com.gabia.evoting.web.dto.ResponseMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AgendaController extends AbstractController{

    private final AgendaService agendaService;

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

    @PatchMapping("/agendas/{id}")
    public ResponseMessageDto<AgendaResponseDto> updateAgenda(@PathVariable("id") Long id, AgendaChangeRequestDto requestDto){
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
}
