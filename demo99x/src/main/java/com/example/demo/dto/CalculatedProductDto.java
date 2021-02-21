package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sureshk
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculatedProductDto {

	private List<ProductDto> productDto;
	//calculation should be server side
	private Double total;
}
