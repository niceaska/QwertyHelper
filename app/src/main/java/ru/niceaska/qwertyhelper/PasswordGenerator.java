package ru.niceaska.qwertyhelper;

import java.util.Random;

public class PasswordGenerator {

    static byte FL_CAPS = (1 << 0);
    static byte FL_DIGITS = (1 << 1);
    static byte FL_SPEACIAL = (1 << 2);
    private static String DIGITS  = "0123456789";
    private static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static String CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String SPECIAL = "~=+%^*/()[]{}/!@#$?|";


    public CharSequence generatePassword(int generatorMode, int len) {
        StringBuilder valuse = new StringBuilder(ALPHABET);
        StringBuilder res = new StringBuilder();
        Random random = new Random();

        if ((generatorMode & FL_CAPS) > 0) {
            valuse.append(CAPS);
        }
        if ((generatorMode & FL_DIGITS) > 0) {
            valuse.append(DIGITS);
        }
        if ((generatorMode & FL_SPEACIAL) > 0) {
            valuse.append(SPECIAL);
        }

        for (int i = 0; i < len; i++) {
            int rand = random.nextInt(valuse.length());
            res.append(valuse.charAt(rand));
        }
        return res;
    }


}
