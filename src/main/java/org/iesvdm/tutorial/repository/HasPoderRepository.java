package org.iesvdm.tutorial.repository;

import org.iesvdm.tutorial.domain.HeroeHasPoder;
import org.iesvdm.tutorial.domain.PeliculaCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HasPoderRepository extends JpaRepository<HeroeHasPoder, Long> {
    Optional<HeroeHasPoder> findHeroePoderByPoder_IdAndHeroe_Id(Long poderId, Long heroeId);
}
