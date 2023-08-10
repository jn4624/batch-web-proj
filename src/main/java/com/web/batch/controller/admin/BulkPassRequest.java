package com.web.batch.controller.admin;

import com.web.batch.util.LocalDateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BulkPassRequest {
    private Integer packageSeq;
    private String userGroupId;
    private LocalDateTime startedAt;

    public void setStartedAt(String startedAtString) {
        this.startedAt = LocalDateTimeUtils.parse(startedAtString);
    }
}
