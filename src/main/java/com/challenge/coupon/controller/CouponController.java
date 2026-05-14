package com.challenge.coupon.controller;

import com.challenge.coupon.dto.CouponDTO;
import com.challenge.coupon.dto.CouponResponseDTO;
import com.challenge.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/coupon")
@Tag(name = "Coupon", description = "Recursos para gerenciamento de cupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService service;


    @Operation(summary = "Cria um novo cupom", description = "Gera um UUID automaticamente e valida regras de negócio")
    @PostMapping
    public ResponseEntity<CouponResponseDTO> create(@RequestBody @Valid CouponDTO dto) {
        CouponResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Busca um cupom por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> findById(@PathVariable UUID id) {
        CouponResponseDTO response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Executa o soft delete de um cupom")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Resgata um cupom")
    @PatchMapping("/{id}/redemption")
    public ResponseEntity<Void> redeem(@PathVariable UUID id) {
        service.redeem(id);
        return ResponseEntity.noContent().build();
    }
}