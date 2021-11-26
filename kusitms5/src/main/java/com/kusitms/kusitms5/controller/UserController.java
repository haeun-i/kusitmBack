package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.domain.User;
import com.kusitms.kusitms5.dto.ResponseDto;
import com.kusitms.kusitms5.dto.UserDto;
import com.kusitms.kusitms5.dto.StoreDto;
import com.kusitms.kusitms5.repository.StoreRepository;
import com.kusitms.kusitms5.response.BasicResponse;
import com.kusitms.kusitms5.response.CommonResponse;
import com.kusitms.kusitms5.service.LikeService;
import com.kusitms.kusitms5.service.StoreService;
import com.kusitms.kusitms5.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final LikeService likeService;
    private final StoreService storeService;

    public UserController(UserService userService, LikeService likeService, StoreService storeService) {
        this.userService = userService;
        this.likeService = likeService;
        this.storeService = storeService;
    }

    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/user");
    }

    // 회원가입 Controller (username, password, nickname, phone)
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<? extends BasicResponse> likes(@RequestParam("storeName") String storeName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Optional<User> user = userService.getUserWithAuthorities(id);

        Store store = storeService.findRealOne(storeName);

        likeService.likes(store.getStoreId(), user.get().getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/unlikes")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<? extends BasicResponse> unlikes(@RequestParam("storeName") String storeName){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Optional<User> user = userService.getUserWithAuthorities(id);

        Store store = storeService.findRealOne(storeName);

        likeService.unlikes(store.getStoreId(), user.get().getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/aboutme")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<? extends BasicResponse> aboutMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Optional<User> user = userService.getUserWithAuthorities(id);

        UserDto userdto = new UserDto(user.get());

        return ResponseEntity.ok(new CommonResponse<UserDto>(userdto));
    }

    @GetMapping("/store/like")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<? extends BasicResponse> likeList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Optional<User> user = userService.getUserWithAuthorities(id);

        List<StoreDto> likeStores = likeService.findLike(user.get().getUserId());
        return ResponseEntity.ok().body(new CommonResponse<List<StoreDto>>(likeStores));
    }

    // 아이디 중복확인 : 존재하면 true, 없으면 false
    @GetMapping("/check-duplicate/id/{username}")
    public ResponseEntity<? extends BasicResponse> vaildateId(@PathVariable String username) {
        if(userService.validateDuplicateUsername(username) == true) {
            ResponseDto r = new ResponseDto("true");
            return ResponseEntity.ok().body(new CommonResponse<ResponseDto>(r));
        }else {
            ResponseDto r = new ResponseDto("false");
            return ResponseEntity.ok().body(new CommonResponse<ResponseDto>(r));
        }
    }

    // 닉네임 중복확인 : 존재하면 true, 없으면 false
    @GetMapping("/check-duplicate/nickname/{nickname}")
    public ResponseEntity<? extends BasicResponse> vaildateNickname(@PathVariable String nickname) {
        if(userService.validateDuplicateNickname(nickname) == true) {
            ResponseDto r = new ResponseDto("true");
            return ResponseEntity.ok().body(new CommonResponse<ResponseDto>(r));
        }else {
            ResponseDto r = new ResponseDto("false");
            return ResponseEntity.ok().body(new CommonResponse<ResponseDto>(r));
        }
    }

    // push알림 테스트
    @GetMapping("/test/pushAlarm")
    public ResponseEntity<Boolean> testPuahAlarm(@PathVariable String nickname) {

        return ResponseEntity.ok(userService.validateDuplicateNickname(nickname));
    }

    @PutMapping("/modify-nickname/")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<? extends BasicResponse> modifyNickname(String nickname){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Optional<User> user = userService.getUserWithAuthorities(id);

        userService.modifyNickname(user.get(), nickname);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/modify-password/")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<? extends BasicResponse> modifyPassword(String password){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Optional<User> user = userService.getUserWithAuthorities(id);

        userService.modifyPassword(user.get(), password);

        return ResponseEntity.ok().body(new CommonResponse<String>("ok"));
    }

    @DeleteMapping("/delete/user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
    })
    public ResponseEntity<? extends BasicResponse> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Optional<User> user = userService.getUserWithAuthorities(id);

        userService.delete(user.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}