package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CalculatedProductDto;
import com.example.demo.dto.ProductDto;

/**
 * @author sureshk
 *
 */
public interface ProductService {

	/**
	 * @return
	 */
	List<ProductDto> getPriceList();

	/**
	 * @param productDtoList
	 * @return
	 */
	CalculatedProductDto calculate(List<ProductDto> productDtoList);
	
}
