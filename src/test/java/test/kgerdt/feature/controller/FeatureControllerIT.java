package test.kgerdt.feature.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import test.kgerdt.feature.exception.FeatureNotFoundException;
import test.kgerdt.feature.model.web.FeatureWebDto;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeatureControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String URL = "/features/";

    @BeforeAll
    static void beforeAll() {
        System.out.println("----------> " + FeatureControllerIT.class.getSimpleName());
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Running Test: " + testInfo.getDisplayName());
    }

    @Test
    @DisplayName("Get all features")
    void getAllFeaturesTest() {
        ResponseEntity<FeatureWebDto[]> response = testRestTemplate.getForEntity(URL, FeatureWebDto[].class);
        FeatureWebDto[] features = response.getBody();
        assertThat(features).hasSizeGreaterThan(10);
    }

    @Test
    @DisplayName("Get feature by id")
    void getFeatureByIdTest() {
        String url = URL + "39c2f29e-c0f8-4a39-a98b-deed547d6aea";
        FeatureWebDto response = testRestTemplate.getForObject(url, FeatureWebDto.class);
        assertThat(response.getMissionName()).isEqualTo("Sentinel-1B");
    }

    @Test
    @DisplayName("Get feature by id, throw exception")
    void getFeatureByIdThrowExceptionTest() {
        String url = URL + "1078";
        FeatureNotFoundException response = testRestTemplate.getForObject(url, FeatureNotFoundException.class);
        assertThat(response.getMessage()).containsIgnoringCase("Feature not found");
    }

    @Test
    @DisplayName("Get image by featureId")
    void getImageByFeatureIdTest() {
        String url = URL + "39c2f29e-c0f8-4a39-a98b-deed547d6aea" + "/quicklook";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.IMAGE_PNG));

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = testRestTemplate.exchange(url, HttpMethod.GET, request, byte[].class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    @DisplayName("Get image by featureId, throw exception")
    void getImageByFeatureIdThrowExceptionTest() {
        String url = URL + "failId" + "/quicklook";

        String response = testRestTemplate.getForObject(url, String.class);
        assertThat(response).containsIgnoringCase("Feature not found");
    }
}