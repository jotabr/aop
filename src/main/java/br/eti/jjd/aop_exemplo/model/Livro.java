package br.eti.jjd.aop_exemplo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "livros")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String titulo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "preco", precision = 19, scale = 2)
    private BigDecimal preco;
}
