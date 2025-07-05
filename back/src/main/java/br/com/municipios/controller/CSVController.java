package br.com.municipios.controller;

import br.com.municipios.service.CSVService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/csv")
@RequiredArgsConstructor
public class CSVController {

    private final CSVService csvService;

    @PostMapping("/upload")
    public ResponseEntity<String> lerCsv(
            @RequestParam("file") MultipartFile file
    ) throws BadRequestException{
        try {
            this.csvService.importarCsv(file);
            return ResponseEntity.ok("CSV importado com sucesso!");
        } catch (Exception e) {
            throw new BadRequestException("Erro ao importar csv");
        }
    }
}
