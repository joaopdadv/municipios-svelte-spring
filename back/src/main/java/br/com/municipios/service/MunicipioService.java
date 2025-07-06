package br.com.municipios.service;

import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.municipios.Municipio;
import br.com.municipios.entity.municipios.dto.MunicipioFilterParamsDTO;
import br.com.municipios.repository.MunicipioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MunicipioService {

    private final MunicipioRepository municipioRepository;

    public Page<Municipio> getAll(User user, Pageable pageable, MunicipioFilterParamsDTO filters) {
        return municipioRepository.findAllFiltered(
                user,
                filters.getNome(),
                filters.getCapital(),
                filters.getPopulacaoIni(),
                filters.getPopulacaoFim(),
                pageable
        );
    }
}