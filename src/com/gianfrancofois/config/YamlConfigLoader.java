package com.gianfrancofois.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlConfigLoader implements ConfigLoader {

    private String filename;

    public YamlConfigLoader(String filename) {
        this.filename = filename;
    }

    @Override
    public Map<String, Object> loadConfiguration() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(filename);
        return yaml.load(inputStream);
    }

}
