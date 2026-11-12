package org.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class DataImporter {

    @Bean
    CommandLineRunner importCukraszdaData(JdbcTemplate jdbc) {
        return args -> {
            if (isEmpty(jdbc, "suti")) {
                List<Object[]> rows = new ArrayList<>();
                for (String[] f : read("data/suti.txt", 4)) {
                    // a "dijazott" oszlop Access-stílusú logikai érték: -1 = igaz, 0 = hamis
                    boolean dijazott = !f[3].equals("0");
                    rows.add(new Object[]{Integer.valueOf(f[0]), f[1], f[2], dijazott});
                }
                jdbc.batchUpdate("INSERT INTO suti (id, nev, tipus, dijazott) VALUES (?, ?, ?, ?)", rows);
            }
            if (isEmpty(jdbc, "tartalom")) {
                List<Object[]> rows = new ArrayList<>();
                for (String[] f : read("data/tartalom.txt", 3)) {
                    rows.add(new Object[]{Integer.valueOf(f[0]), Integer.valueOf(f[1]), f[2]});
                }
                jdbc.batchUpdate("INSERT INTO tartalom (id, sutiid, mentes) VALUES (?, ?, ?)", rows);
            }
            if (isEmpty(jdbc, "ar")) {
                List<Object[]> rows = new ArrayList<>();
                for (String[] f : read("data/ar.txt", 4)) {
                    rows.add(new Object[]{Integer.valueOf(f[0]), Integer.valueOf(f[1]),
                            Integer.valueOf(f[2]), f[3]});
                }
                jdbc.batchUpdate("INSERT INTO ar (id, sutiid, ertek, egyseg) VALUES (?, ?, ?, ?)", rows);
            }
        };
    }

    private boolean isEmpty(JdbcTemplate jdbc, String table) {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM " + table, Integer.class);
        return count == null || count == 0;
    }



    private List<String[]> read(String path, int columns) throws Exception {
        List<String[]> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource(path).getInputStream(), StandardCharsets.UTF_8))) {
            String line = reader.readLine(); // fejléc
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] raw = line.split("\t", -1);
                String[] fields = new String[columns];
                for (int i = 0; i < columns; i++) {
                    fields[i] = i < raw.length ? clean(raw[i]) : "";
                }
                result.add(fields);
            }
        }
        return result;
    }

    private String clean(String s) {
        return s.replaceAll("[\\u200B\\uFEFF]", "").trim();
    }
}
