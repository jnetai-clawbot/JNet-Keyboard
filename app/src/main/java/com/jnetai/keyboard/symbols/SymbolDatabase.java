package com.jnetai.keyboard.symbols;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SymbolDatabase {
    public static class SymbolEntry {
        public final String symbol;
        public final String name;
        public final String category;

        SymbolEntry(String symbol, String name, String category) {
            this.symbol = symbol;
            this.name = name;
            this.category = category;
        }
    }

    private static final List<SymbolEntry> symbols = new ArrayList<>();
    private static final Map<String, List<SymbolEntry>> categoryMap = new LinkedHashMap<>();

    static {
        add("Punctuation", "!", "Exclamation", "?", "Question", ".", "Period", ",", "Comma",
            ";", "Semicolon", ":", "Colon", "\"", "Quotation", "'", "Apostrophe",
            "(", "Left Parenthesis", ")", "Right Parenthesis", "[", "Left Bracket", "]", "Right Bracket",
            "{", "Left Brace", "}", "Right Brace", "<", "Less Than", ">", "Greater Than",
            "/", "Slash", "\\", "Backslash", "|", "Vertical Bar", "~", "Tilde",
            "`", "Backtick", "@", "At Sign", "#", "Hash", "$", "Dollar",
            "%", "Percent", "^", "Caret", "&", "Ampersand", "*", "Asterisk",
            "_", "Underscore", "-", "Hyphen", "+", "Plus", "=", "Equals",
            "·", "Middle Dot", "…", "Ellipsis", "–", "En Dash", "—", "Em Dash",
            "«", "Left Guillemet", "»", "Right Guillemet", "‹", "Single Left Guillemet", "›", "Single Right Guillemet",
            "„", "Double Low Quote", "“", "Left Double Quote", "”", "Right Double Quote",
            "‘", "Left Single Quote", "’", "Right Single Quote");

        add("Math", "×", "Multiplication", "÷", "Division", "±", "Plus-Minus", "∓", "Minus-Plus",
            "√", "Square Root", "∛", "Cube Root", "∞", "Infinity", "≈", "Approximately Equal",
            "≠", "Not Equal", "≤", "Less Than or Equal", "≥", "Greater Than or Equal",
            "∑", "Summation", "∏", "Product", "∫", "Integral", "∂", "Partial Differential",
            "∆", "Increment", "∇", "Nabla", "∈", "Element Of", "∉", "Not Element Of",
            "⊂", "Subset", "⊃", "Superset", "∪", "Union", "∩", "Intersection",
            "∀", "For All", "∃", "There Exists", "∅", "Empty Set", "∎", "End of Proof",
            "∠", "Angle", "∡", "Measured Angle", "⊥", "Perpendicular", "∥", "Parallel",
            "∼", "Similar", "≅", "Approximately Equal To", "≡", "Identical To",
            "≪", "Much Less Than", "≫", "Much Greater Than", "∝", "Proportional To",
            "∧", "Logical And", "∨", "Logical Or", "¬", "Not Sign", "⇒", "Implies",
            "⇔", "If and Only If", "⊕", "Circled Plus", "⊗", "Circled Times",
            "½", "One Half", "⅓", "One Third", "¼", "One Quarter", "¾", "Three Quarters",
            "⅕", "One Fifth", "⅖", "Two Fifths", "⅗", "Three Fifths", "⅘", "Four Fifths",
            "⅙", "One Sixth", "⅚", "Five Sixths", "⅛", "One Eighth", "⅜", "Three Eighths",
            "⅝", "Five Eighths", "⅞", "Seven Eighths");

        add("Currency", "$", "Dollar", "€", "Euro", "£", "Pound", "¥", "Yen",
            "¢", "Cent", "₿", "Bitcoin", "₹", "Rupee", "₽", "Ruble",
            "₩", "Won", "₪", "Shekel", "₫", "Dong", "₴", "Hryvnia",
            "₦", "Naira", "₱", "Peso", "₲", "Guarani", "₳", "Austral",
            "₵", "Cedi", "₸", "Tenge", "₺", "Lira", "₼", "Manat",
            "₾", "Lari", "₿", "Bitcoin");

        add("Arrows", "←", "Left Arrow", "→", "Right Arrow", "↑", "Up Arrow", "↓", "Down Arrow",
            "↔", "Left-Right Arrow", "↕", "Up-Down Arrow", "↖", "North West Arrow", "↗", "North East Arrow",
            "↘", "South East Arrow", "↙", "South West Arrow", "↩", "Right Arrow Curving Left",
            "↪", "Left Arrow Curving Right", "↫", "Left Arrow with Loop", "↬", "Right Arrow with Loop",
            "↯", "Zigzag Arrow", "↰", "Up Arrow with Tip Left", "↱", "Up Arrow with Tip Right",
            "↲", "Down Arrow with Tip Left", "↳", "Down Arrow with Tip Right",
            "⟵", "Long Left Arrow", "⟶", "Long Right Arrow", "⟷", "Long Left-Right Arrow",
            "⇐", "Left Double Arrow", "⇒", "Right Double Arrow", "⇑", "Up Double Arrow",
            "⇓", "Down Double Arrow", "⇔", "Left-Right Double Arrow",
            "➔", "Heavy Wide-Headed Right Arrow", "➘", "Heavy South East Arrow",
            "➙", "Heavy Right Arrow", "➚", "Heavy North East Arrow", "➛", "Drafting Point Right Arrow",
            "➜", "Heavy Round-Tipped Right Arrow", "➝", "Triangle-Headed Right Arrow",
            "➞", "Heavy Triangle-Headed Right Arrow", "➟", "Dashed Triangle-Headed Right Arrow",
            "➠", "Heavy Dashed Triangle-Headed Right Arrow", "➡", "Black Right Arrow",
            "➢", "Three-D Top-Lighted Right Arrowhead", "➣", "Three-D Bottom-Lighted Right Arrowhead");

        add("Brackets", "「", "Left Corner Bracket", "」", "Right Corner Bracket",
            "『", "Left White Corner Bracket", "』", "Right White Corner Bracket",
            "【", "Left Black Lenticular Bracket", "】", "Right Black Lenticular Bracket",
            "〖", "Left White Lenticular Bracket", "〗", "Right White Lenticular Bracket",
            "〈", "Left Angle Bracket", "〉", "Right Angle Bracket",
            "《", "Left Double Angle Bracket", "》", "Right Double Angle Bracket",
            "〘", "Left White Tortoise Shell Bracket", "〙", "Right White Tortoise Shell Bracket",
            "〚", "Left White Square Bracket", "〛", "Right White Square Bracket");

        add("Special", "©", "Copyright", "®", "Registered", "™", "Trademark",
            "°", "Degree", "№", "Numero", "§", "Section", "¶", "Pilcrow",
            "†", "Dagger", "‡", "Double Dagger", "•", "Bullet", "◦", "White Bullet",
            "◉", "Fisheye", "○", "White Circle", "●", "Black Circle",
            "□", "White Square", "■", "Black Square", "△", "White Up Triangle", "▲", "Black Up Triangle",
            "▽", "White Down Triangle", "▼", "Black Down Triangle", "◇", "White Diamond", "◆", "Black Diamond",
            "☆", "White Star", "★", "Black Star", "♡", "White Heart Suit", "♥", "Black Heart Suit",
            "♢", "White Diamond Suit", "♦", "Black Diamond Suit", "♤", "White Spade Suit", "♠", "Black Spade Suit",
            "♧", "White Club Suit", "♣", "Black Club Suit", "♪", "Eighth Note", "♫", "Beamed Eighth Notes",
            "♬", "Beamed Sixteenth Notes", "♭", "Music Flat", "♮", "Music Natural", "♯", "Music Sharp",
            "☺", "White Smiling Face", "☹", "White Frowning Face", "☠", "Skull and Crossbones",
            "☢", "Radioactive", "☣", "Biohazard", "☤", "Caduceus", "☥", "Ankh",
            "☮", "Peace", "☯", "Yin Yang", "☸", "Wheel of Dharma",
            "♲", "Universal Recycling", "♳", "Recycling Type-1", "♴", "Recycling Type-2",
            "♵", "Recycling Type-3", "♶", "Recycling Type-4", "♷", "Recycling Type-5",
            "♸", "Recycling Type-6", "♹", "Recycling Type-7",
            "⚀", "Die Face-1", "⚁", "Die Face-2", "⚂", "Die Face-3",
            "⚃", "Die Face-4", "⚄", "Die Face-5", "⚅", "Die Face-6",
            "⚐", "White Flag", "⚑", "Black Flag", "⚒", "Hammer and Pick", "⚓", "Anchor",
            "⚔", "Crossed Swords", "⚕", "Staff of Aesculapius", "⚖", "Scales",
            "⚗", "Alembic", "⚘", "Flower", "⚙", "Gear", "⚚", "Staff of Hermes",
            "⚛", "Atom Symbol", "⚜", "Fleur-de-lis", "⚠", "Warning Sign",
            "⚡", "High Voltage", "⚢", "Doubled Female", "⚣", "Doubled Male",
            "⚤", "Interlocked Female and Male", "⚥", "Male and Female",
            "⚦", "Male with Stroke", "⚧", "Transgender", "⚨", "Vertical Male with Stroke",
            "⚩", "Horizontal Male with Stroke", "⚪", "Medium White Circle", "⚫", "Medium Black Circle",
            "⬛", "Black Large Square", "⬜", "White Large Square",
            "🗸", "Light Check Mark", "🗹", "Ballot Box with Bold Check",
            "🗶", "Ballot Bold Script X", "🗷", "Ballot Box with Bold Script X",
            "🗴", "Ballot Script X", "🗵", "Ballot Box with Script X");

        add("Greek", "Α", "Alpha", "Β", "Beta", "Γ", "Gamma", "Δ", "Delta",
            "Ε", "Epsilon", "Ζ", "Zeta", "Η", "Eta", "Θ", "Theta",
            "Ι", "Iota", "Κ", "Kappa", "Λ", "Lambda", "Μ", "Mu",
            "Ν", "Nu", "Ξ", "Xi", "Ο", "Omicron", "Π", "Pi",
            "Ρ", "Rho", "Σ", "Sigma", "Τ", "Tau", "Υ", "Upsilon",
            "Φ", "Phi", "Χ", "Chi", "Ψ", "Psi", "Ω", "Omega",
            "α", "alpha", "β", "beta", "γ", "gamma", "δ", "delta",
            "ε", "epsilon", "ζ", "zeta", "η", "eta", "θ", "theta",
            "ι", "iota", "κ", "kappa", "λ", "lambda", "μ", "mu",
            "ν", "nu", "ξ", "xi", "ο", "omicron", "π", "pi",
            "ρ", "rho", "σ", "sigma", "τ", "tau", "υ", "upsilon",
            "φ", "phi", "χ", "chi", "ψ", "psi", "ω", "omega");

        add("Roman", "Ⅰ", "Roman Numeral One", "Ⅱ", "Roman Numeral Two", "Ⅲ", "Roman Numeral Three",
            "Ⅳ", "Roman Numeral Four", "Ⅴ", "Roman Numeral Five", "Ⅵ", "Roman Numeral Six",
            "Ⅶ", "Roman Numeral Seven", "Ⅷ", "Roman Numeral Eight", "Ⅸ", "Roman Numeral Nine",
            "Ⅹ", "Roman Numeral Ten", "Ⅺ", "Roman Numeral Eleven", "Ⅻ", "Roman Numeral Twelve",
            "ⅰ", "Small Roman One", "ⅱ", "Small Roman Two", "ⅲ", "Small Roman Three",
            "ⅳ", "Small Roman Four", "ⅴ", "Small Roman Five", "ⅵ", "Small Roman Six",
            "ⅶ", "Small Roman Seven", "ⅷ", "Small Roman Eight", "ⅸ", "Small Roman Nine",
            "ⅹ", "Small Roman Ten", "ⅺ", "Small Roman Eleven", "ⅻ", "Small Roman Twelve");
    }

    private static void add(String category, String... pairs) {
        for (int i = 0; i < pairs.length; i += 2) {
            SymbolEntry entry = new SymbolEntry(pairs[i], pairs[i + 1], category);
            symbols.add(entry);
            categoryMap.computeIfAbsent(category, k -> new ArrayList<>()).add(entry);
        }
    }

    public static List<SymbolEntry> getAllSymbols() { return symbols; }

    public static Map<String, List<SymbolEntry>> getCategoryMap() { return categoryMap; }

    public static List<SymbolEntry> search(String query) {
        List<SymbolEntry> results = new ArrayList<>();
        String q = query.toLowerCase().trim();
        if (q.isEmpty()) return results;
        for (SymbolEntry s : symbols) {
            if (s.name.toLowerCase().contains(q) || s.category.toLowerCase().contains(q)) {
                results.add(s);
            }
        }
        return results;
    }
}
