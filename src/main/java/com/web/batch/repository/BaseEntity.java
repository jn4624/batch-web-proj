package com.web.batch.repository;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 공통 매핑 정보를 가진 Entity
 */
@MappedSuperclass // 상속 받은 entity에서 아래 필드들을 컬럼으로 사용하기 위한 어노테이션
@EntityListeners(AuditingEntityListener.class) // Auditing 정보를 캡처하는 Listener 어노테이션
public class BaseEntity {
    @CreatedDate // 생성 일시를 생성한다.
    @Column(updatable = false, nullable = false) // 업데이트를 하지 않도록, null이 되지 않도록 명시한다.
    private LocalDateTime createdAt;
    @LastModifiedDate // 마지막 수정 일시를 생성한다.
    private LocalDateTime modifiedAt;
}
