package test.kgerdt.feature.tool;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import test.kgerdt.feature.model.entity.Acquisition;
import test.kgerdt.feature.model.entity.Feature;
import test.kgerdt.feature.model.entity.Property;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class Parser {

    @Value("${source.json}")
    private String sourceJson;

    @Value("${source.noImage}")
    private String sourceNoImage;
    private byte[] noImageByteArray;

    @PostConstruct
    public void init() {
        noImageByteArray = getNoImageByteArray(sourceNoImage);
    }

    private byte[] getNoImageByteArray(String path) {
        try {
            File initialFile = new File(path);
            InputStream inputStream = FileUtils.openInputStream(initialFile);
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public List<Feature> loadAllFeatures() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(sourceJson)) {
            List<Feature> features = new ArrayList<>();

            Object object = jsonParser.parse(reader);
            JSONArray featureObjects = (JSONArray) object;

            for (Object featureObject : featureObjects) {
                features.add(getFeatureObject((JSONObject) featureObject));
            }
            return features;
        } catch (ParseException | IOException e) {
            return new ArrayList<>();
        }
    }

    private Feature getFeatureObject(JSONObject jsonObject) {
        Feature feature = new Feature();
        List<Property> properties = new ArrayList<>();

        JSONArray propertyObjects = (JSONArray) jsonObject.get("features");

        for (Object propertyObject : propertyObjects) {
            properties.add(getPropertyObject((JSONObject) propertyObject));
        }

        feature.setProperties(properties);
        return feature;
    }

    private Property getPropertyObject(JSONObject jsonObject) {
        Property property = new Property();

        JSONObject propertyObject = (JSONObject) jsonObject.get("properties");

        String id = (String) propertyObject.get("id");
        property.setId(id);

        long timestamp = (long) propertyObject.get("timestamp");
        property.setTimestamp(timestamp);

        JSONObject acquisitionObject = (JSONObject) propertyObject.get("acquisition");
        Acquisition acquisition = getAcquisitionObject(acquisitionObject);
        property.setAcquisition(acquisition);

        String quicklookString = (String) propertyObject.get("quicklook");
        if (quicklookString != null) {
            byte[] quicklook = Base64.getDecoder().decode(quicklookString);
            property.setQuicklook(quicklook);
        } else {
            property.setQuicklook(noImageByteArray);
        }

        return property;
    }

    private Acquisition getAcquisitionObject(JSONObject jsonObject) {
        Acquisition acquisition = new Acquisition();

        long beginViewingDate = (long) jsonObject.get("beginViewingDate");
        acquisition.setBeginViewingDate(beginViewingDate);

        long endViewingDate = (long) jsonObject.get("endViewingDate");
        acquisition.setEndViewingDate(endViewingDate);

        String missionName = (String) jsonObject.get("missionName");
        acquisition.setMissionName(missionName);

        return acquisition;
    }
}