package com.web.batch.repository.pass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PassRepository extends JpaRepository<PassEntity, Integer> {
    /**
     * 종료 일시가 null인 값들(무기한)이 존재하여 null인 값들은 상단으로 올려서 사용자들에게 노출하기 위해 nulls first 추가
     */
    @Query(value =  "select p from PassEntity p " +
            "join fetch p.packageEntity " +
            "where p.userId = :userId " +
            "order by p.endedAt desc nulls first ")
    List<PassEntity> findByUserId(String userId);
}
