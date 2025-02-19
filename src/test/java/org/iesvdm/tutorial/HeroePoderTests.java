package org.iesvdm.tutorial;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.iesvdm.tutorial.domain.*;
import org.iesvdm.tutorial.repository.HasPoderRepository;
import org.iesvdm.tutorial.repository.HeroeRepository;
import org.iesvdm.tutorial.repository.MisionRepository;
import org.iesvdm.tutorial.repository.PoderRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class )
@SpringBootTest
public class HeroePoderTests {

    @Autowired
    HeroeRepository heroeRepository;

    @Autowired
    PoderRepository poderRepository;

    @Autowired
    MisionRepository misionRepository;

    @Autowired
    HasPoderRepository hasPoderRepository;

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;
    @BeforeEach
    public void setUp() {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Order(1)
    @Test
    void crearHeroeHasPoderTest() {
        Poder poder = Poder.builder()
                .nombre("Sentido Arácnido")
                .build();
        this.poderRepository.save(poder);

        Mision mision = Mision.builder()
                .descripcion("Matar a Venom")
                .villano("Venom")
                .build();
        mision = misionRepository.save(mision);

        Heroe heroe = Heroe.builder()
                .nombre("Spiderman")
                .fechaNac(LocalDate.now())
                .mision(mision)
                .build();

        heroeRepository.save(heroe);

        HeroeHasPoder heroHasPoder = new HeroeHasPoder(null,
                poder, heroe);
        hasPoderRepository.save(heroHasPoder);
    }

    @Order(2)
    @Test
    void leerHeroeYPoderPorPoder() {
        transactionTemplate.executeWithoutResult(transactionStatus -> {

            List<Poder> listPoder = this.poderRepository.findAll();

            listPoder.forEach(poder -> {
                Set<HeroeHasPoder> setHP = poder.getHeroesPoder();

                setHP.forEach(HeroeHasPoder -> {
                    Heroe heroe = HeroeHasPoder.getHeroe();
                    System.out.println(heroe);

                });

            });

        });
    }


    @Order(3)
    @Test
    void desasociarPoder() {

        HeroeHasPoder heroehaspoder = hasPoderRepository
                .findHeroePoderByPoder_IdAndHeroe_Id(1L, 1L)
                .orElse(null);

        hasPoderRepository.delete(heroehaspoder);
    }


}
