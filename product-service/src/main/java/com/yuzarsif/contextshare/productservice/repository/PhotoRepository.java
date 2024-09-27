package com.yuzarsif.contextshare.productservice.repository;

import com.yuzarsif.contextshare.productservice.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
