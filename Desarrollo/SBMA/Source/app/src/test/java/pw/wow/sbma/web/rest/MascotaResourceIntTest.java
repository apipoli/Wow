package pw.wow.sbma.web.rest;

import pw.wow.sbma.AppApp;

import pw.wow.sbma.domain.Mascota;
import pw.wow.sbma.domain.Raza;
import pw.wow.sbma.domain.User;
import pw.wow.sbma.repository.MascotaRepository;
import pw.wow.sbma.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static pw.wow.sbma.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pw.wow.sbma.domain.enumeration.Tamano;
/**
 * Test class for the MascotaResource REST controller.
 *
 * @see MascotaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class MascotaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_MESES = "AAAAAAAAAA";
    private static final String UPDATED_MESES = "BBBBBBBBBB";

    private static final Tamano DEFAULT_TAMANO = Tamano.PEQUENO;
    private static final Tamano UPDATED_TAMANO = Tamano.MEDIANO;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMascotaMockMvc;

    private Mascota mascota;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MascotaResource mascotaResource = new MascotaResource(mascotaRepository);
        this.restMascotaMockMvc = MockMvcBuilders.standaloneSetup(mascotaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mascota createEntity(EntityManager em) {
        Mascota mascota = new Mascota()
            .nombre(DEFAULT_NOMBRE)
            .meses(DEFAULT_MESES)
            .tamano(DEFAULT_TAMANO);
        // Add required entity
        Raza raza = RazaResourceIntTest.createEntity(em);
        em.persist(raza);
        em.flush();
        mascota.setRaza(raza);
        // Add required entity
        User dueno = UserResourceIntTest.createEntity(em);
        em.persist(dueno);
        em.flush();
        mascota.setDueno(dueno);
        return mascota;
    }

    @Before
    public void initTest() {
        mascota = createEntity(em);
    }

    @Test
    @Transactional
    public void createMascota() throws Exception {
        int databaseSizeBeforeCreate = mascotaRepository.findAll().size();

        // Create the Mascota
        restMascotaMockMvc.perform(post("/api/mascotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mascota)))
            .andExpect(status().isCreated());

        // Validate the Mascota in the database
        List<Mascota> mascotaList = mascotaRepository.findAll();
        assertThat(mascotaList).hasSize(databaseSizeBeforeCreate + 1);
        Mascota testMascota = mascotaList.get(mascotaList.size() - 1);
        assertThat(testMascota.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testMascota.getMeses()).isEqualTo(DEFAULT_MESES);
        assertThat(testMascota.getTamano()).isEqualTo(DEFAULT_TAMANO);
    }

    @Test
    @Transactional
    public void createMascotaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mascotaRepository.findAll().size();

        // Create the Mascota with an existing ID
        mascota.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMascotaMockMvc.perform(post("/api/mascotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mascota)))
            .andExpect(status().isBadRequest());

        // Validate the Mascota in the database
        List<Mascota> mascotaList = mascotaRepository.findAll();
        assertThat(mascotaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = mascotaRepository.findAll().size();
        // set the field null
        mascota.setNombre(null);

        // Create the Mascota, which fails.

        restMascotaMockMvc.perform(post("/api/mascotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mascota)))
            .andExpect(status().isBadRequest());

        List<Mascota> mascotaList = mascotaRepository.findAll();
        assertThat(mascotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMesesIsRequired() throws Exception {
        int databaseSizeBeforeTest = mascotaRepository.findAll().size();
        // set the field null
        mascota.setMeses(null);

        // Create the Mascota, which fails.

        restMascotaMockMvc.perform(post("/api/mascotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mascota)))
            .andExpect(status().isBadRequest());

        List<Mascota> mascotaList = mascotaRepository.findAll();
        assertThat(mascotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTamanoIsRequired() throws Exception {
        int databaseSizeBeforeTest = mascotaRepository.findAll().size();
        // set the field null
        mascota.setTamano(null);

        // Create the Mascota, which fails.

        restMascotaMockMvc.perform(post("/api/mascotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mascota)))
            .andExpect(status().isBadRequest());

        List<Mascota> mascotaList = mascotaRepository.findAll();
        assertThat(mascotaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMascotas() throws Exception {
        // Initialize the database
        mascotaRepository.saveAndFlush(mascota);

        // Get all the mascotaList
        restMascotaMockMvc.perform(get("/api/mascotas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mascota.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].meses").value(hasItem(DEFAULT_MESES.toString())))
            .andExpect(jsonPath("$.[*].tamano").value(hasItem(DEFAULT_TAMANO.toString())));
    }

    @Test
    @Transactional
    public void getMascota() throws Exception {
        // Initialize the database
        mascotaRepository.saveAndFlush(mascota);

        // Get the mascota
        restMascotaMockMvc.perform(get("/api/mascotas/{id}", mascota.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mascota.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.meses").value(DEFAULT_MESES.toString()))
            .andExpect(jsonPath("$.tamano").value(DEFAULT_TAMANO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMascota() throws Exception {
        // Get the mascota
        restMascotaMockMvc.perform(get("/api/mascotas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMascota() throws Exception {
        // Initialize the database
        mascotaRepository.saveAndFlush(mascota);
        int databaseSizeBeforeUpdate = mascotaRepository.findAll().size();

        // Update the mascota
        Mascota updatedMascota = mascotaRepository.findOne(mascota.getId());
        updatedMascota
            .nombre(UPDATED_NOMBRE)
            .meses(UPDATED_MESES)
            .tamano(UPDATED_TAMANO);

        restMascotaMockMvc.perform(put("/api/mascotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMascota)))
            .andExpect(status().isOk());

        // Validate the Mascota in the database
        List<Mascota> mascotaList = mascotaRepository.findAll();
        assertThat(mascotaList).hasSize(databaseSizeBeforeUpdate);
        Mascota testMascota = mascotaList.get(mascotaList.size() - 1);
        assertThat(testMascota.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testMascota.getMeses()).isEqualTo(UPDATED_MESES);
        assertThat(testMascota.getTamano()).isEqualTo(UPDATED_TAMANO);
    }

    @Test
    @Transactional
    public void updateNonExistingMascota() throws Exception {
        int databaseSizeBeforeUpdate = mascotaRepository.findAll().size();

        // Create the Mascota

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMascotaMockMvc.perform(put("/api/mascotas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mascota)))
            .andExpect(status().isCreated());

        // Validate the Mascota in the database
        List<Mascota> mascotaList = mascotaRepository.findAll();
        assertThat(mascotaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMascota() throws Exception {
        // Initialize the database
        mascotaRepository.saveAndFlush(mascota);
        int databaseSizeBeforeDelete = mascotaRepository.findAll().size();

        // Get the mascota
        restMascotaMockMvc.perform(delete("/api/mascotas/{id}", mascota.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mascota> mascotaList = mascotaRepository.findAll();
        assertThat(mascotaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mascota.class);
        Mascota mascota1 = new Mascota();
        mascota1.setId(1L);
        Mascota mascota2 = new Mascota();
        mascota2.setId(mascota1.getId());
        assertThat(mascota1).isEqualTo(mascota2);
        mascota2.setId(2L);
        assertThat(mascota1).isNotEqualTo(mascota2);
        mascota1.setId(null);
        assertThat(mascota1).isNotEqualTo(mascota2);
    }
}
