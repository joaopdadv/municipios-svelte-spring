package br.com.municipios.controller;

import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.municipios.dto.MunicipioRequestDTO;
import br.com.municipios.entity.municipios.dto.MunicipioResponseDTO;
import br.com.municipios.service.MunicipioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/municipios")
@RequiredArgsConstructor
public class MunicipioController {

    private final MunicipioService municipioService;

    // Nota: O Get All e Get By Id para municípios são geralmente feitos através do EstadoController.
    // Estes endpoints são para criação e manipulação direta.

    @PostMapping("/estado/{estadoId}")
    public ResponseEntity<MunicipioResponseDTO> create(
            @PathVariable Long estadoId,
            @RequestBody MunicipioRequestDTO request,
            @AuthenticationPrincipal User user) {

        MunicipioResponseDTO response = municipioService.create(estadoId, request, user);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/municipios/{id}")
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{municipioId}")
    public ResponseEntity<MunicipioResponseDTO> update(
            @PathVariable Long municipioId,
            @RequestBody MunicipioRequestDTO request,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(municipioService.update(municipioId, request, user));
    }

    @DeleteMapping("/{municipioId}")
    public ResponseEntity<Void> delete(@PathVariable Long municipioId, @AuthenticationPrincipal User user) {
        municipioService.delete(municipioId, user);
        return ResponseEntity.noContent().build();
    }
}