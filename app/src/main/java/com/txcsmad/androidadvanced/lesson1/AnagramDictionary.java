package com.txcsmad.androidadvanced.lesson1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

@SuppressWarnings("WeakerAccess")
public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 6;

    private Random random = new Random();
    private HashSet<String> wordSet = new HashSet<>();
    private ArrayList<String> wordList = new ArrayList<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);

            ArrayList<String> currentWordsList = new ArrayList<>();
            String key = alphabeticalOrder(word);

            if (!lettersToWord.containsKey(key)) {
                currentWordsList.add(word);
                lettersToWord.put(key, currentWordsList);
            } else {
                currentWordsList = lettersToWord.get(key);
                currentWordsList.add(word);
                lettersToWord.put(key, currentWordsList);
            }
        }
    }

    public String alphabeticalOrder(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);

        return new String(chars);
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !base.contains(word);
    }

    public ArrayList<String> getAnagrams(String word) {
        ArrayList<String> resultant;
        ArrayList<String> result = new ArrayList<String>();

        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            String newWord = word + alphabet;
            String extendedKey = alphabeticalOrder(newWord);

            if (lettersToWord.containsKey(extendedKey)) {
                resultant = lettersToWord.get(extendedKey);

                for (int i = 0; i < resultant.size(); i++) {
                    result.add(String.valueOf(resultant.get(i)));
                }
            }
        }
//            if(lettersToWord.containsKey(extendedKey) && isGoodWord(word,newWord)){
//                resultant = new ArrayList();
//                resultant = (ArrayList) lettersToWord.get(extendedKey);
//                for(int i = 0;i< resultant.size();i++)
//                    result.add(String.valueOf(resultant.get(i)));
//            }

        return result;
    }

    public String pickGoodStarterWord() {
        int wordIndex = random.nextInt(9000) + 1;
        String randomWord = wordList.get(wordIndex);
        ArrayList<String> resultsForWord = lettersToWord.get(alphabeticalOrder(randomWord));

        if (resultsForWord.size() >= MIN_NUM_ANAGRAMS && randomWord.length() > MAX_WORD_LENGTH) {
            return randomWord;
        } else {
            return pickGoodStarterWord();
        }
    }
}