package com.example.demo.dto;

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
public class ProductDto {
	
	private Long id;
	private String name;
	private Integer qty;
	private Integer unitsPerCarton;
	private Double price;
	private Integer requestQty=0;
	//error messages
	private String message;
	//if 1, stock not enough
	private Integer errorCode;
	
}
