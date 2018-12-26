package me.aboullaite.model;

public class card_product_infos {

    /*卡模板编号  */
    private String template_no;
    /*卡外观图片地址 */
    private String picture_url;

    /*当前卡模板制卡数量 */
    private Long count;

    /*卡面额，单元分*/
    private String par_value;

    /*子制卡单ID*/
    private String card_name;

    public String getTemplate_no() {
        return template_no;
    }

    public void setTemplate_no(String template_no) {
        this.template_no = template_no;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getPar_value() {
        return par_value;
    }

    public void setPar_value(String par_value) {
        this.par_value = par_value;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }
}
