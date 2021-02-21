package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CalculatedProductDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

/**
 * @author sureshk
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	ModelMapper modelMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.demo.service.ProductService#getPriceList()
	 */
	@Override
	public List<ProductDto> getPriceList() {
		Iterable<Product> productList = productRepository.findAll();
		// convert entity to DTO
		List<ProductDto> prductDtoList = new ArrayList<>();
		productList.forEach(product -> {
			prductDtoList.add(modelMapper.map(product, ProductDto.class));
		});
		return prductDtoList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.demo.service.ProductService#calculate(java.util.List)
	 */
	@Override
	public CalculatedProductDto calculate(List<ProductDto> productDtoList) {

		Map<Long, ProductDto> productDtoMap = new HashMap<>();
		// recheck item details and qty
		productDtoList.forEach(productDto -> {
			if (productDto.getRequestQty() > 0) {
				Product product = productRepository.findById(productDto.getId()).orElse(null);
				if (null != product) {
					// check availability of map
					if (productDtoMap.containsKey(productDto.getId())) {
						Integer newQty = productDtoMap.get(productDto.getId()).getQty() + productDto.getRequestQty();
						productDto.setQty(newQty);
					}
					// check qty
					if (product.getQty() >= productDto.getRequestQty()) {
						productDto.setPrice(product.getPrice());
						productDto.setName(product.getName());
						productDto.setUnitsPerCarton(product.getUnitsPerCarton());
					} else {
						// add error
						productDto.setMessage(product.getQty() + "unit(s) only available in stock");
						productDto.setErrorCode(1);
					}
				}
				productDtoMap.put(productDto.getId(), productDto);
			}

		});
		// calculate total price
		return calculator(productDtoMap);
	}

	/**
	 * @param productDtoMap
	 * @return
	 */
	private CalculatedProductDto calculator(Map<Long, ProductDto> productDtoMap) {
		CalculatedProductDto calculatedProductDto = new CalculatedProductDto();

		productDtoMap.forEach((k, productDto) -> {
			if (null == productDto.getErrorCode()) {
				int remaining = productDto.getRequestQty() % productDto.getUnitsPerCarton();
				int unitsForCarton = productDto.getRequestQty() - remaining;
				int totalCarton = unitsForCarton / productDto.getUnitsPerCarton();

				double cartonPrice = getCartonPrice(totalCarton, productDto.getPrice());
				double remainUnitPrice = getUnitPrice(remaining, productDto.getPrice(), productDto.getUnitsPerCarton(),
						totalCarton);

				double availableTotal = null != calculatedProductDto.getTotal() ? calculatedProductDto.getTotal() : 0;
				calculatedProductDto.setTotal(availableTotal + cartonPrice + remainUnitPrice);
			}
		});

		calculatedProductDto.setProductDto(new ArrayList<>(productDtoMap.values()));

		return calculatedProductDto;
	}

	/**
	 * @param totalCarton
	 * @param cartonPrice
	 * @return
	 */
	private double getCartonPrice(int totalCarton, double cartonPrice) {
		double totCartonPrice = cartonPrice * totalCarton;
		if (totalCarton >= 3) {
			// 10% discount
			double discount = (totCartonPrice / 100) * 10;
			totCartonPrice = totCartonPrice - discount;
		}
		return totCartonPrice;
	}

	/**
	 * @param totalUnit
	 * @param cartonPrice
	 * @param unitForCarton
	 * @param totalCarton
	 * @return
	 */
	private double getUnitPrice(int totalUnit, double cartonPrice, int unitForCarton, int totalCarton) {
		double unitPrice = (cartonPrice / unitForCarton);
		double totalUnitPrice = unitPrice * totalUnit;
		//if (totalCarton < 3) {
			// 30% increment
			double increment = (totalUnitPrice / 100) * 30;
			totalUnitPrice = totalUnitPrice + increment;
		//}
		return totalUnitPrice;
	}
}
