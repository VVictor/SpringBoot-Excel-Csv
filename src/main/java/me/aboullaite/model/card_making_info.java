package me.aboullaite.model;

public class card_making_info {

    private String cardNo;
    private String cardPassword;
    private String qrCode;
    private String shortQrCode;
    private String templateNo;
    private String status;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardPassword() {
        return cardPassword;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getShortQrCode() {
        return shortQrCode;
    }

    public void setShortQrCode(String shortQrCode) {
        this.shortQrCode = shortQrCode;
    }

    public String getTemplateNo() {
        return templateNo;
    }

    public void setTemplateNo(String templateNo) {
        this.templateNo = templateNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
