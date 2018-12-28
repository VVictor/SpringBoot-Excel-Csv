package me.aboullaite.model;

public class card_template extends card_product_infos {

private Boolean is_test;
private String own_sign;

    public Boolean getIs_test() {
        return is_test;
    }

    public void setIs_test(Boolean is_test) {
        this.is_test = is_test;
    }

    public String getOwn_sign() {
        return own_sign;
    }

    public void setOwn_sign(String own_sign) {
        this.own_sign = own_sign;
    }
}
