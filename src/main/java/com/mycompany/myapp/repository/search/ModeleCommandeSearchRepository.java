package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.ModeleCommande;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ModeleCommande} entity.
 */
public interface ModeleCommandeSearchRepository extends ElasticsearchRepository<ModeleCommande, Long> {
}
