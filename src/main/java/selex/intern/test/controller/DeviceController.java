package selex.intern.test.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import selex.intern.test.dto.DeviceDto;
import selex.intern.test.exception.DeviceException;
import selex.intern.test.exception.UserException;
import selex.intern.test.exception.WarehouseException;
import selex.intern.test.model.Device;
import selex.intern.test.service.DeviceService;
import selex.intern.test.shared.GenericResponse;
import selex.intern.test.shared.Message;
import selex.intern.test.shared.MessageResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @PostMapping()
    public ResponseEntity<GenericResponse> addNewDeviceHandler(@Valid @RequestBody DeviceDto deviceData)
            throws WarehouseException, UserException, DeviceException {
        Device device = deviceService.addNewDevice(deviceData);

        return ResponseEntity.ok(new GenericResponse(Message.ADD_SUCCESSFULLY, device));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<GenericResponse> getAllDeviceHandler() {
        List<Device> deviceList = deviceService.getAllDevice();

        return ResponseEntity.ok(new GenericResponse(Message.GET_SUCCESSFULLY, deviceList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getDeviceByIdHandler(@PathVariable("id") Integer id)
            throws UserException, DeviceException {
        Device device = deviceService.getDeviceById(id);

        return ResponseEntity.ok(new GenericResponse(Message.GET_SUCCESSFULLY, device));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> changeDeviceByIdHandler(@PathVariable("id") Integer id,
                                                                   @RequestBody DeviceDto deviceDto)
            throws UserException, DeviceException, WarehouseException {
        Device device = deviceService.changeDeviceById(id, deviceDto);

        return ResponseEntity.ok(new GenericResponse(Message.CHANGE_SUCCESSFULLY, device));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteDeviceByIdHandler(@PathVariable("id") Integer id)
            throws UserException, DeviceException {
        deviceService.deleteDeviceById(id);

        return ResponseEntity.ok(new MessageResponse(Message.DELETE_SUCCESSFULLY));
    }

}
