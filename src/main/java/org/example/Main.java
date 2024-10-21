package org.example;

import org.example.service.GithubService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        GithubService githubService = GithubService.getInstance();
        Scanner sc = new Scanner(System.in);
        var username = askUsername(sc).replaceAll("<", "").replaceAll(">", "");
        githubService.fetchGithubUserEvents(username);

    }














    private static String askUsername(Scanner sc) {
        String username = sc.nextLine().trim();
        String usernamePattern = "^[a-zA-Z0-9_<>]{3,15}$";
        Pattern pattern = Pattern.compile(usernamePattern);
        Matcher matcher = pattern.matcher(username);
        if (matcher.matches()) {
            System.out.println("Username is valid.");
        } else {
            System.out.println("Username is invalid.");
        }
        return username;
    }


}
