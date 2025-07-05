package br.com.municipios.service;

import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.estados.Estado;
import br.com.municipios.entity.municipios.Municipio;
import br.com.municipios.entity.municipios.dto.MunicipioRequestDTO;
import br.com.municipios.entity.municipios.dto.MunicipioResponseDTO;
import br.com.municipios.repository.EstadoRepository;
import br.com.municipios.repository.MunicipioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MunicipioService {

    private final MunicipioRepository municipioRepository;
    private final EstadoRepository estadoRepository;

    @Transactional
    public MunicipioResponseDTO create(Long estadoId, MunicipioRequestDTO requestDTO, User user) {
        Estado estado = estadoRepository.findByIdAndUser(estadoId, user)
                .orElseThrow(() -> new EntityNotFoundException("Estado não encontrado ou não pertence ao usuário."));

        Municipio municipio = new Municipio(requestDTO.getNome(), requestDTO.getCodigo(), requestDTO.getPopulacao(), requestDTO.isCapital(), estado);
        Municipio savedMunicipio = municipioRepository.save(municipio);

        return toResponseDTO(savedMunicipio);
    }

    @Transactional
    public MunicipioResponseDTO update(Long municipioId, MunicipioRequestDTO requestDTO, User user) {
        Municipio municipio = municipioRepository.findById(municipioId)
                .orElseThrow(() -> new EntityNotFoundException("Município não encontrado."));

        if (!municipio.getEstado().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Acesso negado. O estado deste município não pertence ao usuário.");
        }

        municipio.setNome(requestDTO.getNome());
        municipio.setCodigo(requestDTO.getCodigo());
        municipio.setPopulacao(requestDTO.getPopulacao());
        municipio.setCapital(requestDTO.isCapital());

        Municipio updatedMunicipio = municipioRepository.save(municipio);
        return toResponseDTO(updatedMunicipio);
    }

    @Transactional
    public void delete(Long municipioId, User user) {
        Municipio municipio = municipioRepository.findById(municipioId)
                .orElseThrow(() -> new EntityNotFoundException("Município não encontrado."));

        if (!municipio.getEstado().getUser().getId().equals(user.getId())) {
            throw new SecurityException("Acesso negado para deletar este município.");
        }

        municipioRepository.delete(municipio);
    }

    private MunicipioResponseDTO toResponseDTO(Municipio municipio) {
        return new MunicipioResponseDTO(
                municipio.getId(),
                municipio.getNome(),
                municipio.getCodigo(),
                municipio.getPopulacao(),
                municipio.isCapital(),
                municipio.getEstado().getId()
        );
    }
}