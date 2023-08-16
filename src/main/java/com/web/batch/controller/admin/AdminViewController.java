package com.web.batch.controller.admin;

import com.web.batch.service.packaze.PackageService;
import com.web.batch.service.pass.BulkPassService;
import com.web.batch.service.statistics.StatisticsService;
import com.web.batch.service.user.UserGroupMappingService;
import com.web.batch.util.LocalDateTimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

/**
 * 관리자가 이용권 일괄 지급을 위한 정보를 등록하는 페이지를 반환하기 위한 컨트롤러
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminViewController {
    private final BulkPassService bulkPassService;
    private final PackageService packageService;
    private final UserGroupMappingService userGroupMappingService;
    private final StatisticsService statisticsService;

    public AdminViewController(BulkPassService bulkPassService, PackageService packageService, UserGroupMappingService userGroupMappingService, StatisticsService statisticsService) {
        this.bulkPassService = bulkPassService;
        this.packageService = packageService;
        this.userGroupMappingService = userGroupMappingService;
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public ModelAndView home(ModelAndView modelAndView, @RequestParam("to") String toString) {
        LocalDateTime to = LocalDateTimeUtils.parseDate(toString);

        // chartData 를 조회한다(라벨, 출석 횟수, 취소 횟수).
        modelAndView.addObject("chartData", statisticsService.makeChartData(to));
        modelAndView.setViewName("admin/index");

        return modelAndView;
    }

    @GetMapping("/bulk-pass")
    public ModelAndView registerBulkPass(ModelAndView modelAndView) {
        // bulk pass 목록을 조회한다.
        modelAndView.addObject("bulkPasses", bulkPassService.getAllBulkPasses());
        // bulk pass 를 등록할 때 필요한 package 값을 위해 모든 package를 조회한다.
        modelAndView.addObject("packages", packageService.getAllPackages());
        // bulk pass 를 등록할 때 필요한 userGroupId 값을 위해 모든 userGroupId를 조회한다.
        modelAndView.addObject("userGroupIds", userGroupMappingService.getAllUserGroupIds());
        // bulk pass request 를 제공한다.
        modelAndView.addObject("request", new BulkPassRequest());
        modelAndView.setViewName("admin/bulk-pass");

        return modelAndView;
    }

    @PostMapping("bulk-pass")
    public String addBulkPass(@ModelAttribute("request") BulkPassRequest request, Model model) {
        // bulk pass 를 생성한다.
        bulkPassService.addBulkPass(request);
        return "redirect:/admin/bulk-pass";
    }
}
