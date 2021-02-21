package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sureshk
 *
 */
@Entity
@Data
@NoArgsConstructor
public class Product {
	
	@TableGenerator(name = "sequence_item", table = "ID_GEN", pkColumnName = "GEN_KEY", pkColumnValue = "id", valueColumnName = "LAST_VALUE", initialValue = 0, allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sequence_item")
	private Long id;
	
	private String name;
	
	//assume decimal point qty is not allowed
	private Integer qty;
	
	private Integer unitsPerCarton;
	
	private Double price;
}
