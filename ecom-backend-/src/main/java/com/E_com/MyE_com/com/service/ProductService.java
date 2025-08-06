package com.E_com.MyE_com.com.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.E_com.MyE_com.com.Model.Products;
import com.E_com.MyE_com.com.Repo.ProductRepo;

@Service
public class ProductService {
	@Autowired
	private ProductRepo repo;
	public List<Products> getallproducts(){
		return repo.findAll();
	}
	public Products getProductByid(int id) {
		return repo.findById(id).orElse(null);
	}
	public Products addProducts(Products product, MultipartFile imageFile) throws IOException {
		product.setImagename(imageFile.getOriginalFilename());
		product.setImagetype(imageFile.getContentType());
		product.setImagedate(imageFile.getBytes());
		return repo.save(product);
	}
	public Products updateProduct(int id, Products product, MultipartFile imageFile) throws IOException {
		product.setImagedate(imageFile.getBytes());
		product.setImagename(imageFile.getOriginalFilename());
		product.setImagetype(imageFile.getContentType());
		return  repo.save(product);
		
	}
	public void deleteproduct(int id) {
		 repo.deleteById(id);
	}
	public List<Products> searchProducts(String keyword) {
		return repo.searchProducts (keyword);
		
	}

}
