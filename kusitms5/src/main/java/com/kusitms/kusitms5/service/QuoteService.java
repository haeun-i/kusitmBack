package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.dto.PriceModel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuoteService {

    // 인증키
    private final String key = "4751567074646d733635726c7a4f73";

    private final String[] types = {"사과", "호박", "돼지고기"};



    // 마트를 말하면 그에 맞는 품목별 가격 제공

    @Transactional
    public List<PriceModel> getPriceInfoByMarket(String marketName) {

        Date latest = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String strlatest = simpleDateFormat.format(latest); //지정한 포맷으로 변환

        // 응답한 obj받을 리스트
        List<PriceModel> priceModelList = new ArrayList<>();

        //http://openAPI.seoul.go.kr:8088/(인증키)/xml/ListNecessariesPricesService/요청시작위치/요청종료위치/시장,마트이름/품목/년도-월/시장유형구분이름/자치구이름

        // 품목별로, 가장 최신의 값을 보내주기
        for (String type:
                types) {

            String url = "http://openAPI.seoul.go.kr:8088/" + key +
                    "/json/ListNecessariesPricesService/1/2" +
                    "/" +marketName +
                    "/" + type +
                    "/" + strlatest +
                    "/시장";
                    // /자치구명


            System.out.println("url : " + url);
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);


            JSONObject jObject = new JSONObject(response);

            JSONObject jListService = jObject.getJSONObject("ListNecessariesPricesService");
            JSONArray jArray = jListService.getJSONArray("row");

            System.out.println("response : " +jArray);

            for(int i=0; i<jArray.length(); i++) {

                JSONObject obj = jArray.getJSONObject(i);

                // A_PRICE : String 가격
                String price = obj.getString("A_PRICE");

                // P_YEAR_MONTH : String 업데이트 날짜
                String unit = obj.getString("A_UNIT");

                // M_NAME : String 시장이름
                String m_name = obj.getString("M_NAME");
                System.out.println(m_name);

                // A_NAME : 품목명
                String a_name = obj.getString("A_NAME");
                System.out.println(a_name);

                PriceModel priceModel = new PriceModel(price, unit, m_name, a_name);
                priceModelList.add(priceModel);
            }

            System.out.println("ERROR IN FOR");
        }
        System.out.println("ERROR IN FOREACH");
        return priceModelList;
    }

    @Transactional
    public List<PriceModel> getPriceInfoByCategory(String category){
        Date latest = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String strlatest = simpleDateFormat.format(latest); //지정한 포맷으로 변환

        // 응답한 obj받을 리스트
        List<PriceModel> priceModelList = new ArrayList<>();

        //http://openAPI.seoul.go.kr:8088/(인증키)/xml/ListNecessariesPricesService/요청시작위치/요청종료위치/시장,마트이름/품목/년도-월/시장유형구분이름/자치구이름

        String url = "http://openAPI.seoul.go.kr:8088/" + key +
                "/json/ListNecessariesPricesService/1/1000" +
                "/시장" +
                "/" + category +
                "/" + strlatest +
                "/시장";
        // /자치구명

        System.out.println("url : " + url);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);


        JSONObject jObject = new JSONObject(response);

        JSONObject jListService = jObject.getJSONObject("ListNecessariesPricesService");
        JSONArray jArray = jListService.getJSONArray("row");

        System.out.println("response : " +jArray);


        for(int i=0; i<jArray.length(); i++) {

            JSONObject obj = jArray.getJSONObject(i);

            // A_PRICE : String 가격
            String price = obj.getString("A_PRICE");

            // P_YEAR_MONTH : String 업데이트 날짜
            String date = obj.getString("A_UNIT");

            // M_NAME : String 시장이름
            String m_name = obj.getString("M_NAME");
            System.out.println(m_name);

            // A_NAME : 품목명
            String a_name = obj.getString("A_NAME");
            System.out.println(a_name);

            PriceModel priceModel = new PriceModel(price, date, m_name, a_name);
            priceModelList.add(priceModel);
        }


        return priceModelList;
    }


}
