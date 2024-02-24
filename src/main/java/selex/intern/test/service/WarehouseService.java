package selex.intern.test.service;

import selex.intern.test.dto.WarehouseDto;
import selex.intern.test.exception.WarehouseException;
import selex.intern.test.model.Warehouse;

import java.util.List;

public interface WarehouseService {
    Warehouse addWareHouse(WarehouseDto wareHouseData) throws WarehouseException;

    List<Warehouse> getAllWareHouse();

    Warehouse getWareHouseById(Integer id) throws WarehouseException;

    void deleteWareHouseById(Integer id) throws WarehouseException;

    Warehouse changeWareHouse(Integer id, WarehouseDto wareHouseData) throws WarehouseException;
}
