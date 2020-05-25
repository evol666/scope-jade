package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ModeleCommandeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeleCommande.class);
        ModeleCommande modeleCommande1 = new ModeleCommande();
        modeleCommande1.setId(1L);
        ModeleCommande modeleCommande2 = new ModeleCommande();
        modeleCommande2.setId(modeleCommande1.getId());
        assertThat(modeleCommande1).isEqualTo(modeleCommande2);
        modeleCommande2.setId(2L);
        assertThat(modeleCommande1).isNotEqualTo(modeleCommande2);
        modeleCommande1.setId(null);
        assertThat(modeleCommande1).isNotEqualTo(modeleCommande2);
    }
}
