package br.com.fiap.java_challenge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_AVALIACAO")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_AVALIACAO")
    @SequenceGenerator(name = "SQ_AVALIACAO", sequenceName = "SQ_AVALIACAO", allocationSize = 1)
    @Column(name = "ID_AVALIACAO")
    private Long id;

    private String comentario;

    private Long nota;

    private LocalDate dataAvaliacao;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ESTABELECIMENTO", referencedColumnName = "ID_ESTABELECIMENTO", foreignKey = @ForeignKey(name = "FK_AVALIACAO_ESTABELECIMENTO"))
    private Estabelecimento estabelecimento;
}