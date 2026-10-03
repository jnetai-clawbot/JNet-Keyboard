package com.jnetai.keyboard.ime;

/**
 * Maps the negative key codes used by the Common Phrases pages to positions in the stored
 * phrase list. Page 1 keeps the original codes -201..-212; every following page is offset by
 * PHRASE_PAGE_STRIDE so the codes never collide with the "+ Add Phrase" key (-213).
 */
public final class PhraseCodes {
    public static final int BASE = -201;
    public static final int PAGE_STRIDE = 20;
    public static final int PER_PAGE = 12;
    public static final int ADD_CODE = -213;

    private PhraseCodes() {
    }

    public static int codeFor(int phraseIndex) {
        if (phraseIndex < 0) return 0;
        int page = phraseIndex / PER_PAGE;
        int slot = phraseIndex % PER_PAGE;
        return BASE - page * PAGE_STRIDE - slot;
    }

    public static int indexFor(int code) {
        if (code > BASE) return -1;
        int offset = BASE - code;
        int page = offset / PAGE_STRIDE;
        int slot = offset % PAGE_STRIDE;
        if (slot >= PER_PAGE) return -1;
        return page * PER_PAGE + slot;
    }

    public static int slotInPage(int code) {
        int index = indexFor(code);
        return index < 0 ? -1 : index % PER_PAGE;
    }

    public static int pageOf(int code) {
        int index = indexFor(code);
        return index < 0 ? -1 : index / PER_PAGE;
    }

    public static boolean isPhraseCode(int code) {
        return indexFor(code) >= 0;
    }
}