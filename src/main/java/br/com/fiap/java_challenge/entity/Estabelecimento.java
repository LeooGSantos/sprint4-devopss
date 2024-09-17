package br.com.fiap.java_challenge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_ESTABELECIMENTO")
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ESTABELECIMENTO")
    @SequenceGenerator(name = "SQ_ESTABELECIMENTO", sequenceName = "SQ_ESTABELECIMENTO", allocationSize = 1)
    @Column(name = "ID_ESTABELECIMENTO")
    private Long id;

    @Column(name = "NM_ESTABELECIMENTO", nullable = false)
    private String nome;

    @Column(name = "CEP_ESTABELECIMENTO", nullable = false)
    private String cep;

    @Enumerated(EnumType.STRING)
    @Column(name = "TP_ESTABELECIMENTO", nullable = false)
    private TipoEstabelecimento tipo;
}