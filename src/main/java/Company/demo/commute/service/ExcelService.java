package Company.demo.commute.service;

import Company.demo.commute.dto.request.OverTimeRequest;
import Company.demo.commute.dto.response.OverTimeResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExcelService {
    private final MemberService memberService;

    public void getMemberExcel(Integer holidays,HttpServletResponse response){
        OverTimeRequest build = OverTimeRequest.builder()
                .yearMonth(getPreviousMonth())
                .holidays(holidays)
                .build();
        List<OverTimeResponse> overTime = memberService.getOverTime(build);
        getExcel(overTime,response);
    }

//    public void getExcel(Integer holidays, HttpServletResponse response){
//        OverTimeRequest build = OverTimeRequest.builder()
//                .yearMonth(getPreviousMonth())
//                .holidays(holidays)
//                .build();
//        List<OverTimeResponse> overTime = memberService.getOverTime(build);
//
//        try{
//            String fileName = "OverTimeResult.xlsx";
//            XSSFWorkbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("초과 근무 계산결과");
//
//            // HTTP 응답 헤더 설정
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-Disposition", "attachment; filename="+fileName);
//
//            //헤더 생성
//            Row headerRow = sheet.createRow(0);
//            Field[] declaredFields = OverTimeResponse.class.getDeclaredFields();
//
//            for(int i=0;i<declaredFields.length;i++){
//                Cell headerCell = headerRow.createCell(i);
//                headerCell.setCellValue(declaredFields[i].getName());
//            }
//
//            //데이터 생성
//            for(int i=0;i<overTime.size();i++){
//                OverTimeResponse overTimeResponse = overTime.get(i);
//                Row row = sheet.createRow(i + 1);
//
//                for(int j=0;j<declaredFields.length;j++){
//                    Cell cell = row.createCell(j);
//                    Field field = declaredFields[j];
//                    field.setAccessible(true);
//                    Object value;
//                    try{
//                        value = field.get(overTimeResponse); // 필드의 값을 가져옴
//                        if (value != null) {
//                            cell.setCellValue(value.toString());
//                        }else{
//                            cell.setCellValue(0);
//                        }
//                    }catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            FileOutputStream fileOut = new FileOutputStream(fileName);
//            try(OutputStream out = response.getOutputStream()){
//                workbook.write(out);
//            }
////            workbook.write(fileOut);
//            workbook.close();
//            System.out.println("엑셀 파일이 저장되었습니다");
//
//
//        }
//        catch (Exception e){
//            System.out.println("엑셀 파일 저장 중 오류가 발생했습니다.");
//            e.printStackTrace();
//        }
//
//    }

    private void getExcel(List object, HttpServletResponse response){

        try{
            String fileName = "OverTimeResult.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("초과 근무 계산결과");

            // HTTP 응답 헤더 설정
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename="+fileName);

            //헤더 생성
            Row headerRow = sheet.createRow(0);
            Field[] declaredFields = OverTimeResponse.class.getDeclaredFields();

            for(int i=0;i<declaredFields.length;i++){
                Cell headerCell = headerRow.createCell(i);
                headerCell.setCellValue(declaredFields[i].getName());
            }

            //데이터 생성
            for(int i=0;i<object.size();i++){
                Object o = object.get(i);
                Row row = sheet.createRow(i + 1);

                for(int j=0;j<declaredFields.length;j++){
                    Cell cell = row.createCell(j);
                    Field field = declaredFields[j];
                    field.setAccessible(true);
                    Object value;
                    try{
                        value = field.get(o); // 필드의 값을 가져옴
                        if (value != null) {
                            cell.setCellValue(value.toString());
                        }else{
                            cell.setCellValue(0);
                        }
                    }catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            FileOutputStream fileOut = new FileOutputStream(fileName);
            try(OutputStream out = response.getOutputStream()){
                workbook.write(out);
            }
//            workbook.write(fileOut);
            workbook.close();
            System.out.println("엑셀 파일이 저장되었습니다");
        }
        catch (Exception e){
            System.out.println("엑셀 파일 저장 중 오류가 발생했습니다.");
            e.printStackTrace();
        }

    }

    private YearMonth getPreviousMonth() {
        LocalDate previousMonth = LocalDate.now().minusMonths(1);
        return YearMonth.from(previousMonth);
    }
}
