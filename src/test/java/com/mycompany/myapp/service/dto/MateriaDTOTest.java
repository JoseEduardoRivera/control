package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MateriaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MateriaDTO.class);
        MateriaDTO materiaDTO1 = new MateriaDTO();
        materiaDTO1.setId(1L);
        MateriaDTO materiaDTO2 = new MateriaDTO();
        assertThat(materiaDTO1).isNotEqualTo(materiaDTO2);
        materiaDTO2.setId(materiaDTO1.getId());
        assertThat(materiaDTO1).isEqualTo(materiaDTO2);
        materiaDTO2.setId(2L);
        assertThat(materiaDTO1).isNotEqualTo(materiaDTO2);
        materiaDTO1.setId(null);
        assertThat(materiaDTO1).isNotEqualTo(materiaDTO2);
    }
}
