package com.web.batch.service.pass;

import com.web.batch.repository.pass.PassEntity;
import com.web.batch.repository.pass.PassRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassService {
    private final PassRepository passRepository;

    public PassService(PassRepository passRepository) {
        this.passRepository = passRepository;
    }

    public List<Pass> getPasses(final String userId) {
        // userId를 조건으로 pass 정보를 조회한다(이때 packageSeq에 맞는 package 정보도 필요하다).
        final List<PassEntity> passEntities = passRepository.findByUserId(userId);
        return PassModelMapper.INSTANCE.map(passEntities);
    }
}
