package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MaestroMapperTest {

    private MaestroMapper maestroMapper;

    @BeforeEach
    public void setUp() {
        maestroMapper = new MaestroMapperImpl();
    }
}
