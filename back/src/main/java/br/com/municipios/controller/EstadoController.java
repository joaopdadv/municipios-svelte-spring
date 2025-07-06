package br.com.municipios.controller;

import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.estados.Estado;
import br.com.municipios.entity.estados.dto.EstadoFilterParamsDTO;
import br.com.municipios.entity.estados.dto.EstadoResponseDTO;
import br.com.municipios.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;

    @GetMapping
    public ResponseEntity<Page<EstadoResponseDTO>> getAll(
            @AuthenticationPrincipal User user,
            EstadoFilterParamsDTO filters,
            Pageable pageable
    ) throws BadRequestException {
        return ResponseEntity.ok(estadoService.getAll(user, filters, pageable));
    }
}