package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.domain.User;
import com.kusitms.kusitms5.dto.LoginDto;
import com.kusitms.kusitms5.dto.TokenDto;
import com.kusitms.kusitms5.jwt.JwtFilter;
import com.kusitms.kusitms5.jwt.TokenProvider;
import com.kusitms.kusitms5.response.BasicResponse;
//import com.kusitms.kusitms5.service.CertificationService;
import com.kusitms.kusitms5.service.CertificationService;
import com.kusitms.kusitms5.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    public AuthController(TokenProvider tokenProvider,
                          AuthenticationManagerBuilder authenticationManagerBuilder,
                          UserService userService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
    }

    // 로그인 : 토큰 생성
    @PostMapping("/authenticate") // /api/authenticate
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        String id = authentication.getName();
        User user = userService.getUserWithAuthorities(id).get();

        return new ResponseEntity<>(new TokenDto(jwt, user.getUserClick()), httpHeaders, HttpStatus.OK);
    }

    @PutMapping("/logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<? extends BasicResponse> logout(@Valid @RequestParam int cnt) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        User user = userService.getUserWithAuthorities(id).get();

        userService.saveCnt(cnt, user.getUserId());

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
