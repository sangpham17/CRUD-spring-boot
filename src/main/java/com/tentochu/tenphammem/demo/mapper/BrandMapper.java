package com.tentochu.tenphammem.demo.mapper;

import java.util.List;


import com.tentochu.tenphammem.demo.dto.BrandDTO;
import com.tentochu.tenphammem.demo.entities.Brands;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandMapper {
    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    BrandDTO toBrandDTO(Brands brands);

    List<BrandDTO> toBrandDTOs(List<Brands> brands);

    Brands toBrands(BrandDTO brandDTO);

}
