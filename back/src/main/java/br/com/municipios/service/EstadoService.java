package br.com.municipios.service;

import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.estados.Estado;
import br.com.municipios.entity.estados.dto.EstadoRequestDTO;
import br.com.municipios.entity.estados.dto.EstadoResponseDTO;
import br.com.municipios.entity.municipios.dto.MunicipioResponseDTO;
import br.com.municipios.repository.EstadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public List<EstadoResponseDTO> findAllByUser(User user) {
        return estadoRepository.findByUser(user).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EstadoResponseDTO findByIdAndUser(Long id, User user) {
        Estado estado = estadoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Estado não encontrado ou não pertence ao usuário."));
        return toResponseDTO(estado);
    }

    @Transactional
    public EstadoResponseDTO create(EstadoRequestDTO requestDTO, User user) {
        Estado estado = new Estado(requestDTO.getUf(), requestDTO.getCodigoUf(), user);
        Estado savedEstado = estadoRepository.save(estado);
        return toResponseDTO(savedEstado);
    }

    @Transactional
    public EstadoResponseDTO update(Long id, EstadoRequestDTO requestDTO, User user) {
        Estado estado = estadoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new EntityNotFoundException("Estado não encontrado ou não pertence ao usuário."));

        estado.setUf(requestDTO.getUf());
        estado.setCodigoUf(requestDTO.getCodigoUf());

        Estado updatedEstado = estadoRepository.save(estado);
        return toResponseDTO(updatedEstado);
    }

    @Transactional
    public void delete(Long id, User user) {
        if (!estadoRepository.existsById(id)) {
            throw new EntityNotFoundException("Estado não encontrado.");
        }

        estadoRepository.findByIdAndUser(id, user).ifPresentOrElse(
                estadoRepository::delete,
                () -> { throw new SecurityException("Acesso negado para deletar este estado."); }
        );
    }

    private EstadoResponseDTO toResponseDTO(Estado estado) {
        List<MunicipioResponseDTO> municipiosDTO = estado.getMunicipios().stream()
                .map(m -> new MunicipioResponseDTO(m.getId(), m.getNome(), m.getCodigo(), m.getPopulacao(), m.isCapital(), m.getEstado().getId()))
                .collect(Collectors.toList());
        return new EstadoResponseDTO(estado.getId(), estado.getUf(), estado.getCodigoUf(), municipiosDTO);
    }
}