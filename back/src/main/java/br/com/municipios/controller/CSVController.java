package br.com.municipios.controller;

import br.com.municipios.entity.auth.User;
import br.com.municipios.service.CSVService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/csv")
@RequiredArgsConstructor
public class CSVController {

    private final CSVService csvService;

    @PostMapping("/upload")
    public ResponseEntity<String> lerCsv(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user
    ) throws BadRequestException {
        this.csvService.importarCsv(file, user);
        return ResponseEntity.ok("CSV importado com sucesso!");
    }
}
