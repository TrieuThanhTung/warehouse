package selex.intern.test.service;

import org.springframework.stereotype.Service;
import selex.intern.test.dto.DeviceDto;
import selex.intern.test.exception.DeviceException;
import selex.intern.test.exception.UserException;
import selex.intern.test.exception.WarehouseException;
import selex.intern.test.model.Device;
import selex.intern.test.model.Role;
import selex.intern.test.model.UserEntity;
import selex.intern.test.model.Warehouse;
import selex.intern.test.repository.DeviceRepository;
import selex.intern.test.repository.WarehouseRepository;
import selex.intern.test.shared.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService{
    private DeviceRepository deviceRepository;
    private WarehouseRepository wareHouseRepository;
    private UserService userService;

    public DeviceServiceImpl(DeviceRepository deviceRepository, WarehouseRepository wareHouseRepository, UserService userService) {
        this.deviceRepository = deviceRepository;
        this.wareHouseRepository = wareHouseRepository;
        this.userService = userService;
    }

    @Override
    public Device addNewDevice(DeviceDto deviceData) throws DeviceException, WarehouseException, UserException {
        Optional<Device> existsDevice = deviceRepository.findDeviceByName(deviceData.getName());

        if(existsDevice.isPresent()) {
            throw new DeviceException(Message.NAME_IS_USED);
        }

        Warehouse wareHouse = wareHouseRepository.findById(deviceData.getWarehouseId())
                .orElseThrow(() -> new WarehouseException(Message.WAREHOUSE_NOT_FOUND));

        List<UserEntity> users = new ArrayList<>();
        users.add(userService.getCurrentUser());

        Device newDevice = new Device();
        newDevice.setName(deviceData.getName());
        newDevice.setQuantity(deviceData.getQuantity());
        newDevice.setWareHouse(wareHouse);
        newDevice.setUsers(users);

        return deviceRepository.save(newDevice);
    }

    @Override
    public List<Device> getAllDevice() {
        return deviceRepository.findAll();
    }

    @Override
    public Device getDeviceById(Integer id) throws UserException, DeviceException {
        UserEntity existsUser = userService.getCurrentUser();

        Device existsDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceException(Message.DEVICE_NOT_FOUND));

        if(existsUser.getRole().equals(Role.ADMIN)) {
            return existsDevice;
        }

        for(UserEntity user : existsDevice.getUsers()) {
            if(Objects.equals(user.getId(), existsUser.getId())) {
                return existsDevice;
            }
        }

        throw new DeviceException(Message.DEVICE_NOT_AUTHORIZED);
    }

    @Override
    public void deleteDeviceById(Integer id) throws UserException, DeviceException {
        UserEntity existsUser = userService.getCurrentUser();

        Device existsDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceException(Message.DEVICE_NOT_FOUND));

        if(existsUser.getRole().equals(Role.ADMIN)) {
            deviceRepository.delete(existsDevice);
            return;
        }

        for(UserEntity user : existsDevice.getUsers()) {
            if(user.getId() == existsUser.getId()) {
                deviceRepository.delete(existsDevice);
                return;
            }
        }

        throw new DeviceException(Message.DEVICE_NOT_AUTHORIZED);
    }

    @Override
    public Device changeDeviceById(Integer id, DeviceDto deviceDto) throws UserException, DeviceException, WarehouseException {
        UserEntity existsUser = userService.getCurrentUser();

        Device existsDevice = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceException(Message.DEVICE_NOT_FOUND));

        existsDevice.setName(deviceDto.getName());
        existsDevice.setQuantity(deviceDto.getQuantity());

        if(!Objects.equals(existsDevice.getWareHouse().getId(), deviceDto.getWarehouseId())) {
            Warehouse wareHouse = wareHouseRepository.findById(deviceDto.getWarehouseId())
                    .orElseThrow(() -> new WarehouseException(Message.WAREHOUSE_NOT_FOUND));
            existsDevice.setWareHouse(wareHouse);
        }

        if(existsUser.getRole().equals(Role.ADMIN)) {
            return deviceRepository.save(existsDevice);
        }

        for(UserEntity user : existsDevice.getUsers()) {
            if(user.getId() == existsUser.getId()) {
                return deviceRepository.save(existsDevice);
            }
        }

        throw new DeviceException(Message.DEVICE_NOT_AUTHORIZED);
    }
}
