package com.web.batch.repository.statistics;

import com.web.batch.service.statistics.AggregatedStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Integer> {
    @Query(value = "select new com.web.batch.service.statistics.AggregatedStatistics(s.statisticsAt, sum(s.allCount), sum(s.attendedCount), sum(s.cancelledCount)) " +
                    "from StatisticsEntity s " +
                    "where s.statisticsAt between :from and :to " +
                    "group by s.statisticsAt ")
    List<AggregatedStatistics> findByStatisticsAtBetweenAndGroupBy(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}

