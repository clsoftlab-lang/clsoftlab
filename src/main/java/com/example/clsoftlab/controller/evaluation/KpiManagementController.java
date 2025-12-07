package com.example.clsoftlab.controller.evaluation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/evaluation")
public class KpiManagementController {

    @GetMapping("/kpi-management")
    public String kpiManagementPage() {
        return "evaluation/kpi_management";
    }
}
