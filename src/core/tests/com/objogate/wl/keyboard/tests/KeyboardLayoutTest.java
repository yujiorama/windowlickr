package com.objogate.wl.keyboard.tests;

import static com.objogate.wl.keyboard.KeyboardLayout.getKeyboardLayout;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.objogate.wl.Problematic;
import com.objogate.wl.internal.Platform;
import com.objogate.wl.keyboard.KeyboardLayout;


public class KeyboardLayoutTest {
    static final String[] KNOWN_LAYOUTS = {"GB", "US"};

    @Test
    public void translatesNumbersKeyStrokesInAllKnownLayouts() {
        for (String layoutName : KNOWN_LAYOUTS) {
            KeyboardLayout layout = getKeyboardLayout(layoutName);

            for (char ch = '0'; ch <= '9'; ch++) {
                assertHasTranslation(layout, ch);
            }
        }
    }

    @Test
    public void translatesLatinCharactersToKeyStrokesInAllKnownLayouts() {
        for (String layoutName : KNOWN_LAYOUTS) {
            KeyboardLayout layout = getKeyboardLayout(layoutName);

            for (char lower = 'a'; lower <= 'z'; lower++) {
                char upper = Character.toUpperCase(lower);
                
                assertHasTranslation(layout, lower);
                assertHasTranslation(layout, upper);
            }
        }
    }

    @Test
    public void translatesTabSpaceReturnAndBackspaceInAllKnownLayouts() {
        for (String layoutName : KNOWN_LAYOUTS) {
            KeyboardLayout layout = getKeyboardLayout(layoutName);

            assertHasTranslations(layout, " \t\b\n");
        }
    }
    
    @Test
    @Problematic(platform = Platform.Mac, why="throws - java.lang.IllegalArgumentException: no stroke available for character '¨'")
    public void translatesAllPunctuationAndSymbolsOnGBKeyboardToKeyStrokes() {
        assertHasTranslations(getKeyboardLayout("GB"), "\u00AC`!\"\u00A3$%^&*()-_=+[{]};:'@#~,<.>/?\\|");
    }
    
    @Test
    public void translatesAllPunctuationAndSymbolsOnUSKeyboardToKeyStrokes() {
        assertHasTranslations(getKeyboardLayout("US"), "`!\"$%^&*()-_=+[{]};:'@#~,<.>/?\\|");
    }
    
    private void assertHasTranslation(KeyboardLayout layout, char ch) {
        assertHasTranslations(layout, String.valueOf(ch));
    }
    
    private void assertHasTranslations(KeyboardLayout layout, String characters) {
        StringBuffer untranslated = new StringBuffer();
        
        for (int i = 0; i < characters.length(); i++) {
            char ch = characters.charAt(i);

            try {
                layout.gestureForTyping(ch);
            }
            catch (IllegalArgumentException e) {e.printStackTrace();
                untranslated.append(ch);
            }
        }

        assertTrue(layout + " has no translation for: " + untranslated, untranslated.length() == 0);
    }
}
