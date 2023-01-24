package hu.ltk.jakabgabor;
import hu.ltk.jakabgabor.entity.MotorwayVignette;
import hu.ltk.jakabgabor.storage.MotorwayVignetteStorageInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TollSystemPersistenceStorage implements MotorwayVignetteStorageInterface {
    private List<MotorwayVignette> motorwayVignetteList;

    public TollSystemPersistenceStorage() {
        this.motorwayVignetteList = new ArrayList<>();
        motorwayVignetteList.add(new MotorwayVignette("asd-123",
                "d1", "yearly", 1000, new Date(), new Date(), new Date()));
    }

    @Override
    public List<MotorwayVignette> findByRegistrationNumber(String registrationNumber) {
        return motorwayVignetteList.stream()
                .filter(motorwayVignette -> motorwayVignette.getRegistrationNumber()
                        .equals(registrationNumber))
                .collect(Collectors.toList());
    }
}
