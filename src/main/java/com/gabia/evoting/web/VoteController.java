package com.gabia.evoting.web;

import com.gabia.evoting.domain.UserModel;
import com.gabia.evoting.service.VoteService;
import com.gabia.evoting.web.dto.AgendaResponseDto;
import com.gabia.evoting.web.dto.ResponseMessageDto;
import com.gabia.evoting.web.dto.VoteRequestDto;
import com.gabia.evoting.web.dto.VoteResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VoteController extends AbstractController{

    private final VoteService voteService;

    @Operation(summary = "투표"
            , description = "사용자가 특정 안건에 투표합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "OK !!"),
    })
    @PostMapping("/vote")
    public ResponseMessageDto<VoteResponseDto> voteToAgenda(VoteRequestDto voteRequestDto){
        UserModel user = getUser();
        VoteResponseDto responseDto = voteService.vote(user,voteRequestDto);
//        VoteResponseDto responseDto = voteService.vote(voteRequestDto);

        if(responseDto.getVoteStatus() == "fail")
            return failureMessage(responseDto);
        else{
            return successMessage(responseDto);
        }
    }
}
