package ru.niceaska.qwertyhelper;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PasswordsHelper {
    private final String[] fromLang;
    private final String[] toLang;
    private Map<String, String> keysDict;

    public PasswordsHelper(String[] from, String[] to) {
        this.fromLang = from;
        this.toLang = to;
    }

    private Map<String, String> fillMap() {
        keysDict = new HashMap<>();
        for (int i = 0; i < fromLang.length; i++) {
            keysDict.put(fromLang[i], toLang[i]);
        }
        return keysDict;
    }

    public CharSequence convert(CharSequence source) {
        StringBuilder res = new StringBuilder();

        if (fromLang.length != toLang.length) {
            throw  new IllegalArgumentException();
        }
        if (keysDict == null)
            keysDict = fillMap();
       for (int i = 0; i < source.length(); i++) {
            String key = String.valueOf(source.charAt(i)).toLowerCase();
            if (keysDict.containsKey(key)) {
                boolean isCapital = Character.isUpperCase(source.charAt(i));
                res.append(isCapital ? keysDict.get(key).toUpperCase() : keysDict.get(key));
            } else {
                res.append(source.charAt(i));
            }
        }
        return (res);
    }

    public int checkPassword(CharSequence pass) {
        boolean isCaps = Pattern.matches("\\S*[A-Z]+\\S*", pass);
        boolean isDigit = Pattern.matches("\\S*\\d+\\S*", pass);
        boolean isAlphs = Pattern.matches("\\S*[a-z]+\\S*", pass);
        boolean isSpecial = Pattern.matches("\\S*[^A-Za-z0-9]+\\S*", pass);

        int res = pass.length();

        if (res > 8) {
            res += ((isAlphs && isCaps) || (isAlphs && isDigit)) ? 3 : 0;
            res += ((isAlphs || isCaps || isDigit) && isSpecial) ? 4 : 0;
        }
        return res;
    }
}
