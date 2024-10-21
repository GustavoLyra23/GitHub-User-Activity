package org.example.service;

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
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Body: " + response.body());
    }





}
