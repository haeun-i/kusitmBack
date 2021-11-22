package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.dto.PushNotificationRequest;
import com.kusitms.kusitms5.dto.PushNotificationResponse;
import com.kusitms.kusitms5.dto.TokenDto;
import com.kusitms.kusitms5.dto.UserDto;
import com.kusitms.kusitms5.service.LikeService;
import com.kusitms.kusitms5.service.PushNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PushNotificationController {

    private PushNotificationService pushNotificationService;
    private LikeService likeService;

    public PushNotificationController(PushNotificationService pushNotificationService, LikeService likeService) {
        this.pushNotificationService = pushNotificationService;
        this.likeService = likeService;
    }

    // 특정 토픽에 대한 알림 반환
    /* curl -d '{"title":"Hello", "message":"The message...", "topic":"contactTopic"}' -H "Content-Type: application/json" -X POST http://localhost:8080/notification/topic */
    @PostMapping("/notification/topic")
    public ResponseEntity sendNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationWithoutData(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    // 해당 토큰을 가진 디바이스만 알림 반환
    /* 알림을 db에서 가져와서 저장*/
    /* curl -d '{"title":"Hey you!", "message":"Watch out!", "token":"cct00ebz8eg:APA91bFcTkFE_0Qafj6nWv5yHxqCLTyxAaqi4QzwsFNLP5M9G78X8Z5UMZTW004q1PUux63Ut-1WMGVToMNTdB3ZfO8lCZlc4lGpxm7LBdWfkhaUxdbpQ5xIO5cAb-w9H2dBLNHT7i-U", "topic": ""}' -H "Content-Type: application/json" -X POST http://localhost:8080/notification/token */
    @PostMapping("/notification/token")
    public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationToToken(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    //  Send a message to a specific topic with additional payload data.
    /* curl -d '{"title":"Hello", "message":"Data message", "topic":"contactTopic"}' -H "Content-Type: application/json" -X POST http://localhost:8080/notification/data */
    @PostMapping("/notification/data")
    public ResponseEntity sendDataNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotification(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    // default 값을 반환해준다.
    /* curl -H "Content-Type: application/json" -X GET http://localhost:8080/notification */
    @GetMapping("/notification")
    public ResponseEntity sendSampleNotification() {
        pushNotificationService.sendSamplePushNotification();
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    // 가게 내용 수정됨 -> 그 가게를 찜한 사람에게 수정 내용을 알림
    /*
    *  1. 가게 이름 post요청
    *  2. 가게 이름으로 id찾기 (생략가능)
    *  3. 찾은 가게와 연결된 좋아요 한 사용자 찾기
    *  4. 좋아요 한 사용자 토큰 알아보기
    *  5. 그 토큰을 가진 사람들에게 수정 내용 push */
    // 알림 내용 : 수정되었습니다..?

    // 해당 토큰을 가진 디바이스만 알림 반환
    /* 알림을 db에서 가져와서 저장*/
    /* curl -d '{"title":"Hey you!", "message":"Watch out!", "token":"cct00ebz8eg:APA91bFcTkFE_0Qafj6nWv5yHxqCLTyxAaqi4QzwsFNLP5M9G78X8Z5UMZTW004q1PUux63Ut-1WMGVToMNTdB3ZfO8lCZlc4lGpxm7LBdWfkhaUxdbpQ5xIO5cAb-w9H2dBLNHT7i-U", "topic": ""}' -H "Content-Type: application/json" -X POST http://localhost:8080/notification/token */
    @PostMapping("/notification/change-store")
    public ResponseEntity sendLikesNotification() {

        PushNotificationRequest request = new PushNotificationRequest(); // 뭐가 바뀌었는지

        // 점포이름
        String storeName = "점포이름";
        List<UserDto> userDtos = likeService.findLikeUser(storeName);
        // 유저의 토큰을 가져와야함. 토큰 -> 파이어베이스의 디바이스 토큰
        for (UserDto target :
                userDtos) {

           /* Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //String jwt = authentication*/
          //  TokenDto tokenDto = new TokenDto();

            request.setTitle("title");
            request.setMessage("message");
          //  request.setToken(tokenDto.getToken());
            pushNotificationService.sendPushNotificationToToken(request);
        }

        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

}