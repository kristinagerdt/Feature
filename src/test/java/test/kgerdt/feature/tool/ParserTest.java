package test.kgerdt.feature.tool;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.kgerdt.feature.model.entity.Feature;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParserTest {

    @Test
    @DisplayName("Load all features")
    void loadAllFeaturesTest() {
        Parser parser = new Parser();
        List<Feature> features = parser.loadAllFeatures();
        assertThat(features).hasSize(1);
        assertThat(features.get(0).getProperties().get(0).getId()).isEqualTo("39c2f29e-c0f8-4a39-a98b-deed547d6aea");
    }
}