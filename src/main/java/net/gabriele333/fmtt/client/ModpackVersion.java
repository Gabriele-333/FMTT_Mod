package net.gabriele333.fmtt.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class ModpackVersion {
    public static String main() {
        String url = "https://img.shields.io/curseforge/v/843360";
        String svgContent = fetchSVGContent(url);
        String version = extractVersion(svgContent);
        return version;
    }

    private static String fetchSVGContent(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String extractVersion(String svg) {
        Pattern pattern = Pattern.compile("From Magic to Tech (\\d+\\.\\d+\\.\\d+-\\d+\\.\\d+\\.\\d+(?:\\.\\d+)?)");
        Matcher matcher = pattern.matcher(svg);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }
}
