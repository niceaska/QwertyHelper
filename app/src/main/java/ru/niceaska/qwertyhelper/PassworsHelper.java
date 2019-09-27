package ru.niceaska.qwertyhelper;

import java.util.HashMap;
import java.util.Map;

public class PassworsHelper {
    private final String[] fromLang;
    private final String[] toLang;
    private Map<String, String> keysDict;

    public PassworsHelper(String[] from, String[] to) {
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

    public String convert(CharSequence source) {
        StringBuilder res = new StringBuilder();

        if (fromLang.length != toLang.length) {
            throw  new IllegalArgumentException();
        }
        if (keysDict == null)
            keysDict = fillMap();
        for (int i = 0; i < source.length(); i++) {
            if (keysDict.containsKey(String.valueOf(source.charAt(i)))) {
                res.append(keysDict.get(String.valueOf(source.charAt(i))));
            } else {
                res.append(source.charAt(i));
            }
        }
        return new String(res);
    }
}
