package me.aboullaite.view;

import me.aboullaite.controller.IndexController;
import me.aboullaite.model.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ExcelView extends AbstractXlsView {
    private static Logger log = LoggerFactory.getLogger(IndexController.class);

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) model.get("users");

        // create excel xls sheet
        Sheet sheet = workbook.createSheet("User Detail");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);


        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("FirstName");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("LastName");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("Age");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("Job Title");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("Company");
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue("Address");
        header.getCell(5).setCellStyle(style);
        header.createCell(6).setCellValue("City");
        header.getCell(6).setCellStyle(style);
        header.createCell(7).setCellValue("Country");
        header.getCell(7).setCellStyle(style);
        header.createCell(8).setCellValue("Phone Number");
        header.getCell(8).setCellStyle(style);


        int rowCount = 1;

        for (User user : users) {
            Row userRow = sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue(user.getFirstName());
            userRow.createCell(1).setCellValue(user.getLastName());
            userRow.createCell(2).setCellValue(user.getAge());
            userRow.createCell(3).setCellValue(user.getJobTitle());
            userRow.createCell(4).setCellValue(user.getCompany());
            userRow.createCell(5).setCellValue(user.getAddress());
            userRow.createCell(6).setCellValue(user.getCity());
            userRow.createCell(7).setCellValue(user.getCountry());
            userRow.createCell(8).setCellValue(user.getPhoneNumber());

        }


        //将数据保存到本机
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = simpleDateFormat2.format(new Date()) + ".xls";

        File file = new File(this.getClass().getResource("/").getPath() + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        OutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
    }

    /*
     * *
     * type
     *      代表保存表格类型：1 代表保存 CardMakeInfo 产品  2 代表保存CardTemplate
     *  */
    public void buildExcelDocument(CardOrderMake cardOrderMake,
                                   List<CardProductInfo> listCardProductInfo,
                                   card_template cardTemplate,
                                   HttpServletResponse response,
                                   String excelfilename,
                                   int type) throws Exception {

        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");

        @SuppressWarnings("unchecked")
        // List<CardProductInfo> users = (List<CardProductInfo>) model.get("users");

                // create excel xls sheet
                XSSFWorkbook workbook = new XSSFWorkbook();
        if (type == 1) {
            getCardOrderMakeInfo(workbook, cardOrderMake, listCardProductInfo);
        } else {
            getCardTemplate(workbook, cardTemplate);
        }

        //Save Excel File to  Server
        excelFileSave(workbook, excelfilename);

    }

    public void buildExcelDocumentCardMakeInfo(List<card_making_info> card_making_infoList,
                                               HttpServletResponse response,
                                               String excelfilename) throws Exception {
        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
        response.setContentType("pplication/vnd.ms-excel");

        // create excel xls sheet
        XSSFWorkbook workbook = new XSSFWorkbook();


        Sheet sheet = workbook.createSheet("卡产品列表");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = setSheetStyle(workbook);

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("卡编号");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("卡密码");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("二维码链接");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("短连接二维码");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("卡商品编码");
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue("卡状态");
        header.getCell(5).setCellStyle(style);

        int rowCount = 1;


        if (card_making_infoList != null && card_making_infoList.size() > 0) {

            for (card_making_info card : card_making_infoList) {
                Row userRow = sheet.createRow(rowCount++);
                userRow.createCell(0).setCellValue(card.getCardNo());
                userRow.createCell(1).setCellValue(card.getCardPassword());
                userRow.createCell(2).setCellValue(card.getQrCode());
                userRow.createCell(3).setCellValue(card.getShortQrCode());
                userRow.createCell(4).setCellValue(card.getTemplateNo());
                userRow.createCell(5).setCellValue(card.getStatus());

            }

        }

        //Save Excel File to  Server
        excelFileSave(workbook, excelfilename);

        //  renderWorkbook(workbook,response);
        //网页流输出 另存

        // renderWorkbook(workbook,response);
    }

    private void getCardTemplate(XSSFWorkbook workbook,
                                 card_template cardTemplate) {
        Sheet sheet = workbook.createSheet("卡模板信息");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = setSheetStyle(workbook);

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("卡模板编号");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("卡名称");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("卡面额(分)");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("卡外观图片地址");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("是否测试卡模板");
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue("环境变量");
        header.getCell(5).setCellStyle(style);

        int rowCount2 = 1;

        // for (CardProductInfo cardProductInfo : listCardProductInfo) {
        Row userRow = sheet.createRow(rowCount2);
        userRow.createCell(0).setCellValue(cardTemplate.getTemplate_no());
        userRow.createCell(1).setCellValue(cardTemplate.getCard_name());
        userRow.createCell(2).setCellValue(cardTemplate.getPar_value());
        userRow.createCell(3).setCellValue(cardTemplate.getPicture_url());
        userRow.createCell(4).setCellValue(cardTemplate.getIs_test());
        userRow.createCell(5).setCellValue(cardTemplate.getOwn_sign());
        //   userRow.createCell(5).setCellValue(cardOrderMake.getCard_name());

    }

    private void getCardOrderMakeInfo(XSSFWorkbook workbook, CardOrderMake cardOrderMake,
                                      List<CardProductInfo> listCardProductInfo) {

        Sheet sheet = workbook.createSheet("卡产品列表");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = setSheetStyle(workbook);

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("子制卡单ID");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("卡模板编号");
        header.getCell(1).setCellStyle(style);
        header.createCell(2).setCellValue("卡外观图片地址");
        header.getCell(2).setCellStyle(style);
        header.createCell(3).setCellValue("当前卡模板制卡数量");
        header.getCell(3).setCellStyle(style);
        header.createCell(4).setCellValue("卡面额(分)");
        header.getCell(4).setCellStyle(style);
        header.createCell(5).setCellValue("卡名称");
        header.getCell(5).setCellStyle(style);
//        header.createCell(6).setCellValue("City");
//        header.getCell(6).setCellStyle(style);
//        header.createCell(7).setCellValue("Country");
//        header.getCell(7).setCellStyle(style);
//        header.createCell(8).setCellValue("Phone Number");
//        header.getCell(8).setCellStyle(style);


        int rowCount = 1;


        if (listCardProductInfo != null && listCardProductInfo.size() > 0) {

//            if(listCardProductInfo.size()==1){
//                Row userRow = sheet.createRow(rowCount);
//                userRow.createCell(0).setCellValue(listCardProductInfo.get(0).getCard_order_id());
//                userRow.createCell(1).setCellValue(listCardProductInfo.get(0).getTemplate_no());
//                userRow.createCell(2).setCellValue(listCardProductInfo.get(0).getPicture_url());
//                userRow.createCell(3).setCellValue(listCardProductInfo.get(0).getCount());
//                userRow.createCell(4).setCellValue(listCardProductInfo.get(0).getPar_value());
//                userRow.createCell(5).setCellValue(listCardProductInfo.get(0).getCard_name());
//            }else {
            for (CardProductInfo cardProductInfo : listCardProductInfo) {
                Row userRow = sheet.createRow(rowCount++);
                userRow.createCell(0).setCellValue(cardProductInfo.getCard_order_id());
                userRow.createCell(1).setCellValue(cardProductInfo.getTemplate_no());
                userRow.createCell(2).setCellValue(cardProductInfo.getPicture_url());
                userRow.createCell(3).setCellValue(cardProductInfo.getCount());
                userRow.createCell(4).setCellValue(cardProductInfo.getPar_value());
                userRow.createCell(5).setCellValue(cardProductInfo.getCard_name());
//            userRow.createCell(6).setCellValue(user.getCity());
//            userRow.createCell(7).setCellValue(user.getCountry());
//            userRow.createCell(8).setCellValue(user.getPhoneNumber());

            }

        }


        Sheet sheet2 = workbook.createSheet("卡物流信息");

        sheet2.setDefaultColumnWidth(30);


        // create header row
        Row header2 = sheet2.createRow(0);
        header2.createCell(0).setCellValue("子制卡单ID");
        header2.getCell(0).setCellStyle(style);
        header2.createCell(1).setCellValue("收件人邮编");
        header2.getCell(1).setCellStyle(style);
        header2.createCell(2).setCellValue("收件人地址");
        header2.getCell(2).setCellStyle(style);
        header2.createCell(3).setCellValue("收件人联系方式");
        header2.getCell(3).setCellStyle(style);
        header2.createCell(4).setCellValue("收件人名称");
        header2.getCell(4).setCellStyle(style);
//        header.createCell(5).setCellValue("卡名称");
//        header.getCell(5).setCellStyle(style);
//        header.createCell(6).setCellValue("City");
//        header.getCell(6).setCellStyle(style);
//        header.createCell(7).setCellValue("Country");
//        header.getCell(7).setCellStyle(style);
//        header.createCell(8).setCellValue("Phone Number");
//        header.getCell(8).setCellStyle(style);


        int rowCount2 = 1;

        // for (CardProductInfo cardProductInfo : listCardProductInfo) {
        Row userRow = sheet2.createRow(rowCount2);
        userRow.createCell(0).setCellValue(cardOrderMake.getCard_order_id());
        userRow.createCell(1).setCellValue(cardOrderMake.getReceiver_zip_code());
        userRow.createCell(2).setCellValue(cardOrderMake.getReceiver_address());
        userRow.createCell(3).setCellValue(cardOrderMake.getReceiver_number());
        userRow.createCell(4).setCellValue(cardOrderMake.getReceiver_name());
        //   userRow.createCell(5).setCellValue(cardOrderMake.getCard_name());
//            userRow.createCell(6).setCellValue(user.getCity());
//            userRow.createCell(7).setCellValue(user.getCountry());
//            userRow.createCell(8).setCellValue(user.getPhoneNumber());

        //   }

    }

    private CellStyle setSheetStyle(XSSFWorkbook workbook) {
        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
        return style;
    }

    private void excelFileSave(XSSFWorkbook workbook, String excelfilename) throws Exception {

        //将数据保存到本机
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String fileName = excelfilename + simpleDateFormat2.format(new Date()) + ".xls";
       // Random random = new Random();

        File file = new File("D:/data/TELHM/" + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        log.info("========文件保存路径=============");
        log.info(file.getPath());

        OutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);

        if (workbook instanceof Closeable) {
            workbook.close();
        }
    }

    protected void renderWorkbook(Workbook workbook, HttpServletResponse response) throws IOException {
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        if (workbook instanceof Closeable) {
            workbook.close();
        }

    }

}
