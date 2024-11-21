package com.ziio.mapstruct;

import com.ziio.model.dto.generator.GeneratorAddRequest;
import com.ziio.model.dto.generator.GeneratorEditRequest;
import com.ziio.model.dto.generator.GeneratorQueryRequest;
import com.ziio.model.dto.generator.GeneratorUpdateRequest;
import com.ziio.model.entity.Generator;
import com.ziio.model.vo.GeneratorVO;
import org.apache.ibatis.annotations.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Generator MapStruct
 */
@Mapper
public interface GeneratorConvert {

    GeneratorConvert INSTANCE = Mappers.getMapper(GeneratorConvert.class);

    Generator convertGeneratorByAddRequest(GeneratorAddRequest generatorAddRequest);

    Generator convertGeneratorByUpdateRequest(GeneratorUpdateRequest generatorUpdateRequest);

    Generator convertGeneratorByQueryRequest(GeneratorQueryRequest generatorQueryRequest);

    Generator convertGeneratorByEditRequest(GeneratorEditRequest generatorEditRequest);

    GeneratorVO convertGeneratorVOByGenerator(Generator generator);

}
