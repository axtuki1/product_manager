package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Item;
import com.example.demo.entity.ItemSales;

public interface ItemSalesRepository extends JpaRepository<ItemSales,Integer>, JpaSpecificationExecutor<ItemSales> {

	List<ItemSales> findByItemId(int itemId);
	
	List<ItemSales> findBySalesCode(int saleCode);
	
}
