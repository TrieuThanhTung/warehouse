package selex.intern.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import selex.intern.test.model.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
}
