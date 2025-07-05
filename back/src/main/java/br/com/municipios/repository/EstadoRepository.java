package br.com.municipios.repository;

import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.estados.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
    List<Estado> findByUser(User user);

    Optional<Estado> findByIdAndUser(Long id, User user);

    Optional<Estado> findByCodigoUf(Integer codigoUf);

    Optional<Estado> findByCodigoUfAndUser(Integer codigoUf, User user);
}
