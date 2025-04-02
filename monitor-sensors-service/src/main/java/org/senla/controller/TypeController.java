package org.senla.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.senla.dto.TypeDto;
import org.senla.dto.creators.TypeCreateDto;
import org.senla.service.Impl.TypeServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/type")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TypeController {

    private final TypeServiceImp typeService;

    @GetMapping("/{id}")
    @Operation(summary = "Find by id")
    public ResponseEntity<TypeDto> findById(@PathVariable Integer id){
        return ResponseEntity.ok(typeService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<TypeDto>> findAllTypes(){
        return ResponseEntity.ok(typeService.findAllTypes());
    }

    @PostMapping("/save")
    public ResponseEntity<TypeDto> addType(@RequestBody TypeCreateDto type){
        return new ResponseEntity<>(typeService.saveType(type), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeDto> updateUnit(@PathVariable Integer id,
                                              @RequestBody TypeCreateDto newType){
        return ResponseEntity.ok(typeService.updateType(id, newType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Integer id){
        typeService.deleteTypeById(id);
        return ResponseEntity.noContent().build();
    }


}
