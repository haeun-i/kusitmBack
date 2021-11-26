package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.Market;
import com.kusitms.kusitms5.dto.MarketDto;
import com.kusitms.kusitms5.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;

    public List<MarketDto> findAll() { // 상설장 목록 불러오기
        List<Market> markets = marketRepository.findAll();
        List<MarketDto> marketDtos = new ArrayList<>();
        for(Market market : markets) {
            MarketDto response = new MarketDto(market);
            marketDtos.add(response);
        }
        return marketDtos;
    }


    public List<MarketDto> findPermanent() { // 상설장 목록 불러오기
        List<Market> markets = marketRepository.findPermanent();
        List<MarketDto> marketDtos = new ArrayList<>();
        for(Market market : markets) {
            MarketDto response = new MarketDto(market);
            marketDtos.add(response);
        }
        return marketDtos;
    }

    public List<MarketDto> findNotPermanent() { // 비상설장 목록 불러오기
        List<Market> markets = marketRepository.findNotPermanent();
        List<MarketDto> marketDtos = new ArrayList<>();
        for(Market market : markets) {
            MarketDto response = new MarketDto(market);
            marketDtos.add(response);
        }
        return marketDtos;
    }

    public List<MarketDto> findBig() { // 비상설장 목록 불러오기
        List<Market> markets = marketRepository.findBig();
        List<MarketDto> marketDtos = new ArrayList<>();
        for(Market market : markets) {
            MarketDto response = new MarketDto(market);
            marketDtos.add(response);
        }
        return marketDtos;
    }

    public List<MarketDto> findMedium() { // 비상설장 목록 불러오기
        List<Market> markets = marketRepository.findMedium();
        List<MarketDto> marketDtos = new ArrayList<>();
        for(Market market : markets) {
            MarketDto response = new MarketDto(market);
            marketDtos.add(response);
        }
        return marketDtos;
    }

    public List<MarketDto> findSmall() { // 비상설장 목록 불러오기
        List<Market> markets = marketRepository.findSmall();
        List<MarketDto> marketDtos = new ArrayList<>();
        for(Market market : markets) {
            MarketDto response = new MarketDto(market);
            marketDtos.add(response);
        }
        return marketDtos;
    }

    public List<MarketDto> findOne(String name) {
        List<Market> markets = marketRepository.findOne(name);
        List<MarketDto> marketDtos = new ArrayList<>();
        for(Market market : markets) {
            MarketDto response = new MarketDto(market);
            marketDtos.add(response);
        }
        return marketDtos;
    }

}
