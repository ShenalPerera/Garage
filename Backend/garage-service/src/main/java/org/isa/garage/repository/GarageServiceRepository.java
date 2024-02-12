package org.isa.garage.repository;

import org.isa.garage.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageServiceRepository extends JpaRepository<Service,Integer> {


}
