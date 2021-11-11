package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.Market;
import com.kusitms.kusitms5.domain.Store;
import com.kusitms.kusitms5.dto.marketDto;
import com.kusitms.kusitms5.dto.storeDto;
import com.kusitms.kusitms5.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;

    public List<marketDto> findPermanent() { // 상설장 목록 불러오기
        List<Market> markets = marketRepository.findPermanent();
        List<marketDto> marketDtos = new ArrayList<>();
        for(Market market : markets) {
            marketDto response = new marketDto(market);
            marketDtos.add(response);
        }
        return marketDtos;
    }

    public List<marketDto> findNotPermanent() { // 비상설장 목록 불러오기
        List<Market> markets = marketRepository.findNotPermanent();
        List<marketDto> marketDtos = new ArrayList<>();
        for(Market market : markets) {
            marketDto response = new marketDto(market);
            marketDtos.add(response);
        }
        return marketDtos;
    }

    public List<marketDto> findBig() { // 비상설장 목록 불러오기
        List<Market> markets = marketRepository.findBig();
        List<marketDto> marketDtos = new ArrayList<>();
        for(Market market : markets) {
            marketDto response = new marketDto(market);
            marketDtos.add(response);
        }
        return marketDtos;
    }

    public List<marketDto> findMedium() { // 비상설장 목록 불러오기
        List<Market> markets = marketRepository.findMedium();
        List<marketDto> marketDtos = new ArrayList<>();
        for(Market market : markets) {
            marketDto response = new marketDto(market);
            marketDtos.add(response);
        }
        return marketDtos;
    }

    public List<marketDto> findSmall() { // 비상설장 목록 불러오기
        List<Market> markets = marketRepository.findSmall();
        List<marketDto> marketDtos = new ArrayList<>();
        for(Market market : markets) {
            marketDto response = new marketDto(market);
            marketDtos.add(response);
        }
        return marketDtos;
    }

    public List<marketDto> findOne(String name) {
        List<Market> markets = marketRepository.findOne(name);
        List<marketDto> marketDtos = new ArrayList<>();
        for(Market market : markets) {
            marketDto response = new marketDto(market);
            marketDtos.add(response);
        }
        return marketDtos;
    }

}
