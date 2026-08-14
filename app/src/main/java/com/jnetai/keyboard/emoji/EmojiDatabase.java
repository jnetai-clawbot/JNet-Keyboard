package com.jnetai.keyboard.emoji;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EmojiDatabase {
    public static class EmojiEntry {
        public final String emoji;
        public final String name;
        public final String category;
        public final List<String> keywords;

        EmojiEntry(String emoji, String name, String category, String... keywords) {
            this.emoji = emoji;
            this.name = name;
            this.category = category;
            this.keywords = new ArrayList<>();
            for (String kw : keywords) this.keywords.add(kw.toLowerCase());
        }
    }

    private static final List<EmojiEntry> emojis = new ArrayList<>();
    private static final Map<String, List<EmojiEntry>> categoryMap = new LinkedHashMap<>();

    static {
        add("😀", "Grinning Face", "Smileys", "smile", "happy", "face", "grin");
        add("😂", "Face with Tears of Joy", "Smileys", "laugh", "tears", "joy", "lol", "funny");
        add("🤣", "Rolling on Floor Laughing", "Smileys", "rofl", "laugh", "funny");
        add("😊", "Smiling Face with Smiling Eyes", "Smileys", "smile", "blush", "happy");
        add("😍", "Smiling Face with Heart-Eyes", "Smileys", "love", "heart", "crush");
        add("😘", "Face Blowing a Kiss", "Smileys", "kiss", "love");
        add("😜", "Winking Face with Tongue", "Smileys", "wink", "tongue", "silly");
        add("🤔", "Thinking Face", "Smileys", "think", "hmm", "ponder");
        add("😎", "Smiling Face with Sunglasses", "Smileys", "cool", "sunglasses");
        add("😢", "Crying Face", "Smileys", "cry", "sad", "tears");
        add("😡", "Pouting Face", "Smileys", "angry", "mad", "pout");
        add("👍", "Thumbs Up", "Gestures", "yes", "ok", "like", "approve");
        add("👎", "Thumbs Down", "Gestures", "no", "dislike", "disapprove");
        add("👏", "Clapping Hands", "Gestures", "clap", "applause", "bravo");
        add("🙏", "Folded Hands", "Gestures", "please", "thanks", "pray");
        add("💪", "Flexed Biceps", "Gestures", "strong", "muscle", "power");
        add("❤️", "Red Heart", "Hearts", "love", "heart", "like");
        add("💙", "Blue Heart", "Hearts", "blue", "heart");
        add("💚", "Green Heart", "Hearts", "green", "heart");
        add("💛", "Yellow Heart", "Hearts", "yellow", "heart");
        add("💜", "Purple Heart", "Hearts", "purple", "heart");
        add("🖤", "Black Heart", "Hearts", "black", "heart");
        add("🤍", "White Heart", "Hearts", "white", "heart");
        add("🤎", "Brown Heart", "Hearts", "brown", "heart");
        add("💔", "Broken Heart", "Hearts", "broken", "heart", "sad");
        add("🔥", "Fire", "Objects", "hot", "flame", "lit");
        add("⭐", "Star", "Objects", "star", "favorite");
        add("✨", "Sparkles", "Objects", "sparkle", "shine", "magic");
        add("🎉", "Party Popper", "Objects", "party", "celebrate", "congrats");
        add("🎂", "Birthday Cake", "Objects", "birthday", "cake", "celebrate");
        add("💰", "Money Bag", "Objects", "money", "cash", "rich");
        add("✅", "Check Mark", "Symbols", "check", "done", "complete", "yes");
        add("❌", "Cross Mark", "Symbols", "no", "wrong", "cancel", "x");
        add("⚠️", "Warning", "Symbols", "warning", "caution", "alert");
        add("ℹ️", "Information", "Symbols", "info", "information");
        add("❓", "Question Mark", "Symbols", "question", "what", "why");
        add("💯", "Hundred Points", "Symbols", "100", "perfect", "score");
        add("☕", "Hot Beverage", "Food", "coffee", "tea", "drink");
        add("🍕", "Pizza", "Food", "pizza", "food", "slice");
        add("🍔", "Hamburger", "Food", "burger", "food", "fast food");
        add("🌮", "Taco", "Food", "taco", "food", "mexican");
        add("🍩", "Doughnut", "Food", "donut", "doughnut", "sweet");
        add("🎵", "Musical Note", "Music", "music", "note", "song");
        add("🎶", "Musical Notes", "Music", "music", "notes", "song");
        add("📱", "Mobile Phone", "Tech", "phone", "mobile", "smartphone");
        add("💻", "Laptop", "Tech", "laptop", "computer", "pc");
        add("🖥️", "Desktop Computer", "Tech", "desktop", "computer", "monitor");
        add("⌨️", "Keyboard", "Tech", "keyboard", "type");
        add("🌍", "Globe Europe-Africa", "Nature", "earth", "world", "globe");
        add("🌈", "Rainbow", "Nature", "rainbow", "gay", "pride");
        add("🌸", "Cherry Blossom", "Nature", "flower", "spring", "blossom");
        add("🌙", "Crescent Moon", "Nature", "moon", "night", "crescent");
        add("☀️", "Sun", "Nature", "sun", "sunny", "day");
        add("⚡", "High Voltage", "Nature", "lightning", "electric", "zap");
        add("💧", "Droplet", "Nature", "water", "drop", "rain");
        add("🚀", "Rocket", "Travel", "rocket", "space", "launch");
        add("✈️", "Airplane", "Travel", "plane", "flight", "travel");
        add("🚗", "Automobile", "Travel", "car", "drive", "vehicle");
        add("⏰", "Alarm Clock", "Time", "alarm", "clock", "time", "wake");
        add("📅", "Calendar", "Time", "calendar", "date", "schedule");
        add("🔒", "Locked", "Objects", "lock", "secure", "private");
        add("🔑", "Key", "Objects", "key", "password", "unlock");
        add("🎯", "Bullseye", "Objects", "target", "goal", "aim");
        add("🏆", "Trophy", "Objects", "trophy", "win", "winner", "award");
        add("🎮", "Video Game", "Objects", "game", "controller", "play");
        add("📚", "Books", "Objects", "books", "read", "study", "library");
        add("✏️", "Pencil", "Objects", "pencil", "write", "draw");
        add("📝", "Memo", "Objects", "memo", "note", "write");
        add("💡", "Light Bulb", "Objects", "idea", "light", "bulb");
        add("🔔", "Bell", "Objects", "bell", "notification", "ring");
        add("🎤", "Microphone", "Objects", "mic", "sing", "karaoke");
        add("📷", "Camera", "Objects", "camera", "photo", "picture");
        add("🎬", "Clapper Board", "Objects", "movie", "film", "action");
        add("🏠", "House", "Places", "house", "home", "building");
        add("🏢", "Office Building", "Places", "office", "work", "building");
        add("🏥", "Hospital", "Places", "hospital", "doctor", "health");
        add("🏫", "School", "Places", "school", "education", "learn");
        add("⛪", "Church", "Places", "church", "religion", "worship");
        add("☮️", "Peace Symbol", "Symbols", "peace", "hippie");
        add("♻️", "Recycling Symbol", "Symbols", "recycle", "green", "environment");
        add("©️", "Copyright", "Symbols", "copyright", "c");
        add("®️", "Registered", "Symbols", "registered", "trademark", "r");
        add("™️", "Trade Mark", "Symbols", "trademark", "tm");
        add("➕", "Plus", "Symbols", "plus", "add", "math");
        add("➖", "Minus", "Symbols", "minus", "subtract", "math");
        add("✖️", "Multiply", "Symbols", "multiply", "times", "math");
        add("➗", "Divide", "Symbols", "divide", "math");
        add("〰️", "Wavy Dash", "Symbols", "wave", "dash", "squiggle");
        add("💬", "Speech Balloon", "Objects", "speech", "chat", "talk");
        add("🗨️", "Left Speech Bubble", "Objects", "chat", "talk", "reply");
        add("👋", "Waving Hand", "Gestures", "wave", "hello", "goodbye", "hi");
        add("🤝", "Handshake", "Gestures", "shake", "deal", "agree");
        add("✌️", "Victory Hand", "Gestures", "peace", "victory", "v");
        add("🤞", "Crossed Fingers", "Gestures", "luck", "hope", "fingers crossed");
        add("🤘", "Sign of the Horns", "Gestures", "rock", "metal", "horns");
        add("👌", "OK Hand", "Gestures", "ok", "perfect", "fine");
        add("🤙", "Call Me Hand", "Gestures", "call", "phone", "shaka");
        add("🖖", "Vulcan Salute", "Gestures", "spock", "star trek", "vulcan");
        add("🙂", "Slightly Smiling Face", "Smileys", "smile", "happy", "fine");
        add("🙃", "Upside-Down Face", "Smileys", "upside down", "silly", "weird");
        add("😉", "Winking Face", "Smileys", "wink", "joke", "flirt");
        add("😋", "Face Savoring Food", "Smileys", "yum", "delicious", "tasty");
        add("😛", "Face with Tongue", "Smileys", "tongue", "silly", "playful");
        add("🤪", "Zany Face", "Smileys", "crazy", "silly", "goofy");
        add("😌", "Relieved Face", "Smileys", "relieved", "calm", "peaceful");
        add("😏", "Smirking Face", "Smileys", "smirk", "smug", "flirt");
        add("😒", "Unamused Face", "Smileys", "unamused", "unimpressed", "meh");
        add("😔", "Pensive Face", "Smileys", "sad", "pensive", "sorry");
        add("😴", "Sleeping Face", "Smileys", "sleep", "tired", "zzz");
        add("🤤", "Drooling Face", "Smileys", "drool", "hungry", "want");
        add("🤢", "Nauseated Face", "Smileys", "sick", "nauseous", "gross");
        add("🤮", "Face Vomiting", "Smileys", "vomit", "sick", "throw up");
        add("🤧", "Sneezing Face", "Smileys", "sneeze", "sick", "achoo");
        add("🥵", "Hot Face", "Smileys", "hot", "sweating", "heat");
        add("🥶", "Cold Face", "Smileys", "cold", "freezing", "winter");
        add("🥳", "Partying Face", "Smileys", "party", "celebrate", "birthday");
        add("🥺", "Pleading Face", "Smileys", "please", "beg", "puppy eyes");
        add("🤯", "Exploding Head", "Smileys", "mind blown", "wow", "amazed");
        add("🤬", "Face with Symbols on Mouth", "Smileys", "swear", "curse", "angry");
        add("😈", "Smiling Face with Horns", "Smileys", "devil", "evil", "mischief");
        add("👿", "Angry Face with Horns", "Smileys", "devil", "angry", "demon");
        add("💀", "Skull", "Smileys", "skull", "death", "dead");
        add("👻", "Ghost", "Smileys", "ghost", "halloween", "spooky");
        add("👽", "Alien", "Smileys", "alien", "ufo", "space");
        add("🤖", "Robot", "Smileys", "robot", "ai", "tech");
        add("😺", "Grinning Cat", "Smileys", "cat", "smile", "happy");
        add("😸", "Grinning Cat with Smiling Eyes", "Smileys", "cat", "smile");
        add("😹", "Cat with Tears of Joy", "Smileys", "cat", "laugh", "joy");
        add("😻", "Smiling Cat with Heart-Eyes", "Smileys", "cat", "love", "heart");
        add("😼", "Cat with Wry Smile", "Smileys", "cat", "smirk");
        add("😽", "Kissing Cat", "Smileys", "cat", "kiss");
        add("🙀", "Weary Cat", "Smileys", "cat", "shocked", "surprised");
        add("😿", "Crying Cat", "Smileys", "cat", "cry", "sad");
        add("😾", "Pouting Cat", "Smileys", "cat", "angry", "pout");
        add("🐶", "Dog Face", "Animals", "dog", "puppy", "pet");
        add("🐱", "Cat Face", "Animals", "cat", "kitten", "pet");
        add("🐭", "Mouse Face", "Animals", "mouse", "rat", "rodent");
        add("🐹", "Hamster Face", "Animals", "hamster", "pet", "rodent");
        add("🐰", "Rabbit Face", "Animals", "rabbit", "bunny", "easter");
        add("🦊", "Fox Face", "Animals", "fox", "animal");
        add("🐻", "Bear Face", "Animals", "bear", "animal");
        add("🐼", "Panda Face", "Animals", "panda", "bear", "cute");
        add("🐨", "Koala", "Animals", "koala", "australia", "cute");
        add("🐯", "Tiger Face", "Animals", "tiger", "cat", "animal");
        add("🦁", "Lion Face", "Animals", "lion", "king", "animal");
        add("🐮", "Cow Face", "Animals", "cow", "moo", "farm");
        add("🐷", "Pig Face", "Animals", "pig", "oink", "farm");
        add("🐸", "Frog Face", "Animals", "frog", "toad", "amphibian");
        add("🐵", "Monkey Face", "Animals", "monkey", "ape", "animal");
        add("🐔", "Chicken", "Animals", "chicken", "bird", "farm");
        add("🐧", "Penguin", "Animals", "penguin", "bird", "cold");
        add("🐦", "Bird", "Animals", "bird", "tweet", "fly");
        add("🐤", "Baby Chick", "Animals", "chick", "bird", "easter");
        add("🦆", "Duck", "Animals", "duck", "bird", "quack");
        add("🦅", "Eagle", "Animals", "eagle", "bird", "america");
        add("🦉", "Owl", "Animals", "owl", "bird", "wise");
        add("🦇", "Bat", "Animals", "bat", "vampire", "halloween");
        add("🐺", "Wolf Face", "Animals", "wolf", "animal", "howl");
        add("🐗", "Boar", "Animals", "boar", "pig", "animal");
        add("🐴", "Horse Face", "Animals", "horse", "pony", "animal");
        add("🦄", "Unicorn Face", "Animals", "unicorn", "magic", "fantasy");
        add("🐝", "Honeybee", "Animals", "bee", "honey", "insect");
        add("🐛", "Bug", "Animals", "bug", "insect", "caterpillar");
        add("🦋", "Butterfly", "Animals", "butterfly", "insect", "beautiful");
        add("🐌", "Snail", "Animals", "snail", "slow", "insect");
        add("🐞", "Lady Beetle", "Animals", "ladybug", "insect", "bug");
        add("🐜", "Ant", "Animals", "ant", "insect", "small");
        add("🕷️", "Spider", "Animals", "spider", "insect", "web");
        add("🦂", "Scorpion", "Animals", "scorpion", "insect", "dangerous");
        add("🐢", "Turtle", "Animals", "turtle", "slow", "reptile");
        add("🐍", "Snake", "Animals", "snake", "reptile", "dangerous");
        add("🦎", "Lizard", "Animals", "lizard", "reptile");
        add("🦖", "T-Rex", "Animals", "dinosaur", "trex", "jurassic");
        add("🦕", "Sauropod", "Animals", "dinosaur", "long neck", "jurassic");
        add("🐙", "Octopus", "Animals", "octopus", "sea", "ocean");
        add("🦑", "Squid", "Animals", "squid", "sea", "ocean");
        add("🦐", "Shrimp", "Animals", "shrimp", "seafood", "ocean");
        add("🐠", "Tropical Fish", "Animals", "fish", "tropical", "ocean");
        add("🐟", "Fish", "Animals", "fish", "ocean", "sea");
        add("🐡", "Blowfish", "Animals", "blowfish", "puffer", "fish");
        add("🦈", "Shark", "Animals", "shark", "fish", "ocean");
        add("🐳", "Spouting Whale", "Animals", "whale", "ocean", "sea");
        add("🐋", "Whale", "Animals", "whale", "ocean", "sea");
        add("🐊", "Crocodile", "Animals", "crocodile", "alligator", "reptile");
        add("🐆", "Leopard", "Animals", "leopard", "cat", "animal");
        add("🐅", "Tiger", "Animals", "tiger", "cat", "animal");
        add("🐃", "Water Buffalo", "Animals", "buffalo", "cow", "farm");
        add("🐂", "Ox", "Animals", "ox", "cow", "farm");
        add("🐄", "Cow", "Animals", "cow", "moo", "farm");
        add("🐪", "Camel", "Animals", "camel", "desert", "animal");
        add("🐫", "Two-Hump Camel", "Animals", "camel", "desert", "animal");
        add("🐘", "Elephant", "Animals", "elephant", "animal", "big");
        add("🦏", "Rhinoceros", "Animals", "rhino", "animal", "big");
        add("🦍", "Gorilla", "Animals", "gorilla", "ape", "animal");
        add("🐒", "Monkey", "Animals", "monkey", "ape", "animal");
        add("🐑", "Sheep", "Animals", "sheep", "wool", "farm");
        add("🐐", "Goat", "Animals", "goat", "animal", "farm");
        add("🐏", "Ram", "Animals", "ram", "sheep", "animal");
        add("🐕", "Dog", "Animals", "dog", "puppy", "pet");
        add("🐩", "Poodle", "Animals", "poodle", "dog", "pet");
        add("🐈", "Cat", "Animals", "cat", "kitten", "pet");
        add("🐓", "Rooster", "Animals", "rooster", "chicken", "farm");
        add("🦃", "Turkey", "Animals", "turkey", "bird", "thanksgiving");
        add("🐿️", "Chipmunk", "Animals", "chipmunk", "squirrel", "animal");
        add("🐾", "Paw Prints", "Animals", "paw", "animal", "pet");
    }

    private static void add(String emoji, String name, String category, String... keywords) {
        EmojiEntry entry = new EmojiEntry(emoji, name, category, keywords);
        emojis.add(entry);
        categoryMap.computeIfAbsent(category, k -> new ArrayList<>()).add(entry);
    }

    public static List<EmojiEntry> getAllEmojis() { return emojis; }

    public static Map<String, List<EmojiEntry>> getCategoryMap() { return categoryMap; }

    public static List<EmojiEntry> search(String query) {
        List<EmojiEntry> results = new ArrayList<>();
        String q = query.toLowerCase().trim();
        if (q.isEmpty()) return results;
        for (EmojiEntry e : emojis) {
            if (e.name.toLowerCase().contains(q)) { results.add(e); continue; }
            for (String kw : e.keywords) {
                if (kw.contains(q)) { results.add(e); break; }
            }
        }
        return results;
    }
}
