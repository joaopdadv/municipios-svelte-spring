package br.com.municipios.repository;

import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.municipios.Municipio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {
    @Query("SELECT m FROM Municipio m JOIN FETCH m.estado e WHERE e.user = :user " +
            "AND (lower(m.nome) LIKE lower(concat('%', :nome, '%')) OR :nome IS NULL) " +
            "AND (:capital IS NULL OR m.capital = :capital)"
    )
    Page<Municipio> findAllFiltered(
            @Param("user") User user,
            @Param("nome") String nome,
            @Param("capital") Boolean capital,
            Pageable pageable
    );
}
