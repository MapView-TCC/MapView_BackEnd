package com.MapView.BackEnd.controller;

import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.serviceImp.ReportServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/excel")
@Tag(name = "Excel Report", description = "Operations related to Excel report generation")
public class ReportRestController {

    private final ReportServiceImp reportServiceImp ;

    public ReportRestController(ReportServiceImp reportServiceImp) {
        this.reportServiceImp = reportServiceImp;
    }

    @Operation(
            summary = "Generate Excel report",
            description = "Generates an Excel report for equipment and sends it as a downloadable file.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Excel report generated successfully and is being sent as a downloadable file."),
                    @ApiResponse(responseCode = "500", description = "Internal server error while generating the report.")
            }
    )
    @GetMapping
    public void generateExcelReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=equipment.xls";

        response.setHeader(headerKey, headerValue);

        reportServiceImp.generateExcel(response);
    }
}
