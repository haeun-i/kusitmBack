package com.kusitms.kusitms5.dto;

public class PriceModel {

    // A_PRICE : String 가격
    String price;
    // P_YEAR_MONTH : String 업데이트 날짜
    String date;
    // M_NAME : String 시장이름
    String m_name;
    // A_NAME : 품목명
    String a_name;

    public PriceModel() {
    }

    public PriceModel(String price, String date, String m_name, String a_name) {
        this.price = price;
        this.date = date;
        this.m_name = m_name;
        this.a_name = a_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }
}
