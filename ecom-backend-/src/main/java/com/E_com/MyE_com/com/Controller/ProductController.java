package com.E_com.MyE_com.com.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.E_com.MyE_com.com.Model.Products;
import com.E_com.MyE_com.com.service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
	@Autowired
	ProductService service;
	@RequestMapping("/")
	public String greet() {
		return "hello";
	}
	@GetMapping("/products")
	public  ResponseEntity<List<Products>> getallproducts(){
		return new ResponseEntity<>(service.getallproducts(),HttpStatus.OK);
	}
	@GetMapping("/product/{id}")
	public ResponseEntity<Products> getProduct(@PathVariable int id) {
		Products product=service.getProductByid(id);
		if(product!=null)
			return new ResponseEntity<>(service.getProductByid(id),HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	@PostMapping("/product")
	public ResponseEntity<?> addProducts(
		    @RequestPart Products product,        // 👈 important
		    @RequestPart MultipartFile imageFile) {
		try {
			Products product1=service.addProducts(product,imageFile);
			return new ResponseEntity<>(product1,HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/product/{productId}/image")
	public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
		Products product1=service.getProductByid(productId);
		byte[] imgeFile=product1.getImagedate();
		return ResponseEntity.ok().contentType(MediaType.valueOf(product1.getImagetype())).body(imgeFile);
		
	}
	@PutMapping("/product/{productId}")
	public ResponseEntity<String> updateProduct(@PathVariable("productId") int productId,
	                                            @RequestPart Products product,
	                                            @RequestPart MultipartFile imageFile) {
	    Products product1 = null;
	    try {
	        product1 = service.updateProduct(productId, product, imageFile);
	    } catch (IOException e) {
	        return new ResponseEntity<>("Failed to Update", HttpStatus.BAD_REQUEST);
	    }
	    if (product1 != null) {
	        return new ResponseEntity<>("Updated", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Failed to Update", HttpStatus.BAD_REQUEST);
	    }
	}
	@DeleteMapping("product/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id){
		Products product2=service.getProductByid(id);
		if(product2 !=null) {
			service.deleteproduct(id);
			return new ResponseEntity<String>("deleted",HttpStatus.OK);

		}
		else {
			return new ResponseEntity<String>("Product Not Found",HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/product/search")
	public ResponseEntity<List<Products>> searchProducts (@RequestParam String keyword){ 
		List<Products> products =service.searchProducts(keyword); 
		return new ResponseEntity<>(products, HttpStatus.OK);
		}
	
	
}
