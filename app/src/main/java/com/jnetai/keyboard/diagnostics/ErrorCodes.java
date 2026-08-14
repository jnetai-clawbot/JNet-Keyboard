package com.jnetai.keyboard.diagnostics;

public final class ErrorCodes {
    private static final String PREFIX = "JNK-";

    public static final String TR_001 = PREFIX + "TR-001"; // Translation network failure
    public static final String TR_002 = PREFIX + "TR-002"; // Translation API error
    public static final String TR_003 = PREFIX + "TR-003"; // Translation invalid response
    public static final String TR_004 = PREFIX + "TR-004"; // Translation timeout
    public static final String TR_005 = PREFIX + "TR-005"; // Translation provider config error

    public static final String UN_001 = PREFIX + "UN-001"; // Unicode mapping not found
    public static final String UN_002 = PREFIX + "UN-002"; // Unicode transformation error
    public static final String UN_003 = PREFIX + "UN-003"; // Unicode surrogate pair error

    public static final String KB_001 = PREFIX + "KB-001"; // Keyboard layout error
    public static final String KB_002 = PREFIX + "KB-002"; // InputConnection error
    public static final String KB_003 = PREFIX + "KB-003"; // Keyboard initialization error

    public static final String ST_001 = PREFIX + "ST-001"; // Settings load error
    public static final String ST_002 = PREFIX + "ST-002"; // Settings save error

    public static final String UP_001 = PREFIX + "UP-001"; // Update check network error
    public static final String UP_002 = PREFIX + "UP-002"; // Update check API error
    public static final String UP_003 = PREFIX + "UP-003"; // Update check parse error

    public static final String CL_001 = PREFIX + "CL-001"; // Clipboard save error
    public static final String CL_002 = PREFIX + "CL-002"; // Clipboard load error

    public static final String EM_001 = PREFIX + "EM-001"; // Emoji load error
    public static final String EM_002 = PREFIX + "EM-002"; // Emoji search error

    public static final String SY_001 = PREFIX + "SY-001"; // Symbol load error

    public static final String RM_001 = PREFIX + "RM-001"; // Remapping load error
    public static final String RM_002 = PREFIX + "RM-002"; // Remapping save error

    public static final String GE_001 = PREFIX + "GE-001"; // General unexpected error

    private ErrorCodes() {}
}
