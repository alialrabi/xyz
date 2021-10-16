package com.upwork.xyz.service.mapper;

import org.mapstruct.Mapper;
import com.upwork.xyz.model.Product;
import com.upwork.xyz.service.dto.ProductDTO;

/**
 * 
 * @author ali
 *
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {}
