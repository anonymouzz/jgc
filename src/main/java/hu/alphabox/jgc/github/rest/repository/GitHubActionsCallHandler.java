package hu.alphabox.jgc.github.rest.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v3/repos/{owner}/{repository}/actions")
@RequiredArgsConstructor
class GitHubActionsCallHandler {

  @GetMapping("runs")
  ResponseEntity<String> handleRuns(
      @PathVariable String owner,
      @PathVariable String repository,
      @RequestParam MultiValueMap<String, String> requestParams
  ) {
    //We cannot support this feature because of missing key fields from Gitea API
    return ResponseEntity.ok("""
        {
          "total_count": 0,
          "workflow_runs": []
        }
        """);
  }

}
