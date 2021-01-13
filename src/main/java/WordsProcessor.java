import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class WordsProcessor {
    BufferedReader reader;
    Set<String> badWords = new HashSet<>();
    static List<String> list = new ArrayList<>();

    public WordsProcessor(Path badWordsFilePath) throws IOException {
        initBadWords(badWordsFilePath);
    }

    public Map<String, Integer> getAllWords(Path path) throws IOException {
        reader = new BufferedReader(new FileReader(path.toFile()));
        Map<String, Integer> words = new HashMap<>();
        String line;
        String regex = "[!._,'@?-: \"]";
        while ((line = reader.readLine()) != null){
            StringTokenizer str = new StringTokenizer(line,regex);
            while(str.hasMoreTokens()) {
                String word = str.nextToken().toLowerCase();
                if (!words.containsKey(word))
                    words.put(word, 1);
                else
                    words.put(word, words.get(word) + 1);
            }
        }
        return words;
    }

    public Map<String, Integer> getCensoredWords(Map<String, Integer> map) {
        map.entrySet().removeIf(entry -> entry.getKey().length() <= 2 || badWords.contains(entry.getKey()));
        return map;
    }

    public Map<String, Integer> getWordsBySearchDeep(int n, Map<String, Integer> words){
        return words.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(n).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public String[] getBadWordsToArray(Set<String> set){
        String[] badWordsArray = set.toArray(new String[0]);
        return badWordsArray;
    }


    public void initBadWords(Path path) throws IOException {
        reader = new BufferedReader(new FileReader(path.toFile()));
        String line;
        while((line = reader.readLine()) != null)
            badWords.add(line.toLowerCase());
        reader.close();
    }
}
