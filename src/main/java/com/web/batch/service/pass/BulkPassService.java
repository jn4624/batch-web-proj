package com.web.batch.service.pass;

import com.web.batch.controller.admin.BulkPassRequest;
import com.web.batch.repository.packaze.PackageEntity;
import com.web.batch.repository.packaze.PackageRepository;
import com.web.batch.repository.pass.BulkPassEntity;
import com.web.batch.repository.pass.BulkPassRepository;
import com.web.batch.repository.pass.BulkPassStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BulkPassService {
    private final BulkPassRepository bulkPassRepository;
    private final PackageRepository packageRepository;

    public BulkPassService(BulkPassRepository bulkPassRepository, PackageRepository packageRepository) {
        this.bulkPassRepository = bulkPassRepository;
        this.packageRepository = packageRepository;
    }

    public List<BulkPass> getAllBulkPasses() {
        // startedAt 역순으로 모든 bulkPass 를 조회한다.
        List<BulkPassEntity> bulkPassEntities = bulkPassRepository.findAllOrderByStartedAtDesc();
        return BulkPassModelMapper.INSTANCE.map(bulkPassEntities);
    }

    public void addBulkPass(BulkPassRequest bulkPassRequest) {
        // bulkPassRequest 를 기반으로 passEntity 를 생성하여 DB 에 저장한다.
        PackageEntity packageEntity = packageRepository.findById(bulkPassRequest.getPackageSeq()).orElseThrow();

        BulkPassEntity bulkPassEntity = BulkPassModelMapper.INSTANCE.map(bulkPassRequest);
        bulkPassEntity.setStatus(BulkPassStatus.READY);
        bulkPassEntity.setCount(packageEntity.getCount());
        bulkPassEntity.setEndedAt(packageEntity.getPeriod());

        bulkPassRepository.save(bulkPassEntity);
    }
}
