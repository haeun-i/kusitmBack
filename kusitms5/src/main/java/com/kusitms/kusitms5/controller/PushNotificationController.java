package com.kusitms.kusitms5.controller;

import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.domain.User;
import com.kusitms.kusitms5.dto.DeviceTokenRequest;
import com.kusitms.kusitms5.dto.PushNotificationRequest;
import com.kusitms.kusitms5.dto.PushNotificationResponse;
import com.kusitms.kusitms5.dto.StoreDto;
import com.kusitms.kusitms5.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// send push notification to firebase
@RestController
public class PushNotificationController {

    private PushNotificationService pushNotificationService;

    private final StoreService storeService;
    private final LikeService likeService;
    private final UserService userService;
    private final DeviceService deviceService;

    public PushNotificationController(PushNotificationService pushNotificationService, StoreService storeService, LikeService likeService, UserService userService, DeviceService deviceService) {
        this.pushNotificationService = pushNotificationService;
        this.storeService = storeService;
        this.likeService = likeService;
        this.userService = userService;
        this.deviceService = deviceService;
    }

    @PostMapping("/notification/topic")
    public ResponseEntity sendNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationWithoutData(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    @PostMapping("/notification/token")
    public ResponseEntity sendTokenNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationToToken(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    @PostMapping("/notification/data")
    public ResponseEntity sendDataNotification(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotification(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    @PostMapping("/notification/data/customdatawithtopic")
    public ResponseEntity sendDataNotificationCustom(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationCustomDataWithTopic(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }
    @PostMapping("/notification/data/customdatawithtopicjson")
    public ResponseEntity sendDataNotificationCustomWithSpecificJson(@RequestBody PushNotificationRequest request) {
        pushNotificationService.sendPushNotificationCustomDataWithTopicWithSpecificJson(request);
        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }

    /* refresh토큰이 생성될 때마다 무조건 DB에 update가 되도록 하는 요청
    *  -> 디바이스가 생성 될 때 토큰이 만들어짐, 그때 사용자id와 토큰의 정보를 요청해야함, 그러면 업데이트 시킴*/
    @PostMapping("/notification/tokenUpdate")
    public String updateToken(@RequestBody DeviceTokenRequest request) {

        // 1. username으로 바로 userid 찾기
        User user = userService.findByUsername(request.getUsername()).get();
        // 2. device테이블에서 userid에 해당하는 token을 업데이트
        String newJwt = deviceService.UpdateDeviceToken(user.getUserId(), user.getDeviceToken().getToken());

        return newJwt;
    }


    /*
    * 1. 주인이 가게 내용을 수정한다.
    * 2. 수정한 내용을 request에 저장하고 요청한다.(POST)
    * 3. 수정한 내용을 DB에 update한다.
    * 4. 수정한 내용을 연결된 디바이스에 푸시한다.
    *       1) store과 연결된 user을 찾고 리스트로 저장한다.
    *       2) user와 연결된 디바이스의 토큰을 찾는다.
    *       3) 해당 토큰으로 푸시알림을 보낸다.
    */

    // 1. 주인이 가게 내용을 수정한다.
    @PostMapping("/notification/updateStore")
    public ResponseEntity sendUpdateNotification(@RequestBody StoreDto storeDto) { // 2.수정한 내용을 request에 저장하고 요청한다.(POST)

        // 3. 수정한 내용을 DB에 update한다.
        storeService.updateStore(storeDto); // get(0)부분 수정필요
        String updateMessage = storeDto.getStoreName() + "을 확인해보세요!";
        System.out.println("message : " + updateMessage);

        // 4-1) store과 연결된 user을 찾고 리스트로 저장한다.
        List<User> userList = likeService.findLikeUser(storeDto.getStoreName()); // get(0)부분 수정
        System.out.println("좋아요한 user 수 : " + userList.size());

        for (User user:
                userList) {
            if(user.getDeviceToken().getToken() != null) {
                PushNotificationRequest request = new PushNotificationRequest();
                request.setTitle("알림");
                request.setMessage(updateMessage);
                // 4-2) user와 연결된 디바이스의 토큰을 찾는다.
                request.setToken(user.getDeviceToken().getToken());
                //request.setTopic("");

                // 4-3) 해당 토큰으로 푸시알림을 보낸다.
                pushNotificationService.sendPushNotificationToToken(request);
            }
        }

        return new ResponseEntity<>(new PushNotificationResponse(HttpStatus.OK.value(), "Notification has been sent."), HttpStatus.OK);
    }


    public void sendAutomaticNotification(){
        PushNotificationRequest request = new PushNotificationRequest();
        request.setTopic("global");
        pushNotificationService.sendPushNotificationCustomDataWithTopicWithSpecificJson(request);
    }
}