package br.com.municipios.service;

import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.estados.Estado;
import br.com.municipios.entity.estados.dto.EstadoFilterParamsDTO;
import br.com.municipios.entity.estados.dto.EstadoResponseDTO;
import br.com.municipios.entity.municipios.Municipio;
import br.com.municipios.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoRepository;

    public Page<EstadoResponseDTO> getAll(User user, EstadoFilterParamsDTO filters, Pageable pageable) {
        Page<Object[]> results = estadoRepository.findAllFiltered(
                user,
                filters.getUf(),
                filters.getPopulacaoIni(),
                filters.getPopulacaoFim(),
                pageable
        );

        return results.map(result -> {
            Estado estado = (Estado) result[0];
            Municipio municipioMaisPopuloso = (Municipio) result[1];
            Long populacaoTotal = (Long) result[2];

            return new EstadoResponseDTO(
                    estado.getId(),
                    estado.getUf(),
                    estado.getCodigoUf(),
                    populacaoTotal,
                    municipioMaisPopuloso
            );
        });
    }
}