package sample;

import static sample.Main.dictionary;

public class Word {
    String value;
    int address;
    String suggestions;

    Word(String s) {
        value       = s.toLowerCase();
        suggestions = "";
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isAlphabetic(value.charAt(i))) {
                value = value.replace(value.charAt(i) + "", "");
            }
        }
    }
    String getContext(String[] testDoc) {
        int begin, end, location, item;
        String sum;
        begin = -1;
        end = -1;

        if (periodExists(testDoc[address])) {
            location = searchForPeriod(testDoc[address]);
            if (location > 0) {
                end   = location;
                sum   = testDoc[address].substring(0, end);
            } else {
                begin = location;
                sum   = testDoc[address].substring(begin);
            }
        } else sum = testDoc[address] + " ";
        item          = address - 1;
        while ((begin == -1) && (item >= 0)) {
            if (periodExists(testDoc[item])) {
                begin = searchForPeriod(testDoc[item]);
                sum   = testDoc[item].substring(begin) + " " + sum;
            } else {
                sum   = testDoc[item] + " " + sum;
                item--;
            }
        }
        item = address + 1;
        while ((end == -1) && (item < testDoc.length)) {
            if (periodExists(testDoc[item])) {
                end   = searchForPeriod(testDoc[item]);
                sum = sum + testDoc[item].substring(0, end) + " ";
            } else {
                sum   = sum + testDoc[item] + " ";
                item++;
            }
        }
        return sum;
    }
    int searchForPeriod(String subject) {
        for (int i = 0; i < subject.length(); i++) {
            if (subject.charAt(i) == '.') return i;
        }
        return -1;
    }
    boolean periodExists(String subject) {
        for (int i = 0; i < subject.length(); i++) {
            if (subject.charAt(i) == '.') return true;
        }
        return false;
    }
    String getSuggestions() {
        checkVowelSwitch();
        checkLettersFlipFlop();
        checkLetterCutOff();
        checkLetterToShort();
        if (suggestions.length() <= 0)
            suggestions = "No suggestions.";
        return suggestions;
    }
    void checkLetterToShort(){
        char [] alphabet;
        int suggestionCount;
        suggestionCount = 0;
        alphabet = new char[] {'a','b','c','d','e','f','g','h',
                'i','j','k','l','m','n','o','p','q','r','s',
                't','u','v','w','x','y','z'};
        for(int i = 0; i < alphabet.length;i++){
            if(dictionary.contains(value + alphabet[i])){
                suggestions = suggestions + value + alphabet[i] + "\n";
                suggestionCount++;
                if(suggestionCount >= 5)break;
            }
        }
    }
    void checkLetterCutOff(){
        String test;
        if(value.length() > 0) {
            test = value.substring(0, value.length() - 1);
            if (dictionary.contains(test)) {
                suggestions = suggestions + test + "\n";
            }
        }
    }
    void checkLettersFlipFlop() {
        int suggestionCount;
        String results;
        suggestionCount = 0;
        for (int i = 0; i < value.length() - 1; i++) {
            results = value.substring(0, i) + value.charAt(i + 1) +
                    value.charAt(i) +
                    value.substring(i + 2, value.length());
            if (dictionary.contains(results)) {
                suggestions = suggestions + results + "\n";
                suggestionCount++;
            }
            if (suggestionCount >= 5) break;
        }
    }
    void checkVowelSwitch() {
        int suggestionCount;
        String results;
        for (int i = 0; i < value.length(); i++) {
            results = vowelSwitch(i);
            if (!results.equals(""))suggestions = suggestions +
                    results;
            suggestionCount = countSpaces(results);
            if(suggestionCount >= 5) break;
        }
    }
    int countSpaces(String test) {
        int count;
        count = 0;
        for (int i = 0; i < test.length(); i++) {
            if (test.charAt(i) == ' ')count++;
        }
        return count;
    }
    String vowelSwitch(int location) {
        char[] vowel;
        String test;
        vowel = new char[]{'a', 'e', 'i', 'o', 'u'};
        test = "";
        for (int i = 0; i < vowel.length; i++) {
            if (value.charAt(location) == vowel[i]) {
                test = test + replaceVowel(location);
            }
        }
        return test;
    }
    String replaceVowel(int location){
        String results, test;
        char[] vowel;
        vowel = new char[]{'a', 'e', 'i', 'o', 'u'};
        results = "";
        for (int i = 0; i < vowel.length; i++){
            test = value.substring(0, location);
            test = test + vowel[i];
            test = test + value.substring(location + 1);
            if(dictionary.contains(test)){
                results = results + test + "\n";
            }
        }
        return results;
    }
}
