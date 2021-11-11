package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Item;

public interface ItemRepository extends JpaRepository<Item,Integer>, JpaSpecificationExecutor<Item> {
//    List<Item> findAllSort(int offset, int limit, String sort);
}
