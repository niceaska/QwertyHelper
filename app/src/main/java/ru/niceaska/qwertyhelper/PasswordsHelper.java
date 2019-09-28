package ru.niceaska.qwertyhelper;

import java.util.HashMap;
import java.util.Map;

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
}
