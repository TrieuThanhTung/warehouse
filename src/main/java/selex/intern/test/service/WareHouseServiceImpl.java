package selex.intern.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import selex.intern.test.dto.WareHouseDto;
import selex.intern.test.exception.WareHouseException;
import selex.intern.test.model.WareHouse;
import selex.intern.test.repository.WareHouseRepository;
import selex.intern.test.shared.Message;

import java.util.List;
import java.util.Optional;

@Service
public class WareHouseServiceImpl implements WareHouseService{
    @Autowired
    private WareHouseRepository wareHouseRepository;

    @Override
    public WareHouse addWareHouse(WareHouseDto wareHouseData) throws WareHouseException {
        Optional<WareHouse> existsWareHouse = wareHouseRepository.findWareHouseByName(wareHouseData.getName());

        if(existsWareHouse.isPresent()) {
            throw new WareHouseException(Message.NAME_IS_USED);
        }

        WareHouse newWareHouse = WareHouse.builder()
                .name(wareHouseData.getName())
                .address(wareHouseData.getAddress())
                .build();

        return wareHouseRepository.save(newWareHouse);
    }

    @Override
    public List<WareHouse> getAllWareHouse() {
        return wareHouseRepository.findAll();
    }

    @Override
    public WareHouse getWareHouseById(Integer id) throws WareHouseException {
        WareHouse wareHouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new WareHouseException(Message.WAREHOUSE_NOT_FOUND));

        return wareHouse;
    }

    @Override
    public void deleteWareHouseById(Integer id) throws WareHouseException {
        WareHouse wareHouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new WareHouseException(Message.WAREHOUSE_NOT_FOUND));

        wareHouseRepository.delete(wareHouse);
    }

    @Override
    public WareHouse changeWareHouse(Integer id, WareHouseDto wareHouseData) throws WareHouseException {
        Optional<WareHouse> existsWareHouseWithName = wareHouseRepository.findWareHouseByName(wareHouseData.getName());
        if(existsWareHouseWithName.isPresent()) {
            throw new WareHouseException(Message.NAME_IS_USED);
        }

        WareHouse wareHouse = wareHouseRepository.findById(id)
                .orElseThrow(() -> new WareHouseException(Message.WAREHOUSE_NOT_FOUND));

        wareHouse.setName(wareHouseData.getName());
        wareHouse.setAddress(wareHouseData.getAddress());

        return wareHouseRepository.save(wareHouse);
    }
}
