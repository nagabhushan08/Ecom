package com.E_com.MyE_com.com.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.E_com.MyE_com.com.Model.Products;

@Repository
public interface ProductRepo extends JpaRepository<Products, Integer> {

    @Query("SELECT p from Products p WHERE " + // Changed 'Product' to 'Products' for consistency
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Products> searchProducts(@Param("keyword") String keyword);
}