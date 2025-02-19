package org.iesvdm.tutorial.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

@Entity
public class Heroe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nombre;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private Date fechaNac;

    @OneToMany( mappedBy = "heroe")
    @Builder.Default
    private Set<HeroHasPoder> heroesPoder = new HashSet<>();

    @ManyToOne
    private Mision mision;

}
