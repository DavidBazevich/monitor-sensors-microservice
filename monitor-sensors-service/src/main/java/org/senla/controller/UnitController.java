package org.senla.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.senla.dto.UnitDto;
import org.senla.dto.creators.UnitCreateDto;
import org.senla.service.Impl.UnitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/unit")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UnitController {

    private final UnitService unitService;

    @GetMapping("/{id}")
    @Operation(summary = "Find by id")
    public ResponseEntity<UnitDto> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(unitService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UnitDto>> findAllUnits(){
        return ResponseEntity.ok(unitService.findAllUnits());
    }

    @PostMapping("/save")
    public ResponseEntity<UnitDto> addUnit(@RequestBody UnitCreateDto unit){
        return new ResponseEntity<>(unitService.saveUnit(unit), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitDto> updateUnit(@PathVariable("id") Integer id,
                                              @RequestBody UnitCreateDto newUnit){
       return ResponseEntity.ok(unitService.updateUnit(id, newUnit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable("id") Integer id){
        unitService.deleteUnitById(id);
        return ResponseEntity.noContent().build();
    }

}
