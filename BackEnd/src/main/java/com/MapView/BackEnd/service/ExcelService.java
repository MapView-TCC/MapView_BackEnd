package com.MapView.BackEnd.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ExcelService {
    void generateExcel(HttpServletResponse response) throws IOException;
}
