package com.eku.EKU.service;

import com.eku.EKU.domain.Building;
import com.eku.EKU.domain.Doodle;
import com.eku.EKU.domain.DoodleResponse;
import com.eku.EKU.exceptions.NoSuchBuildingException;
import com.eku.EKU.exceptions.NoSuchDoodleException;
import com.eku.EKU.form.DoodleForm;
import com.eku.EKU.repository.BuildingRepository;
import com.eku.EKU.repository.DoodleRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class DoodleService {
    private final DoodleRepository doodleRepository;
    private final BuildingRepository buildingRepository;

    public DoodleService(DoodleRepository doodleRepository, BuildingRepository buildingRepository) {
        this.doodleRepository = doodleRepository;
        this.buildingRepository = buildingRepository;
    }

    public DoodleResponse applyDoodle(DoodleForm form) throws IllegalArgumentException, NoSuchBuildingException{
        Building building = buildingRepository.findById(form.getMinor()).orElseThrow(NoSuchBuildingException::new);
        Doodle doodle = Doodle.builder()
                .content(form.getContent())
                .building(building)
                .build();

        return new DoodleResponse(doodleRepository.save(doodle));
    }

    public void deleteDoodle(DoodleForm form) throws NoSuchElementException, IllegalArgumentException {
        doodleRepository.deleteById(form.getDoodleId());
    }

    @Transactional
    public void updateDoodle(DoodleForm form) throws NoSuchDoodleException, IllegalArgumentException{
        Doodle target = doodleRepository.findById(form.getDoodleId()).orElseThrow(NoSuchDoodleException::new);
        if (form.getContent() != null) {
            target.setContent(form.getContent());
        }
    }

    public List<DoodleResponse> getRecentDoodle(String minor) throws NoSuchElementException{
        return doodleRepository.findAllByBuildingBeaconIdEquals(Pageable.ofSize(10), minor).getContent()
                .stream()
                .map(DoodleResponse::new)
                .collect(Collectors.toList());
    }
}
