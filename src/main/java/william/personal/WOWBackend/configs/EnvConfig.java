package william.personal.WOWBackend.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    @Value("${google.api.search.key}")
    private String googleApiKey;

    @Value("${search.engine.id}")
    private String searchEngineId;


    public String getGoogleApiKey() {
        return googleApiKey;
    }

    public String getSearchEngineId() {
        return searchEngineId;
    }
}
