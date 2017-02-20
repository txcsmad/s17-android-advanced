/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.txcsmad.androidadvanced.lesson2.applied;

import com.annimon.stream.Stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    private final Random random = new Random();

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));

        words = new ArrayList<>();

        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();

            if (word.length() >= MIN_WORD_LENGTH) {
                words.add(line.trim());
            }
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        for(String word : words) {
            if(word.startsWith(prefix)) {
                return word;
            }
        }

        return "";
    }

    @Override
    public String getGoodWordStartingWith(final String prefix) {
        List<String> wordsWithPrefix = Stream.of(words)
                .filter(word -> word.contains(prefix))
                .toList();

        int listSize = wordsWithPrefix.size();

        return listSize > 0 ?
                wordsWithPrefix.get(random.nextInt(listSize)) :
                "";
    }
}
