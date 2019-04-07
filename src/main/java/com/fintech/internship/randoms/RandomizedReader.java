package com.fintech.internship.randoms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RandomizedReader {

    public String generate(InputStreamReader fileReader) {

        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader
                (fileReader)) {
            String word;
            while ((word = reader.readLine()) != null) {
                words.add(word);
            }
            int randomWordNumber = (int) (Math.random() * words.size());
            return words.get(randomWordNumber);
        } catch (IOException e) {
            return "cannot read the file";
        }
    }
}