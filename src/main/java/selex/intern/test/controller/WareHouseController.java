package selex.intern.test.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import selex.intern.test.dto.WareHouseDto;
import selex.intern.test.exception.WareHouseException;
import selex.intern.test.model.WareHouse;
import selex.intern.test.service.WareHouseService;
import selex.intern.test.shared.GenericResponse;
import selex.intern.test.shared.Message;
import selex.intern.test.shared.MessageResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouse")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class WareHouseController {
    @Autowired
    private WareHouseService wareHouseService;

    @PostMapping()
    public ResponseEntity<GenericResponse> addNewWareHouse(@Valid @RequestBody WareHouseDto wareHouseData) throws WareHouseException {
        WareHouse wareHouse =  wareHouseService.addWareHouse(wareHouseData);

        return ResponseEntity.ok(new GenericResponse(Message.WAREHOUSE_ADD_SUCCESSFULLY, wareHouse));
    }

    @GetMapping()
    public ResponseEntity<GenericResponse> getAllWareHouseHandler() {
        List<WareHouse> wareHouseList = wareHouseService.getAllWareHouse();

        return ResponseEntity.ok(new GenericResponse(Message.GET_SUCCESSFULLY, wareHouseList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getWareHouseByIdHandler(@PathVariable("id") Integer id)
            throws WareHouseException {
        WareHouse wareHouse = wareHouseService.getWareHouseById(id);

        return ResponseEntity.ok(new GenericResponse(Message.GET_SUCCESSFULLY, wareHouse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> changeWareHouseHandler(@PathVariable("id") Integer id,
                                                                      @Valid @RequestBody WareHouseDto wareHouseData)
            throws WareHouseException {
        WareHouse wareHouse = wareHouseService.changeWareHouse(id, wareHouseData);

        return ResponseEntity.ok(new GenericResponse(Message.CHANGE_SUCCESSFULLY, wareHouse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteWareHouseByIdHandler(@PathVariable("id") Integer id)
            throws WareHouseException {
        wareHouseService.deleteWareHouseById(id);

        return ResponseEntity.ok(new MessageResponse(Message.DELETE_SUCCESSFULLY));
    }
}
