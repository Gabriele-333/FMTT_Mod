/*
 * This file is part of From Magic To Tech.
 * Copyright (c) 2024, Gabriele_333, All rights reserved.
 *
 * From Magic To Tech is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * From Magic To Tech is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with From Magic To Tech.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
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
        Pattern pattern = Pattern.compile("From Magic to Tech (\\d+\\.\\d+\\.\\d+-\\d+\\.\\d+\\.\\d+)");
        Matcher matcher = pattern.matcher(svg);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }
}


