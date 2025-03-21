package net.gabriele333.fmtt.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ModpackVersion {
    public static String main() {
        String url = "https://img.shields.io/curseforge/v/843360?logo=curseforge&label=Last%20Modpack%20Version";
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
        if (svg == null) {
            System.out.println("SVG content is null.");
            return null;
        }

        try {
            // Parse the SVG content
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new java.io.ByteArrayInputStream(svg.getBytes()));

            // Create XPath object
            XPath xpath = XPathFactory.newInstance().newXPath();

            // Compile the XPath expression to find the text node
            String expression = "//text()[contains(., 'From Magic To Tech')]";

            // Evaluate the XPath expression
            Node node = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);

            if (node != null) {
                String fullText = node.getTextContent().trim();
                System.out.println("Full text extracted: " + fullText); // Debugging

                // Use regex to extract the version number (e.g., 3.2.2.10)
                Pattern pattern = Pattern.compile("From Magic To Tech \\d+\\.\\d+\\.\\d+-(\\d+(?:\\.\\d+)+)");
                Matcher matcher = pattern.matcher(fullText);

                if (matcher.find()) {
                    return matcher.group(1); // Return the version part (e.g., 3.2.2.10)
                } else {
                    System.out.println("Regex did not match the version.");
                }
            } else {
                System.out.println("XPath did not find the text node.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}