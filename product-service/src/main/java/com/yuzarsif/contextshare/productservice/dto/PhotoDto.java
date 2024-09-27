package com.yuzarsif.contextshare.productservice.dto;

import com.yuzarsif.contextshare.productservice.model.Photo;

import java.util.List;
import java.util.Set;

public record PhotoDto(
    Long id,
    String url,
    Integer order
) {

    public static PhotoDto convert(Photo photo) {
        return new PhotoDto(
            photo.getId(),
            photo.getUrl(),
            photo.getOrder()
        );
    }

    public static List<PhotoDto> convert(Set<Photo> photos) {
        return photos
            .stream()
            .map(PhotoDto::convert)
            .toList();
    }
}
