package com.web.batch.service.user;

import com.web.batch.repository.user.UserEntity;
import com.web.batch.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(final String userId) {
        // userId를 조건으로 사용자 정보를 조회한다(프로필에 노출할 사용자의 이름이 필요하다).
        UserEntity userEntity = userRepository.findByUserId(userId);
        return UserModelMapper.INSTANCE.map(userEntity);
    }
}
