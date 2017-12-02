package pe.wow.sbma.web.rest;

import pe.wow.sbma.AppApp;

import pe.wow.sbma.domain.Raza;
import pe.wow.sbma.repository.RazaRepository;
import pe.wow.sbma.service.RazaService;
import pe.wow.sbma.web.rest.errors.ExceptionTranslator;

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

import static pe.wow.sbma.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RazaResource REST controller.
 *
 * @see RazaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class RazaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private RazaRepository razaRepository;

    @Autowired
    private RazaService razaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRazaMockMvc;

    private Raza raza;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RazaResource razaResource = new RazaResource(razaService);
        this.restRazaMockMvc = MockMvcBuilders.standaloneSetup(razaResource)
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
    public static Raza createEntity(EntityManager em) {
        Raza raza = new Raza()
            .nombre(DEFAULT_NOMBRE);
        return raza;
    }

    @Before
    public void initTest() {
        raza = createEntity(em);
    }

    @Test
    @Transactional
    public void createRaza() throws Exception {
        int databaseSizeBeforeCreate = razaRepository.findAll().size();

        // Create the Raza
        restRazaMockMvc.perform(post("/api/razas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raza)))
            .andExpect(status().isCreated());

        // Validate the Raza in the database
        List<Raza> razaList = razaRepository.findAll();
        assertThat(razaList).hasSize(databaseSizeBeforeCreate + 1);
        Raza testRaza = razaList.get(razaList.size() - 1);
        assertThat(testRaza.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createRazaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = razaRepository.findAll().size();

        // Create the Raza with an existing ID
        raza.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRazaMockMvc.perform(post("/api/razas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raza)))
            .andExpect(status().isBadRequest());

        // Validate the Raza in the database
        List<Raza> razaList = razaRepository.findAll();
        assertThat(razaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = razaRepository.findAll().size();
        // set the field null
        raza.setNombre(null);

        // Create the Raza, which fails.

        restRazaMockMvc.perform(post("/api/razas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raza)))
            .andExpect(status().isBadRequest());

        List<Raza> razaList = razaRepository.findAll();
        assertThat(razaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRazas() throws Exception {
        // Initialize the database
        razaRepository.saveAndFlush(raza);

        // Get all the razaList
        restRazaMockMvc.perform(get("/api/razas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(raza.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getRaza() throws Exception {
        // Initialize the database
        razaRepository.saveAndFlush(raza);

        // Get the raza
        restRazaMockMvc.perform(get("/api/razas/{id}", raza.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(raza.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRaza() throws Exception {
        // Get the raza
        restRazaMockMvc.perform(get("/api/razas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRaza() throws Exception {
        // Initialize the database
        razaService.save(raza);

        int databaseSizeBeforeUpdate = razaRepository.findAll().size();

        // Update the raza
        Raza updatedRaza = razaRepository.findOne(raza.getId());
        updatedRaza
            .nombre(UPDATED_NOMBRE);

        restRazaMockMvc.perform(put("/api/razas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRaza)))
            .andExpect(status().isOk());

        // Validate the Raza in the database
        List<Raza> razaList = razaRepository.findAll();
        assertThat(razaList).hasSize(databaseSizeBeforeUpdate);
        Raza testRaza = razaList.get(razaList.size() - 1);
        assertThat(testRaza.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingRaza() throws Exception {
        int databaseSizeBeforeUpdate = razaRepository.findAll().size();

        // Create the Raza

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRazaMockMvc.perform(put("/api/razas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(raza)))
            .andExpect(status().isCreated());

        // Validate the Raza in the database
        List<Raza> razaList = razaRepository.findAll();
        assertThat(razaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRaza() throws Exception {
        // Initialize the database
        razaService.save(raza);

        int databaseSizeBeforeDelete = razaRepository.findAll().size();

        // Get the raza
        restRazaMockMvc.perform(delete("/api/razas/{id}", raza.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Raza> razaList = razaRepository.findAll();
        assertThat(razaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Raza.class);
        Raza raza1 = new Raza();
        raza1.setId(1L);
        Raza raza2 = new Raza();
        raza2.setId(raza1.getId());
        assertThat(raza1).isEqualTo(raza2);
        raza2.setId(2L);
        assertThat(raza1).isNotEqualTo(raza2);
        raza1.setId(null);
        assertThat(raza1).isNotEqualTo(raza2);
    }
}
