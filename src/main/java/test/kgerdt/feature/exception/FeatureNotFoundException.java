package test.kgerdt.feature.exception;

public class FeatureNotFoundException extends RuntimeException {
    public FeatureNotFoundException(String message) {
        super(message);
    }
}