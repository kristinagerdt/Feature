package test.kgerdt.feature.model.entity;

import lombok.Data;

@Data
public class Property {
    private String id;
    private long timestamp;
    private Acquisition acquisition;
    private byte[] quicklook;
}