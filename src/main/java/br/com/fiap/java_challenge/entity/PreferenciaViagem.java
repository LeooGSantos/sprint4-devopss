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
@Table(name = "TB_PREFERENCIA_VIAGEM")
public class PreferenciaViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PREFERENCIAVIAGEM")
    @SequenceGenerator(name = "SQ_PREFERENCIAVIAGEM", sequenceName = "SQ_PREFERENCIAVIAGEM", allocationSize = 1)
    @Column(name = "ID_PREFERENCIAVIAGEM")
    private Long id;

    private String tipo_culinaria;

    private String restricoes_alimentares;

    private String tipo_transporte;

    private String tipo_hospedagem;

    private String viaja_sozinho;
}