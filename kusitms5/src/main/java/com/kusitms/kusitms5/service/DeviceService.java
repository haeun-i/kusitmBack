package com.kusitms.kusitms5.service;

import com.kusitms.kusitms5.domain.Device;
import com.kusitms.kusitms5.repository.DeviceRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public String UpdateDeviceToken(Long userId, String token) {
        Device device = deviceRepository.findByUser(userId);
        device.setToken(token);// update -> default : true

        return device.getToken();
    }


}
