package me.aboullaite.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.taobao.api.DefaultTaobaoClient;
//import com.taobao.api.TaobaoClient;
//import com.taobao.api.request.AlibabaFundplatformCardordersInfoQueryRequest;
//import com.taobao.api.response.AlibabaFundplatformCardordersInfoQueryResponse;
import me.aboullaite.model.*;
import me.aboullaite.service.UserService;
import me.aboullaite.view.ExcelView;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ExportController {
    private static Logger log = LoggerFactory.getLogger(ExportController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ExcelView excelView;

    private final String appkey = "25375662";  //三宇数码科技
    private final String appSecret = "1f579736d67afc84252d3d9b2e48834e"; //三宇数码科技

    private final String alibaba_fundplatform_cardorder_info_query_api_url = "";

    /**
     * Handle request to download an Excel document
     */
    @GetMapping("/download")
    public String download(Model model) {

        model.addAttribute("users", userService.findAllUsers());
        return "";
    }

    @RequestMapping(value = "/getcardorder", method = RequestMethod.POST)
    @ResponseBody
    public String GetCardOrderMakeInfo(@RequestBody cardorder_make cardorderMake, HttpServletResponse response) {
        //  JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsoNbject = new JSONObject();
        List<CardProductInfo> listCardProductInfo = new ArrayList<>();
        try {
            log.info("=============数据保存到 CardOrderMake 开始=============");
            CardOrderMake cardOrderMake = new CardOrderMake();
            cardOrderMake.setCard_order_id(cardorderMake.getCard_order_id());
            cardOrderMake.setOwn_sign(cardorderMake.getOwn_sign());
            cardOrderMake.setReceiver_zip_code(cardorderMake.getLogistics_info().getReceiver_zip_code());
            cardOrderMake.setReceiver_name(cardorderMake.getLogistics_info().getReceiver_name());
            cardOrderMake.setReceiver_address(cardorderMake.getLogistics_info().getReceiver_address());
            cardOrderMake.setReceiver_number(cardorderMake.getLogistics_info().getReceiver_number());

            //    cardOrderMakeRepository.save(cardOrderMake);
            log.info("=============数据保存到 CardOrderMake 结束=============");

            log.info("=============数据保存到 CardProductInf 开始=============");

            CardProductInfo cardProductInfo = null;
            //   Long count = cardorderMake.getCard_product_infos().get(1).getCount();
            if (cardorderMake.getCard_product_infos() != null && cardorderMake.getCard_product_infos().size() > 0) {
                for (int i = 0; i < cardorderMake.getCard_product_infos().size(); i++) {
                    cardProductInfo = new CardProductInfo();
                    cardProductInfo.setCard_order_id(cardorderMake.getCard_order_id());
                    cardProductInfo.setCard_name(cardorderMake.getCard_product_infos().get(i).getCard_name());
                    cardProductInfo.setCount(cardorderMake.getCard_product_infos().get(i).getCount());
                    cardProductInfo.setPar_value(cardorderMake.getCard_product_infos().get(i).getPar_value());
                    cardProductInfo.setPicture_url(cardorderMake.getCard_product_infos().get(i).getPicture_url());
                    cardProductInfo.setTemplate_no(cardorderMake.getCard_product_infos().get(i).getTemplate_no());
                    listCardProductInfo.add(cardProductInfo);
                }
            }

          //  ExcelView excelView = new ExcelView();
            excelView.buildExcelDocument(cardOrderMake, listCardProductInfo, null, response, "卡产品信息", 1);

            //   cardProductInfoRepository.saveAll(listCardProductInfo);
            log.info("=============数据保存到 CardProductInf 结束=============");


        } catch (Exception ex) {
            log.info("=============出现错误=============");
            log.info("ErrorMessage:" + ex.getMessage());
            log.info("StackTrace:" + ex.getStackTrace());
        }

        jsoNbject.put("makingCardNo", "N0001");
        jsoNbject.put("resultMessage", "N0001");
        jsoNbject.put("resultCode", "N0001");
        jsoNbject.put("success", "N0001");
        jsonObject1.put("response", jsoNbject);

        log.info("=============方法结束=============");
        log.info(jsonObject1.toJSONString());
        return jsonObject1.toJSONString();
    }


    @RequestMapping(value = "/getcardtemplatenew", method = RequestMethod.POST)
    @ResponseBody
    public String getcardtemplatenew(@RequestBody card_template cardTemplate, HttpServletResponse response)throws Exception  {

        JSONObject jsoNbject = new JSONObject();



        excelView.buildExcelDocument(null, null, cardTemplate, response, "卡模板信息", 2);

        jsoNbject.put("resultMessage", "接口服务");
        jsoNbject.put("resultCode", "N0001");
        jsoNbject.put("success", "true");

        log.info("=============方法结束=============");
        log.info(jsoNbject.toJSONString());
        return jsoNbject.toJSONString();
    }


    @RequestMapping(value = "/getcardorderquery", method = RequestMethod.POST)
    @ResponseBody
    public String GetCardOrders(Model model) {

 //       TaobaoClient client = new DefaultTaobaoClient("", appkey, appSecret);
//        AlibabaFundplatformCardordersInfoQueryRequest req = new AlibabaFundplatformCardordersInfoQueryRequest();
//        CardMakingInfoQueryRequest obj1 = new CardMakingInfoQueryRequest();
//        obj1.setPageSize(500L);
//        obj1.setCurrentPage(1L);
//        obj1.setSignture("test");
//        obj1.setCardOrderId(1001L);
//        req.setParameters(obj1);
        //    AlibabaFundplatformCardordersInfoQueryResponse rsp = client.execute(req);
        //    System.out.println(rsp.getBody());
        return "";
    }

}
