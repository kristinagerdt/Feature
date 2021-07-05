package test.kgerdt.feature.model.web;

import lombok.Data;

@Data
public class FeatureWebDto {
    private String id;
    private long timestamp;
    private long beginViewingDate;
    private long endViewingDate;
    private String missionName;
}