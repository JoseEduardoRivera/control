package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaestroDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaestroDTO.class);
        MaestroDTO maestroDTO1 = new MaestroDTO();
        maestroDTO1.setId(1L);
        MaestroDTO maestroDTO2 = new MaestroDTO();
        assertThat(maestroDTO1).isNotEqualTo(maestroDTO2);
        maestroDTO2.setId(maestroDTO1.getId());
        assertThat(maestroDTO1).isEqualTo(maestroDTO2);
        maestroDTO2.setId(2L);
        assertThat(maestroDTO1).isNotEqualTo(maestroDTO2);
        maestroDTO1.setId(null);
        assertThat(maestroDTO1).isNotEqualTo(maestroDTO2);
    }
}
