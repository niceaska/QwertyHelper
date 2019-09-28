package ru.niceaska.qwertyhelper;

import android.provider.SyncStateContract;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordsHelperTest {

    private static final String[] rus = {"й", "ц", "у", "к", "е", "н"};
    private static final String[] eng = {"q", "w", "e", "r", "t", "y"};
    private PasswordsHelper helper;

    private static final String[] SOURCES = {
            "",
            "йцукен",
            "//"
    };
    private static final String[] RESULTS = {
            "",
            "qwerty",
            "//"
    };

    @Before
    public void setUp() throws Exception {
        helper = new PasswordsHelper(rus, eng);
    }

    @Test
    public void convert() {
        assertEquals("Errror is test case", SOURCES.length == RESULTS.length);

        for (int i = 0; i < SOURCES.length; i++) {
            assertEquals(RESULTS[i], helper.convert(SOURCES[i]));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertIsThrows() {
        new PasswordsHelper(rus, new String[]{}).convert(SOURCES[1]);
    }
}