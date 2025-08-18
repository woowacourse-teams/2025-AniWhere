package com.yagubogu.game.controller;

import com.yagubogu.auth.dto.MemberClaims;
import com.yagubogu.game.dto.GameResponse;
import com.yagubogu.game.dto.GameResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Game", description = "경기 조회 관련 API")
@RequestMapping("/api/games")
public interface GameControllerInterface {

    @Operation(summary = "특정 날짜의 경기 목록 조회", description = "지정한 날짜에 해당하는 경기 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "경기 조회 성공"),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음"),
            @ApiResponse(responseCode = "422", description = "미래 날짜는 조회할 수 없음")
    })
    @GetMapping
    ResponseEntity<GameResponse> findGamesByDate(
            @Parameter(hidden = true) MemberClaims memberClaims,
            @RequestParam LocalDate date
    );

    @Operation(summary = "특정 경기의 스코어보드 조회", description = "지정한 경기에 해당하는 경기 결과 스코어보드를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "경기 결과 스코어보드 성공"),
            @ApiResponse(responseCode = "404", description = "스코어보드 찾을 수 없음"),
    })
    @GetMapping("/{gameId}")
    ResponseEntity<GameResultResponse> findGameScoreBoard(
            @PathVariable long gameId
    );
}
