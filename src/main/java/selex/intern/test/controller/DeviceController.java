package selex.intern.test.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Add new device",
            description = "Both user and admin can add a new device"
    )
    @PostMapping()
    public ResponseEntity<GenericResponse> addNewDeviceHandler(@Valid @RequestBody DeviceDto deviceData)
            throws WarehouseException, UserException, DeviceException {
        Device device = deviceService.addNewDevice(deviceData);

        return ResponseEntity.ok(new GenericResponse(Message.ADD_SUCCESSFULLY, device));
    }

    @Operation(
            summary = "Get all devices",
            description = "Only admin can get all devices"
    )
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<GenericResponse> getAllDeviceHandler() {
        List<Device> deviceList = deviceService.getAllDevice();

        return ResponseEntity.ok(new GenericResponse(Message.GET_SUCCESSFULLY, deviceList));
    }

    @Operation(
            summary = "Get device by id",
            description = "Users can only retrieve authorized devices. Admin can retrieve all devices"
    )
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getDeviceByIdHandler(@PathVariable("id") Integer id)
            throws UserException, DeviceException {
        Device device = deviceService.getDeviceById(id);

        return ResponseEntity.ok(new GenericResponse(Message.GET_SUCCESSFULLY, device));
    }

    @Operation(
            summary = "Change device information",
            description = "Users can only change authorized devices. Admin can change all devices"
    )
    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> changeDeviceByIdHandler(@PathVariable("id") Integer id,
                                                                   @RequestBody DeviceDto deviceDto)
            throws UserException, DeviceException, WarehouseException {
        Device device = deviceService.changeDeviceById(id, deviceDto);

        return ResponseEntity.ok(new GenericResponse(Message.CHANGE_SUCCESSFULLY, device));
    }

    @Operation(
            summary = "Remove device",
            description = "Users can only remove authorized devices. Admin can remove all devices"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteDeviceByIdHandler(@PathVariable("id") Integer id)
            throws UserException, DeviceException {
        deviceService.deleteDeviceById(id);

        return ResponseEntity.ok(new MessageResponse(Message.DELETE_SUCCESSFULLY));
    }

}
