package net.gabriele333.fmtt.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModpackVersion {
    public static String ReadVersion() {
        try {
            String url = "https://github.com/Gabriele-333/FromMagicToTechMod-1.20.1-Forge#readme";
            URL siteUrl = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(siteUrl.openStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            String patternString = "<p dir=\"auto\">Last modpack version:(.*?)</p>";
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(content.toString());

            if (matcher.find()) {
                return matcher.group(1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
