package com.springboot.nbshop.service.imp;


import com.springboot.nbshop.entity.Product;
import com.springboot.nbshop.reponsitory.ProductRepository;
import com.springboot.nbshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
	public List<Product> getAllProduct() {
        return productRepository.findAll();
    }//findAll

    @Override
    public List<Product> finProductsByProductName(String name) {

        String temp = Normalizer.normalize(name, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String s= pattern.matcher(temp).replaceAll("");

        return productRepository.finProductsByProductName("%"+s+"%");
    }

    @Override
	public void updateProduct(Product product) {
        productRepository.save(product);
    }//add or update (tuy vao pri-key)

    @Override
	public void removeProductById(long id) {
        productRepository.deleteById(id);
    }//delete dua vao pri-key

    @Override
	public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }//search theo id

    @Override
	public List<Product> getAllProductByCategoryId(int id) {
        return productRepository.findAllByCategory_Id(id);
    }
    //findList theo ProductDTO.categoryId

}
