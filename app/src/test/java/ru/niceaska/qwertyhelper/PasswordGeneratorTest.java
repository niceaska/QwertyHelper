package ru.niceaska.qwertyhelper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordGeneratorTest {

    private PasswordGenerator passwordGenerator;

    @Before
    public void setUp() throws Exception {
        passwordGenerator = new PasswordGenerator();
    }

    @Test
    public void generatePassword() {
        for (int i = 0; i < 108; i++) {
            assertEquals(i, passwordGenerator.generatePassword(((int)Math.random() * 100), i).length());
        }
    }
}