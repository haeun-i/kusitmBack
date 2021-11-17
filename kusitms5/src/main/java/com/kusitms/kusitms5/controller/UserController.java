package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.domain.User;
import com.kusitms.kusitms5.dto.UserDto;
import com.kusitms.kusitms5.dto.StoreDto;
import com.kusitms.kusitms5.response.BasicResponse;
import com.kusitms.kusitms5.response.CommonResponse;
import com.kusitms.kusitms5.service.LikeService;
import com.kusitms.kusitms5.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final LikeService likeService;


    public UserController(UserService userService, LikeService likeService) {
        this.userService = userService;
        this.likeService = likeService;
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

    @PostMapping("/likes")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
//    })
    public ResponseEntity<? extends BasicResponse> likes(@RequestParam("storeId") Long storeId){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String id = authentication.getName();
//        System.out.println(authentication);
//        Optional<User> user = userService.getUserWithAuthorities(id);
//
//        likeService.likes(storeId, user.get().getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/unlikes")
    public ResponseEntity<? extends BasicResponse> unlikes(@RequestParam("storeId") Long storeId){
        likeService.unlikes(storeId, 6L);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/store/like")
    public ResponseEntity<? extends BasicResponse> likeList(@RequestParam("userId") Long userId){
        List<StoreDto> likeStores = likeService.findLike(userId);
        return ResponseEntity.ok().body(new CommonResponse<List<StoreDto>>(likeStores));
    }

    // 아이디 중복확인 : 존재하면 true, 없으면 false
    @GetMapping("/check-duplicate/id/{username}")
    public ResponseEntity<Boolean> vaildateId(@PathVariable String username) {
        return ResponseEntity.ok(userService.validateDuplicateUsername(username));
    }

    // 닉네임 중복확인 : 존재하면 true, 없으면 false
    @GetMapping("/check-duplicate/nickname/{nickname}")
    public ResponseEntity<Boolean> vaildateNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.validateDuplicateNickname(nickname));
    }

}