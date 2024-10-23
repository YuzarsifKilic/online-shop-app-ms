package com.yuzarsif.cartservice.client;


import java.util.List;
import java.util.Set;

public record ProductResponse(
        Long id,
        String name,
        Double price,
        String mainImageUrl
) {

}
