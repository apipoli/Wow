package pw.wow.sbma.web.rest;

import pw.wow.sbma.AppApp;

import pw.wow.sbma.domain.PublicacionMascotaPerdida;
import pw.wow.sbma.domain.User;
import pw.wow.sbma.domain.Distrito;
import pw.wow.sbma.domain.Mascota;
import pw.wow.sbma.repository.PublicacionMascotaPerdidaRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static pw.wow.sbma.web.rest.TestUtil.sameInstant;
import static pw.wow.sbma.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pw.wow.sbma.domain.enumeration.EstadoMascotaPerdida;
/**
 * Test class for the PublicacionMascotaPerdidaResource REST controller.
 *
 * @see PublicacionMascotaPerdidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class PublicacionMascotaPerdidaResourceIntTest {

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LUGAR = "AAAAAAAAAA";
    private static final String UPDATED_LUGAR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_FECHA_ENCUENTRO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_ENCUENTRO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final EstadoMascotaPerdida DEFAULT_ESTADO = EstadoMascotaPerdida.PERDIDA;
    private static final EstadoMascotaPerdida UPDATED_ESTADO = EstadoMascotaPerdida.ENCONTRADA;

    @Autowired
    private PublicacionMascotaPerdidaRepository publicacionMascotaPerdidaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPublicacionMascotaPerdidaMockMvc;

    private PublicacionMascotaPerdida publicacionMascotaPerdida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PublicacionMascotaPerdidaResource publicacionMascotaPerdidaResource = new PublicacionMascotaPerdidaResource(publicacionMascotaPerdidaRepository);
        this.restPublicacionMascotaPerdidaMockMvc = MockMvcBuilders.standaloneSetup(publicacionMascotaPerdidaResource)
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
    public static PublicacionMascotaPerdida createEntity(EntityManager em) {
        PublicacionMascotaPerdida publicacionMascotaPerdida = new PublicacionMascotaPerdida()
            .fecha(DEFAULT_FECHA)
            .lugar(DEFAULT_LUGAR)
            .fechaEncuentro(DEFAULT_FECHA_ENCUENTRO)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        User dueno = UserResourceIntTest.createEntity(em);
        em.persist(dueno);
        em.flush();
        publicacionMascotaPerdida.setDueno(dueno);
        // Add required entity
        Distrito distrito = DistritoResourceIntTest.createEntity(em);
        em.persist(distrito);
        em.flush();
        publicacionMascotaPerdida.setDistrito(distrito);
        // Add required entity
        Mascota mascota = MascotaResourceIntTest.createEntity(em);
        em.persist(mascota);
        em.flush();
        publicacionMascotaPerdida.setMascota(mascota);
        return publicacionMascotaPerdida;
    }

    @Before
    public void initTest() {
        publicacionMascotaPerdida = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicacionMascotaPerdida() throws Exception {
        int databaseSizeBeforeCreate = publicacionMascotaPerdidaRepository.findAll().size();

        // Create the PublicacionMascotaPerdida
        restPublicacionMascotaPerdidaMockMvc.perform(post("/api/publicacion-mascota-perdidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionMascotaPerdida)))
            .andExpect(status().isCreated());

        // Validate the PublicacionMascotaPerdida in the database
        List<PublicacionMascotaPerdida> publicacionMascotaPerdidaList = publicacionMascotaPerdidaRepository.findAll();
        assertThat(publicacionMascotaPerdidaList).hasSize(databaseSizeBeforeCreate + 1);
        PublicacionMascotaPerdida testPublicacionMascotaPerdida = publicacionMascotaPerdidaList.get(publicacionMascotaPerdidaList.size() - 1);
        assertThat(testPublicacionMascotaPerdida.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPublicacionMascotaPerdida.getLugar()).isEqualTo(DEFAULT_LUGAR);
        assertThat(testPublicacionMascotaPerdida.getFechaEncuentro()).isEqualTo(DEFAULT_FECHA_ENCUENTRO);
        assertThat(testPublicacionMascotaPerdida.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createPublicacionMascotaPerdidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicacionMascotaPerdidaRepository.findAll().size();

        // Create the PublicacionMascotaPerdida with an existing ID
        publicacionMascotaPerdida.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicacionMascotaPerdidaMockMvc.perform(post("/api/publicacion-mascota-perdidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionMascotaPerdida)))
            .andExpect(status().isBadRequest());

        // Validate the PublicacionMascotaPerdida in the database
        List<PublicacionMascotaPerdida> publicacionMascotaPerdidaList = publicacionMascotaPerdidaRepository.findAll();
        assertThat(publicacionMascotaPerdidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicacionMascotaPerdidaRepository.findAll().size();
        // set the field null
        publicacionMascotaPerdida.setFecha(null);

        // Create the PublicacionMascotaPerdida, which fails.

        restPublicacionMascotaPerdidaMockMvc.perform(post("/api/publicacion-mascota-perdidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionMascotaPerdida)))
            .andExpect(status().isBadRequest());

        List<PublicacionMascotaPerdida> publicacionMascotaPerdidaList = publicacionMascotaPerdidaRepository.findAll();
        assertThat(publicacionMascotaPerdidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLugarIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicacionMascotaPerdidaRepository.findAll().size();
        // set the field null
        publicacionMascotaPerdida.setLugar(null);

        // Create the PublicacionMascotaPerdida, which fails.

        restPublicacionMascotaPerdidaMockMvc.perform(post("/api/publicacion-mascota-perdidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionMascotaPerdida)))
            .andExpect(status().isBadRequest());

        List<PublicacionMascotaPerdida> publicacionMascotaPerdidaList = publicacionMascotaPerdidaRepository.findAll();
        assertThat(publicacionMascotaPerdidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicacionMascotaPerdidaRepository.findAll().size();
        // set the field null
        publicacionMascotaPerdida.setEstado(null);

        // Create the PublicacionMascotaPerdida, which fails.

        restPublicacionMascotaPerdidaMockMvc.perform(post("/api/publicacion-mascota-perdidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionMascotaPerdida)))
            .andExpect(status().isBadRequest());

        List<PublicacionMascotaPerdida> publicacionMascotaPerdidaList = publicacionMascotaPerdidaRepository.findAll();
        assertThat(publicacionMascotaPerdidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPublicacionMascotaPerdidas() throws Exception {
        // Initialize the database
        publicacionMascotaPerdidaRepository.saveAndFlush(publicacionMascotaPerdida);

        // Get all the publicacionMascotaPerdidaList
        restPublicacionMascotaPerdidaMockMvc.perform(get("/api/publicacion-mascota-perdidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicacionMascotaPerdida.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))))
            .andExpect(jsonPath("$.[*].lugar").value(hasItem(DEFAULT_LUGAR.toString())))
            .andExpect(jsonPath("$.[*].fechaEncuentro").value(hasItem(sameInstant(DEFAULT_FECHA_ENCUENTRO))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    @Transactional
    public void getPublicacionMascotaPerdida() throws Exception {
        // Initialize the database
        publicacionMascotaPerdidaRepository.saveAndFlush(publicacionMascotaPerdida);

        // Get the publicacionMascotaPerdida
        restPublicacionMascotaPerdidaMockMvc.perform(get("/api/publicacion-mascota-perdidas/{id}", publicacionMascotaPerdida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publicacionMascotaPerdida.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)))
            .andExpect(jsonPath("$.lugar").value(DEFAULT_LUGAR.toString()))
            .andExpect(jsonPath("$.fechaEncuentro").value(sameInstant(DEFAULT_FECHA_ENCUENTRO)))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPublicacionMascotaPerdida() throws Exception {
        // Get the publicacionMascotaPerdida
        restPublicacionMascotaPerdidaMockMvc.perform(get("/api/publicacion-mascota-perdidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicacionMascotaPerdida() throws Exception {
        // Initialize the database
        publicacionMascotaPerdidaRepository.saveAndFlush(publicacionMascotaPerdida);
        int databaseSizeBeforeUpdate = publicacionMascotaPerdidaRepository.findAll().size();

        // Update the publicacionMascotaPerdida
        PublicacionMascotaPerdida updatedPublicacionMascotaPerdida = publicacionMascotaPerdidaRepository.findOne(publicacionMascotaPerdida.getId());
        updatedPublicacionMascotaPerdida
            .fecha(UPDATED_FECHA)
            .lugar(UPDATED_LUGAR)
            .fechaEncuentro(UPDATED_FECHA_ENCUENTRO)
            .estado(UPDATED_ESTADO);

        restPublicacionMascotaPerdidaMockMvc.perform(put("/api/publicacion-mascota-perdidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPublicacionMascotaPerdida)))
            .andExpect(status().isOk());

        // Validate the PublicacionMascotaPerdida in the database
        List<PublicacionMascotaPerdida> publicacionMascotaPerdidaList = publicacionMascotaPerdidaRepository.findAll();
        assertThat(publicacionMascotaPerdidaList).hasSize(databaseSizeBeforeUpdate);
        PublicacionMascotaPerdida testPublicacionMascotaPerdida = publicacionMascotaPerdidaList.get(publicacionMascotaPerdidaList.size() - 1);
        assertThat(testPublicacionMascotaPerdida.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPublicacionMascotaPerdida.getLugar()).isEqualTo(UPDATED_LUGAR);
        assertThat(testPublicacionMascotaPerdida.getFechaEncuentro()).isEqualTo(UPDATED_FECHA_ENCUENTRO);
        assertThat(testPublicacionMascotaPerdida.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingPublicacionMascotaPerdida() throws Exception {
        int databaseSizeBeforeUpdate = publicacionMascotaPerdidaRepository.findAll().size();

        // Create the PublicacionMascotaPerdida

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPublicacionMascotaPerdidaMockMvc.perform(put("/api/publicacion-mascota-perdidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicacionMascotaPerdida)))
            .andExpect(status().isCreated());

        // Validate the PublicacionMascotaPerdida in the database
        List<PublicacionMascotaPerdida> publicacionMascotaPerdidaList = publicacionMascotaPerdidaRepository.findAll();
        assertThat(publicacionMascotaPerdidaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePublicacionMascotaPerdida() throws Exception {
        // Initialize the database
        publicacionMascotaPerdidaRepository.saveAndFlush(publicacionMascotaPerdida);
        int databaseSizeBeforeDelete = publicacionMascotaPerdidaRepository.findAll().size();

        // Get the publicacionMascotaPerdida
        restPublicacionMascotaPerdidaMockMvc.perform(delete("/api/publicacion-mascota-perdidas/{id}", publicacionMascotaPerdida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PublicacionMascotaPerdida> publicacionMascotaPerdidaList = publicacionMascotaPerdidaRepository.findAll();
        assertThat(publicacionMascotaPerdidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicacionMascotaPerdida.class);
        PublicacionMascotaPerdida publicacionMascotaPerdida1 = new PublicacionMascotaPerdida();
        publicacionMascotaPerdida1.setId(1L);
        PublicacionMascotaPerdida publicacionMascotaPerdida2 = new PublicacionMascotaPerdida();
        publicacionMascotaPerdida2.setId(publicacionMascotaPerdida1.getId());
        assertThat(publicacionMascotaPerdida1).isEqualTo(publicacionMascotaPerdida2);
        publicacionMascotaPerdida2.setId(2L);
        assertThat(publicacionMascotaPerdida1).isNotEqualTo(publicacionMascotaPerdida2);
        publicacionMascotaPerdida1.setId(null);
        assertThat(publicacionMascotaPerdida1).isNotEqualTo(publicacionMascotaPerdida2);
    }
}
