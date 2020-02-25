package org.fasttrakit.online.shop.persistance;

import org.fasttrakit.online.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {



}
