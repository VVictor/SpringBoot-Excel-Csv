package me.aboullaite.model;

import java.util.List;

public class cardorder_make {
    private logistics_info logistics_info;
    private List<card_product_infos> card_product_infos;
    private Long card_order_id;
    private String own_sign;

    public logistics_info getLogistics_info() {
        return logistics_info;
    }

    public void setLogistics_info(logistics_info logistics_info) {
        this.logistics_info = logistics_info;
    }

    public List<card_product_infos> getCard_product_infos() {
        return card_product_infos;
    }

    public void setCard_product_infos(List<card_product_infos> card_product_infos) {
        this.card_product_infos = card_product_infos;
    }

    public Long getCard_order_id() {
        return card_order_id;
    }

    public void setCard_order_id(Long card_order_id) {
        this.card_order_id = card_order_id;
    }

    public String getOwn_sign() {
        return own_sign;
    }

    public void setOwn_sign(String own_sign) {
        this.own_sign = own_sign;
    }
}
