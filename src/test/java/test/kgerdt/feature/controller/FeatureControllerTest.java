package test.kgerdt.feature.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.kgerdt.feature.exception.FeatureNotFoundException;
import test.kgerdt.feature.model.web.FeatureWebDto;
import test.kgerdt.feature.service.FeatureService;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static test.kgerdt.feature.controller.Helper.getQuicklookString;

@ExtendWith(MockitoExtension.class)
class FeatureControllerTest {

    @Mock
    private FeatureService featureService;

    @InjectMocks
    private FeatureController featureController;

    private FeatureWebDto featureWebDto;

    @BeforeAll
    static void beforeAll() {
        System.out.println("----------> " + FeatureControllerTest.class.getSimpleName());
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Running Test: " + testInfo.getDisplayName());

        String id = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";
        long timestamp = 1554831167697L;
        long beginViewingDate = 1554831202043L;
        long endViewingDate = 1554831167697L;
        String missionName = "Sentinel-1B";

        featureWebDto = new FeatureWebDto();
        featureWebDto.setId(id);
        featureWebDto.setTimestamp(timestamp);
        featureWebDto.setBeginViewingDate(beginViewingDate);
        featureWebDto.setEndViewingDate(endViewingDate);
        featureWebDto.setMissionName(missionName);
    }

    @Test
    @DisplayName("Get all features")
    void getAllFeaturesTest() {
        List<FeatureWebDto> features = new ArrayList<>();
        features.add(featureWebDto);

        given(featureService.getAllFeatures()).willReturn(features);

        List<FeatureWebDto> response = featureController.getAllFeatures();

        then(featureService).should().getAllFeatures();
        assertThat(response).isNotNull();
        assertThat(response).hasSize(1);
        assertThat(response.get(0).getId()).isEqualTo("39c2f29e-c0f8-4a39-a98b-deed547d6aea");
    }

    @Test
    @DisplayName("Get all features, result - empty list")
    void getAllFeaturesEmptyListTest() {
        List<FeatureWebDto> features = new ArrayList<>();

        given(featureService.getAllFeatures()).willReturn(features);

        List<FeatureWebDto> response = featureController.getAllFeatures();

        then(featureService).should().getAllFeatures();
        assertThat(response).isEmpty();
        assertThat(response).hasSize(0);
    }

    @Test
    @DisplayName("Get feature by id")
    void getFeatureByIdTest() {
        given(featureService.getFeatureById(anyString())).willReturn(featureWebDto);

        FeatureWebDto response = featureController.getFeatureById(anyString());

        then(featureService).should().getFeatureById(anyString());
        assertThat(response).isNotNull();
        assertThat(response.getMissionName()).isEqualTo("Sentinel-1B");
    }

    @Test
    @DisplayName("Get feature by id, throw exception")
    void getFeatureByIdThrowExceptionTest() {
        given(featureService.getFeatureById(anyString())).willThrow(new FeatureNotFoundException("Feature not found"));

        assertThrows(FeatureNotFoundException.class, () -> featureService.getFeatureById(anyString()));
        then(featureService).should().getFeatureById(anyString());
    }

    @Test
    @DisplayName("Get image by featureId")
    void getImageByFeatureIdTest() {
        String quicklookString = getQuicklookString("src/test/java/test/kgerdt/feature/controller/quicklook.txt");
        byte[] quicklook = Base64.getDecoder().decode(quicklookString);

        given(featureService.getImageByFeatureId(anyString())).willReturn(quicklook);

        byte[] response = featureController.getImageByFeatureId(anyString());

        then(featureService).should().getImageByFeatureId(anyString());
        assertThat(response).isNotNull();
        assertThat(response[0]).isEqualTo(Byte.valueOf("-119"));
    }

    @Test
    @DisplayName("Get image by featureId, throw exception")
    void getImageByFeatureIdThrowExceptionTest() {
        given(featureService.getImageByFeatureId(anyString())).willThrow(new FeatureNotFoundException("Feature not found"));

        assertThrows(FeatureNotFoundException.class, () -> featureService.getImageByFeatureId(anyString()));
        then(featureService).should().getImageByFeatureId(anyString());
    }
}