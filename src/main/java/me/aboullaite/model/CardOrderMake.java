package me.aboullaite.model;




public class CardOrderMake {

    private Long card_order_id;
    private String own_sign;
    private String receiver_zip_code;
    private String receiver_address;
    private String receiver_number;
    private String receiver_name;

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

    public String getReceiver_zip_code() {
        return receiver_zip_code;
    }

    public void setReceiver_zip_code(String receiver_zip_code) {
        this.receiver_zip_code = receiver_zip_code;
    }

    public String getReceiver_address() {
        return receiver_address;
    }

    public void setReceiver_address(String receiver_address) {
        this.receiver_address = receiver_address;
    }

    public String getReceiver_number() {
        return receiver_number;
    }

    public void setReceiver_number(String receiver_number) {
        this.receiver_number = receiver_number;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }
}
