package br.com.municipios.controller;

import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.municipios.Municipio;
import br.com.municipios.entity.municipios.dto.MunicipioFilterParamsDTO;
import br.com.municipios.service.MunicipioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/municipios")
@RequiredArgsConstructor
public class MunicipioController {

    private final MunicipioService municipioService;

    @GetMapping
    public ResponseEntity<Page<Municipio>> getAll(
            @AuthenticationPrincipal User user,
            MunicipioFilterParamsDTO filters,
            Pageable pageable
    ) {
        return ResponseEntity.ok(municipioService.getAll(user, pageable, filters));
    }

}