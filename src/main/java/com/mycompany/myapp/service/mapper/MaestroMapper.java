package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.MaestroDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Maestro} and its DTO {@link MaestroDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MaestroMapper extends EntityMapper<MaestroDTO, Maestro> {}
