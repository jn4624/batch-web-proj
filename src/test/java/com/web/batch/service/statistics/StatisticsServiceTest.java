package com.web.batch.service.statistics;

import com.web.batch.repository.statistics.StatisticsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTest {
    @Mock
    private StatisticsRepository statisticsRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    // Junit5 에서만 제공하는 기능
    @Nested // 계층적인 구조로 테스트 데이터 구현을 위한 어노테이션
    @DisplayName("통계 데이터를 기반으로 차트 만들기") // 메소드명 대신 정의한 테스트명으로 대체 표시하기 위한 어노테이션
    class MakeChartData {
        final LocalDateTime to = LocalDateTime.of(2022, 9, 10, 0, 0);

        @DisplayName("통계 데이터가 있을 때")
        @Test
        void makeChartData_when_hasStatistics() {
            // given
            List<AggregatedStatistics> statisticsList = List.of(
                    new AggregatedStatistics(to.minusDays(1), 15, 10, 5),
                    new AggregatedStatistics(to, 15, 8, 2)
            );

            // when
            when(statisticsRepository.findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)), eq(to))).thenReturn(statisticsList);
            final ChartData chartData = statisticsService.makeChartData(to);

            // then
            // statisticsRepository 호출이 되었는지 검증
            verify(statisticsRepository, times(1)).findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)), eq(to));

            // chartData null 체크
            assertNotNull(chartData);
            // labels 데이터 검증
            assertEquals(new ArrayList<>(List.of("09-09", "09-10")), chartData.getLabels());
            // attendedCounts 데이터 검증
            assertEquals(new ArrayList<>(List.of(10L, 8L)), chartData.getAttendedCounts());
            // cancelledCounts 데이터 검증
            assertEquals(new ArrayList<>(List.of(5L, 2L)), chartData.getCancelledCounts());
        }

        @DisplayName("통계 데이터가 없을 때")
        @Test
        void makeChartData_when_notHasStatistics() {
            // when
            when(statisticsRepository.findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)), eq(to))).thenReturn(Collections.emptyList());
            final ChartData chartData = statisticsService.makeChartData(to);

            // then
            // statisticsRepository 호출이 되었는지 검증
            verify(statisticsRepository, times(1)).findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)), eq(to));

            // chartData null 체크
            assertNotNull(chartData);
            // labels 데이터 검증
            assertTrue(chartData.getLabels().isEmpty());
            // attendedCounts 데이터 검증
            assertTrue(chartData.getAttendedCounts().isEmpty());
            // cancelledCounts 데이터 검증
            assertTrue(chartData.getCancelledCounts().isEmpty());
        }
    }

    @Test
    public void test_makeChartData() {
        // given
        final LocalDateTime to = LocalDateTime.of(2022, 9, 10, 0, 0);

        List<AggregatedStatistics> statisticsList = List.of(
                new AggregatedStatistics(to.minusDays(1), 15, 10, 5),
                new AggregatedStatistics(to, 15, 8, 2)
        );

        // when
        when(statisticsRepository.findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)), eq(to))).thenReturn(statisticsList);
        final ChartData chartData = statisticsService.makeChartData(to);

        // then
        // statisticsRepository 호출이 되었는지 검증
        verify(statisticsRepository, times(1)).findByStatisticsAtBetweenAndGroupBy(eq(to.minusDays(10)), eq(to));

        // chartData null 체크
        assertNotNull(chartData);
        // labels 데이터 검증
        assertEquals(new ArrayList<>(List.of("09-09", "09-10")), chartData.getLabels());
        // attendedCounts 데이터 검증
        assertEquals(new ArrayList<>(List.of(10L, 8L)), chartData.getAttendedCounts());
        // cancelledCounts 데이터 검증
        assertEquals(new ArrayList<>(List.of(5L, 2L)), chartData.getCancelledCounts());
    }
}
