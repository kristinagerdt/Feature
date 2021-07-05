package test.kgerdt.feature.tool;

import test.kgerdt.feature.model.entity.Feature;
import test.kgerdt.feature.model.entity.Property;
import test.kgerdt.feature.model.web.FeatureWebDto;

public class Converter {

    public static FeatureWebDto convertToFeatureWebDto(Feature feature) {
        FeatureWebDto featureWebDto = new FeatureWebDto();
        Property property = feature.getProperties().get(0);

        featureWebDto.setId(property.getId());
        featureWebDto.setTimestamp(property.getTimestamp());
        featureWebDto.setBeginViewingDate(property.getAcquisition().getBeginViewingDate());
        featureWebDto.setEndViewingDate(property.getAcquisition().getEndViewingDate());
        featureWebDto.setMissionName(property.getAcquisition().getMissionName());

        return featureWebDto;
    }
}