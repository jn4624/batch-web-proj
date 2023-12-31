package com.web.batch.service.packaze;

import com.web.batch.repository.packaze.PackageEntity;
import com.web.batch.repository.packaze.PackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {
    private final PackageRepository packageRepository;

    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public List<Package> getAllPackages() {
        // 패키지 이름으로 정렬하여 모든 패키지를 조회한다.
        List<PackageEntity> packageEntities = packageRepository.findAllByOrderByPackageName();
        return PackageModelMapper.INSTANCE.map(packageEntities);
    }
}
