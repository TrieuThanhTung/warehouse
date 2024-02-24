package selex.intern.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import selex.intern.test.dto.WarehouseDto;
import selex.intern.test.exception.WarehouseException;
import selex.intern.test.model.Warehouse;
import selex.intern.test.service.WarehouseService;
import selex.intern.test.shared.GenericResponse;
import selex.intern.test.shared.Message;
import selex.intern.test.shared.MessageResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouse")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class WarehouseController {
    @Autowired
    private WarehouseService wareHouseService;

    @Operation(
            summary = "Add new warehouse",
            description = "Only Admin can add a new warehouse"
    )
    @PostMapping()
    public ResponseEntity<GenericResponse> addNewWareHouse(@Valid @RequestBody WarehouseDto wareHouseData) throws WarehouseException {
        Warehouse wareHouse =  wareHouseService.addWareHouse(wareHouseData);

        return ResponseEntity.ok(new GenericResponse(Message.ADD_SUCCESSFULLY, wareHouse));
    }

    @Operation(
            summary = "Get all warehouses",
            description = "Only Admin can get all warehouses"
    )
    @GetMapping()
    public ResponseEntity<GenericResponse> getAllWareHouseHandler() {
        List<Warehouse> wareHouseList = wareHouseService.getAllWareHouse();

        return ResponseEntity.ok(new GenericResponse(Message.GET_SUCCESSFULLY, wareHouseList));
    }

    @Operation(
            summary = "Get a warehouse by id",
            description = "Only admin can get a warehouse by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getWareHouseByIdHandler(@PathVariable("id") Integer id)
            throws WarehouseException {
        Warehouse wareHouse = wareHouseService.getWareHouseById(id);

        return ResponseEntity.ok(new GenericResponse(Message.GET_SUCCESSFULLY, wareHouse));
    }

    @Operation(
            summary = "Change warehouse information by id",
            description = "Only admin can change a warehouse by id"
    )
    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> changeWareHouseHandler(@PathVariable("id") Integer id,
                                                                      @Valid @RequestBody WarehouseDto wareHouseData)
            throws WarehouseException {
        Warehouse wareHouse = wareHouseService.changeWareHouse(id, wareHouseData);

        return ResponseEntity.ok(new GenericResponse(Message.CHANGE_SUCCESSFULLY, wareHouse));
    }

    @Operation(
            summary = "Remove warehouse by id",
            description = "Only admin can remove warehouse by id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteWareHouseByIdHandler(@PathVariable("id") Integer id)
            throws WarehouseException {
        wareHouseService.deleteWareHouseById(id);

        return ResponseEntity.ok(new MessageResponse(Message.DELETE_SUCCESSFULLY));
    }
}
