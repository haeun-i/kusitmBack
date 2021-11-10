package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.domain.User;
import com.kusitms.kusitms5.dto.UserDto;
import com.kusitms.kusitms5.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/user");
    }

    // 회원가입 Controller (username, password, nickname)
    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    // USER와 ADMIN 권한을 가진 사람만 이 api호출 가능
    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')") // User와 ADMIN 권한 모두 호출 가능하도록
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    // ADMIN 권한만 호출하는 Contorller : ADMIN권한(ADMIN으로 발급받은 토큰)이 있어야 호출가능하다(호출대상은 관련 없음)
    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')") // ADMIN권한만
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }
}