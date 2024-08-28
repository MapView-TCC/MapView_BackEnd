package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.service.ReportService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ReportServiceImp implements ReportService {

    private final EquipmentRepository equipmentRepository;

    public ReportServiceImp(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public void generateExcel(HttpServletResponse response) throws IOException {
        List<Equipment> equipments = equipmentRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Equipment Info");

        // Estilo do cabeçalho
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex()); // Cor de fundo (cinza claro)
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); // Define o padrão de preenchimento
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerStyle.setBorderRight(BorderStyle.MEDIUM);
        headerStyle.setBorderTop(BorderStyle.MEDIUM);

        // Estilizando a fonte
        HSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex()); // Cor da fonte (branca)
        headerStyle.setFont(font);

        // Criar a linha do cabeçalho
        HSSFRow rowDate = sheet.createRow(0);
        rowDate.createCell(0).setCellValue(String.format("Data da última utilização: %s", new java.util.Date())); // data atual

        // Criar a linha do cabeçalho
        HSSFRow row = sheet.createRow(2);

        // Criar as células do cabeçalho e aplicar o estilo
        createHeaderCell(row, 0, "Codigo Equipamento", headerStyle);
        createHeaderCell(row, 1, "Tipo", headerStyle);
        createHeaderCell(row, 2, "Modelo", headerStyle);
        createHeaderCell(row, 3, "Validade", headerStyle);
        createHeaderCell(row, 4, "Usuário Principal", headerStyle);
        createHeaderCell(row, 5, "Ambiente", headerStyle);
        createHeaderCell(row, 6, "Posto", headerStyle);
        createHeaderCell(row, 7, "Observação", headerStyle);

        int dataRowIndex = 3;

        for (Equipment equipment : equipments) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(equipment.getId_equipment());
            dataRow.createCell(1).setCellValue(equipment.getType());
            dataRow.createCell(2).setCellValue(equipment.getModel());
            dataRow.createCell(3).setCellValue(equipment.getValidity());

            // Extrair informações textuais das entidades Location e MainOwner
            String IdownerName = equipment.getId_owner() != null ? equipment.getId_owner().getId_owner() : "N/A";
            Location location = equipment.getId_location();
            String environment = (location != null && location.getEnvironment() != null) ? location.getEnvironment().getEnvironment_name() : "N/A";
            String postName = (location != null && location.getPost() != null) ? location.getPost().getPost() : "N/A";

            dataRow.createCell(4).setCellValue(IdownerName);
            dataRow.createCell(5).setCellValue(environment);
            dataRow.createCell(6).setCellValue(postName);
            dataRow.createCell(7).setCellValue(equipment.getObservation());

            dataRowIndex++;
        }

        // Definir largura fixa para cada coluna
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 5000);

        // adiciona o filtro
        sheet.setAutoFilter(new CellRangeAddress(2,2,0,7));

        // Gerar o arquivo e enviá-lo na resposta
        ServletOutputStream ops = response.getOutputStream(); // Obtém o ServletOutputStream a partir da resposta HTTP.
        workbook.write(ops); // escreve o conteúdo do workbook para o output stream.
        workbook.close();
        ops.close();
    }

    private void createHeaderCell(HSSFRow row, int columnIndex, String value, HSSFCellStyle style) {
        HSSFCell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
}