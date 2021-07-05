package test.kgerdt.feature.model.entity;

import lombok.Data;

import java.util.List;

@Data
public class Feature {
    private List<Property> properties;
}