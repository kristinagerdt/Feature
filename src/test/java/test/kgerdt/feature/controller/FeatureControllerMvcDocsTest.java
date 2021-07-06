package test.kgerdt.feature.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import test.kgerdt.feature.model.web.FeatureWebDto;
import test.kgerdt.feature.service.FeatureService;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static test.kgerdt.feature.controller.Helper.getQuicklookString;

@WebMvcTest(FeatureController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
class FeatureControllerMvcDocsTest {

    @MockBean
    private FeatureService featureService;

    @Autowired
    private MockMvc mockMvc;

    private final String URL = "/features/";

    private FeatureWebDto featureWebDto;

    @BeforeAll
    static void beforeAll() {
        System.out.println("----------> " + FeatureControllerMvcDocsTest.class.getSimpleName());
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
                .andDo(
                        document("features-get",
                                responseFields(
                                        fieldWithPath("[].id").description("Id"),
                                        fieldWithPath("[].timestamp").description("Timestamp"),
                                        fieldWithPath("[].beginViewingDate").description("Begin Viewing Date"),
                                        fieldWithPath("[].endViewingDate").description("End Viewing Date"),
                                        fieldWithPath("[].missionName").description("Mission Name"))
                        ));
    }

    @Test
    @DisplayName("Get feature by id")
    void getFeatureByIdTest() throws Exception {
        given(featureService.getFeatureById(anyString())).willReturn(featureWebDto);

        String id = "39c2f29e-c0f8-4a39-a98b-deed547d6aea";

        mockMvc.perform(get(URL + "{id}", id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(
                        document("feature-by-id",
                                pathParameters(parameterWithName("id").description("Feature id of desires feature to get")),
                                responseFields(
                                        fieldWithPath("id").description("Id"),
                                        fieldWithPath("timestamp").description("Timestamp"),
                                        fieldWithPath("beginViewingDate").description("Begin Viewing Date"),
                                        fieldWithPath("endViewingDate").description("End Viewing Date"),
                                        fieldWithPath("missionName").description("Mission Name"))
                        ));
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
                .andDo(
                        document("image-by-feature-id",
                                pathParameters(parameterWithName("id").description("Image of desires feature to get"))
                        ));
    }
}