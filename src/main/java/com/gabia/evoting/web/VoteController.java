package com.gabia.evoting.web;

import com.gabia.evoting.domain.UserModel;
import com.gabia.evoting.service.AgendaService;
import com.gabia.evoting.service.VoteService;
import com.gabia.evoting.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VoteController extends AbstractController{

    private final VoteService voteService;

    @Operation(summary = "투표"
            , description = "사용자가 특정 안건에 투표합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created !!"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/vote")
    public ResponseEntity<VoteResponseDto> voteToAgenda(VoteRequestDto voteRequestDto){
        UserModel user = getUser();
        VoteResponseDto responseDto = voteService.vote(user,voteRequestDto);

        if(responseDto.getVoteStatus() == "fail")
            return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
        else{
            return new ResponseEntity(responseDto, HttpStatus.CREATED);
        }
    }

    @Operation(summary = "안건의 현황 조회"
            , description = "안건의 투표 현황을 조회합니다..")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found !!"),
    })
    @GetMapping("/agendas/{id}/votes")
    public ResponseEntity<AgendaVoteResponseDto> getAgendaVote(@PathVariable("id") Long id){
        return new ResponseEntity(voteService.getVoteStatus(id, isAdminUser()), HttpStatus.OK);
    }
}
