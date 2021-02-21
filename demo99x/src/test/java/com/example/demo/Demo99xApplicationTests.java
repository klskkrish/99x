package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.service.ProductServiceImpl;

@SpringBootTest
class Demo99xApplicationTests {

	
	@Autowired
	ProductServiceImpl service;
	@Test
	void contextLoads() {
		
		try {
			Method method = ProductServiceImpl.class.getDeclaredMethod("getCartonPrice", new Class[]{int.class,double.class});
			method.setAccessible(true);
			
			//no discount
			assertEquals(175.0, method.invoke(service,1, 175));
			assertEquals(350.0, method.invoke(service,2, 175));
			
			//10% siscount
			assertEquals(472.5, method.invoke(service,3, 175));
			assertEquals(1260.0, method.invoke(service,8, 175));
			
			
			method = ProductServiceImpl.class.getDeclaredMethod("getUnitPrice", new Class[]{int.class,double.class,int.class,int.class});
			method.setAccessible(true);
			//total unit must be <unitForCartoon
			//in this stage not calculate carton total. only singles
			//inputes : totalUnit, cartonPrice, unitForCartoon, totalCarton
			//30% increment added
			assertEquals(11.375, method.invoke(service,1, 175, 20, 0));
			assertEquals(216.125, method.invoke(service,19, 175, 20, 1));
			assertEquals(216.125, method.invoke(service,19, 175, 20, 3));
			
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
	}

}
