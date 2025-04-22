package zj.rickmortyairpg.rickandmortyfandom;

import io.netty.util.CharsetUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@Service
public class RickAndMortyFandomService {

    private final WebClient webClient;

    public RickAndMortyFandomService() {
        this.webClient = WebClient
                .builder()
                .exchangeStrategies(ExchangeStrategies.builder().codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(1024 * 1024)).build())
                .build();
    }

    public String removeHtml(String s) {
        if (!s.contains("<")) return s;
        return removeHtml(s.substring(0, s.indexOf("<")) + s.substring(s.indexOf(">") + 1));
    }

    public Map<String, String> getInfo(String uri, String name, String image) {
        Map<String, String> map = new HashMap<>();
        map.put("NAME", name);
        if (image != null) map.put("IMAGE", image);
        List<String> aside = webClient
                .get()
                .uri(URI.create("https://rickandmorty.fandom.com" + uri))
                .retrieve()
                .bodyToMono(String.class)
                .block()
                .lines()
                .dropWhile(line -> !line.contains("<aside role=\"region\" class=\"portable-infobox"))
                .takeWhile(line -> !line.contains("</aside>"))
                .filter(line -> line.contains("<h3 class=\"pi-data-label pi-secondary-font") || line.contains("<div class=\"pi-data-value pi-font"))
                .toList();
        if (aside.isEmpty()) return map;
        for (int i = 1; i < aside.size(); i += 2) {
            String left = aside.get(i - 1);
            String right = aside.get(i);
            String key = left.substring(left.indexOf('>') + 1, left.lastIndexOf('<'));
            String value = right.substring(right.indexOf('>') + 1, right.lastIndexOf('<'))
                    .replace("<br>", ", ")
                    .replace("<br />", ", ")
                    .replace("</a>", "");
            String cleanValue = removeHtml(value)
                    .replace("&quot;", "\"")
                    .replace("&#39;", "'")
                    .replace("&#039;", "'");
            map.put(key, cleanValue);
        }
        if (map.get("NAME").equals("Suicide Spaghetti")) {
            map.put("FIRST EPISODE", map.get("ONLY"));
            map.remove("ONLY");
        } else if (map.get("NAME").equals("Pink Sentient Switchblade")) {
            Map<String, String> newMap = new HashMap<>();
            newMap.put("NAME", name);
            newMap.put("IMAGE", image);
            newMap.put("TYPE", "Sentient weapon");
            newMap.put("ORIGIN", map.get("PLACE OF ORIGIN"));
            newMap.put("FIRST EPISODE", map.get("FIRST SEEN IN"));
            return newMap;
        }
        return map;
    }

    public List<Map<String, String>> getInfoFromObjectPage() {
        List<String> filterOut = List.of(
                "Farmer Rick&#039;s Dog",
                "Gwendolyn","Hole in the Wall Where the Men Can See it All",
                "Hologram Rick (Edge of Tomorty: Rick Die Repeat)",
                "List of Rick&#039;s inventions",
                "Robobros",
                "Robot Rick",
                "Salisbury steak");
        List<String> lines = webClient
                .get()
                .uri("https://rickandmorty.fandom.com/wiki/Category:Objects")
                .retrieve()
                .bodyToMono(String.class)
                .block()
                .lines()
                .dropWhile(line -> !line.contains("<div class=\"category-page__members\">"))
                .takeWhile(line -> !line.contains("<div class=\"page-footer\">"))
                .filter(line -> line.contains("><img src") || line.contains("category-page__member-link"))
                .map(String::stripLeading)
                .toList();
        List<Map<String, String>> list = new ArrayList<>();
        String image = null;
        for (String line : lines) {
            if (line.charAt(1) == 'n') {
                image = line.substring(20, line.indexOf("/revision/"));
                continue;
            }
            String name = line.substring(line.indexOf('>') + 1, line.indexOf("</a>"));
            if (!name.startsWith("Category:") && !filterOut.contains(name)) {
                String uri = line.substring(9, line.indexOf("class=") - 2);
                list.add(getInfo(uri, name, image));
            }
            image = null;
        }
        return list;
    }

    public void saveAsCsv() {
        List<Map<String, String>> list = getInfoFromObjectPage();
        SortedSet<String> headers = new TreeSet<>();
        list.forEach(map -> headers.addAll(map.keySet()));
        try (CSVPrinter printer = new CSVPrinter(
                new FileWriter("src/main/resources/fandom-objects.csv", CharsetUtil.UTF_8),
                CSVFormat.EXCEL.builder().setDelimiter(';').get())) {
            printer.printRecord(headers.toArray());
            for (Map<String, String> map : list) {
                for (String key : headers) {
                    String value = map.get(key);
                    printer.print(value == null ? "" : value);
                }
                printer.println();
            }
        } catch (IOException ignored) {}
    }

    public List<Map<String, String>> readFromCsv() {
        List<Map<String, String>> list = new ArrayList<>();
        CSVFormat csvFormat = CSVFormat.EXCEL.builder().setDelimiter(';').setHeader().setSkipHeaderRecord(true).get();
        try (FileReader fileReader = new FileReader("src/main/resources/fandom-objects.csv")) {
            Iterable<CSVRecord> records = csvFormat.parse(fileReader);
            for (CSVRecord record : records) {
                list.add(record.toMap());
            }
        } catch (IOException ignored) {}
        return list;
    }

}
