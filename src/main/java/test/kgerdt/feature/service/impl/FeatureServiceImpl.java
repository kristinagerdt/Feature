package test.kgerdt.feature.service.impl;

import org.springframework.stereotype.Service;
import test.kgerdt.feature.model.entity.Feature;
import test.kgerdt.feature.model.web.FeatureWebDto;
import test.kgerdt.feature.service.FeatureService;
import test.kgerdt.feature.tool.Converter;
import test.kgerdt.feature.tool.Parser;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeatureServiceImpl implements FeatureService {

    private final Parser parser;
    private List<Feature> features;

    public FeatureServiceImpl(Parser parser) {
        this.parser = parser;
    }

    @Override
    public List<FeatureWebDto> getAllFeatures() {
        return features
                .stream()
                .map(Converter::convertToFeatureWebDto)
                .collect(Collectors.toList());
    }

    @Override
    public FeatureWebDto getFeatureById(String id) {
        return null;
    }

    @Override
    public byte[] getImageByFeatureId(String id) {
        return new byte[0];
    }
}