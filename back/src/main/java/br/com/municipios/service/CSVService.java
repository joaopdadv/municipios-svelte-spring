package br.com.municipios.service;

import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.csv.CSVEntity;
import br.com.municipios.entity.estados.Estado;
import br.com.municipios.entity.municipios.Municipio;
import br.com.municipios.repository.EstadoRepository;
import br.com.municipios.repository.MunicipioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CSVService {

    private final EstadoRepository estadoRepository;
    private final MunicipioRepository municipioRepository;

    public void importarCsv(MultipartFile file, User user) throws BadRequestException {
        try {
            List<CSVEntity> listCsv = leituraCsv(file);

            saveData(listCsv, user);

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
        System.out.println(Arrays.toString(campos));
        return new CSVEntity(
                campos[0],
                Integer.parseInt(campos[1]),
                campos[2],
                campos[3],
                campos[4],
                Integer.parseInt(campos[5])
        );
    }

    private void saveData(List<CSVEntity> listCsv, User user) {
        Map<Integer, List<CSVEntity>> municipiosAgrupadosPorEstado = listCsv.stream()
                .collect(Collectors.groupingBy(CSVEntity::getCodigoUf));

        for (Map.Entry<Integer, List<CSVEntity>> entry : municipiosAgrupadosPorEstado.entrySet()) {
            Integer codigoUf = entry.getKey();
            List<CSVEntity> municipiosCsv = entry.getValue();
            String nomeUf = municipiosCsv.get(0).getUf();

            Estado estado = estadoRepository.findByCodigoUfAndUser(codigoUf, user)
                    .orElseGet(() -> new Estado(nomeUf, codigoUf, user));

            Map<String, Municipio> municipiosExistentesMap = estado.getMunicipios().stream()
                    .collect(Collectors.toMap(Municipio::getCodigo, Function.identity()));

            for (CSVEntity csvEntity : municipiosCsv) {
                Municipio municipio = municipiosExistentesMap.get(csvEntity.getCodigoMunicipio());

                if (municipio != null) {
                    municipio.setNome(csvEntity.getNomeMunicipio());
                    municipio.setPopulacao((long) csvEntity.getPopulacao());
                    municipio.setCapital("sim".equalsIgnoreCase(csvEntity.getCapitalDeEstado()));
                } else {
                    Municipio novoMunicipio = new Municipio(
                            csvEntity.getNomeMunicipio(),
                            csvEntity.getCodigoMunicipio(),
                            (long) csvEntity.getPopulacao(),
                            "sim".equalsIgnoreCase(csvEntity.getCapitalDeEstado()),
                            estado
                    );
                    estado.addMunicipio(novoMunicipio);
                }
            }
            estadoRepository.save(estado);
        }
    }
}
