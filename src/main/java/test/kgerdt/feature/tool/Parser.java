package test.kgerdt.feature.tool;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import test.kgerdt.feature.model.entity.Acquisition;
import test.kgerdt.feature.model.entity.Feature;
import test.kgerdt.feature.model.entity.Property;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Parser {

    public List<Feature> loadAllFeatures() {
        String sourceJson = "src/main/resources/static/source-data.json";

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
        byte[] quicklook = Base64.getDecoder().decode(quicklookString);
        property.setQuicklook(quicklook);

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