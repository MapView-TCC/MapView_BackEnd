package com.MapView.BackEnd.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ReportService {
    void generateExcel(HttpServletResponse response) throws IOException;
}
