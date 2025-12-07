package com.example.clsoftlab.controller.evaluation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/evaluation")
public class KpiManagementController {

    @GetMapping("/kpi-management")
    public String kpiManagementPage(@RequestParam(required = false) Integer year,
                                  @RequestParam(defaultValue = "전체") String level,
                                  @RequestParam(defaultValue = "전체") String isUsed,
                                  Model model) {

        int currentYear = Year.now().getValue();
        int searchYear = Optional.ofNullable(year).orElse(currentYear);

        // 검색 필터 값들을 모델에 추가 (뷰에서 사용)
        model.addAttribute("filters", new KpiFilter(searchYear, level, isUsed));

        // KPI 레벨 목록 추가 (셀렉트 박스 옵션)
        model.addAttribute("kpiLevels", Arrays.asList("전사 KPI", "본부 KPI", "팀 KPI", "개인 KPI"));

        // TODO: 향후 서비스 계층에서 실제 데이터를 조회해야 함
        // 현재는 빈 KPI 목록을 추가하여 템플릿 렌더링 오류 방지
        model.addAttribute("kpiList", new ArrayList<KpiData>());

        return "evaluation/kpi_management";
    }

    // DTO for search filters
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class KpiFilter {
        private int year;
        private String level;
        private String isUsed;
    }

    // DTO for KPI data
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class KpiData {
        private String name;
        private String level;
        private String parentKpiName;
        private String details;
        private int year;
        private int weight;
        private boolean isUsed;
    }
}
