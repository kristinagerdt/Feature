package test.kgerdt.feature.service.impl;

import org.springframework.stereotype.Service;
import test.kgerdt.feature.model.web.FeatureWebDto;
import test.kgerdt.feature.service.FeatureService;
import test.kgerdt.feature.tool.Parser;

import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService {

    private final Parser parser;

    public FeatureServiceImpl(Parser parser) {
        this.parser = parser;
    }

    @Override
    public List<FeatureWebDto> getAllFeatures() {
        return null;
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