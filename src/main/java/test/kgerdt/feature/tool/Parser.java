package test.kgerdt.feature.tool;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;

public class Parser {

    public void loadAllFeatures() {
        String sourceJson = "src/main/resources/static/source-data.json";

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(sourceJson)) {
            Object object = jsonParser.parse(reader);
            JSONArray featureObjects = (JSONArray) object;

            for (Object featureObject : featureObjects) {
                parseFeatureObject((JSONObject) featureObject);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private void parseFeatureObject(JSONObject jsonObject) {
        JSONArray propertyObjects = (JSONArray) jsonObject.get("features");

        for (Object propertyObject : propertyObjects) {
            parsePropertyObject((JSONObject) propertyObject);
        }
    }

    private void parsePropertyObject(JSONObject jsonObject) {

        JSONObject propertyObject = (JSONObject) jsonObject.get("properties");

        String id = (String) propertyObject.get("id");
        System.out.println(id);

        long timestamp = (long) propertyObject.get("timestamp");
        System.out.println(timestamp);

        JSONObject acquisitionObject = (JSONObject) propertyObject.get("acquisition");
        parseAcquisitionObject(acquisitionObject);

        String quicklookString = (String) propertyObject.get("quicklook");
        byte[] quicklook = Base64.getDecoder().decode(quicklookString);
        System.out.println(quicklook.length);
    }

    private void parseAcquisitionObject(JSONObject jsonObject) {
        long beginViewingDate = (long) jsonObject.get("beginViewingDate");
        System.out.println(beginViewingDate);

        long endViewingDate = (long) jsonObject.get("endViewingDate");
        System.out.println(endViewingDate);

        String missionName = (String) jsonObject.get("missionName");
        System.out.println(missionName);
    }
}