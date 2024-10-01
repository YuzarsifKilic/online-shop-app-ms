package com.yuzarsif.contextshare.productservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    @Column(name = "photo_order")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(id, photo.id) && Objects.equals(url, photo.url) && Objects.equals(order, photo.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, order);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", order=" + order +
                '}';
    }
}
