package br.com.municipios.entity.estados;

import br.com.municipios.entity.auth.User;
import br.com.municipios.entity.municipios.Municipio;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estados")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_uf", nullable = false, length = 2)
    private String uf;

    @Column(name = "codigo_uf", nullable = false, unique = true)
    private Integer codigoUf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(
            mappedBy = "estado",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Municipio> municipios = new ArrayList<>();

    public Estado(String uf, Integer codigoUf, User user) {
        this.uf = uf;
        this.codigoUf = codigoUf;
        this.user = user;
    }

    public void addMunicipio(Municipio municipio) {
        municipios.add(municipio);
        municipio.setEstado(this);
    }

    public void removeMunicipio(Municipio municipio) {
        municipios.remove(municipio);
        municipio.setEstado(null);
    }
}