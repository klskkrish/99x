package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CalculatedProductDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.service.ProductService;

/**
 * @author sureshk
 *
 */
@CrossOrigin("*")
@RestController
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("get-price-list")
	public List<ProductDto> getPriceList() {
		try {
			return productService.getPriceList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	@PostMapping("calculate")
	public CalculatedProductDto calculate(@RequestBody List<ProductDto> productDtoList) {
		try {
			return productService.calculate(productDtoList);
		} catch (Exception e) {
			return new CalculatedProductDto();
		}
	}

}
