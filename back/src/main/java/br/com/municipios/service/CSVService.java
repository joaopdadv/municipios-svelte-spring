package br.com.municipios.service;

import br.com.municipios.entity.csv.CSVEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CSVService {

    private final EstadoService estadoService;
    private final MunicipioService municipioService;

    public void importarCsv(MultipartFile file) {
        try {
            List<CSVEntity> listCsv = leituraCsv(file);

//            List<Estado> listEstados = this.saveEstados(listCsv);

        } catch (Exception e) {
//            logger.severe("Erro ao importar o CSV: " + e.getMessage());
            throw new RuntimeException("Erro ao importar o CSV", e);
        }
    }

    private List<CSVEntity> leituraCsv(MultipartFile file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String header = br.readLine(); // Read the header line

            List<CSVEntity> listLinhas = new ArrayList<>();
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";", -1);
                listLinhas.add(this.parseCamposCsvDTO(campos));
            }

            return listLinhas;
        } catch (Exception e) {
//            logger.severe("Erro ao ler o arquivo CSV: " + e.getMessage());
            throw new RuntimeException("Erro ao ler o arquivo CSV", e);
        }
    }

    private CSVEntity parseCamposCsvDTO(String[] campos) {
        return new CSVEntity(
//                campos[0],
//                campos[1],
//                campos[2],
//                campos[3],
//                campos[4],
//                campos[5],
//                campos[6],
//                campos[7],
//                campos[8],
//                campos[9],
//                this.parseInteger(campos[10]),
//                this.parseInteger(campos[11]),
//                this.parseInteger(campos[12]),
//                this.parseInteger(campos[13]),
//                this.parseInteger(campos[14]),
//                this.parseInteger(campos[15]),
//                this.parseInteger(campos[16]),
//                this.parseInteger(campos[17]),
//                this.parseInteger(campos[18])
        );
    }
}
