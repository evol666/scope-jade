package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ModeleCommande;
import com.mycompany.myapp.repository.ModeleCommandeRepository;
import com.mycompany.myapp.repository.search.ModeleCommandeSearchRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ModeleCommande}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ModeleCommandeResource {

    private final Logger log = LoggerFactory.getLogger(ModeleCommandeResource.class);

    private static final String ENTITY_NAME = "modeleCommande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModeleCommandeRepository modeleCommandeRepository;

    private final ModeleCommandeSearchRepository modeleCommandeSearchRepository;

    public ModeleCommandeResource(ModeleCommandeRepository modeleCommandeRepository, ModeleCommandeSearchRepository modeleCommandeSearchRepository) {
        this.modeleCommandeRepository = modeleCommandeRepository;
        this.modeleCommandeSearchRepository = modeleCommandeSearchRepository;
    }

    /**
     * {@code POST  /modele-commandes} : Create a new modeleCommande.
     *
     * @param modeleCommande the modeleCommande to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modeleCommande, or with status {@code 400 (Bad Request)} if the modeleCommande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modele-commandes")
    public ResponseEntity<ModeleCommande> createModeleCommande(@RequestBody ModeleCommande modeleCommande) throws URISyntaxException {
        log.debug("REST request to save ModeleCommande : {}", modeleCommande);
        if (modeleCommande.getId() != null) {
            throw new BadRequestAlertException("A new modeleCommande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModeleCommande result = modeleCommandeRepository.save(modeleCommande);
        modeleCommandeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/modele-commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /modele-commandes} : Updates an existing modeleCommande.
     *
     * @param modeleCommande the modeleCommande to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modeleCommande,
     * or with status {@code 400 (Bad Request)} if the modeleCommande is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modeleCommande couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modele-commandes")
    public ResponseEntity<ModeleCommande> updateModeleCommande(@RequestBody ModeleCommande modeleCommande) throws URISyntaxException {
        log.debug("REST request to update ModeleCommande : {}", modeleCommande);
        if (modeleCommande.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModeleCommande result = modeleCommandeRepository.save(modeleCommande);
        modeleCommandeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, modeleCommande.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /modele-commandes} : get all the modeleCommandes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modeleCommandes in body.
     */
    @GetMapping("/modele-commandes")
    public List<ModeleCommande> getAllModeleCommandes() {
        log.debug("REST request to get all ModeleCommandes");
        return modeleCommandeRepository.findAll();
    }

    /**
     * {@code GET  /modele-commandes/:id} : get the "id" modeleCommande.
     *
     * @param id the id of the modeleCommande to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modeleCommande, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modele-commandes/{id}")
    public ResponseEntity<ModeleCommande> getModeleCommande(@PathVariable Long id) {
        log.debug("REST request to get ModeleCommande : {}", id);
        Optional<ModeleCommande> modeleCommande = modeleCommandeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(modeleCommande);
    }

    /**
     * {@code DELETE  /modele-commandes/:id} : delete the "id" modeleCommande.
     *
     * @param id the id of the modeleCommande to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modele-commandes/{id}")
    public ResponseEntity<Void> deleteModeleCommande(@PathVariable Long id) {
        log.debug("REST request to delete ModeleCommande : {}", id);

        modeleCommandeRepository.deleteById(id);
        modeleCommandeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/modele-commandes?query=:query} : search for the modeleCommande corresponding
     * to the query.
     *
     * @param query the query of the modeleCommande search.
     * @return the result of the search.
     */
    @GetMapping("/_search/modele-commandes")
    public List<ModeleCommande> searchModeleCommandes(@RequestParam String query) {
        log.debug("REST request to search ModeleCommandes for query {}", query);
        return StreamSupport
            .stream(modeleCommandeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
