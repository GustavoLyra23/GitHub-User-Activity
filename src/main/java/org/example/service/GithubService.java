package org.example.service;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class GithubService {

    private static final GithubService instance = new GithubService();

    private GithubService() {
    }

    public static GithubService getInstance() {
        return instance;
    }

    public void fetchGithubUserEvents(String username) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.github.com/users/" + username + "/events"))
                .GET() // Método GET
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        displayActivity(response.body());
    }


    public static void displayActivity(String jsonResponse) {
        JSONArray events = new JSONArray(jsonResponse);

        for (int i = 0; i < events.length(); i++) {
            JSONObject event = events.getJSONObject(i);
            String type = event.getString("type");
            JSONObject repo = event.getJSONObject("repo");
            String repoName = repo.getString("name");

            switch (type) {
                case "PushEvent":
                    JSONArray commits = event.getJSONObject("payload").getJSONArray("commits");
                    System.out.println("- Pushed " + commits.length() + " commits to " + repoName);
                    break;
                case "IssuesEvent":
                    System.out.println("- Opened a new issue in " + repoName);
                    break;
                case "WatchEvent":
                    System.out.println("- Starred " + repoName);
                    break;
                default:
                    System.out.println("- " + type + " on " + repoName);
                    break;
            }
        }
    }
}
