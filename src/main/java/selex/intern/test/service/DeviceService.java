package selex.intern.test.service;

import selex.intern.test.dto.DeviceDto;
import selex.intern.test.exception.DeviceException;
import selex.intern.test.exception.UserException;
import selex.intern.test.exception.WarehouseException;
import selex.intern.test.model.Device;

import java.util.List;

public interface DeviceService {
    Device addNewDevice(DeviceDto deviceData) throws DeviceException, WarehouseException, UserException;

    List<Device> getAllDevice();

    Device getDeviceById(Integer id) throws UserException, DeviceException;

    void deleteDeviceById(Integer id) throws UserException, DeviceException;

    Device changeDeviceById(Integer id, DeviceDto deviceDto) throws UserException, DeviceException, WarehouseException;
}
