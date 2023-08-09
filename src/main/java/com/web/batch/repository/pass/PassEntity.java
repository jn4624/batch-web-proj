package com.web.batch.repository.pass;

import com.web.batch.repository.BaseEntity;
import com.web.batch.repository.packaze.PackageEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "pass")
public class PassEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 DB에 위임한다. (AUTO_INCREMENT)
    private Integer passSeq;

    private Integer packageSeq;

    private String userId;

    @Enumerated(EnumType.STRING)
    private PassStatus status;

    private Integer remainingCount;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime expiredAt;

    // PackageEntity를 ManyToOne으로 조인 처리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packageSeq", insertable = false, updatable = false)
    private PackageEntity packageEntity;
}
