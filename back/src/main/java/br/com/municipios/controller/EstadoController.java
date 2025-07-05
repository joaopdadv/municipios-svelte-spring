package br.com.municipios.controller;

import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.estados.dto.EstadoRequestDTO;
import br.com.municipios.entity.estados.dto.EstadoResponseDTO;
import br.com.municipios.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<EstadoResponseDTO>> getAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(estadoService.findAllByUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoResponseDTO> getById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(estadoService.findByIdAndUser(id, user));
    }

    @PostMapping
    public ResponseEntity<EstadoResponseDTO> create(@RequestBody EstadoRequestDTO request, @AuthenticationPrincipal User user) {
        EstadoResponseDTO response = estadoService.create(request, user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoResponseDTO> update(@PathVariable Long id, @RequestBody EstadoRequestDTO request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(estadoService.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        estadoService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}