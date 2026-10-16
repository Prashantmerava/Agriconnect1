package com.prashant.agriconnect.repository;

import com.prashant.agriconnect.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {}
