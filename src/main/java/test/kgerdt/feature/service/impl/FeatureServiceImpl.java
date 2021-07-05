package test.kgerdt.feature.service.impl;

import org.springframework.stereotype.Service;
import test.kgerdt.feature.model.entity.Feature;
import test.kgerdt.feature.model.web.FeatureWebDto;
import test.kgerdt.feature.service.FeatureService;
import test.kgerdt.feature.tool.Converter;
import test.kgerdt.feature.tool.Parser;

import java.util.List;
import java.util.Optional;
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

    private Optional<Feature> getEntityFeature(String id) {
        return features
                .stream()
                .filter(feature -> id.equals(feature.getProperties().get(0).getId()))
                .findAny();
    }

    @Override
    public FeatureWebDto getFeatureById(String id) {
        Optional<Feature> optionalFeature = getEntityFeature(id);
        if (optionalFeature.isPresent()) {
            return Converter.convertToFeatureWebDto(optionalFeature.get());
        }
        throw new RuntimeException("Feature not found, id=" + id);
    }

    @Override
    public byte[] getImageByFeatureId(String id) {
        return new byte[0];
    }
}