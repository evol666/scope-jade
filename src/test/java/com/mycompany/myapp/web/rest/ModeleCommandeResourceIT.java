package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JadeApp;
import com.mycompany.myapp.domain.ModeleCommande;
import com.mycompany.myapp.repository.ModeleCommandeRepository;
import com.mycompany.myapp.repository.search.ModeleCommandeSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ModeleCommandeResource} REST controller.
 */
@SpringBootTest(classes = JadeApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ModeleCommandeResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private ModeleCommandeRepository modeleCommandeRepository;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.ModeleCommandeSearchRepositoryMockConfiguration
     */
    @Autowired
    private ModeleCommandeSearchRepository mockModeleCommandeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModeleCommandeMockMvc;

    private ModeleCommande modeleCommande;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModeleCommande createEntity(EntityManager em) {
        ModeleCommande modeleCommande = new ModeleCommande()
            .libelle(DEFAULT_LIBELLE);
        return modeleCommande;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModeleCommande createUpdatedEntity(EntityManager em) {
        ModeleCommande modeleCommande = new ModeleCommande()
            .libelle(UPDATED_LIBELLE);
        return modeleCommande;
    }

    @BeforeEach
    public void initTest() {
        modeleCommande = createEntity(em);
    }

    @Test
    @Transactional
    public void createModeleCommande() throws Exception {
        int databaseSizeBeforeCreate = modeleCommandeRepository.findAll().size();
        // Create the ModeleCommande
        restModeleCommandeMockMvc.perform(post("/api/modele-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modeleCommande)))
            .andExpect(status().isCreated());

        // Validate the ModeleCommande in the database
        List<ModeleCommande> modeleCommandeList = modeleCommandeRepository.findAll();
        assertThat(modeleCommandeList).hasSize(databaseSizeBeforeCreate + 1);
        ModeleCommande testModeleCommande = modeleCommandeList.get(modeleCommandeList.size() - 1);
        assertThat(testModeleCommande.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the ModeleCommande in Elasticsearch
        verify(mockModeleCommandeSearchRepository, times(1)).save(testModeleCommande);
    }

    @Test
    @Transactional
    public void createModeleCommandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modeleCommandeRepository.findAll().size();

        // Create the ModeleCommande with an existing ID
        modeleCommande.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModeleCommandeMockMvc.perform(post("/api/modele-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modeleCommande)))
            .andExpect(status().isBadRequest());

        // Validate the ModeleCommande in the database
        List<ModeleCommande> modeleCommandeList = modeleCommandeRepository.findAll();
        assertThat(modeleCommandeList).hasSize(databaseSizeBeforeCreate);

        // Validate the ModeleCommande in Elasticsearch
        verify(mockModeleCommandeSearchRepository, times(0)).save(modeleCommande);
    }


    @Test
    @Transactional
    public void getAllModeleCommandes() throws Exception {
        // Initialize the database
        modeleCommandeRepository.saveAndFlush(modeleCommande);

        // Get all the modeleCommandeList
        restModeleCommandeMockMvc.perform(get("/api/modele-commandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modeleCommande.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getModeleCommande() throws Exception {
        // Initialize the database
        modeleCommandeRepository.saveAndFlush(modeleCommande);

        // Get the modeleCommande
        restModeleCommandeMockMvc.perform(get("/api/modele-commandes/{id}", modeleCommande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modeleCommande.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }
    @Test
    @Transactional
    public void getNonExistingModeleCommande() throws Exception {
        // Get the modeleCommande
        restModeleCommandeMockMvc.perform(get("/api/modele-commandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModeleCommande() throws Exception {
        // Initialize the database
        modeleCommandeRepository.saveAndFlush(modeleCommande);

        int databaseSizeBeforeUpdate = modeleCommandeRepository.findAll().size();

        // Update the modeleCommande
        ModeleCommande updatedModeleCommande = modeleCommandeRepository.findById(modeleCommande.getId()).get();
        // Disconnect from session so that the updates on updatedModeleCommande are not directly saved in db
        em.detach(updatedModeleCommande);
        updatedModeleCommande
            .libelle(UPDATED_LIBELLE);

        restModeleCommandeMockMvc.perform(put("/api/modele-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedModeleCommande)))
            .andExpect(status().isOk());

        // Validate the ModeleCommande in the database
        List<ModeleCommande> modeleCommandeList = modeleCommandeRepository.findAll();
        assertThat(modeleCommandeList).hasSize(databaseSizeBeforeUpdate);
        ModeleCommande testModeleCommande = modeleCommandeList.get(modeleCommandeList.size() - 1);
        assertThat(testModeleCommande.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the ModeleCommande in Elasticsearch
        verify(mockModeleCommandeSearchRepository, times(1)).save(testModeleCommande);
    }

    @Test
    @Transactional
    public void updateNonExistingModeleCommande() throws Exception {
        int databaseSizeBeforeUpdate = modeleCommandeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModeleCommandeMockMvc.perform(put("/api/modele-commandes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modeleCommande)))
            .andExpect(status().isBadRequest());

        // Validate the ModeleCommande in the database
        List<ModeleCommande> modeleCommandeList = modeleCommandeRepository.findAll();
        assertThat(modeleCommandeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ModeleCommande in Elasticsearch
        verify(mockModeleCommandeSearchRepository, times(0)).save(modeleCommande);
    }

    @Test
    @Transactional
    public void deleteModeleCommande() throws Exception {
        // Initialize the database
        modeleCommandeRepository.saveAndFlush(modeleCommande);

        int databaseSizeBeforeDelete = modeleCommandeRepository.findAll().size();

        // Delete the modeleCommande
        restModeleCommandeMockMvc.perform(delete("/api/modele-commandes/{id}", modeleCommande.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModeleCommande> modeleCommandeList = modeleCommandeRepository.findAll();
        assertThat(modeleCommandeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ModeleCommande in Elasticsearch
        verify(mockModeleCommandeSearchRepository, times(1)).deleteById(modeleCommande.getId());
    }

    @Test
    @Transactional
    public void searchModeleCommande() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        modeleCommandeRepository.saveAndFlush(modeleCommande);
        when(mockModeleCommandeSearchRepository.search(queryStringQuery("id:" + modeleCommande.getId())))
            .thenReturn(Collections.singletonList(modeleCommande));

        // Search the modeleCommande
        restModeleCommandeMockMvc.perform(get("/api/_search/modele-commandes?query=id:" + modeleCommande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modeleCommande.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
}
