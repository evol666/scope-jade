package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ModeleCommande;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ModeleCommande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModeleCommandeRepository extends JpaRepository<ModeleCommande, Long> {
}
