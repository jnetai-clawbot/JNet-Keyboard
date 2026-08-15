package com.jnetai.keyboard.dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordDictionary {
    private static final Set<String> WORDS = new HashSet<>();

    static {
        String[] words = {
                "a", "about", "above", "across", "act", "action", "active", "actually", "add", "after",
                "again", "against", "age", "ago", "agree", "air", "all", "allow", "almost", "alone",
                "along", "already", "also", "although", "always", "am", "among", "an", "and", "another",
                "answer", "any", "anyone", "anything", "appear", "apple", "are", "area", "arm", "around",
                "arrive", "art", "as", "ask", "at", "attack", "aunt", "autumn", "away", "baby", "back",
                "bad", "bag", "ball", "bank", "bar", "base", "be", "beach", "bear", "beautiful",
                "because", "become", "bed", "before", "begin", "behind", "believe", "below", "best",
                "better", "between", "big", "bird", "birthday", "bit", "black", "block", "blood", "blue",
                "board", "boat", "body", "book", "born", "both", "box", "boy", "branch", "bread",
                "break", "breakfast", "bridge", "bright", "bring", "brother", "brown", "build", "bus",
                "business", "busy", "but", "buy", "by", "call", "came", "can", "cap", "capital", "car",
                "card", "care", "carry", "case", "cat", "catch", "cause", "cell", "centre", "century",
                "certain", "chance", "change", "charge", "check", "child", "children", "choose", "church",
                "circle", "city", "class", "clean", "clear", "close", "clothes", "cloud", "coat", "cold",
                "collect", "college", "colour", "come", "common", "company", "complete", "condition",
                "consider", "contain", "continue", "control", "cook", "cool", "copy", "corn", "corner",
                "correct", "cost", "could", "count", "country", "course", "court", "cover", "cross",
                "cry", "cup", "cut", "dance", "dark", "data", "daughter", "day", "dead", "deal",
                "dear", "death", "decide", "deep", "develop", "die", "difference", "different", "difficult",
                "dinner", "direction", "directly", "discover", "do", "doctor", "dog", "door", "down",
                "draw", "dream", "dress", "drink", "drive", "drop", "dry", "during", "each", "ear",
                "early", "earth", "east", "easy", "eat", "education", "effect", "effort", "egg", "eight",
                "either", "else", "end", "energy", "engineer", "enjoy", "enough", "enter", "entire",
                "equal", "escape", "especially", "even", "evening", "ever", "every", "everybody",
                "everyone", "everything", "exactly", "example", "except", "exercise", "expect", "experience",
                "explain", "eye", "face", "fact", "fail", "fair", "fall", "family", "far", "farm",
                "fast", "father", "favorite", "favourite", "fear", "feel", "feet", "fell", "felt",
                "few", "field", "fight", "figure", "fill", "film", "final", "finally", "find", "fine",
                "finger", "finish", "fire", "firm", "first", "fish", "five", "floor", "fly", "follow",
                "food", "foot", "for", "force", "forget", "form", "forward", "four", "free", "freedom",
                "fresh", "friend", "from", "front", "full", "fun", "funny", "future", "gain", "game",
                "garden", "gas", "gave", "general", "get", "girl", "give", "glass", "go", "goal",
                "god", "gold", "good", "got", "government", "great", "green", "ground", "group", "grow",
                "guess", "gun", "guy", "hair", "half", "hall", "hand", "happen", "happy", "hard",
                "has", "hat", "have", "he", "head", "hear", "heard", "heart", "heat", "heavy",
                "held", "help", "her", "here", "herself", "high", "him", "himself", "his", "history",
                "hit", "hold", "home", "hope", "horse", "hospital", "hot", "hour", "house", "how",
                "however", "huge", "human", "hundred", "husband", "i", "idea", "if", "imagine", "important",
                "in", "include", "increase", "indeed", "information", "instead", "interest", "into", "involve",
                "is", "issue", "it", "its", "itself", "job", "join", "just", "keep", "kept",
                "key", "kid", "kill", "kind", "king", "know", "known", "land", "language", "large",
                "last", "late", "later", "laugh", "law", "lay", "lead", "learn", "least", "leave",
                "led", "left", "leg", "less", "let", "letter", "level", "lie", "life", "light",
                "like", "line", "link", "list", "listen", "little", "live", "local", "long", "look",
                "lose", "loss", "lot", "love", "low", "luck", "machine", "made", "main", "make",
                "man", "many", "map", "mark", "market", "marry", "matter", "may", "maybe", "me",
                "mean", "measure", "meet", "member", "men", "mention", "message", "method", "middle",
                "might", "mile", "military", "milk", "million", "mind", "minute", "miss", "moment", "money",
                "month", "moon", "more", "morning", "most", "mother", "mountain", "move", "movie", "much",
                "music", "must", "my", "name", "nation", "nature", "near", "necessary", "need", "never",
                "new", "news", "next", "nice", "night", "nine", "no", "north", "not", "nothing",
                "now", "number", "occur", "of", "off", "offer", "office", "officer", "often", "oil",
                "okay", "old", "on", "once", "one", "only", "open", "operation", "opportunity", "or",
                "order", "other", "our", "out", "outside", "over", "own", "page", "paid", "pain",
                "paper", "parent", "park", "part", "particular", "party", "pass", "past", "pattern", "pay",
                "peace", "people", "per", "perhaps", "person", "phone", "photo", "pick", "picture", "piece",
                "place", "plan", "plane", "plant", "play", "please", "point", "police", "poor", "population",
                "position", "possible", "power", "practice", "prepare", "present", "president", "press",
                "pretty", "price", "probably", "problem", "process", "produce", "product", "program", "project",
                "protect", "provide", "public", "pull", "purpose", "push", "put", "question", "quick",
                "quickly", "quiet", "quite", "race", "radio", "rain", "raise", "ran", "range", "rate",
                "rather", "reach", "read", "ready", "real", "really", "reason", "receive", "record", "red",
                "remember", "remove", "report", "research", "result", "return", "right", "ring", "rise",
                "river", "road", "rock", "role", "room", "round", "rule", "run", "sad", "safe",
                "said", "same", "save", "say", "school", "science", "sea", "season", "seat", "second",
                "see", "seek", "seem", "seen", "sell", "send", "sense", "sentence", "separate", "serve",
                "service", "set", "seven", "several", "shall", "she", "ship", "shirt", "shoe", "shoot",
                "shop", "short", "should", "shoulder", "show", "side", "sign", "simple", "since", "sing",
                "single", "sister", "sit", "site", "situation", "six", "size", "skin", "sleep", "small",
                "smile", "snow", "so", "social", "soldier", "some", "somebody", "someone", "something",
                "sometimes", "son", "song", "soon", "sound", "south", "space", "speak", "special",
                "speech", "speed", "spend", "spring", "stand", "standard", "star", "start", "state",
                "stay", "step", "still", "stop", "story", "straight", "street", "strong", "student", "study",
                "stuff", "subject", "success", "such", "suddenly", "suffer", "suggest", "summer", "sun",
                "support", "sure", "surface", "system", "table", "take", "talk", "tall", "team", "tell",
                "ten", "test", "than", "thank", "that", "the", "their", "them", "themselves", "then",
                "there", "these", "they", "thing", "think", "third", "this", "those", "though", "thought",
                "three", "through", "throughout", "throw", "thus", "time", "to", "today", "together", "tomorrow",
                "too", "top", "total", "touch", "toward", "town", "trade", "train", "travel", "tree",
                "trip", "trouble", "true", "trust", "truth", "try", "turn", "two", "type", "understand",
                "university", "until", "up", "upon", "us", "use", "usually", "value", "various", "very",
                "view", "village", "visit", "voice", "wait", "walk", "wall", "want", "war", "warm",
                "was", "watch", "water", "way", "we", "wear", "weather", "week", "well", "went",
                "were", "west", "what", "whatever", "when", "where", "whether", "which", "while", "white",
                "who", "whole", "whom", "why", "wide", "wife", "will", "win", "wind", "window",
                "wish", "with", "within", "without", "woman", "women", "wonder", "wood", "word", "work",
                "world", "would", "write", "wrong", "wrote", "year", "yes", "yet", "you", "young",
                "your", "yourself"
        };
        Collections.addAll(WORDS, words);
    }

    public static boolean isWord(String word) {
        if (word == null || word.isEmpty()) return false;
        return WORDS.contains(word.toLowerCase());
    }

    public static List<String> getSuggestions(String typed) {
        List<String> result = new ArrayList<>();
        if (typed == null || typed.isEmpty()) return result;
        String t = typed.toLowerCase();

        for (String w : WORDS) {
            if (w.startsWith(t)) {
                result.add(w);
                if (result.size() >= 3) break;
            }
        }

        if (result.size() < 3) {
            List<String> near = closestByDistance(t, 3 - result.size());
            for (String w : near) {
                if (!result.contains(w)) result.add(w);
            }
        }
        return result;
    }

    public static String correct(String word) {
        if (word == null || word.isEmpty()) return null;
        if (isWord(word)) return null;
        String w = word.toLowerCase();
        String best = null;
        int bestDist = Integer.MAX_VALUE;
        for (String dict : WORDS) {
            int d = editDistance(w, dict);
            if (d < bestDist) {
                bestDist = d;
                best = dict;
            }
        }
        int maxAllowed = Math.max(1, w.length() / 3);
        if (best != null && bestDist <= maxAllowed) {
            return best;
        }
        return null;
    }

    private static List<String> closestByDistance(String typed, int count) {
        List<String> matches = new ArrayList<>();
        for (String w : WORDS) {
            int d = editDistance(typed, w);
            if (d <= 2) {
                matches.add(w);
                if (matches.size() >= count) break;
            }
        }
        return matches;
    }

    private static int editDistance(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];
        for (int j = 0; j <= n; j++) prev[j] = j;
        for (int i = 1; i <= m; i++) {
            curr[0] = i;
            for (int j = 1; j <= n; j++) {
                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                curr[j] = Math.min(Math.min(curr[j - 1] + 1, prev[j] + 1), prev[j - 1] + cost);
            }
            int[] tmp = prev;
            prev = curr;
            curr = tmp;
        }
        return prev[n];
    }
}
