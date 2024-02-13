package org.isa.garage.repository;

import org.isa.garage.entity.GarageService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GarageServiceRepository extends JpaRepository<GarageService,Integer> {
    List<GarageService> findAllByIdIn(List<Integer> ids);

    @Query("SELECT MAX(gs.duration) FROM GarageService gs WHERE gs.id IN :serviceIds")
    Integer findMaxServiceDurationByIdIn(List<Integer> serviceIds);
}
