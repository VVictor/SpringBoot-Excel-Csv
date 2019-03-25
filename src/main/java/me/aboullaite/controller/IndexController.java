package me.aboullaite.controller;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaFundplatformCardordersInfoQueryRequest;
import com.taobao.api.request.AlibabaFundplatformCardordersStatusMakeFinishRequest;
import com.taobao.api.request.AlibabaFundplatformCardordersStatusSendedRequest;
import com.taobao.api.response.AlibabaFundplatformCardordersInfoQueryResponse;
import com.taobao.api.response.AlibabaFundplatformCardordersStatusMakeFinishResponse;
import com.taobao.api.response.AlibabaFundplatformCardordersStatusSendedResponse;
import me.aboullaite.model.*;
import me.aboullaite.service.UserService;
import me.aboullaite.view.ExcelView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private static Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ExcelView excelView;

    private final String appkey = "25375662";  //三宇数码科技 ，测试环境前面需要加10
    private final String appSecret = "1f579736d67afc84252d3d9b2e48834e"; //三宇数码科技
    private final String sandboxappsecret = "sandbox6d67afc84252d3d9b2e48834e"; //沙箱AppSecret


    private final String alibaba_fundplatform_cardorder_info_query_api_url_alph = "http://gw.api.taobao.com/router/rest";
    private final String alibaba_fundplatform_cardorder_info_query_api_url_test = "http://gw.api.tbsandbox.com/router/rest";

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
                //  ExcelView excelView = new ExcelView();
                excelView.buildExcelDocument(cardOrderMake, listCardProductInfo, null, response, cardorderMake.getCard_order_id()+"_"+"CardInfo", 1);
            }



            //   cardProductInfoRepository.saveAll(listCardProductInfo);
            log.info("=============数据保存到 CardProductInf 结束=============");


        } catch (Exception ex) {
            log.info("=============出现错误=============");
            log.info("ErrorMessage:" + ex.getMessage());
            log.info("StackTrace:" + ex.getStackTrace());
        }

        jsoNbject.put("makingCardNo", "N0001");
        //   jsoNbject.put("resultMessage", "N0001");
        //    jsoNbject.put("resultCode", "N0001");
        jsoNbject.put("success", "true");
        jsonObject1.put("response", jsoNbject);

        log.info("=============方法结束=============");
        log.info(jsonObject1.toJSONString());
        return jsonObject1.toJSONString();
    }


    @RequestMapping(value = "/getcardtemplatenew", method = RequestMethod.POST)
    @ResponseBody
    public String getcardtemplatenew(@RequestBody card_template cardTemplate, HttpServletResponse response) throws Exception {

        JSONObject jsoNbject = new JSONObject();


        excelView.buildExcelDocument(null, null, cardTemplate, response, "Template", 2);

        jsoNbject.put("resultMessage", "接口服务");
        jsoNbject.put("resultCode", "N0001");
        jsoNbject.put("success", "true");

        log.info("=============方法结束=============");
        log.info(jsoNbject.toJSONString());
        return jsoNbject.toJSONString();
    }


    @RequestMapping(value = "/getcardorderquery", method = RequestMethod.POST)
    @ResponseBody
    public String GetCardOrders(String page_size, @RequestBody JSONObject jsonObject, HttpServletResponse response) throws Exception {

        TaobaoClient client = new DefaultTaobaoClient(alibaba_fundplatform_cardorder_info_query_api_url_alph, appkey, appSecret);
        AlibabaFundplatformCardordersInfoQueryRequest req = new AlibabaFundplatformCardordersInfoQueryRequest();
        AlibabaFundplatformCardordersInfoQueryRequest.CardMakingInfoQueryRequest obj1 = null;

        Integer pageSize = 500;//每页返回数据
        Integer currentPage = 1; //当前页，从第1页开始

        //代表总量,
        // +5 是为了预防 整除后有余数的问题，并且淘宝接口，当前分页支持大于实际分页数量
        //
        Integer count = Integer.parseInt(jsonObject.get("count").toString()) / 500 + 1;


        AlibabaFundplatformCardordersInfoQueryResponse rsp = null;
        for (Integer i = 1; i <= count; i++) {

            obj1 = new AlibabaFundplatformCardordersInfoQueryRequest.CardMakingInfoQueryRequest();
            obj1.setPageSize(Long.parseLong(pageSize.toString()));
            obj1.setCurrentPage(Long.parseLong(i.toString()));
            obj1.setSignture("test");
            obj1.setCardOrderId(Long.parseLong(jsonObject.get("card_order_id").toString()));
            req.setParameters(obj1);

            rsp = client.execute(req);
            if (rsp.getResult() != null && rsp.getResult().getSuccess()) {
                List<card_making_info> getCardMakingInfoLis = this.getCardMakingInfoList(rsp.getResult().getCardMakingInfos());
                excelView.buildExcelDocumentCardMakeInfo(getCardMakingInfoLis, response, jsonObject.get("card_order_id").toString()+"_" + i.toString() + "CardList");
                log.info(rsp.getResult().toString());
                System.out.println(rsp.getResult());
            }
        }


        return "true";
    }

    private List<card_making_info> getCardMakingInfoList(List<String> CardMakingInfos) {
        List<card_making_info> card_making_infoList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();

        card_making_info card_making_info = null;
        if (CardMakingInfos != null && CardMakingInfos.size() > 0) {
            for (String card : CardMakingInfos) {
                jsonObject = JSONObject.parseObject(card);
                card_making_info = new card_making_info();
                card_making_info.setCardNo(jsonObject.getString("cardNo"));
                card_making_info.setCardPassword(jsonObject.getString("cardPassword"));
                card_making_info.setQrCode(jsonObject.getString("qrCode"));
                card_making_info.setShortQrCode(jsonObject.getString("shortQrCode"));
                card_making_info.setStatus(jsonObject.getString("status"));
                card_making_info.setTemplateNo(jsonObject.getString("templateNo"));
                card_making_infoList.add(card_making_info);
            }
        }
        return card_making_infoList;
    }

    @RequestMapping(value = "/makecardstatus", method = RequestMethod.POST)
    @ResponseBody
    public String MakeCardOrderStatusFinish(@RequestBody JSONObject jsonObject) throws Exception {

        TaobaoClient client = new DefaultTaobaoClient(alibaba_fundplatform_cardorder_info_query_api_url_alph, appkey, appSecret);
        AlibabaFundplatformCardordersStatusMakeFinishRequest req = new AlibabaFundplatformCardordersStatusMakeFinishRequest();
        req.setCardOrderId(Long.parseLong(jsonObject.get("card_order_id").toString()));
        AlibabaFundplatformCardordersStatusMakeFinishResponse rsp = client.execute(req);

        System.out.println(rsp.getBody());
        log.info(rsp.getResult().toString());
        return rsp.getResult().getSuccess().toString();
    }

    @RequestMapping(value = "/cardordersStatusSended", method = RequestMethod.POST)
    @ResponseBody
    public String CardOrderStatusSended(@RequestBody JSONObject jsonObject) throws Exception {
        TaobaoClient client = new DefaultTaobaoClient(alibaba_fundplatform_cardorder_info_query_api_url_alph, appkey, appSecret);
        AlibabaFundplatformCardordersStatusSendedRequest req = new AlibabaFundplatformCardordersStatusSendedRequest();
        req.setCardOrderId(Long.parseLong(jsonObject.get("card_order_id").toString()));
        req.setLogisticsOrderId(jsonObject.get("logistics_order_id").toString());
        req.setLogisticsCompany(jsonObject.get("logistics_company").toString());
        AlibabaFundplatformCardordersStatusSendedResponse rsp = client.execute(req);

        System.out.println(rsp.getBody());
        log.info(rsp.getResult().toString());
        return rsp.getResult().getSuccess().toString();
    }

}
