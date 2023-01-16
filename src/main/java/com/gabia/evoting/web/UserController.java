package com.gabia.evoting.web;

import com.gabia.evoting.auth.jwt.impl.JwtAuthenticationToken;
import com.gabia.evoting.repository.UserRepository;
import com.gabia.evoting.service.AuthService;
import com.gabia.evoting.service.UserService;
import com.gabia.evoting.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController extends AbstractController{

    private final AuthService authService;

    private final UserService userService;

    @Operation(summary = "로그인"
            , description = "Email 와 Password로 로그인 작업을 수행하고 JWT를 넘겨받습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "401", description = "Unauthorized !!"),
    })
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtResponseDto> login(@RequestBody JwtRequestDto request) {
        try {
            return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new JwtResponseDto(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
    @Operation(summary = "회원가입"
            , description = "Email과 Password로 회원가입을 수행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created !!"),
            @ApiResponse(responseCode = "409", description = "Conflict !!"),
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signup(@RequestBody UserSignupRequestDto request) {
        authService.signup(request);
        return new ResponseEntity<>(request.getEmail(), HttpStatus.CREATED);
    }


    @Operation(summary = "사용자의 의결권 수 변경"
            , description = "사용자의 의결권 수 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No Content !!"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found !!"),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/api/users/{id}/vote")
    public ResponseEntity<Void> setVote(@PathVariable("id") Long id, UserVoteCountDto requestDto){
        userService.updateCount(id,requestDto.getVoteCnt());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
