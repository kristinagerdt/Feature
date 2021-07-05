package test.kgerdt.feature.model.entity;

import lombok.Data;

@Data
public class Acquisition {
    private long beginViewingDate;
    private long endViewingDate;
    private String missionName;
}