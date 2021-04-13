package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.AlumnoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Alumno} and its DTO {@link AlumnoDTO}.
 */
@Mapper(componentModel = "spring", uses = { MateriaMapper.class })
public interface AlumnoMapper extends EntityMapper<AlumnoDTO, Alumno> {
    @Mapping(target = "materia", source = "materia", qualifiedByName = "idMateria")
    AlumnoDTO toDto(Alumno s);
}
