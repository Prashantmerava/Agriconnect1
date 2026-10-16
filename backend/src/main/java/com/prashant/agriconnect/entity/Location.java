package com.prashant.agriconnect.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "locations", indexes = @Index(name = "idx_location_coordinates", columnList = "latitude,longitude"))
public class Location {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false) private Double latitude;
    @Column(nullable = false) private Double longitude;
    @Column(nullable = false, length = 255) private String address;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product product;

    public Location() {}
    public String getId() { return id; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
