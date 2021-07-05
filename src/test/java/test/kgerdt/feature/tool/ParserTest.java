package test.kgerdt.feature.tool;

import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void parseEntitiesTest() {
        Parser parser = new Parser();
        parser.loadAllFeatures();
    }
}