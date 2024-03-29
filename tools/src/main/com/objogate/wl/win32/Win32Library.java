package com.objogate.wl.win32;

import com.sun.jna.win32.W32APITypeMapper;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.StdCallLibrary;

import java.util.Map;
import java.util.HashMap;


public interface Win32Library extends StdCallLibrary {
    Map UNICODE_OPTIONS = new HashMap() {
        {
            put(OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
            put(OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
        }
    };
    /**
     * Standard options to use the ASCII/MBCS version of a w32 API.
     */
    Map ASCII_OPTIONS = new HashMap() {
        {
            put(OPTION_TYPE_MAPPER, W32APITypeMapper.ASCII);
            put(OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.ASCII);
        }
    };
    Map DEFAULT_OPTIONS = Boolean.getBoolean("w32.ascii") ? ASCII_OPTIONS : UNICODE_OPTIONS;
}
