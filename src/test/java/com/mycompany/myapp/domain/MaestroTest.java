package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MaestroTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Maestro.class);
        Maestro maestro1 = new Maestro();
        maestro1.setId(1L);
        Maestro maestro2 = new Maestro();
        maestro2.setId(maestro1.getId());
        assertThat(maestro1).isEqualTo(maestro2);
        maestro2.setId(2L);
        assertThat(maestro1).isNotEqualTo(maestro2);
        maestro1.setId(null);
        assertThat(maestro1).isNotEqualTo(maestro2);
    }
}
