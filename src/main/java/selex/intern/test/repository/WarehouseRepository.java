package selex.intern.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import selex.intern.test.model.Warehouse;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    Optional<Warehouse> findWareHouseByName(String name);
}
