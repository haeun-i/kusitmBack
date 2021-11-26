package com.kusitms.kusitms5.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.kusitms.kusitms5.domain.*;
import com.kusitms.kusitms5.dto.UserDto;
import com.kusitms.kusitms5.repository.*;
import com.kusitms.kusitms5.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ModifyRepository modifyRepository;
    private final PasswordEncoder passwordEncoder;
    private final QuestionRepository questionRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       StoreRepository storeRepository, QuestionRepository questionRepository,
                       ModifyRepository modifyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.storeRepository = storeRepository;
        this.modifyRepository = modifyRepository;
        this.questionRepository = questionRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User signup(UserDto userDto) {// 회원가입 : 이미 저장되어있는지 확인해봄
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        // 없다면, 권한정보를 만듦(권한이 ROLE USER)
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        // user정보 만듦
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .phone(userDto.getPhone())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userRepository.save(user); // user와 권한정보 저장
    }

    // username을 파라미터로 받아서 그 기준으로 유저, 권한정보를 가져옴
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    // 현재 security context에 저장에 되어있는 username의 정보만 가져옴
    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }

    // 중복 회원 검증(username)
    public boolean validateDuplicateUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // 중복 회원 검증(nickname)
    public boolean validateDuplicateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional
    public void modifyNickname(User user, String nickname){
        user.setNickname(nickname);
        userRepository.save(user);
    };

    @Transactional
    public void modifyPassword(User user, String password){
        String realPassword = passwordEncoder.encode(password);
        user.setPassword(realPassword);
        userRepository.save(user);
    };

    @Transactional
    public void delete(User user){ // 수정 필요함
        List<Question> questions = questionRepository.UserQuestionList(user.getUserId());
        List<Modify> modifies = modifyRepository.UserModifyList(user.getUserId());
        List<Review> reviews = storeRepository.findUserReview(user);

        Optional<User> admin = userRepository.findByUsername("admin");
        for(Question question : questions){
            question.setUser(admin.get());
        }
        for(Review review : reviews){
            review.setUser(admin.get());
        }
        for(Modify modify : modifies){
            modify.setUser(admin.get());
        }
         userRepository.delete(user);
//        likeRepository.delete(user);
    };

    public void saveCnt(int cnt, long userId){
        userRepository.saveCnt(cnt, userId);
    }
}
