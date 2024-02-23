package selex.intern.test.service;

import selex.intern.test.dto.WareHouseDto;
import selex.intern.test.exception.WareHouseException;
import selex.intern.test.model.WareHouse;

import java.util.List;

public interface WareHouseService {
    WareHouse addWareHouse(WareHouseDto wareHouseData) throws WareHouseException;

    List<WareHouse> getAllWareHouse();

    WareHouse getWareHouseById(Integer id) throws WareHouseException;

    void deleteWareHouseById(Integer id) throws WareHouseException;

    WareHouse changeWareHouse(Integer id, WareHouseDto wareHouseData) throws WareHouseException;
}
