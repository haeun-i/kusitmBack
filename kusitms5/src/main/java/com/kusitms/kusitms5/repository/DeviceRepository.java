package com.kusitms.kusitms5.repository;

import com.kusitms.kusitms5.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findByUser(Long userId);
    Device findByToken(String token);
    void delete(Device device);
}
