package test.kgerdt.feature.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import test.kgerdt.feature.exception.FeatureNotFoundException;
import test.kgerdt.feature.model.web.FeatureWebDto;
import test.kgerdt.feature.service.FeatureService;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static test.kgerdt.feature.controller.Helper.getQuicklookString;

@WebMvcTest(FeatureController.class)
class FeatureControllerMvcTest {

    @MockBean
    private FeatureService featureService;

    @Autowired
    private MockMvc mockMvc;

    private final String URL = "/features/";

    private FeatureWebDto featureWebDto;

    @BeforeAll
    static void beforeAll() {
        System.out.println("----------> " + FeatureControllerMvcTest.class.getSimpleName());
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
    void getAllFeaturesTest() throws Exception {
        List<FeatureWebDto> features = new ArrayList<>();
        features.add(featureWebDto);

        given(featureService.getAllFeatures()).willReturn(features);

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("39c2f29e-c0f8-4a39-a98b-deed547d6aea")))
                .andExpect(jsonPath("$[0].timestamp", is(1554831167697L)))
                .andExpect(jsonPath("$[0].beginViewingDate", is(1554831202043L)))
                .andExpect(jsonPath("$[0].endViewingDate", is(1554831167697L)))
                .andExpect(jsonPath("$[0].missionName", is("Sentinel-1B")));
    }

    @Test
    @DisplayName("Get all features, result - empty list")
    void getAllFeaturesEmptyListTest() throws Exception {
        List<FeatureWebDto> features = new ArrayList<>();

        given(featureService.getAllFeatures()).willReturn(features);

        mockMvc.perform(get(URL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Get feature by id")
    void getFeatureByIdTest() throws Exception {
        given(featureService.getFeatureById(anyString())).willReturn(featureWebDto);

        String id = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";

        mockMvc.perform(get(URL + "{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("39c2f29e-c0f8-4a39-a98b-deed547d6aea")))
                .andExpect(jsonPath("$.timestamp", is(1554831167697L)))
                .andExpect(jsonPath("$.beginViewingDate", is(1554831202043L)))
                .andExpect(jsonPath("$.endViewingDate", is(1554831167697L)))
                .andExpect(jsonPath("$.missionName", is("Sentinel-1B")));
    }

    @Test
    @DisplayName("Get feature by id, throw exception")
    void getFeatureByIdThrowExceptionTest() throws Exception {
        given(featureService.getFeatureById(anyString())).willThrow(new FeatureNotFoundException("Feature not found"));

        String id = "1078";

        mockMvc.perform(get(URL + "{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get image by featureId")
    void getImageByFeatureIdTest() throws Exception {
        String quicklookString = getQuicklookString("src/test/java/test/kgerdt/feature/controller/quicklook.txt");
        byte[] quicklook = Base64.getDecoder().decode(quicklookString);

        given(featureService.getImageByFeatureId(anyString())).willReturn(quicklook);

        String id = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";

        mockMvc.perform(get(URL + "{id}/quicklook", id)
                .accept(MediaType.IMAGE_PNG_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG_VALUE));
    }

    @Test
    @DisplayName("Get image by featureId, throw exception")
    void getImageByFeatureIdThrowExceptionTest() throws Exception {
        given(featureService.getImageByFeatureId(anyString())).willThrow(new FeatureNotFoundException("Feature not found"));

        String id = "1078";

        mockMvc.perform(get(URL + "{id}/quicklook", id))
                .andExpect(status().isNotFound());
    }
}