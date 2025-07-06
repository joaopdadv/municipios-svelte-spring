package br.com.municipios.repository;

import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.estados.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Optional<Estado> findByCodigoUfAndUser(Integer codigoUf, User user);

    @Query(value = "SELECT e, m, (SELECT SUM(m3.populacao) FROM Municipio m3 WHERE m3.estado = e) as totalPopulacao " +
            "FROM Estado e JOIN e.municipios m " +
            "WHERE e.user = :user " +
            "AND m.populacao = (SELECT MAX(m2.populacao) FROM Municipio m2 WHERE m2.estado = e) " +
            "AND (:uf IS NULL OR e.uf = :uf) " +
            "AND (:populacaoIni IS NULL OR (SELECT SUM(m4.populacao) FROM Municipio m4 WHERE m4.estado = e) >= :populacaoIni) " +
            "AND (:populacaoFim IS NULL OR (SELECT SUM(m5.populacao) FROM Municipio m5 WHERE m5.estado = e) <= :populacaoFim) ",

            countQuery = "SELECT COUNT(e) FROM Estado e WHERE e.user = :user " +
                    "AND (:uf IS NULL OR e.uf = :uf) " +
                    "AND (:populacaoIni IS NULL OR (SELECT SUM(m4.populacao) FROM Municipio m4 WHERE m4.estado = e) >= :populacaoIni) " +
                    "AND (:populacaoFim IS NULL OR (SELECT SUM(m5.populacao) FROM Municipio m5 WHERE m5.estado = e) <= :populacaoFim)"
    )
    Page<Object[]> findAllFiltered(
            @Param("user") User user,
            @Param("uf") String uf,
            @Param("populacaoIni") Long populacaoIni,
            @Param("populacaoFim") Long populacaoFim,
            Pageable pageable
    );}
