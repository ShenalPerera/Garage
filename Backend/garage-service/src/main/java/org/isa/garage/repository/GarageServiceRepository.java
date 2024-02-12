package org.isa.garage.repository;

import org.isa.garage.entity.GarageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageServiceRepository extends JpaRepository<GarageService,Integer> {


}
