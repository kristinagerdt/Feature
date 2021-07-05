package test.kgerdt.feature.service;

import test.kgerdt.feature.model.web.FeatureWebDto;

import java.util.List;

public interface FeatureService {

    List<FeatureWebDto> getAllFeatures();

    FeatureWebDto getFeatureById(String id);

    byte[] getImageByFeatureId(String id);
}