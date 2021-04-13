package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.MateriaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Materia} and its DTO {@link MateriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MateriaMapper extends EntityMapper<MateriaDTO, Materia> {
    @Named("idMateria")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "idMateria", source = "idMateria")
    MateriaDTO toDtoIdMateria(Materia materia);
}
