package com.web.batch.service.user;

import com.web.batch.repository.user.UserGroupMappingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupMappingService {
    private final UserGroupMappingRepository userGroupMappingRepository;

    public UserGroupMappingService(UserGroupMappingRepository userGroupMappingRepository) {
        this.userGroupMappingRepository = userGroupMappingRepository;
    }

    public List<String> getAllUserGroupIds() {
        // user group id 를 중복 없이 user group id 역순으로 조회한다.
        return userGroupMappingRepository.findDistinctUserGroupId();
    }
}
