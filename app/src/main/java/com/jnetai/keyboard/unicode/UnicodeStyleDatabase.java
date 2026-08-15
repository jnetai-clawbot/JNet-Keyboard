package com.jnetai.keyboard.unicode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UnicodeStyleDatabase {
    public static class UnicodeStyle {
        public final String id;
        public final String displayName;
        public final String preview;
        public final String category;
        public boolean favourite;
        public final Map<Character, String> mappings;

        UnicodeStyle(String id, String displayName, String preview, String category,
                     Map<Character, String> mappings) {
            this.id = id;
            this.displayName = displayName;
            this.preview = preview;
            this.category = category;
            this.favourite = false;
            this.mappings = mappings;
        }
    }

    private static final List<UnicodeStyle> styles = new ArrayList<>();
    private static final Map<String, UnicodeStyle> styleMap = new LinkedHashMap<>();

    static {
        registerStyle("normal", "Normal", "Normal", "Default", normalMappings());
        registerStyle("bold", "Bold", "𝐁𝐨𝐥𝐝", "Serif", boldMappings());
        registerStyle("italic", "Italic", "𝐼𝑡𝑎𝑙𝑖𝑐", "Serif", italicMappings());
        registerStyle("bold-italic", "Bold Italic", "𝑩𝒐𝒍𝒅 𝑰𝒕𝒂𝒍𝒊𝒄", "Serif", boldItalicMappings());
        registerStyle("script", "Script", "𝒮𝒸𝓇𝒾𝓅𝓉", "Script", scriptMappings());
        registerStyle("bold-script", "Bold Script", "𝓑𝓸𝓵𝓭 𝓢𝓬𝓻𝓲𝓹𝓽", "Script", boldScriptMappings());
        registerStyle("fraktur", "Fraktur", "𝔉𝔯𝔞𝔨𝔱𝔲𝔯", "Fraktur", frakturMappings());
        registerStyle("bold-fraktur", "Bold Fraktur", "𝕱𝖗𝖆𝖐𝖙𝖚𝖗", "Fraktur", boldFrakturMappings());
        registerStyle("sans-serif", "Sans-Serif", "𝖲𝖺𝗇𝗌", "Sans-Serif", sansSerifMappings());
        registerStyle("sans-serif-bold", "Sans-Serif Bold", "𝗦𝗮𝗻𝘀 𝗕𝗼𝗹𝗱", "Sans-Serif", sansSerifBoldMappings());
        registerStyle("sans-serif-italic", "Sans-Serif Italic", "𝘚𝘢𝘯𝘴 𝘐𝘵𝘢𝘭𝘪𝘤", "Sans-Serif", sansSerifItalicMappings());
        registerStyle("sans-serif-bold-italic", "Sans-Serif Bold Italic", "𝙎𝙖𝙣𝙨 𝘽𝙤𝙡𝙙 𝙄𝙩𝙖𝙡𝙞𝙘", "Sans-Serif", sansSerifBoldItalicMappings());
        registerStyle("monospace", "Monospace", "𝙼𝚘𝚗𝚘𝚜𝚙𝚊𝚌𝚎", "Monospace", monospaceMappings());
        registerStyle("double-struck", "Double-Struck", "𝔻𝕠𝕦𝕓𝕝𝕖", "Double-Struck", doubleStruckMappings());
        registerStyle("small-caps", "Small Caps", "Sᴍᴀʟʟ Cᴀᴘs", "Small Caps", smallCapsMappings());
        registerStyle("superscript", "Superscript", "ˢᵘᵖᵉʳˢᶜʳⁱᵖᵗ", "Superscript", superscriptMappings());
        registerStyle("subscript", "Subscript", "ₛᵤᵦₛ꜀ᵣᵢₚₜ", "Subscript", subscriptMappings());
        registerStyle("bubble", "Bubble", "ⓑⓤⓑⓑⓛⓔ", "Enclosed", bubbleMappings());
        registerStyle("bubble-filled", "Bubble Filled", "🅑🅤🅑🅑🅛🅔", "Enclosed", bubbleFilledMappings());
        registerStyle("square", "Square", "🅂🅀🅄🄰🅁🄴", "Enclosed", squareMappings());
        registerStyle("parenthesized", "Parenthesized", "⒫⒜⒭⒠⒩⒯⒣⒠⒮⒤⒵⒠⒟", "Enclosed", parenthesizedMappings());
        registerStyle("fullwidth", "Fullwidth", "Ｆｕｌｌｗｉｄｔｈ", "Fullwidth", fullwidthMappings());
        registerStyle("circled", "Circled", "Ⓒⓘⓡⓒⓛⓔⓓ", "Enclosed", circledMappings());
        registerStyle("inverted", "Inverted", "ɥǝllo", "Flipped", invertedMappings());
        registerStyle("strikethrough", "Strikethrough", "h̶e̶l̶l̶o̶", "Combining", strikethroughMappings());
        registerStyle("underline", "Underline", "h̲e̲l̲l̲o̲", "Combining", underlineMappings());
        registerStyle("double-underline", "Double Underline", "h̳e̳l̳l̳o̳", "Combining", doubleUnderlineMappings());
        registerStyle("overline", "Overline", "h̅e̅l̅l̅o̅", "Combining", overlineMappings());
        registerStyle("double-overline", "Double Overline", "h̿e̿l̿l̿o̿", "Combining", doubleOverlineMappings());
        registerStyle("slashed", "Slashed", "h̸e̸l̸l̸o̸", "Combining", slashedMappings());
        registerStyle("dotted", "Dotted", "ḣėl̇l̇ȯ", "Combining", dottedMappings());
        registerStyle("diaeresis", "Diaeresis", "ḧël̈l̈ö", "Combining", diaeresisMappings());
        registerStyle("tilde", "Tilde", "h̃ẽl̃l̃õ", "Combining", tildeMappings());
        registerStyle("tilde-below", "Tilde Below", "h̰ḛl̰l̰o̰", "Combining", tildeBelowMappings());
        registerStyle("macron", "Macron", "h̄ēl̄l̄ō", "Combining", macronMappings());
        registerStyle("acute", "Acute", "h́éĺĺó", "Combining", acuteMappings());
        registerStyle("grave", "Grave", "h̀èl̀l̀ò", "Combining", graveMappings());
        registerStyle("circumflex", "Circumflex", "ĥêl̂l̂ô", "Combining", circumflexMappings());
        registerStyle("caron", "Caron", "ȟěľľǒ", "Combining", caronMappings());
        registerStyle("breve", "Breve", "h̆ĕl̆l̆ŏ", "Combining", breveMappings());
        registerStyle("ring", "Ring", "h̊e̊l̊l̊o̊", "Combining", ringMappings());
        registerStyle("cedilla", "Cedilla", "ḩȩļļo̧", "Combining", cedillaMappings());
        registerStyle("ogonek", "Ogonek", "h̨ęl̨l̨ǫ", "Combining", ogonekMappings());
        registerStyle("comma", "Comma", "h̓e̓l̓l̓o̓", "Combining", commaMappings());
        registerStyle("negative-squared", "Negative Squared", "🅗🅔🅛🅛🅞", "Enclosed", negativeSquaredMappings());
        registerStyle("zalgo", "Zalgo", "h̷̛̖e̵̱̓l̶̲̕l̶̲̕o̵̱̓", "Combining", zalgoMappings());
        registerStyle("halfwidth", "Halfwidth", "ﾊﾞﾚﾚﾛ", "Halfwidth", halfwidthMappings());
        registerStyle("tiny", "Tiny", "ʰᵉˡˡᵒ", "Small Caps", tinyMappings());
        registerStyle("cursive", "Cursive", "𝒽𝑒𝓁𝓁𝑜", "Script", cursiveMappings());
        registerStyle("gothic", "Gothic", "𝔥𝔢𝔩𝔩𝔬", "Fraktur", gothicMappings());
        registerStyle("typewriter", "Typewriter", "𝚑𝚎𝚕𝚕𝚘", "Monospace", typewriterMappings());
        registerStyle("regional-indicator", "Regional Indicator", "🇭🇪🇱🇱🇴", "Regional", regionalIndicatorMappings());
    }

    private static void registerStyle(String id, String displayName, String preview, String category,
                                       Map<Character, String> mappings) {
        UnicodeStyle style = new UnicodeStyle(id, displayName, preview, category, mappings);
        styles.add(style);
        styleMap.put(id, style);
    }

    public static List<UnicodeStyle> getAllStyles() {
        return Collections.unmodifiableList(styles);
    }

    public static UnicodeStyle getStyle(String id) {
        return styleMap.get(id);
    }

    public static String getDefaultStyleId() {
        return "normal";
    }

    public static String transform(String text, String styleId) {
        if (text == null || text.isEmpty()) return text;
        UnicodeStyle style = styleMap.get(styleId);
        if (style == null) return text;

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isHighSurrogate(c) && i + 1 < text.length()) {
                char low = text.charAt(i + 1);
                if (Character.isLowSurrogate(low)) {
                    int codePoint = Character.toCodePoint(c, low);
                    String mapped = style.mappings.get((char) codePoint);
                    if (mapped != null) {
                        result.append(mapped);
                    } else {
                        result.appendCodePoint(codePoint);
                    }
                    i++;
                    continue;
                }
            }
            String mapped = style.mappings.get(c);
            if (mapped != null) {
                result.append(mapped);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private static Map<Character, String> boldMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝐀"); m.put('B', "𝐁"); m.put('C', "𝐂"); m.put('D', "𝐃"); m.put('E', "𝐄");
        m.put('F', "𝐅"); m.put('G', "𝐆"); m.put('H', "𝐇"); m.put('I', "𝐈"); m.put('J', "𝐉");
        m.put('K', "𝐊"); m.put('L', "𝐋"); m.put('M', "𝐌"); m.put('N', "𝐍"); m.put('O', "𝐎");
        m.put('P', "𝐏"); m.put('Q', "𝐐"); m.put('R', "𝐑"); m.put('S', "𝐒"); m.put('T', "𝐓");
        m.put('U', "𝐔"); m.put('V', "𝐕"); m.put('W', "𝐖"); m.put('X', "𝐗"); m.put('Y', "𝐘");
        m.put('Z', "𝐙");
        m.put('a', "𝐚"); m.put('b', "𝐛"); m.put('c', "𝐜"); m.put('d', "𝐝"); m.put('e', "𝐞");
        m.put('f', "𝐟"); m.put('g', "𝐠"); m.put('h', "𝐡"); m.put('i', "𝐢"); m.put('j', "𝐣");
        m.put('k', "𝐤"); m.put('l', "𝐥"); m.put('m', "𝐦"); m.put('n', "𝐧"); m.put('o', "𝐨");
        m.put('p', "𝐩"); m.put('q', "𝐪"); m.put('r', "𝐫"); m.put('s', "𝐬"); m.put('t', "𝐭");
        m.put('u', "𝐮"); m.put('v', "𝐯"); m.put('w', "𝐰"); m.put('x', "𝐱"); m.put('y', "𝐲");
        m.put('z', "𝐳");
        m.put('0', "𝟎"); m.put('1', "𝟏"); m.put('2', "𝟐"); m.put('3', "𝟑"); m.put('4', "𝟒");
        m.put('5', "𝟓"); m.put('6', "𝟔"); m.put('7', "𝟕"); m.put('8', "𝟖"); m.put('9', "𝟗");
        return m;
    }

    private static Map<Character, String> italicMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝐴"); m.put('B', "𝐵"); m.put('C', "𝐶"); m.put('D', "𝐷"); m.put('E', "𝐸");
        m.put('F', "𝐹"); m.put('G', "𝐺"); m.put('H', "𝐻"); m.put('I', "𝐼"); m.put('J', "𝐽");
        m.put('K', "𝐾"); m.put('L', "𝐿"); m.put('M', "𝑀"); m.put('N', "𝑁"); m.put('O', "𝑂");
        m.put('P', "𝑃"); m.put('Q', "𝑄"); m.put('R', "𝑅"); m.put('S', "𝑆"); m.put('T', "𝑇");
        m.put('U', "𝑈"); m.put('V', "𝑉"); m.put('W', "𝑊"); m.put('X', "𝑋"); m.put('Y', "𝑌");
        m.put('Z', "𝑍");
        m.put('a', "𝑎"); m.put('b', "𝑏"); m.put('c', "𝑐"); m.put('d', "𝑑"); m.put('e', "𝑒");
        m.put('f', "𝑓"); m.put('g', "𝑔"); m.put('h', "ℎ"); m.put('i', "𝑖"); m.put('j', "𝑗");
        m.put('k', "𝑘"); m.put('l', "𝑙"); m.put('m', "𝑚"); m.put('n', "𝑛"); m.put('o', "𝑜");
        m.put('p', "𝑝"); m.put('q', "𝑞"); m.put('r', "𝑟"); m.put('s', "𝑠"); m.put('t', "𝑡");
        m.put('u', "𝑢"); m.put('v', "𝑣"); m.put('w', "𝑤"); m.put('x', "𝑥"); m.put('y', "𝑦");
        m.put('z', "𝑧");
        return m;
    }

    private static Map<Character, String> boldItalicMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝑨"); m.put('B', "𝑩"); m.put('C', "𝑪"); m.put('D', "𝑫"); m.put('E', "𝑬");
        m.put('F', "𝑭"); m.put('G', "𝑮"); m.put('H', "𝑯"); m.put('I', "𝑰"); m.put('J', "𝑱");
        m.put('K', "𝑲"); m.put('L', "𝑳"); m.put('M', "𝑴"); m.put('N', "𝑵"); m.put('O', "𝑶");
        m.put('P', "𝑷"); m.put('Q', "𝑸"); m.put('R', "𝑹"); m.put('S', "𝑺"); m.put('T', "𝑻");
        m.put('U', "𝑼"); m.put('V', "𝑽"); m.put('W', "𝑾"); m.put('X', "𝑿"); m.put('Y', "𝒀");
        m.put('Z', "𝒁");
        m.put('a', "𝒂"); m.put('b', "𝒃"); m.put('c', "𝒄"); m.put('d', "𝒅"); m.put('e', "𝒆");
        m.put('f', "𝒇"); m.put('g', "𝒈"); m.put('h', "𝒉"); m.put('i', "𝒊"); m.put('j', "𝒋");
        m.put('k', "𝒌"); m.put('l', "𝒍"); m.put('m', "𝒎"); m.put('n', "𝒏"); m.put('o', "𝒐");
        m.put('p', "𝒑"); m.put('q', "𝒒"); m.put('r', "𝒓"); m.put('s', "𝒔"); m.put('t', "𝒕");
        m.put('u', "𝒖"); m.put('v', "𝒗"); m.put('w', "𝒘"); m.put('x', "𝒙"); m.put('y', "𝒚");
        m.put('z', "𝒛");
        return m;
    }

    private static Map<Character, String> scriptMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝒜"); m.put('B', "ℬ"); m.put('C', "𝒞"); m.put('D', "𝒟"); m.put('E', "ℰ");
        m.put('F', "ℱ"); m.put('G', "𝒢"); m.put('H', "ℋ"); m.put('I', "ℐ"); m.put('J', "𝒥");
        m.put('K', "𝒦"); m.put('L', "ℒ"); m.put('M', "ℳ"); m.put('N', "𝒩"); m.put('O', "𝒪");
        m.put('P', "𝒫"); m.put('Q', "𝒬"); m.put('R', "ℛ"); m.put('S', "𝒮"); m.put('T', "𝒯");
        m.put('U', "𝒰"); m.put('V', "𝒱"); m.put('W', "𝒲"); m.put('X', "𝒳"); m.put('Y', "𝒴");
        m.put('Z', "𝒵");
        m.put('a', "𝒶"); m.put('b', "𝒷"); m.put('c', "𝒸"); m.put('d', "𝒹"); m.put('e', "ℯ");
        m.put('f', "𝒻"); m.put('g', "ℊ"); m.put('h', "𝒽"); m.put('i', "𝒾"); m.put('j', "𝒿");
        m.put('k', "𝓀"); m.put('l', "𝓁"); m.put('m', "𝓂"); m.put('n', "𝓃"); m.put('o', "ℴ");
        m.put('p', "𝓅"); m.put('q', "𝓆"); m.put('r', "𝓇"); m.put('s', "𝓈"); m.put('t', "𝓉");
        m.put('u', "𝓊"); m.put('v', "𝓋"); m.put('w', "𝓌"); m.put('x', "𝓍"); m.put('y', "𝓎");
        m.put('z', "𝓏");
        return m;
    }

    private static Map<Character, String> boldScriptMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝓐"); m.put('B', "𝓑"); m.put('C', "𝓒"); m.put('D', "𝓓"); m.put('E', "𝓔");
        m.put('F', "𝓕"); m.put('G', "𝓖"); m.put('H', "𝓗"); m.put('I', "𝓘"); m.put('J', "𝓙");
        m.put('K', "𝓚"); m.put('L', "𝓛"); m.put('M', "𝓜"); m.put('N', "𝓝"); m.put('O', "𝓞");
        m.put('P', "𝓟"); m.put('Q', "𝓠"); m.put('R', "𝓡"); m.put('S', "𝓢"); m.put('T', "𝓣");
        m.put('U', "𝓤"); m.put('V', "𝓥"); m.put('W', "𝓦"); m.put('X', "𝓧"); m.put('Y', "𝓨");
        m.put('Z', "𝓩");
        m.put('a', "𝓪"); m.put('b', "𝓫"); m.put('c', "𝓬"); m.put('d', "𝓭"); m.put('e', "𝓮");
        m.put('f', "𝓯"); m.put('g', "𝓰"); m.put('h', "𝓱"); m.put('i', "𝓲"); m.put('j', "𝓳");
        m.put('k', "𝓴"); m.put('l', "𝓵"); m.put('m', "𝓶"); m.put('n', "𝓷"); m.put('o', "𝓸");
        m.put('p', "𝓹"); m.put('q', "𝓺"); m.put('r', "𝓻"); m.put('s', "𝓼"); m.put('t', "𝓽");
        m.put('u', "𝓾"); m.put('v', "𝓿"); m.put('w', "𝔀"); m.put('x', "𝔁"); m.put('y', "𝔂");
        m.put('z', "𝔃");
        return m;
    }

    private static Map<Character, String> frakturMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝔄"); m.put('B', "𝔅"); m.put('C', "ℭ"); m.put('D', "𝔇"); m.put('E', "𝔈");
        m.put('F', "𝔉"); m.put('G', "𝔊"); m.put('H', "ℌ"); m.put('I', "ℑ"); m.put('J', "𝔍");
        m.put('K', "𝔎"); m.put('L', "𝔏"); m.put('M', "𝔐"); m.put('N', "𝔑"); m.put('O', "𝔒");
        m.put('P', "𝔓"); m.put('Q', "𝔔"); m.put('R', "ℜ"); m.put('S', "𝔖"); m.put('T', "𝔗");
        m.put('U', "𝔘"); m.put('V', "𝔙"); m.put('W', "𝔚"); m.put('X', "𝔛"); m.put('Y', "𝔜");
        m.put('Z', "ℨ");
        m.put('a', "𝔞"); m.put('b', "𝔟"); m.put('c', "𝔠"); m.put('d', "𝔡"); m.put('e', "𝔢");
        m.put('f', "𝔣"); m.put('g', "𝔤"); m.put('h', "𝔥"); m.put('i', "𝔦"); m.put('j', "𝔧");
        m.put('k', "𝔨"); m.put('l', "𝔩"); m.put('m', "𝔪"); m.put('n', "𝔫"); m.put('o', "𝔬");
        m.put('p', "𝔭"); m.put('q', "𝔮"); m.put('r', "𝔯"); m.put('s', "𝔰"); m.put('t', "𝔱");
        m.put('u', "𝔲"); m.put('v', "𝔳"); m.put('w', "𝔴"); m.put('x', "𝔵"); m.put('y', "𝔶");
        m.put('z', "𝔷");
        return m;
    }

    private static Map<Character, String> boldFrakturMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝕬"); m.put('B', "𝕭"); m.put('C', "𝕮"); m.put('D', "𝕯"); m.put('E', "𝕰");
        m.put('F', "𝕱"); m.put('G', "𝕲"); m.put('H', "𝕳"); m.put('I', "𝕴"); m.put('J', "𝕵");
        m.put('K', "𝕶"); m.put('L', "𝕷"); m.put('M', "𝕸"); m.put('N', "𝕹"); m.put('O', "𝕺");
        m.put('P', "𝕻"); m.put('Q', "𝕼"); m.put('R', "𝕽"); m.put('S', "𝕾"); m.put('T', "𝕿");
        m.put('U', "𝖀"); m.put('V', "𝖁"); m.put('W', "𝖂"); m.put('X', "𝖃"); m.put('Y', "𝖄");
        m.put('Z', "𝖅");
        m.put('a', "𝖆"); m.put('b', "𝖇"); m.put('c', "𝖈"); m.put('d', "𝖉"); m.put('e', "𝖊");
        m.put('f', "𝖋"); m.put('g', "𝖌"); m.put('h', "𝖍"); m.put('i', "𝖎"); m.put('j', "𝖏");
        m.put('k', "𝖐"); m.put('l', "𝖑"); m.put('m', "𝖒"); m.put('n', "𝖓"); m.put('o', "𝖔");
        m.put('p', "𝖕"); m.put('q', "𝖖"); m.put('r', "𝖗"); m.put('s', "𝖘"); m.put('t', "𝖙");
        m.put('u', "𝖚"); m.put('v', "𝖛"); m.put('w', "𝖜"); m.put('x', "𝖝"); m.put('y', "𝖞");
        m.put('z', "𝖟");
        return m;
    }

    private static Map<Character, String> sansSerifMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝖠"); m.put('B', "𝖡"); m.put('C', "𝖢"); m.put('D', "𝖣"); m.put('E', "𝖤");
        m.put('F', "𝖥"); m.put('G', "𝖦"); m.put('H', "𝖧"); m.put('I', "𝖨"); m.put('J', "𝖩");
        m.put('K', "𝖪"); m.put('L', "𝖫"); m.put('M', "𝖬"); m.put('N', "𝖭"); m.put('O', "𝖮");
        m.put('P', "𝖯"); m.put('Q', "𝖰"); m.put('R', "𝖱"); m.put('S', "𝖲"); m.put('T', "𝖳");
        m.put('U', "𝖴"); m.put('V', "𝖵"); m.put('W', "𝖶"); m.put('X', "𝖷"); m.put('Y', "𝖸");
        m.put('Z', "𝖹");
        m.put('a', "𝖺"); m.put('b', "𝖻"); m.put('c', "𝖼"); m.put('d', "𝖽"); m.put('e', "𝖾");
        m.put('f', "𝖿"); m.put('g', "𝗀"); m.put('h', "𝗁"); m.put('i', "𝗂"); m.put('j', "𝗃");
        m.put('k', "𝗄"); m.put('l', "𝗅"); m.put('m', "𝗆"); m.put('n', "𝗇"); m.put('o', "𝗈");
        m.put('p', "𝗉"); m.put('q', "𝗊"); m.put('r', "𝗋"); m.put('s', "𝗌"); m.put('t', "𝗍");
        m.put('u', "𝗎"); m.put('v', "𝗏"); m.put('w', "𝗐"); m.put('x', "𝗑"); m.put('y', "𝗒");
        m.put('z', "𝗓");
        m.put('0', "𝟢"); m.put('1', "𝟣"); m.put('2', "𝟤"); m.put('3', "𝟥"); m.put('4', "𝟦");
        m.put('5', "𝟧"); m.put('6', "𝟨"); m.put('7', "𝟩"); m.put('8', "𝟪"); m.put('9', "𝟫");
        return m;
    }

    private static Map<Character, String> sansSerifBoldMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝗔"); m.put('B', "𝗕"); m.put('C', "𝗖"); m.put('D', "𝗗"); m.put('E', "𝗘");
        m.put('F', "𝗙"); m.put('G', "𝗚"); m.put('H', "𝗛"); m.put('I', "𝗜"); m.put('J', "𝗝");
        m.put('K', "𝗞"); m.put('L', "𝗟"); m.put('M', "𝗠"); m.put('N', "𝗡"); m.put('O', "𝗢");
        m.put('P', "𝗣"); m.put('Q', "𝗤"); m.put('R', "𝗥"); m.put('S', "𝗦"); m.put('T', "𝗧");
        m.put('U', "𝗨"); m.put('V', "𝗩"); m.put('W', "𝗪"); m.put('X', "𝗫"); m.put('Y', "𝗬");
        m.put('Z', "𝗭");
        m.put('a', "𝗮"); m.put('b', "𝗯"); m.put('c', "𝗰"); m.put('d', "𝗱"); m.put('e', "𝗲");
        m.put('f', "𝗳"); m.put('g', "𝗴"); m.put('h', "𝗵"); m.put('i', "𝗶"); m.put('j', "𝗷");
        m.put('k', "𝗸"); m.put('l', "𝗹"); m.put('m', "𝗺"); m.put('n', "𝗻"); m.put('o', "𝗼");
        m.put('p', "𝗽"); m.put('q', "𝗾"); m.put('r', "𝗿"); m.put('s', "𝘀"); m.put('t', "𝘁");
        m.put('u', "𝘂"); m.put('v', "𝘃"); m.put('w', "𝘄"); m.put('x', "𝘅"); m.put('y', "𝘆");
        m.put('z', "𝘇");
        m.put('0', "𝟬"); m.put('1', "𝟭"); m.put('2', "𝟮"); m.put('3', "𝟯"); m.put('4', "𝟰");
        m.put('5', "𝟱"); m.put('6', "𝟲"); m.put('7', "𝟳"); m.put('8', "𝟴"); m.put('9', "𝟵");
        return m;
    }

    private static Map<Character, String> sansSerifItalicMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝘈"); m.put('B', "𝘉"); m.put('C', "𝘊"); m.put('D', "𝘋"); m.put('E', "𝘌");
        m.put('F', "𝘍"); m.put('G', "𝘎"); m.put('H', "𝘏"); m.put('I', "𝘐"); m.put('J', "𝘑");
        m.put('K', "𝘒"); m.put('L', "𝘓"); m.put('M', "𝘔"); m.put('N', "𝘕"); m.put('O', "𝘖");
        m.put('P', "𝘗"); m.put('Q', "𝘘"); m.put('R', "𝘙"); m.put('S', "𝘚"); m.put('T', "𝘛");
        m.put('U', "𝘜"); m.put('V', "𝘝"); m.put('W', "𝘞"); m.put('X', "𝘟"); m.put('Y', "𝘠");
        m.put('Z', "𝘡");
        m.put('a', "𝘢"); m.put('b', "𝘣"); m.put('c', "𝘤"); m.put('d', "𝘥"); m.put('e', "𝘦");
        m.put('f', "𝘧"); m.put('g', "𝘨"); m.put('h', "𝘩"); m.put('i', "𝘪"); m.put('j', "𝘫");
        m.put('k', "𝘬"); m.put('l', "𝘭"); m.put('m', "𝘮"); m.put('n', "𝘯"); m.put('o', "𝘰");
        m.put('p', "𝘱"); m.put('q', "𝘲"); m.put('r', "𝘳"); m.put('s', "𝘴"); m.put('t', "𝘵");
        m.put('u', "𝘶"); m.put('v', "𝘷"); m.put('w', "𝘸"); m.put('x', "𝘹"); m.put('y', "𝘺");
        m.put('z', "𝘻");
        return m;
    }

    private static Map<Character, String> sansSerifBoldItalicMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝘼"); m.put('B', "𝘽"); m.put('C', "𝘾"); m.put('D', "𝘿"); m.put('E', "𝙀");
        m.put('F', "𝙁"); m.put('G', "𝙂"); m.put('H', "𝙃"); m.put('I', "𝙄"); m.put('J', "𝙅");
        m.put('K', "𝙆"); m.put('L', "𝙇"); m.put('M', "𝙈"); m.put('N', "𝙉"); m.put('O', "𝙊");
        m.put('P', "𝙋"); m.put('Q', "𝙌"); m.put('R', "𝙍"); m.put('S', "𝙎"); m.put('T', "𝙏");
        m.put('U', "𝙐"); m.put('V', "𝙑"); m.put('W', "𝙒"); m.put('X', "𝙓"); m.put('Y', "𝙔");
        m.put('Z', "𝙕");
        m.put('a', "𝙖"); m.put('b', "𝙗"); m.put('c', "𝙘"); m.put('d', "𝙙"); m.put('e', "𝙚");
        m.put('f', "𝙛"); m.put('g', "𝙜"); m.put('h', "𝙝"); m.put('i', "𝙞"); m.put('j', "𝙟");
        m.put('k', "𝙠"); m.put('l', "𝙡"); m.put('m', "𝙢"); m.put('n', "𝙣"); m.put('o', "𝙤");
        m.put('p', "𝙥"); m.put('q', "𝙦"); m.put('r', "𝙧"); m.put('s', "𝙨"); m.put('t', "𝙩");
        m.put('u', "𝙪"); m.put('v', "𝙫"); m.put('w', "𝙬"); m.put('x', "𝙭"); m.put('y', "𝙮");
        m.put('z', "𝙯");
        return m;
    }

    private static Map<Character, String> monospaceMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝙰"); m.put('B', "𝙱"); m.put('C', "𝙲"); m.put('D', "𝙳"); m.put('E', "𝙴");
        m.put('F', "𝙵"); m.put('G', "𝙶"); m.put('H', "𝙷"); m.put('I', "𝙸"); m.put('J', "𝙹");
        m.put('K', "𝙺"); m.put('L', "𝙻"); m.put('M', "𝙼"); m.put('N', "𝙽"); m.put('O', "𝙾");
        m.put('P', "𝙿"); m.put('Q', "𝚀"); m.put('R', "𝚁"); m.put('S', "𝚂"); m.put('T', "𝚃");
        m.put('U', "𝚄"); m.put('V', "𝚅"); m.put('W', "𝚆"); m.put('X', "𝚇"); m.put('Y', "𝚈");
        m.put('Z', "𝚉");
        m.put('a', "𝚊"); m.put('b', "𝚋"); m.put('c', "𝚌"); m.put('d', "𝚍"); m.put('e', "𝚎");
        m.put('f', "𝚏"); m.put('g', "𝚐"); m.put('h', "𝚑"); m.put('i', "𝚒"); m.put('j', "𝚓");
        m.put('k', "𝚔"); m.put('l', "𝚕"); m.put('m', "𝚖"); m.put('n', "𝚗"); m.put('o', "𝚘");
        m.put('p', "𝚙"); m.put('q', "𝚚"); m.put('r', "𝚛"); m.put('s', "𝚜"); m.put('t', "𝚝");
        m.put('u', "𝚞"); m.put('v', "𝚟"); m.put('w', "𝚠"); m.put('x', "𝚡"); m.put('y', "𝚢");
        m.put('z', "𝚣");
        m.put('0', "𝟶"); m.put('1', "𝟷"); m.put('2', "𝟸"); m.put('3', "𝟹"); m.put('4', "𝟺");
        m.put('5', "𝟻"); m.put('6', "𝟼"); m.put('7', "𝟽"); m.put('8', "𝟾"); m.put('9', "𝟿");
        return m;
    }

    private static Map<Character, String> doubleStruckMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝔸"); m.put('B', "𝔹"); m.put('C', "ℂ"); m.put('D', "𝔻"); m.put('E', "𝔼");
        m.put('F', "𝔽"); m.put('G', "𝔾"); m.put('H', "ℍ"); m.put('I', "𝕀"); m.put('J', "𝕁");
        m.put('K', "𝕂"); m.put('L', "𝕃"); m.put('M', "𝕄"); m.put('N', "ℕ"); m.put('O', "𝕆");
        m.put('P', "ℙ"); m.put('Q', "ℚ"); m.put('R', "ℝ"); m.put('S', "𝕊"); m.put('T', "𝕋");
        m.put('U', "𝕌"); m.put('V', "𝕍"); m.put('W', "𝕎"); m.put('X', "𝕏"); m.put('Y', "𝕐");
        m.put('Z', "ℤ");
        m.put('a', "𝕒"); m.put('b', "𝕓"); m.put('c', "𝕔"); m.put('d', "𝕕"); m.put('e', "𝕖");
        m.put('f', "𝕗"); m.put('g', "𝕘"); m.put('h', "𝕙"); m.put('i', "𝕚"); m.put('j', "𝕛");
        m.put('k', "𝕜"); m.put('l', "𝕝"); m.put('m', "𝕞"); m.put('n', "𝕟"); m.put('o', "𝕠");
        m.put('p', "𝕡"); m.put('q', "𝕢"); m.put('r', "𝕣"); m.put('s', "𝕤"); m.put('t', "𝕥");
        m.put('u', "𝕦"); m.put('v', "𝕧"); m.put('w', "𝕨"); m.put('x', "𝕩"); m.put('y', "𝕪");
        m.put('z', "𝕫");
        m.put('0', "𝟘"); m.put('1', "𝟙"); m.put('2', "𝟚"); m.put('3', "𝟛"); m.put('4', "𝟜");
        m.put('5', "𝟝"); m.put('6', "𝟞"); m.put('7', "𝟟"); m.put('8', "𝟠"); m.put('9', "𝟡");
        return m;
    }

    private static Map<Character, String> smallCapsMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('a', "ᴀ"); m.put('b', "ʙ"); m.put('c', "ᴄ"); m.put('d', "ᴅ"); m.put('e', "ᴇ");
        m.put('f', "ꜰ"); m.put('g', "ɢ"); m.put('h', "ʜ"); m.put('i', "ɪ"); m.put('j', "ᴊ");
        m.put('k', "ᴋ"); m.put('l', "ʟ"); m.put('m', "ᴍ"); m.put('n', "ɴ"); m.put('o', "ᴏ");
        m.put('p', "ᴘ"); m.put('q', "ǫ"); m.put('r', "ʀ"); m.put('s', "s"); m.put('t', "ᴛ");
        m.put('u', "ᴜ"); m.put('v', "ᴠ"); m.put('w', "ᴡ"); m.put('x', "x"); m.put('y', "ʏ");
        m.put('z', "ᴢ");
        return m;
    }

    private static Map<Character, String> superscriptMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('0', "⁰"); m.put('1', "¹"); m.put('2', "²"); m.put('3', "³"); m.put('4', "⁴");
        m.put('5', "⁵"); m.put('6', "⁶"); m.put('7', "⁷"); m.put('8', "⁸"); m.put('9', "⁹");
        m.put('a', "ᵃ"); m.put('b', "ᵇ"); m.put('c', "ᶜ"); m.put('d', "ᵈ"); m.put('e', "ᵉ");
        m.put('f', "ᶠ"); m.put('g', "ᵍ"); m.put('h', "ʰ"); m.put('i', "ⁱ"); m.put('j', "ʲ");
        m.put('k', "ᵏ"); m.put('l', "ˡ"); m.put('m', "ᵐ"); m.put('n', "ⁿ"); m.put('o', "ᵒ");
        m.put('p', "ᵖ"); m.put('r', "ʳ"); m.put('s', "ˢ"); m.put('t', "ᵗ"); m.put('u', "ᵘ");
        m.put('v', "ᵛ"); m.put('w', "ʷ"); m.put('x', "ˣ"); m.put('y', "ʸ"); m.put('z', "ᶻ");
        m.put('A', "ᴬ"); m.put('B', "ᴮ"); m.put('D', "ᴰ"); m.put('E', "ᴱ"); m.put('G', "ᴳ");
        m.put('H', "ᴴ"); m.put('I', "ᴵ"); m.put('J', "ᴶ"); m.put('K', "ᴷ"); m.put('L', "ᴸ");
        m.put('M', "ᴹ"); m.put('N', "ᴺ"); m.put('O', "ᴼ"); m.put('P', "ᴾ"); m.put('R', "ᴿ");
        m.put('T', "ᵀ"); m.put('U', "ᵁ"); m.put('V', "ⱽ"); m.put('W', "ᵂ");
        m.put('+', "⁺"); m.put('-', "⁻"); m.put('=', "⁼"); m.put('(', "⁽"); m.put(')', "⁾");
        return m;
    }

    private static Map<Character, String> subscriptMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('0', "₀"); m.put('1', "₁"); m.put('2', "₂"); m.put('3', "₃"); m.put('4', "₄");
        m.put('5', "₅"); m.put('6', "₆"); m.put('7', "₇"); m.put('8', "₈"); m.put('9', "₉");
        m.put('a', "ₐ"); m.put('e', "ₑ"); m.put('h', "ₕ"); m.put('i', "ᵢ"); m.put('j', "ⱼ");
        m.put('k', "ₖ"); m.put('l', "ₗ"); m.put('m', "ₘ"); m.put('n', "ₙ"); m.put('o', "ₒ");
        m.put('p', "ₚ"); m.put('r', "ᵣ"); m.put('s', "ₛ"); m.put('t', "ₜ"); m.put('u', "ᵤ");
        m.put('v', "ᵥ"); m.put('x', "ₓ");
        m.put('+', "₊"); m.put('-', "₋"); m.put('=', "₌"); m.put('(', "₍"); m.put(')', "₎");
        return m;
    }

    private static Map<Character, String> bubbleMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "Ⓐ"); m.put('B', "Ⓑ"); m.put('C', "Ⓒ"); m.put('D', "Ⓓ"); m.put('E', "Ⓔ");
        m.put('F', "Ⓕ"); m.put('G', "Ⓖ"); m.put('H', "Ⓗ"); m.put('I', "Ⓘ"); m.put('J', "Ⓙ");
        m.put('K', "Ⓚ"); m.put('L', "Ⓛ"); m.put('M', "Ⓜ"); m.put('N', "Ⓝ"); m.put('O', "Ⓞ");
        m.put('P', "Ⓟ"); m.put('Q', "Ⓠ"); m.put('R', "Ⓡ"); m.put('S', "Ⓢ"); m.put('T', "Ⓣ");
        m.put('U', "Ⓤ"); m.put('V', "Ⓥ"); m.put('W', "Ⓦ"); m.put('X', "Ⓧ"); m.put('Y', "Ⓨ");
        m.put('Z', "Ⓩ");
        m.put('a', "ⓐ"); m.put('b', "ⓑ"); m.put('c', "ⓒ"); m.put('d', "ⓓ"); m.put('e', "ⓔ");
        m.put('f', "ⓕ"); m.put('g', "ⓖ"); m.put('h', "ⓗ"); m.put('i', "ⓘ"); m.put('j', "ⓙ");
        m.put('k', "ⓚ"); m.put('l', "ⓛ"); m.put('m', "ⓜ"); m.put('n', "ⓝ"); m.put('o', "ⓞ");
        m.put('p', "ⓟ"); m.put('q', "ⓠ"); m.put('r', "ⓡ"); m.put('s', "ⓢ"); m.put('t', "ⓣ");
        m.put('u', "ⓤ"); m.put('v', "ⓥ"); m.put('w', "ⓦ"); m.put('x', "ⓧ"); m.put('y', "ⓨ");
        m.put('z', "ⓩ");
        m.put('0', "⓪"); m.put('1', "①"); m.put('2', "②"); m.put('3', "③"); m.put('4', "④");
        m.put('5', "⑤"); m.put('6', "⑥"); m.put('7', "⑦"); m.put('8', "⑧"); m.put('9', "⑨");
        return m;
    }

    private static Map<Character, String> bubbleFilledMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "🅐"); m.put('B', "🅑"); m.put('C', "🅒"); m.put('D', "🅓"); m.put('E', "🅔");
        m.put('F', "🅕"); m.put('G', "🅖"); m.put('H', "🅗"); m.put('I', "🅘"); m.put('J', "🅙");
        m.put('K', "🅚"); m.put('L', "🅛"); m.put('M', "🅜"); m.put('N', "🅝"); m.put('O', "🅞");
        m.put('P', "🅟"); m.put('Q', "🅠"); m.put('R', "🅡"); m.put('S', "🅢"); m.put('T', "🅣");
        m.put('U', "🅤"); m.put('V', "🅥"); m.put('W', "🅦"); m.put('X', "🅧"); m.put('Y', "🅨");
        m.put('Z', "🅩");
        return m;
    }

    private static Map<Character, String> squareMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "🄰"); m.put('B', "🄱"); m.put('C', "🄲"); m.put('D', "🄳"); m.put('E', "🄴");
        m.put('F', "🄵"); m.put('G', "🄶"); m.put('H', "🄷"); m.put('I', "🄸"); m.put('J', "🄹");
        m.put('K', "🄺"); m.put('L', "🄻"); m.put('M', "🄼"); m.put('N', "🄽"); m.put('O', "🄾");
        m.put('P', "🄿"); m.put('Q', "🅀"); m.put('R', "🅁"); m.put('S', "🅂"); m.put('T', "🅃");
        m.put('U', "🅄"); m.put('V', "🅅"); m.put('W', "🅆"); m.put('X', "🅇"); m.put('Y', "🅈");
        m.put('Z', "🅉");
        return m;
    }

    private static Map<Character, String> parenthesizedMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "⒜"); m.put('B', "⒝"); m.put('C', "⒞"); m.put('D', "⒟"); m.put('E', "⒠");
        m.put('F', "⒡"); m.put('G', "⒢"); m.put('H', "⒣"); m.put('I', "⒤"); m.put('J', "⒥");
        m.put('K', "⒦"); m.put('L', "⒧"); m.put('M', "⒨"); m.put('N', "⒩"); m.put('O', "⒪");
        m.put('P', "⒫"); m.put('Q', "⒬"); m.put('R', "⒭"); m.put('S', "⒮"); m.put('T', "⒯");
        m.put('U', "⒰"); m.put('V', "⒱"); m.put('W', "⒲"); m.put('X', "⒳"); m.put('Y', "⒴");
        m.put('Z', "⒵");
        m.put('a', "⒜"); m.put('b', "⒝"); m.put('c', "⒞"); m.put('d', "⒟"); m.put('e', "⒠");
        m.put('f', "⒡"); m.put('g', "⒢"); m.put('h', "⒣"); m.put('i', "⒤"); m.put('j', "⒥");
        m.put('k', "⒦"); m.put('l', "⒧"); m.put('m', "⒨"); m.put('n', "⒩"); m.put('o', "⒪");
        m.put('p', "⒫"); m.put('q', "⒬"); m.put('r', "⒭"); m.put('s', "⒮"); m.put('t', "⒯");
        m.put('u', "⒰"); m.put('v', "⒱"); m.put('w', "⒲"); m.put('x', "⒳"); m.put('y', "⒴");
        m.put('z', "⒵");
        m.put('1', "⑴"); m.put('2', "⑵"); m.put('3', "⑶"); m.put('4', "⑷"); m.put('5', "⑸");
        m.put('6', "⑹"); m.put('7', "⑺"); m.put('8', "⑻"); m.put('9', "⑼"); m.put('0', "⑽");
        return m;
    }

    private static Map<Character, String> fullwidthMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "Ａ"); m.put('B', "Ｂ"); m.put('C', "Ｃ"); m.put('D', "Ｄ"); m.put('E', "Ｅ");
        m.put('F', "Ｆ"); m.put('G', "Ｇ"); m.put('H', "Ｈ"); m.put('I', "Ｉ"); m.put('J', "Ｊ");
        m.put('K', "Ｋ"); m.put('L', "Ｌ"); m.put('M', "Ｍ"); m.put('N', "Ｎ"); m.put('O', "Ｏ");
        m.put('P', "Ｐ"); m.put('Q', "Ｑ"); m.put('R', "Ｒ"); m.put('S', "Ｓ"); m.put('T', "Ｔ");
        m.put('U', "Ｕ"); m.put('V', "Ｖ"); m.put('W', "Ｗ"); m.put('X', "Ｘ"); m.put('Y', "Ｙ");
        m.put('Z', "Ｚ");
        m.put('a', "ａ"); m.put('b', "ｂ"); m.put('c', "ｃ"); m.put('d', "ｄ"); m.put('e', "ｅ");
        m.put('f', "ｆ"); m.put('g', "ｇ"); m.put('h', "ｈ"); m.put('i', "ｉ"); m.put('j', "ｊ");
        m.put('k', "ｋ"); m.put('l', "ｌ"); m.put('m', "ｍ"); m.put('n', "ｎ"); m.put('o', "ｏ");
        m.put('p', "ｐ"); m.put('q', "ｑ"); m.put('r', "ｒ"); m.put('s', "ｓ"); m.put('t', "ｔ");
        m.put('u', "ｕ"); m.put('v', "ｖ"); m.put('w', "ｗ"); m.put('x', "ｘ"); m.put('y', "ｙ");
        m.put('z', "ｚ");
        m.put('0', "０"); m.put('1', "１"); m.put('2', "２"); m.put('3', "３"); m.put('4', "４");
        m.put('5', "５"); m.put('6', "６"); m.put('7', "７"); m.put('8', "８"); m.put('9', "９");
        m.put(' ', "　"); m.put('!', "！"); m.put('"', "＂"); m.put('#', "＃"); m.put('$', "＄");
        m.put('%', "％"); m.put('&', "＆"); m.put('\'', "＇"); m.put('(', "（"); m.put(')', "）");
        m.put('*', "＊"); m.put('+', "＋"); m.put(',', "，"); m.put('-', "－"); m.put('.', "．");
        m.put('/', "／"); m.put(':', "："); m.put(';', "；"); m.put('<', "＜"); m.put('=', "＝");
        m.put('>', "＞"); m.put('?', "？"); m.put('@', "＠"); m.put('[', "［"); m.put('\\', "＼");
        m.put(']', "］"); m.put('^', "＾"); m.put('_', "＿"); m.put('`', "｀"); m.put('{', "｛");
        m.put('|', "｜"); m.put('}', "｝"); m.put('~', "～");
        return m;
    }

    private static Map<Character, String> circledMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "Ⓐ"); m.put('B', "Ⓑ"); m.put('C', "Ⓒ"); m.put('D', "Ⓓ"); m.put('E', "Ⓔ");
        m.put('F', "Ⓕ"); m.put('G', "Ⓖ"); m.put('H', "Ⓗ"); m.put('I', "Ⓘ"); m.put('J', "Ⓙ");
        m.put('K', "Ⓚ"); m.put('L', "Ⓛ"); m.put('M', "Ⓜ"); m.put('N', "Ⓝ"); m.put('O', "Ⓞ");
        m.put('P', "Ⓟ"); m.put('Q', "Ⓠ"); m.put('R', "Ⓡ"); m.put('S', "Ⓢ"); m.put('T', "Ⓣ");
        m.put('U', "Ⓤ"); m.put('V', "Ⓥ"); m.put('W', "Ⓦ"); m.put('X', "Ⓧ"); m.put('Y', "Ⓨ");
        m.put('Z', "Ⓩ");
        m.put('a', "ⓐ"); m.put('b', "ⓑ"); m.put('c', "ⓒ"); m.put('d', "ⓓ"); m.put('e', "ⓔ");
        m.put('f', "ⓕ"); m.put('g', "ⓖ"); m.put('h', "ⓗ"); m.put('i', "ⓘ"); m.put('j', "ⓙ");
        m.put('k', "ⓚ"); m.put('l', "ⓛ"); m.put('m', "ⓜ"); m.put('n', "ⓝ"); m.put('o', "ⓞ");
        m.put('p', "ⓟ"); m.put('q', "ⓠ"); m.put('r', "ⓡ"); m.put('s', "ⓢ"); m.put('t', "ⓣ");
        m.put('u', "ⓤ"); m.put('v', "ⓥ"); m.put('w', "ⓦ"); m.put('x', "ⓧ"); m.put('y', "ⓨ");
        m.put('z', "ⓩ");
        m.put('0', "⓪"); m.put('1', "①"); m.put('2', "②"); m.put('3', "③"); m.put('4', "④");
        m.put('5', "⑤"); m.put('6', "⑥"); m.put('7', "⑦"); m.put('8', "⑧"); m.put('9', "⑨");
        return m;
    }

    private static Map<Character, String> invertedMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('a', "ɐ"); m.put('b', "q"); m.put('c', "ɔ"); m.put('d', "p"); m.put('e', "ǝ");
        m.put('f', "ɟ"); m.put('g', "ƃ"); m.put('h', "ɥ"); m.put('i', "ᴉ"); m.put('j', "ɾ");
        m.put('k', "ʞ"); m.put('l', "l"); m.put('m', "ɯ"); m.put('n', "u"); m.put('o', "o");
        m.put('p', "d"); m.put('q', "b"); m.put('r', "ɹ"); m.put('s', "s"); m.put('t', "ʇ");
        m.put('u', "n"); m.put('v', "ʌ"); m.put('w', "ʍ"); m.put('x', "x"); m.put('y', "ʎ");
        m.put('z', "z");
        m.put('A', "∀"); m.put('B', "𐐒"); m.put('C', "Ɔ"); m.put('D', "ᗡ"); m.put('E', "Ǝ");
        m.put('F', "Ⅎ"); m.put('G', "⅁"); m.put('H', "H"); m.put('I', "I"); m.put('J', "ſ");
        m.put('K', "⋊"); m.put('L', "⅂"); m.put('M', "W"); m.put('N', "N"); m.put('O', "O");
        m.put('P', "Ԁ"); m.put('Q', "Ὁ"); m.put('R', "ᴚ"); m.put('S', "S"); m.put('T', "⊥");
        m.put('U', "∩"); m.put('V', "Λ"); m.put('W', "M"); m.put('X', "X"); m.put('Y', "⅄");
        m.put('Z', "Z");
        m.put('!', "¡"); m.put('?', "¿"); m.put('.', "˙"); m.put(',', "'");
        return m;
    }

    private static Map<Character, String> strikethroughMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        String strike = "\u0336";
        for (char c = 'A'; c <= 'Z'; c++) m.put(c, c + strike);
        for (char c = 'a'; c <= 'z'; c++) m.put(c, c + strike);
        for (char c = '0'; c <= '9'; c++) m.put(c, c + strike);
        return m;
    }

    private static Map<Character, String> underlineMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        String underline = "\u0332";
        for (char c = 'A'; c <= 'Z'; c++) m.put(c, c + underline);
        for (char c = 'a'; c <= 'z'; c++) m.put(c, c + underline);
        for (char c = '0'; c <= '9'; c++) m.put(c, c + underline);
        return m;
    }

    private static Map<Character, String> cursiveMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "𝒜"); m.put('B', "ℬ"); m.put('C', "𝒞"); m.put('D', "𝒟"); m.put('E', "ℰ");
        m.put('F', "ℱ"); m.put('G', "𝒢"); m.put('H', "ℋ"); m.put('I', "ℐ"); m.put('J', "𝒥");
        m.put('K', "𝒦"); m.put('L', "ℒ"); m.put('M', "ℳ"); m.put('N', "𝒩"); m.put('O', "𝒪");
        m.put('P', "𝒫"); m.put('Q', "𝒬"); m.put('R', "ℛ"); m.put('S', "𝒮"); m.put('T', "𝒯");
        m.put('U', "𝒰"); m.put('V', "𝒱"); m.put('W', "𝒲"); m.put('X', "𝒳"); m.put('Y', "𝒴");
        m.put('Z', "𝒵");
        m.put('a', "𝒶"); m.put('b', "𝒷"); m.put('c', "𝒸"); m.put('d', "𝒹"); m.put('e', "ℯ");
        m.put('f', "𝒻"); m.put('g', "ℊ"); m.put('h', "𝒽"); m.put('i', "𝒾"); m.put('j', "𝒿");
        m.put('k', "𝓀"); m.put('l', "𝓁"); m.put('m', "𝓂"); m.put('n', "𝓃"); m.put('o', "ℴ");
        m.put('p', "𝓅"); m.put('q', "𝓆"); m.put('r', "𝓇"); m.put('s', "𝓈"); m.put('t', "𝓉");
        m.put('u', "𝓊"); m.put('v', "𝓋"); m.put('w', "𝓌"); m.put('x', "𝓍"); m.put('y', "𝓎");
        m.put('z', "𝓏");
        return m;
    }

    private static Map<Character, String> gothicMappings() {
        return frakturMappings();
    }

    private static Map<Character, String> typewriterMappings() {
        return monospaceMappings();
    }

    private static Map<Character, String> regionalIndicatorMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            m.put(c, String.valueOf((char)(0xDDE6 + (c - 'A'))));
        }
        for (char c = 'a'; c <= 'z'; c++) {
            m.put(c, String.valueOf((char)(0xDDE6 + (c - 'a'))));
        }
        return m;
    }

    private static Map<Character, String> normalMappings() {
        return new LinkedHashMap<>();
    }

    private static Map<Character, String> combiningMappings(String mark) {
        Map<Character, String> m = new LinkedHashMap<>();
        for (char c = 'A'; c <= 'Z'; c++) m.put(c, c + mark);
        for (char c = 'a'; c <= 'z'; c++) m.put(c, c + mark);
        for (char c = '0'; c <= '9'; c++) m.put(c, c + mark);
        return m;
    }

    private static Map<Character, String> doubleUnderlineMappings() {
        return combiningMappings("\u0333");
    }

    private static Map<Character, String> overlineMappings() {
        return combiningMappings("\u0305");
    }

    private static Map<Character, String> doubleOverlineMappings() {
        return combiningMappings("\u033F");
    }

    private static Map<Character, String> slashedMappings() {
        return combiningMappings("\u0338");
    }

    private static Map<Character, String> dottedMappings() {
        return combiningMappings("\u0307");
    }

    private static Map<Character, String> diaeresisMappings() {
        return combiningMappings("\u0308");
    }

    private static Map<Character, String> tildeMappings() {
        return combiningMappings("\u0303");
    }

    private static Map<Character, String> tildeBelowMappings() {
        return combiningMappings("\u0330");
    }

    private static Map<Character, String> macronMappings() {
        return combiningMappings("\u0304");
    }

    private static Map<Character, String> acuteMappings() {
        return combiningMappings("\u0301");
    }

    private static Map<Character, String> graveMappings() {
        return combiningMappings("\u0300");
    }

    private static Map<Character, String> circumflexMappings() {
        return combiningMappings("\u0302");
    }

    private static Map<Character, String> caronMappings() {
        return combiningMappings("\u030C");
    }

    private static Map<Character, String> breveMappings() {
        return combiningMappings("\u0306");
    }

    private static Map<Character, String> ringMappings() {
        return combiningMappings("\u030A");
    }

    private static Map<Character, String> cedillaMappings() {
        return combiningMappings("\u0327");
    }

    private static Map<Character, String> ogonekMappings() {
        return combiningMappings("\u0328");
    }

    private static Map<Character, String> commaMappings() {
        return combiningMappings("\u0313");
    }

    private static Map<Character, String> negativeSquaredMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "🅰"); m.put('B', "🅱"); m.put('C', "🅲"); m.put('D', "🅳"); m.put('E', "🅴");
        m.put('F', "🅵"); m.put('G', "🅶"); m.put('H', "🅷"); m.put('I', "🅸"); m.put('J', "🅹");
        m.put('K', "🅺"); m.put('L', "🅻"); m.put('M', "🅼"); m.put('N', "🅽"); m.put('O', "🅾");
        m.put('P', "🅿"); m.put('Q', "🆀"); m.put('R', "🆁"); m.put('S', "🆂"); m.put('T', "🆃");
        m.put('U', "🆄"); m.put('V', "🆅"); m.put('W', "🆆"); m.put('X', "🆇"); m.put('Y', "🆈");
        m.put('Z', "🆉");
        m.put('a', "🅰"); m.put('b', "🅱"); m.put('c', "🅲"); m.put('d', "🅳"); m.put('e', "🅴");
        m.put('f', "🅵"); m.put('g', "🅶"); m.put('h', "🅷"); m.put('i', "🅸"); m.put('j', "🅹");
        m.put('k', "🅺"); m.put('l', "🅻"); m.put('m', "🅼"); m.put('n', "🅽"); m.put('o', "🅾");
        m.put('p', "🅿"); m.put('q', "🆀"); m.put('r', "🆁"); m.put('s', "🆂"); m.put('t', "🆃");
        m.put('u', "🆄"); m.put('v', "🆅"); m.put('w', "🆆"); m.put('x', "🆇"); m.put('y', "🆈");
        m.put('z', "🆉");
        return m;
    }

    private static Map<Character, String> zalgoMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        String[] zalgo = {"\u0300","\u0301","\u0302","\u0303","\u0304","\u0305","\u0306","\u0307",
                "\u0308","\u0309","\u030A","\u030B","\u030C","\u030D","\u030E","\u030F",
                "\u0310","\u0311","\u0312","\u0313","\u0314","\u0315","\u0316","\u0317",
                "\u0318","\u0319","\u031A","\u031B","\u031C","\u031D","\u031E","\u031F",
                "\u0320","\u0321","\u0322","\u0323","\u0324","\u0325","\u0326","\u0327"};
        for (char c = 'A'; c <= 'Z'; c++) {
            StringBuilder sb = new StringBuilder().append(c);
            for (int i = 0; i < 3; i++) sb.append(zalgo[(c + i * 7) % zalgo.length]);
            m.put(c, sb.toString());
        }
        for (char c = 'a'; c <= 'z'; c++) {
            StringBuilder sb = new StringBuilder().append(c);
            for (int i = 0; i < 3; i++) sb.append(zalgo[(c + i * 7) % zalgo.length]);
            m.put(c, sb.toString());
        }
        return m;
    }

    private static Map<Character, String> halfwidthMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('A', "A"); m.put('B', "B"); m.put('C', "C"); m.put('D', "D"); m.put('E', "E");
        m.put('F', "F"); m.put('G', "G"); m.put('H', "H"); m.put('I', "I"); m.put('J', "J");
        m.put('K', "K"); m.put('L', "L"); m.put('M', "M"); m.put('N', "N"); m.put('O', "O");
        m.put('P', "P"); m.put('Q', "Q"); m.put('R', "R"); m.put('S', "S"); m.put('T', "T");
        m.put('U', "U"); m.put('V', "V"); m.put('W', "W"); m.put('X', "X"); m.put('Y', "Y");
        m.put('Z', "Z");
        m.put('a', "a"); m.put('b', "b"); m.put('c', "c"); m.put('d', "d"); m.put('e', "e");
        m.put('f', "f"); m.put('g', "g"); m.put('h', "h"); m.put('i', "i"); m.put('j', "j");
        m.put('k', "k"); m.put('l', "l"); m.put('m', "m"); m.put('n', "n"); m.put('o', "o");
        m.put('p', "p"); m.put('q', "q"); m.put('r', "r"); m.put('s', "s"); m.put('t', "t");
        m.put('u', "u"); m.put('v', "v"); m.put('w', "w"); m.put('x', "x"); m.put('y', "y");
        m.put('z', "z");
        m.put('0', "0"); m.put('1', "1"); m.put('2', "2"); m.put('3', "3"); m.put('4', "4");
        m.put('5', "5"); m.put('6', "6"); m.put('7', "7"); m.put('8', "8"); m.put('9', "9");
        return m;
    }

    private static Map<Character, String> tinyMappings() {
        Map<Character, String> m = new LinkedHashMap<>();
        m.put('a', "ᵃ"); m.put('b', "ᵇ"); m.put('c', "ᶜ"); m.put('d', "ᵈ"); m.put('e', "ᵉ");
        m.put('f', "ᶠ"); m.put('g', "ᵍ"); m.put('h', "ʰ"); m.put('i', "ⁱ"); m.put('j', "ʲ");
        m.put('k', "ᵏ"); m.put('l', "ˡ"); m.put('m', "ᵐ"); m.put('n', "ⁿ"); m.put('o', "ᵒ");
        m.put('p', "ᵖ"); m.put('q', "ᑫ"); m.put('r', "ʳ"); m.put('s', "ˢ"); m.put('t', "ᵗ");
        m.put('u', "ᵘ"); m.put('v', "ᵛ"); m.put('w', "ʷ"); m.put('x', "ˣ"); m.put('y', "ʸ");
        m.put('z', "ᶻ");
        m.put('A', "ᴬ"); m.put('B', "ᴮ"); m.put('C', "ᶜ"); m.put('D', "ᴰ"); m.put('E', "ᴱ");
        m.put('F', "ᶠ"); m.put('G', "ᴳ"); m.put('H', "ᴴ"); m.put('I', "ᴵ"); m.put('J', "ᴶ");
        m.put('K', "ᴷ"); m.put('L', "ᴸ"); m.put('M', "ᴹ"); m.put('N', "ᴺ"); m.put('O', "ᴼ");
        m.put('P', "ᴾ"); m.put('Q', "ᑫ"); m.put('R', "ᴿ"); m.put('S', "ˢ"); m.put('T', "ᵀ");
        m.put('U', "ᵁ"); m.put('V', "ⱽ"); m.put('W', "ᵂ"); m.put('X', "ˣ"); m.put('Y', "ʸ");
        m.put('Z', "ᶻ");
        return m;
    }
}
