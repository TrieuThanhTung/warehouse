package selex.intern.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import selex.intern.test.model.WareHouse;

import java.util.Optional;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Integer> {
    Optional<WareHouse> findWareHouseByName(String name);
}
