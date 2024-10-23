package com.yuzarsif.userservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
public class Seller extends User {

    private String companyName;
    private String companyLogoUrl;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Seller seller = (Seller) o;
        return Objects.equals(companyName, seller.companyName) && Objects.equals(companyLogoUrl, seller.companyLogoUrl) && Objects.equals(phoneNumber, seller.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyName, companyLogoUrl, phoneNumber);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "companyName='" + companyName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
