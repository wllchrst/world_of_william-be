package william.personal.WOWBackend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import william.personal.WOWBackend.configs.EnvConfig;
import william.personal.WOWBackend.models.entity.SearchHistory;
import william.personal.WOWBackend.models.entity.SearchResult;
import william.personal.WOWBackend.repositories.SearchHistoryRepository;
import william.personal.WOWBackend.repositories.SearchResultRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SearchService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EnvConfig envHelper;
    @Autowired
    private UserService userService;

    private final SearchHistoryRepository searchHistoryRepository;
    private final SearchResultRepository searchResultRepository;

    private String url;

    public SearchService(SearchHistoryRepository searchHistoryRepository, SearchResultRepository searchResultRepository) {
        this.url = "https://www.googleapis.com/customsearch/v1";
        this.searchHistoryRepository = searchHistoryRepository;
        this.searchResultRepository = searchResultRepository;
    }

    public List<SearchResult> search(String query, String userEmail) {
        var searchResults = this.googleSearch(query);
        var user = this.userService.getUserByEmail(userEmail);
        var searchHistory = SearchHistory.builder()
                .user(user).searchQuery(query).build();

        this.saveSearch(searchHistory, searchResults);
        return searchResults;
    }

    @Transactional
    public void saveSearch(SearchHistory searchHistory, List<SearchResult> searchResults) {
        this.searchHistoryRepository.save(searchHistory);
        for (SearchResult result : searchResults) {
            result.setSearchHistory(searchHistory);
        }
        this.searchResultRepository.saveAll(searchResults);

        CompletableFuture.completedFuture(null);
    }

    public List<SearchResult> googleSearch(String query) {
        URI uri = UriComponentsBuilder.fromUriString(url)
                .queryParam("q", query)
                .queryParam("key", this.envHelper.getGoogleApiKey())
                .queryParam("cx", this.envHelper.getSearchEngineId())
                .build()
                .toUri();

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            String body = response.getBody();
            return this.extractResultFromJson(body);
        } else
            throw new RestClientException(String.format("Failed doing google search for query: %s", query));
    }

    public List<SearchResult> extractResultFromJson(String body) {
        var searchResults = new ArrayList<SearchResult>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(body);
            JsonNode items = root.path("items");

            if (items.isArray()) {
                for (JsonNode item : items) {
                    String link = item.path("link").asText();
                    String title = item.path("title").asText();
                    String linkSnippet = item.path("snippet").asText();

                    SearchResult searchResult = SearchResult.builder()
                            .link(link).title(title).snippet(linkSnippet).build();

                    searchResults.add(searchResult);
                }
            } else {
                System.out.println("No items found in response.");
            }
        } catch (Exception ignored) {
        }

        return searchResults;
    }
}
