package william.personal.WOWBackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import william.personal.WOWBackend.models.data.BaseResponse;
import william.personal.WOWBackend.models.entity.SearchResult;
import william.personal.WOWBackend.services.SearchService;

import java.util.List;

@RestController
@RequestMapping("api/search")
public class SearchController {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/")
    public ResponseEntity<BaseResponse<List<SearchResult>>> search(@RequestParam String query, Authentication authentication) {
        var email = authentication.getPrincipal().toString();
        var searchResults = this.searchService.search(query, email);

        return BaseResponse.success("Search success!", searchResults);
    }
}
