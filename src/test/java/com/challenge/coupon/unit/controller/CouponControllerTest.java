package com.challenge.coupon.unit.controller;

import com.challenge.coupon.controller.CouponController;
import com.challenge.coupon.dto.CouponDTO;
import com.challenge.coupon.dto.CouponResponseDTO;
import com.challenge.coupon.service.CouponService;
import com.challenge.coupon.util.CouponTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CouponController.class)
@DisplayName("Testes de Unidade: CouponController")
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CouponService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve retornar 200 OK ao criar um cupom válido")
    void shouldReturnOkOnCreate() throws Exception {
        CouponDTO dto = CouponTestUtil.createValidCouponDTO();
        CouponResponseDTO response = CouponTestUtil.createValidResponseDTO(UUID.randomUUID());

        when(service.create(any(CouponDTO.class))).thenReturn(response);

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(response.code()));    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request ao enviar DTO inválido")
    void shouldReturnBadRequestOnInvalidDto() throws Exception {
        CouponDTO invalidDto = CouponTestUtil.createValidCouponDTO().toBuilder()
                .code("")
                .build();

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Erro de Validação"));
    }

    @Test
    @DisplayName("Deve retornar 200 OK ao buscar por ID")
    void shouldReturnOkOnFindById() throws Exception {
        UUID id = UUID.randomUUID();
        CouponResponseDTO response = CouponTestUtil.createValidResponseDTO(id);

        when(service.findById(id)).thenReturn(response);

        mockMvc.perform(get("/coupon/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    @DisplayName("Deve retornar 204 No Content ao deletar")
    void shouldReturnNoContentOnDelete() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(service).delete(id);

        mockMvc.perform(delete("/coupon/{id}", id))
                .andExpect(status().isNoContent());
    }
}