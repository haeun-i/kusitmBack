package com.kusitms.kusitms5.price;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@Service
public class PriceService {
    private String key = "4751567074646d733635726c7a4f73";

    public void getProductCodeByKeword() { // 키워드로 검색해 가장 상위에 있는 물품 한 개의 상품 코드 반환
//        String url = "http://openapi.11st.co.kr/openapi/OpenApiService.tmall?key="+key+"&apiCode=ProductSearch&keyword="+keyword;
        String url = "http://openapi.seoul.go.kr:8088/4751567074646d733635726c7a4f73/json/ListNecessariesPricesService/1/1";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = (Document) builder.parse(url);

            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();
//            // keyword에 대한 상품 리스트 조회
            NodeList productList = doc.getElementsByTagName("row"); // Product 리스트
            System.out.println(productList);
//            Node product = productList.item(0); // 상품 리스트 중 가장 위에 있는 물품 한 개
//            NodeList infoList = product.getChildNodes(); // 상품의 정보
//            for (int i=0; i<infoList.getLength(); i++) {
//                Node info = infoList.item(i);
//                System.out.println(info.getNodeName());
//                if (info.getNodeType() == Node.ELEMENT_NODE) {
//                    if(info.getNodeName().equals("ProductCode")) {
//                        return info.getTextContent();
//                    }
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}