package com.Week3.JPATutorial;

import com.Week3.JPATutorial.entities.ProductEntity;
import com.Week3.JPATutorial.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class JpaTutorialApplicationTests {
  @Autowired
	ProductRepository productRepository;
	@Test
	void contextLoads() {
	}
	@Test
	void textRepository(){
		ProductEntity productEntity = ProductEntity.builder()
				.sku("nest123")
				.title("nestle")
				.price(BigDecimal.valueOf(23.33))
				.quantity(4)
				.build();
		ProductEntity savedProductEntity = productRepository.save(productEntity);
		System.out.println(savedProductEntity);


	}

	@Test
	void getRepository(){
//		List<ProductEntity> entities = productRepository.findByCreatedAtAfter(LocalDateTime.of(2025,1,1,0,0,0));
//		List<ProductEntity> entities = productRepository.findByQuantityAndPrice(4,BigDecimal.valueOf(23.45));
		List<ProductEntity> entities = productRepository.findByTitleContainingIgnoreCase("CHOco",null);
		System.out.println(entities);
	}
 
}
