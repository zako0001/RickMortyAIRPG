package zj.rickmortyai.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import zj.rickmortyai.service.RickMortyApiService;

@Component
public class ApiDataPrinter implements CommandLineRunner {

    @Override
    public void run(String... args) {
        rickMortyApiService
                .fetchCharacters()
                .block()
                .forEach(System.out::println);
        rickMortyApiService
                .fetchLocations()
                .block()
                .forEach(System.out::println);
    }

    private final RickMortyApiService rickMortyApiService;

    public ApiDataPrinter(RickMortyApiService rickMortyApiService) {
        this.rickMortyApiService = rickMortyApiService;
    }
}
