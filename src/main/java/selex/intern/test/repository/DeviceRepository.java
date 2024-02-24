package selex.intern.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import selex.intern.test.model.Device;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    Optional<Device> findDeviceByName(String name);
}
