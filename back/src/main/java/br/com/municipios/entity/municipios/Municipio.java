package br.com.municipios.entity.municipios;

import br.com.municipios.entity.estados.Estado;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "municipios")
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "codigo_municipio", nullable = false)
    private String codigo;

    @Column(name = "populacao")
    private Long populacao;

    @Column(name = "is_capital")
    private boolean capital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", nullable = false)
    @JsonBackReference
    private Estado estado;

    public Municipio(String nome, String codigo, Long populacao, boolean isCapital, Estado estado) {
        this.nome = nome;
        this.codigo = codigo;
        this.populacao = populacao;
        this.capital = isCapital;
        this.estado = estado;
    }
}