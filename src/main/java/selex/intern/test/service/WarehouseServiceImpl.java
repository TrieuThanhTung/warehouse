package selex.intern.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selex.intern.test.dto.WarehouseDto;
import selex.intern.test.exception.WarehouseException;
import selex.intern.test.model.Warehouse;
import selex.intern.test.repository.WarehouseRepository;
import selex.intern.test.shared.Message;

import java.util.List;
import java.util.Optional;

@Service
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseRepository wareHouseRepository;

    @Override
    public Warehouse addWareHouse(WarehouseDto wareHouseData) throws WarehouseException {
        Optional<Warehouse> existsWareHouse = wareHouseRepository.findWareHouseByName(wareHouseData.getName());

        if(existsWareHouse.isPresent()) {
            throw new WarehouseException(Message.NAME_IS_USED);
        }

        Warehouse newWareHouse = Warehouse.builder()
                .name(wareHouseData.getName())
                .address(wareHouseData.getAddress())
                .build();

        return wareHouseRepository.save(newWareHouse);
    }

    @Override
    public List<Warehouse> getAllWareHouse() {
        return wareHouseRepository.findAll();
    }

    @Override
    public Warehouse getWareHouseById(Integer id) throws WarehouseException {
        Warehouse wareHouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new WarehouseException(Message.WAREHOUSE_NOT_FOUND));

        return wareHouse;
    }

    @Override
    public void deleteWareHouseById(Integer id) throws WarehouseException {
        Warehouse wareHouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new WarehouseException(Message.WAREHOUSE_NOT_FOUND));

        wareHouseRepository.delete(wareHouse);
    }

    @Override
    public Warehouse changeWareHouse(Integer id, WarehouseDto wareHouseData) throws WarehouseException {
        Optional<Warehouse> existsWareHouseWithName = wareHouseRepository.findWareHouseByName(wareHouseData.getName());
        if(existsWareHouseWithName.isPresent()) {
            throw new WarehouseException(Message.NAME_IS_USED);
        }

        Warehouse wareHouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new WarehouseException(Message.WAREHOUSE_NOT_FOUND));

        wareHouse.setName(wareHouseData.getName());
        wareHouse.setAddress(wareHouseData.getAddress());

        return wareHouseRepository.save(wareHouse);
    }
}
