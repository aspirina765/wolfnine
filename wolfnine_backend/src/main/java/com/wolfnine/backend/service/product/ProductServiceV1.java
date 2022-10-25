package com.wolfnine.backend.service.product;

import com.wolfnine.backend.entity.Product;
import com.wolfnine.backend.entity.dto.ProductDto;
import com.wolfnine.backend.entity.entityEnum.ProductStatus;
import com.wolfnine.backend.repository.ProductRepository;
import com.wolfnine.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceV1 implements ProductService {
    final ProductRepository productRepository;
    final UserService userService;

    @Override
    public List<ProductDto> findByUserId(long userId) {
        List<Product> products = productRepository.findAllByUserId(userId);
        List<ProductDto> productDtos = products.stream()
                .map(product -> product.toProductDto())
                .collect(Collectors.toList());
        return productDtos;
    }

    @Override
    public ProductDto findById(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return product.toProductDto();
        }
        return null;
    }

    @Override
    public Page<ProductDto> findAllByAuthUser(int limit, int page, String sortBy) {
        Page<Product> productPage = productRepository.findAllByUserId(
                userService.findByAuthUser().getId(),
                PageRequest.of(page, limit, Sort.by(sortBy).descending())
        );
        List<ProductDto> products = productPage.stream()
                .map(product -> product.toProductDto())
                .collect(Collectors.toList());
        return new PageImpl<>(products, PageRequest.of(page, limit, Sort.by(sortBy).descending()), productPage.getTotalElements());
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Override
    public List<Product> findAllByStatus(ProductStatus status) {
        return productRepository.findAllByStatus(status);
    }

    @Override
    public Product update(long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean deleteAllByIdIn(List<Long> ids) {
        try {
            productRepository.deleteByIdIn(ids);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(long id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
