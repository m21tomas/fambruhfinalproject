package coms.configuration;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RandomStringGenerator {
    @Value("${codeLength}")
    private int codeLength;

    public String generate() {
        String generated = "";

        var letters = "abcdefghijklmnopqrstyvwz0123456789"
                .toUpperCase()
                .chars()
                .mapToObj(x -> (char) x)
                .collect(Collectors.toList());

        Collections.shuffle(letters);

        for (int i = 0; i < codeLength; i++) {
            generated += letters.get(i);
        }
        return generated;
    }
}
