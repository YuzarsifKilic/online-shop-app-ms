package com.yuzarsif.contextshare.productservice.repository;

import com.yuzarsif.contextshare.productservice.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findAllByProductId(Long productId);
}
