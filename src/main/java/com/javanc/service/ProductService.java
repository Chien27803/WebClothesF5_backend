package com.javanc.service;



import com.javanc.model.request.admin.ProductAdminRequest;
import com.javanc.model.response.admin.ProductAdminResponse;
import com.javanc.model.response.admin.ProductPaginationResponse;
import com.javanc.model.response.client.ProductClientResponse;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void createProduct(ProductAdminRequest productAdminRequest) throws IOException;
    ProductPaginationResponse getProducts(String searchKey, String categoryId, String status, String pageNumber);
    void deleteProducts(List<Long> ids);
    ProductAdminResponse getProductById(Long id);

    // user
    List<ProductClientResponse> getProductsForClient(Long categoryId, String searchKey);
    ProductClientResponse getDetailProduct(String slug);
}
