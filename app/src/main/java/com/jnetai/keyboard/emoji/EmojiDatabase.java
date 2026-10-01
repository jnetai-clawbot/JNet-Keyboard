package com.jnetai.keyboard.emoji;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class EmojiDatabase {
    /** How many emoji chips a single completed word may fill the suggestion bar with. */
    public static final int MAX_WORD_MATCHES = 10;

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
            for (String kw : keywords) this.keywords.add(kw.toLowerCase(Locale.ROOT));
        }
    }

    /**
     * Purely grammatical words that carry no meaning as an emoji trigger. Only applied when
     * breaking up emoji <em>names</em> - an explicitly listed keyword is always honoured, so a
     * real keyword such as "up" or "new" is never filtered out.
     */
    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList(
            "with", "and", "the", "of", "for", "in", "on", "at", "to", "from", "a", "an",
            "its", "it", "is", "are", "be", "or", "but", "not", "so", "as", "by", "if",
            "that", "this", "these", "those", "out", "over", "under", "very", "just", "than",
            "very", "symbol", "emoji", "smiley", "and", "no", "s", "t", "without", "no"));

    private static final List<EmojiEntry> emojis = new ArrayList<>();
    private static final Map<String, List<EmojiEntry>> categoryMap = new LinkedHashMap<>();
    /** emoji -> entry, used by the synonym table. */
    private static final Map<String, EmojiEntry> byEmoji = new LinkedHashMap<>();

    /** word -> entries that listed the word as a primary keyword or full name (best matches). */
    private static final Map<String, List<EmojiEntry>> exactIndex = new HashMap<>();
    /** word -> entries where the word is only one part of a multi-word keyword or name. */
    private static final Map<String, List<EmojiEntry>> tokenIndex = new HashMap<>();

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
        add("🦌", "Deer", "Animals", "deer", "reindeer", "christmas");
        add("🍎", "Red Apple", "Food", "apple", "fruit", "red");
        add("🍏", "Green Apple", "Food", "apple", "fruit", "green");
        add("🍌", "Banana", "Food", "banana", "fruit");
        add("🍇", "Grapes", "Food", "grapes", "grape", "fruit");
        add("🍊", "Tangerine", "Food", "orange", "tangerine", "fruit");
        add("🍓", "Strawberry", "Food", "strawberry", "berry", "fruit");
        add("🍉", "Watermelon", "Food", "watermelon", "melon", "fruit");
        add("🍍", "Pineapple", "Food", "pineapple", "fruit");
        add("🍒", "Cherries", "Food", "cherry", "cherries", "fruit");
        add("🍑", "Peach", "Food", "peach", "fruit");
        add("🍋", "Lemon", "Food", "lemon", "citrus");
        add("🥝", "Kiwi", "Food", "kiwi", "fruit");
        add("🥑", "Avocado", "Food", "avocado", "fruit");
        add("🥕", "Carrot", "Food", "carrot", "vegetable");
        add("🍆", "Eggplant", "Food", "eggplant", "aubergine");
        add("🌽", "Corn", "Food", "corn", "maize");
        add("🥦", "Broccoli", "Food", "broccoli", "vegetable");
        add("🥒", "Cucumber", "Food", "cucumber", "pickle");
        add("🍄", "Mushroom", "Food", "mushroom", "fungus");
        add("🥔", "Potato", "Food", "potato", "vegetable");
        add("🍅", "Tomato", "Food", "tomato", "vegetable");
        add("🌶️", "Hot Pepper", "Food", "pepper", "spicy", "hot");
        add("🧄", "Garlic", "Food", "garlic");
        add("🧅", "Onion", "Food", "onion");
        add("🥥", "Coconut", "Food", "coconut", "tropical");
        add("🥭", "Mango", "Food", "mango", "fruit");
        add("🫐", "Blueberries", "Food", "blueberry", "blueberries", "berry");
        add("🍞", "Bread", "Food", "bread", "loaf");
        add("🥐", "Croissant", "Food", "croissant", "pastry");
        add("🥖", "Baguette", "Food", "baguette", "bread");
        add("🥯", "Bagel", "Food", "bagel", "bread");
        add("🧀", "Cheese", "Food", "cheese", "dairy");
        add("🥚", "Egg", "Food", "egg", "breakfast");
        add("🍳", "Cooking", "Food", "cooking", "fry", "pan");
        add("🥓", "Bacon", "Food", "bacon", "meat");
        add("🍗", "Poultry Leg", "Food", "chicken", "drumstick", "meat");
        add("🍟", "French Fries", "Food", "fries", "chips", "fast food");
        add("🌭", "Hot Dog", "Food", "hotdog", "hot dog", "sausage");
        add("🌯", "Burrito", "Food", "burrito", "wrap", "mexican");
        add("🍿", "Popcorn", "Food", "popcorn", "movie");
        add("🥨", "Pretzel", "Food", "pretzel", "snack");
        add("🥞", "Pancakes", "Food", "pancake", "pancakes", "breakfast");
        add("🧇", "Waffle", "Food", "waffle", "breakfast");
        add("🧈", "Butter", "Food", "butter", "dairy");
        add("🍦", "Ice Cream", "Food", "icecream", "soft serve", "dessert");
        add("🍧", "Shaved Ice", "Food", "shaved ice", "snow cone");
        add("🍨", "Ice Cream", "Food", "icecream", "dessert");
        add("🍰", "Shortcake", "Food", "cake", "dessert");
        add("🧁", "Cupcake", "Food", "cupcake", "cake", "dessert");
        add("🍪", "Cookie", "Food", "cookie", "biscuit", "dessert");
        add("🍬", "Candy", "Food", "candy", "sweet");
        add("🍫", "Chocolate", "Food", "chocolate", "candy", "sweet");
        add("🍭", "Lollipop", "Food", "lollipop", "candy", "sweet");
        add("🍮", "Custard", "Food", "custard", "pudding", "flan");
        add("🍯", "Honey Pot", "Food", "honey", "sweet");
        add("🍜", "Noodles", "Food", "noodles", "ramen", "soup");
        add("🍝", "Spaghetti", "Food", "spaghetti", "pasta");
        add("🍛", "Curry", "Food", "curry", "rice");
        add("🍣", "Sushi", "Food", "sushi", "japanese");
        add("🍤", "Fried Shrimp", "Food", "shrimp", "tempura");
        add("🍥", "Fish Cake", "Food", "fish cake", "naruto");
        add("🍙", "Rice Ball", "Food", "rice ball", "onigiri");
        add("🍚", "Rice", "Food", "rice");
        add("🥟", "Dumpling", "Food", "dumpling", "dumplings");
        add("🥠", "Fortune Cookie", "Food", "fortune cookie", "cookie");
        add("🥡", "Takeout Box", "Food", "takeout", "takeaway");
        add("🍱", "Bento", "Food", "bento", "lunchbox", "lunch");
        add("🥧", "Pie", "Food", "pie", "dessert");
        add("🥤", "Cup with Straw", "Food", "soda", "drink", "soft drink");
        add("🧃", "Juice Box", "Food", "juice", "drink");
        add("🥛", "Milk", "Food", "milk", "drink");
        add("🍵", "Tea", "Food", "tea", "green tea", "drink");
        add("🍺", "Beer", "Food", "beer", "drink", "alcohol");
        add("🍻", "Clinking Beer Mugs", "Food", "cheers", "beer", "toast");
        add("🍷", "Wine Glass", "Food", "wine", "drink");
        add("🍸", "Cocktail", "Food", "cocktail", "martini", "drink");
        add("🍹", "Tropical Drink", "Food", "cocktail", "drink", "tropical");
        add("🥂", "Clinking Glasses", "Food", "cheers", "toast", "celebrate");
        add("🥃", "Tumbler Glass", "Food", "whiskey", "whisky", "drink");
        add("🍾", "Bottle with Popping Cork", "Food", "champagne", "celebrate", "bottle");
        add("🍶", "Sake", "Food", "sake", "rice wine");
        add("🍲", "Pot of Food", "Food", "stew", "soup", "pot");
        add("🥘", "Shallow Pan of Food", "Food", "paella", "pan");
        add("🥗", "Green Salad", "Food", "salad", "healthy");
        add("🥙", "Stuffed Flatbread", "Food", "kebab", "gyro", "wrap");
        add("🧆", "Falafel", "Food", "falafel");
        add("🍠", "Roasted Sweet Potato", "Food", "sweet potato", "yam");
        add("🥜", "Peanuts", "Food", "peanut", "peanuts", "nuts");
        add("🫒", "Olive", "Food", "olive", "oil");
        add("🫘", "Beans", "Food", "bean", "beans", "legume");
        add("🥢", "Chopsticks", "Food", "chopsticks", "asian");
        add("🍽️", "Fork and Knife with Plate", "Food", "dining", "food", "dinner");
        add("🍴", "Fork and Knife", "Food", "fork", "knife", "cutlery");
        add("🥄", "Spoon", "Food", "spoon", "cutlery");
        add("🔪", "Kitchen Knife", "Food", "knife", "cut", "chef");
        add("🏺", "Amphora", "Food", "amphora", "vase", "pottery");
        add("🧂", "Salt", "Food", "salt", "seasoning");
        add("🍼", "Baby Bottle", "Food", "baby", "milk", "bottle");
        add("🫖", "Teapot", "Food", "teapot", "tea");
        add("🧉", "Mate", "Food", "mate", "tea");
        add("🫗", "Pouring Liquid", "Food", "pour", "drink");
        add("🌦️", "Sun Behind Rain Cloud", "Nature", "weather", "rain", "sun");
        add("🌧️", "Cloud with Rain", "Nature", "rain", "weather");
        add("⛈️", "Cloud with Lightning and Rain", "Nature", "storm", "thunder", "lightning");
        add("🌩️", "Cloud with Lightning", "Nature", "lightning", "storm");
        add("🌨️", "Cloud with Snow", "Nature", "snow", "weather");
        add("❄️", "Snowflake", "Nature", "snow", "flake", "cold", "winter");
        add("☃️", "Snowman", "Nature", "snowman", "snow", "winter");
        add("⛄", "Snowman Without Snow", "Nature", "snowman", "snow");
        add("🌬️", "Wind Face", "Nature", "wind", "blow");
        add("🌀", "Cyclone", "Nature", "cyclone", "hurricane", "tornado");
        add("🌫️", "Foggy", "Nature", "fog", "mist");
        add("🌪️", "Tornado", "Nature", "tornado", "cyclone");
        add("🌡️", "Thermometer", "Nature", "thermometer", "temperature", "fever", "heat");
        add("🧊", "Ice", "Nature", "ice", "cube", "cold");
        add("🪨", "Rock", "Nature", "rock", "stone");
        add("🦴", "Bone", "Nature", "bone", "skeleton");
        add("🧠", "Brain", "Nature", "brain", "mind", "intelligence");
        add("🫀", "Anatomical Heart", "Nature", "anatomical", "heart", "organ");
        add("🫁", "Lungs", "Nature", "lung", "lungs", "breathe");
        add("👁️", "Eye", "Nature", "eye", "sight");
        add("👀", "Eyes", "Nature", "eyes", "look", "watch");
        add("👄", "Lips", "Nature", "lips", "mouth", "kiss");
        add("🦷", "Tooth", "Nature", "tooth", "teeth", "dental");
        add("🦵", "Leg", "Nature", "leg", "limb");
        add("🦶", "Foot", "Nature", "foot", "feet");
        add("💅", "Nail Polish", "Nature", "nails", "nail polish", "manicure");
        add("🦠", "Microbe", "Nature", "bacteria", "germ", "virus");
        add("🌻", "Sunflower", "Nature", "sunflower", "flower");
        add("🌷", "Tulip", "Nature", "tulip", "flower");
        add("🌹", "Rose", "Nature", "rose", "flower", "love");
        add("🌺", "Hibiscus", "Nature", "hibiscus", "flower");
        add("🌴", "Palm Tree", "Nature", "palm", "tree", "tropical");
        add("🌳", "Tree", "Nature", "tree", "forest");
        add("🌲", "Evergreen Tree", "Nature", "tree", "pine", "forest");
        add("🍀", "Four Leaf Clover", "Nature", "clover", "luck", "lucky");
        add("🍃", "Leaf", "Nature", "leaf", "leaves", "autumn");
        add("🍂", "Fallen Leaf", "Nature", "leaf", "autumn", "fall");
        add("🍁", "Maple Leaf", "Nature", "maple", "leaf", "autumn", "canada");
        add("🐚", "Spiral Shell", "Nature", "shell", "beach", "seashell");
        add("🌊", "Ocean Wave", "Nature", "wave", "ocean", "sea", "water");
        add("🌋", "Volcano", "Nature", "volcano", "eruption");
        add("⛰️", "Mountain", "Nature", "mountain");
        add("🏔️", "Snow-Capped Mountain", "Nature", "mountain", "snow");
        add("🗻", "Mount Fuji", "Nature", "mountain", "fuji");
        add("🏜️", "Desert", "Nature", "desert", "sand");
        add("🏝️", "Desert Island", "Nature", "island", "tropical");
        add("🏞️", "National Park", "Nature", "park", "nature");
        add("🗺️", "World Map", "Nature", "map", "world", "globe");
        add("🌏", "Globe Asia-Australia", "Nature", "earth", "globe", "world");
        add("🌎", "Globe Americas", "Nature", "earth", "globe", "world");
        add("🌕", "Full Moon", "Nature", "moon", "full moon", "night");
        add("🌖", "Waning Gibbous Moon", "Nature", "moon");
        add("🌗", "Last Quarter Moon", "Nature", "moon");
        add("🌘", "Waning Crescent Moon", "Nature", "moon");
        add("🌑", "New Moon", "Nature", "moon");
        add("🌒", "Waxing Crescent Moon", "Nature", "moon");
        add("🌓", "First Quarter Moon", "Nature", "moon");
        add("🌔", "Waxing Gibbous Moon", "Nature", "moon");
        add("🌝", "Full Moon Face", "Nature", "moon", "face");
        add("🌛", "First Quarter Moon Face", "Nature", "moon");
        add("🌜", "Last Quarter Moon Face", "Nature", "moon");
        add("🌚", "New Moon Face", "Nature", "moon", "dark");
        add("🌟", "Glowing Star", "Nature", "star", "glow", "shine");
        add("💫", "Dizzy", "Nature", "dizzy", "star", "sparkle");
        add("🌠", "Shooting Star", "Nature", "shooting star", "star", "wish");
        add("🌌", "Milky Way", "Nature", "galaxy", "milky way", "space");
        add("🌆", "Cityscape at Dusk", "Places", "city", "dusk", "evening");
        add("🌃", "Night with Stars", "Places", "night", "city", "stars");
        add("🌇", "Sunset", "Places", "sunset", "evening");
        add("🌅", "Sunrise", "Places", "sunrise", "morning", "dawn");
        add("🌄", "Sunrise over Mountains", "Places", "sunrise", "mountains");
        add("🏰", "Castle", "Places", "castle");
        add("🏯", "Japanese Castle", "Places", "castle", "japanese");
        add("🗼", "Tokyo Tower", "Places", "tokyo", "tower");
        add("🗽", "Statue of Liberty", "Places", "liberty", "statue", "new york");
        add("⛲", "Fountain", "Places", "fountain", "water");
        add("🎡", "Ferris Wheel", "Places", "ferris wheel", "fair", "wheel");
        add("🎢", "Roller Coaster", "Places", "rollercoaster", "coaster", "amusement");
        add("🎠", "Carousel Horse", "Places", "carousel", "merry go round");
        add("🗿", "Moai", "Places", "moai", "statue", "stone");
        add("🏖️", "Beach with Umbrella", "Places", "beach", "sand");
        add("🏗️", "Building Construction", "Places", "construction", "building", "build");
        add("🏘️", "Houses", "Places", "houses", "homes");
        add("🏙️", "Cityscape", "Places", "city", "skyline");
        add("🏪", "Convenience Store", "Places", "store", "shop", "convenience");
        add("🏬", "Department Store", "Places", "store", "department");
        add("🏨", "Hotel", "Places", "hotel");
        add("🏭", "Factory", "Places", "factory", "industrial");
        add("🕌", "Mosque", "Places", "mosque", "muslim");
        add("🕍", "Synagogue", "Places", "synagogue", "jewish");
        add("🛕", "Hindu Temple", "Places", "temple", "hindu");
        add("⛩️", "Shrine", "Places", "shrine", "shinto");
        add("🛤️", "Railway Track", "Places", "railway", "track", "train");
        add("🛣️", "Highway", "Places", "highway", "road", "motorway");
        add("🛢️", "Oil Drum", "Places", "oil", "barrel", "drum");
        add("🗾", "Map of Japan", "Places", "japan", "map");
        add("⚽", "Soccer Ball", "Objects", "football", "soccer", "ball", "sport");
        add("🏀", "Basketball", "Objects", "basketball", "ball", "sport");
        add("🏈", "American Football", "Objects", "football", "nfl", "sport");
        add("⚾", "Baseball", "Objects", "baseball", "ball", "sport");
        add("🎾", "Tennis", "Objects", "tennis", "ball", "sport");
        add("🎱", "Pool 8 Ball", "Objects", "pool", "billiards", "8ball", "ball");
        add("🏉", "Rugby", "Objects", "rugby", "ball", "sport");
        add("🥅", "Goal Net", "Objects", "goal", "net", "score");
        add("🏐", "Volleyball", "Objects", "volleyball", "ball", "sport");
        add("🏓", "Ping Pong", "Objects", "pingpong", "table tennis", "paddle");
        add("🏸", "Badminton", "Objects", "badminton", "shuttlecock", "sport");
        add("🥊", "Boxing Glove", "Objects", "boxing", "glove", "box");
        add("🥋", "Martial Arts", "Objects", "martial arts", "karate", "judo");
        add("🥌", "Curling Stone", "Objects", "curling", "stone");
        add("⛳", "Flag in Hole", "Objects", "golf", "flag", "hole");
        add("🏌️", "Golfer", "Objects", "golf", "golfer", "swing");
        add("🏄", "Surfer", "Objects", "surf", "surfer", "surfing");
        add("🏊", "Swimmer", "Objects", "swim", "swimmer", "swimming");
        add("🤽", "Water Polo", "Objects", "water polo", "swim");
        add("🚣", "Rowing Boat", "Objects", "row", "rowing", "boat");
        add("🧗", "Climbing Person", "Objects", "climb", "climbing", "rock");
        add("🚴", "Bicyclist", "Objects", "cycle", "cycling", "bike");
        add("🚵", "Mountain Bicyclist", "Objects", "bike", "cycling", "mountain bike");
        add("🏇", "Horse Racing", "Objects", "horse", "racing", "race");
        add("⛷️", "Skier", "Objects", "ski", "skier", "skiing");
        add("🎿", "Skis", "Objects", "ski", "skiing", "snow");
        add("🏂", "Snowboarder", "Objects", "snowboard", "snowboarding");
        add("🏋️", "Weight Lifter", "Objects", "weight", "lift", "gym", "muscle");
        add("🤸", "Cartwheeling", "Objects", "cartwheel", "gymnastics", "flip");
        add("🤺", "Person Fencing", "Objects", "fencing", "fence", "sword");
        add("🤼", "Wrestlers", "Objects", "wrestling", "wrestle");
        add("🤾", "Handball", "Objects", "handball");
        add("🤹", "Juggler", "Objects", "juggle", "juggling");
        add("🧘", "Lotus Position", "Objects", "yoga", "meditate", "meditation", "lotus");
        add("🛀", "Person Taking Bath", "Objects", "bath", "bathing", "bathtub");
        add("🛌", "Person in Bed", "Objects", "sleep", "bed", "sleeping");
        add("🏃", "Runner", "Objects", "run", "runner", "running", "sprint");
        add("🚶", "Pedestrian", "Objects", "walk", "walking", "pedestrian");
        add("💃", "Dancer", "Objects", "dance", "dancer", "dancing");
        add("🕺", "Man Dancing", "Objects", "dance", "dancing");
        add("🏕️", "Camping", "Objects", "camp", "camping", "tent");
        add("⛺", "Tent", "Objects", "tent", "camp", "camping");
        add("🏹", "Bow and Arrow", "Objects", "bow", "arrow", "archery");
        add("🎣", "Fishing Pole", "Objects", "fishing", "fish", "rod");
        add("🎪", "Circus Tent", "Objects", "circus");
        add("🎭", "Performing Arts", "Objects", "theater", "theatre", "drama", "mask");
        add("🎨", "Artist Palette", "Objects", "artist", "paint", "palette", "art");
        add("🕹️", "Joystick", "Objects", "joystick", "game");
        add("🎲", "Game Die", "Objects", "dice", "die", "game", "luck");
        add("🧩", "Puzzle Piece", "Objects", "puzzle", "jigsaw");
        add("♟️", "Chess Pawn", "Objects", "chess");
        add("🎰", "Slot Machine", "Objects", "slot", "casino", "gamble");
        add("🀄", "Mahjong", "Objects", "mahjong");
        add("🎴", "Flower Cards", "Objects", "cards", "hanafuda");
        add("🃏", "Joker", "Objects", "joker", "card", "cards");
        add("🎳", "Bowling", "Objects", "bowling", "bowl", "pins");
        add("🎁", "Wrapped Gift", "Objects", "gift", "present", "birthday");
        add("🎈", "Balloon", "Objects", "balloon", "party");
        add("🎀", "Ribbon", "Objects", "ribbon", "bow");
        add("🧸", "Teddy Bear", "Objects", "teddy", "teddy bear", "bear", "toy");
        add("🎄", "Christmas Tree", "Objects", "christmas", "xmas", "tree");
        add("🎅", "Santa Claus", "Objects", "santa", "christmas", "claus");
        add("🧑‍🎄", "Mx Claus", "Objects", "santa", "christmas");
        add("🎊", "Confetti Ball", "Objects", "confetti", "party", "celebrate");
        add("🎆", "Fireworks", "Objects", "firework", "fireworks", "celebrate");
        add("🎇", "Sparkler", "Objects", "sparkler", "firework");
        add("🪅", "Piñata", "Objects", "pinata", "party");
        add("🪩", "Mirror Ball", "Objects", "disco", "dance", "party");
        add("🪆", "Nesting Dolls", "Objects", "nesting dolls", "matryoshka");
        add("💍", "Ring", "Objects", "ring", "engagement", "wedding", "propose");
        add("💎", "Gem Stone", "Objects", "gem", "diamond", "jewel", "jewellery");
        add("🪙", "Coin", "Objects", "coin", "money");
        add("💵", "Dollar Banknote", "Objects", "dollar", "money", "cash", "banknote");
        add("💴", "Yen Banknote", "Objects", "yen", "money");
        add("💶", "Euro Banknote", "Objects", "euro", "money");
        add("💷", "Pound Banknote", "Objects", "pound", "money");
        add("💸", "Money with Wings", "Objects", "money", "spend", "cash");
        add("💳", "Credit Card", "Objects", "card", "credit", "payment", "bank");
        add("🧾", "Receipt", "Objects", "receipt", "bill");
        add("💲", "Heavy Dollar Sign", "Objects", "dollar", "money");
        add("💱", "Currency Exchange", "Objects", "exchange", "currency");
        add("🪪", "ID Card", "Objects", "id", "card", "badge");
        add("🗑️", "Wastebasket", "Objects", "trash", "bin", "rubbish", "delete");
        add("🛒", "Shopping Cart", "Objects", "shopping", "cart", "trolley");
        add("🛍️", "Shopping Bags", "Objects", "shopping", "bags");
        add("🔋", "Battery", "Objects", "battery", "power", "charge", "energy");
        add("🔌", "Electric Plug", "Objects", "plug", "power", "electric");
        add("🔦", "Flashlight", "Objects", "torch", "flashlight", "light");
        add("🕯️", "Candle", "Objects", "candle", "light");
        add("🔧", "Wrench", "Objects", "wrench", "tool", "fix", "spanner");
        add("🔨", "Hammer", "Objects", "hammer", "tool", "fix");
        add("🛠️", "Hammer and Wrench", "Objects", "tool", "tools", "fix", "repair");
        add("⚒️", "Hammer and Pick", "Objects", "hammer", "pick", "mining");
        add("⛏️", "Pick", "Objects", "pick", "mining", "tool");
        add("🔩", "Nut and Bolt", "Objects", "bolt", "screw", "nut");
        add("⚙️", "Gear", "Objects", "gear", "settings", "mechanical", "cog");
        add("🛰️", "Satellite", "Objects", "satellite", "space", "orbit");
        add("🔭", "Telescope", "Objects", "telescope", "space", "astronomy", "stars");
        add("🔬", "Microscope", "Objects", "microscope", "science", "lab");
        add("🧪", "Test Tube", "Objects", "test tube", "science", "lab", "chemistry");
        add("🧫", "Petri Dish", "Objects", "petri dish", "science", "lab", "bacteria");
        add("🧬", "DNA", "Objects", "dna", "gene", "genetics", "science");
        add("⚗️", "Alembic", "Objects", "alembic", "chemistry", "distill");
        add("🧲", "Magnet", "Objects", "magnet", "attract", "magnets");
        add("🧯", "Fire Extinguisher", "Objects", "fire", "extinguisher", "safety");
        add("🪜", "Ladder", "Objects", "ladder", "climb");
        add("🧰", "Toolbox", "Objects", "toolbox", "tools", "tool");
        add("🪛", "Screwdriver", "Objects", "screwdriver", "tool");
        add("🔫", "Water Pistol", "Objects", "gun", "pistol", "toy");
        add("🧹", "Broom", "Objects", "broom", "clean", "sweep");
        add("🧺", "Basket", "Objects", "basket", "picnic");
        add("🧶", "Yarn", "Objects", "yarn", "knit", "wool");
        add("🧵", "Thread", "Objects", "thread", "sew");
        add("🪡", "Sewing Needle", "Objects", "needle", "sew");
        add("🪢", "Knot", "Objects", "knot", "tie");
        add("🩹", "Bandage", "Objects", "bandage", "plaster", "wound");
        add("🩺", "Stethoscope", "Objects", "stethoscope", "doctor", "medical");
        add("🩻", "X-Ray", "Objects", "xray", "x-ray", "scan");
        add("🩼", "Crutch", "Objects", "crutch", "injury");
        add("🛡️", "Shield", "Objects", "shield", "protect", "defense");
        add("⚔️", "Crossed Swords", "Objects", "swords", "sword", "fight");
        add("🗡️", "Dagger", "Objects", "dagger", "knife");
        add("🧿", "Nazar Amulet", "Objects", "nazar", "evil eye", "protection");
        add("🪬", "Hand of Fatima", "Objects", "hamsa", "protection");
        add("🔮", "Crystal Ball", "Objects", "crystal ball", "fortune", "magic", "psychic");
        add("🪄", "Magic Wand", "Objects", "magic", "wand");
        add("🕳️", "Hole", "Objects", "hole");
        add("🕸️", "Spider Web", "Objects", "web", "spider");
        add("🎗️", "Reminder Ribbon", "Objects", "ribbon", "awareness");
        add("🎟️", "Admission Tickets", "Objects", "ticket", "tickets");
        add("🎫", "Ticket", "Objects", "ticket");
        add("🎖️", "Military Medal", "Objects", "medal", "award");
        add("🏅", "Sports Medal", "Objects", "medal", "gold", "win");
        add("🥇", "Gold Medal", "Objects", "gold", "medal", "winner", "win");
        add("🥈", "Silver Medal", "Objects", "silver", "medal", "second");
        add("🥉", "Bronze Medal", "Objects", "bronze", "medal", "third");
        add("🏁", "Checkered Flag", "Objects", "checkered", "racing", "finish");
        add("🎌", "Crossed Flags", "Objects", "flags", "japan");
        add("🏴", "Black Flag", "Objects", "flag", "black");
        add("🏳️", "White Flag", "Objects", "flag", "white", "surrender");
        add("🏳️‍🌈", "Rainbow Flag", "Objects", "pride", "rainbow", "lgbt");
        add("🏴‍☠️", "Pirate Flag", "Objects", "pirate", "flag");
        add("🚩", "Triangular Flag", "Objects", "flag");
        add("🚒", "Fire Engine", "Objects", "fire engine", "firetruck", "fire", "truck");
        add("🚓", "Police Car", "Objects", "police", "car");
        add("🚑", "Ambulance", "Objects", "ambulance", "medical", "emergency");
        add("🚌", "Bus", "Objects", "bus");
        add("🚐", "Minibus", "Objects", "bus", "minibus");
        add("🚕", "Taxi", "Objects", "taxi", "cab");
        add("🚙", "Sport Utility Vehicle", "Objects", "suv", "car", "offroad");
        add("🚚", "Delivery Truck", "Objects", "truck", "delivery", "lorry");
        add("🚛", "Articulated Lorry", "Objects", "truck", "lorry", "semi");
        add("🚜", "Tractor", "Objects", "tractor", "farm");
        add("🚲", "Bicycle", "Objects", "bike", "bicycle", "cycle");
        add("🛵", "Motor Scooter", "Objects", "scooter", "vespa");
        add("🏍️", "Motorcycle", "Objects", "motorcycle", "motorbike", "bike");
        add("🛴", "Kick Scooter", "Objects", "scooter");
        add("🚄", "High-Speed Train", "Objects", "train", "bullet train", "shinkansen");
        add("🚅", "Bullet Train", "Objects", "bullet", "train", "shinkansen");
        add("🚆", "Train", "Objects", "train");
        add("🚇", "Metro", "Objects", "metro", "subway", "underground");
        add("🚈", "Light Rail", "Objects", "train", "rail");
        add("🚉", "Station", "Objects", "station", "train");
        add("🚊", "Tram", "Objects", "tram");
        add("🚝", "Monorail", "Objects", "monorail", "train");
        add("🚞", "Mountain Railway", "Objects", "railway", "mountain", "train");
        add("🚋", "Tram Car", "Objects", "tram", "car");
        add("🚃", "Railway Car", "Objects", "railway", "car", "train");
        add("🚂", "Locomotive", "Objects", "train", "locomotive", "steam");
        add("🚁", "Helicopter", "Objects", "helicopter", "chopper");
        add("🛸", "Flying Saucer", "Objects", "ufo", "saucer", "alien", "space");
        add("🛩️", "Small Airplane", "Objects", "plane", "airplane", "flight");
        add("🛫", "Airplane Departure", "Objects", "departure", "takeoff", "flight");
        add("🛬", "Airplane Arrival", "Objects", "arrival", "landing", "flight");
        add("🚢", "Ship", "Objects", "ship", "boat", "cruise");
        add("⛵", "Sailboat", "Objects", "sailboat", "boat", "sail");
        add("🚤", "Speedboat", "Objects", "speedboat", "boat");
        add("🛥️", "Motorboat", "Objects", "boat", "motorboat");
        add("⛴️", "Ferry", "Objects", "ferry", "boat");
        add("⚓", "Anchor", "Objects", "anchor", "ship", "sail");
        add("🚧", "Construction", "Objects", "construction", "roadwork", "barrier");
        add("🚦", "Traffic Light", "Objects", "traffic", "light", "traffic light");
        add("🚥", "Vertical Traffic Light", "Objects", "traffic", "light");
        add("🛑", "Stop Sign", "Objects", "stop", "stop sign");
        add("🚨", "Police Car Light", "Objects", "siren", "police", "alert");
        add("⛽", "Fuel Pump", "Objects", "fuel", "gas", "petrol", "pump");
        add("🚏", "Bus Stop", "Objects", "bus stop", "bus", "stop");
        add("🖨️", "Printer", "Tech", "printer", "print");
        add("🖱️", "Computer Mouse", "Tech", "mouse", "computer");
        add("🖲️", "Trackball", "Tech", "trackball", "mouse");
        add("💾", "Floppy Disk", "Tech", "floppy", "save", "disk");
        add("💿", "Optical Disk", "Tech", "cd", "disc", "disk");
        add("📀", "DVD", "Tech", "dvd", "disc");
        add("📼", "Videocassette", "Tech", "vhs", "tape", "cassette");
        add("📸", "Camera with Flash", "Tech", "camera", "flash", "photo");
        add("📹", "Video Camera", "Tech", "video", "camcorder", "camera");
        add("🎥", "Movie Camera", "Tech", "movie", "film", "camera", "cinema");
        add("📽️", "Film Projector", "Tech", "projector", "film", "movie");
        add("🎞️", "Film Frames", "Tech", "film", "frames");
        add("📞", "Telephone Receiver", "Tech", "telephone", "phone", "call");
        add("☎️", "Telephone", "Tech", "telephone", "phone", "call");
        add("📟", "Pager", "Tech", "pager", "beeper");
        add("📠", "Fax Machine", "Tech", "fax", "fax machine");
        add("📻", "Radio", "Tech", "radio");
        add("🎙️", "Studio Microphone", "Tech", "mic", "microphone", "studio");
        add("🎚️", "Level Slider", "Tech", "slider", "volume");
        add("🎛️", "Control Knobs", "Tech", "knobs", "volume", "controls");
        add("📡", "Satellite Antenna", "Tech", "satellite", "signal", "antenna");
        add("⌚", "Watch", "Tech", "watch", "time");
        add("📲", "Mobile Phone with Arrow", "Tech", "phone", "mobile", "receive");
        add("📶", "Antenna Bars", "Tech", "signal", "bars", "wifi");
        add("📳", "Vibration Mode", "Tech", "vibrate", "phone");
        add("📴", "Mobile Phone Off", "Tech", "off", "phone");
        add("💽", "Computer Disk", "Tech", "disk", "disc");
        add("🕐", "One O'Clock", "Time", "time", "clock", "one");
        add("🕑", "Two O'Clock", "Time", "time", "clock", "two");
        add("🕒", "Three O'Clock", "Time", "time", "clock", "three");
        add("🕓", "Four O'Clock", "Time", "time", "clock", "four");
        add("🕔", "Five O'Clock", "Time", "time", "clock", "five");
        add("🕕", "Six O'Clock", "Time", "time", "clock", "six");
        add("🕖", "Seven O'Clock", "Time", "time", "clock", "seven");
        add("🕗", "Eight O'Clock", "Time", "time", "clock", "eight");
        add("🕘", "Nine O'Clock", "Time", "time", "clock", "nine");
        add("🕙", "Ten O'Clock", "Time", "time", "clock", "ten");
        add("🕚", "Eleven O'Clock", "Time", "time", "clock", "eleven");
        add("🕛", "Twelve O'Clock", "Time", "time", "clock", "twelve");
        add("🕧", "Twelve-Thirty", "Time", "time", "clock", "half past");
        add("⏱️", "Stopwatch", "Time", "stopwatch", "timer");
        add("⏲️", "Timer Clock", "Time", "timer", "countdown");
        add("🕰️", "Mantelpiece Clock", "Time", "clock", "mantelpiece");
        add("⌛", "Hourglass", "Time", "hourglass", "time", "wait");
        add("⏳", "Hourglass with Flowing Sand", "Time", "hourglass", "time", "wait");
        add("📆", "Tear-Off Calendar", "Time", "calendar", "date");
        add("🗓️", "Spiral Calendar", "Time", "calendar", "date");
        add("📇", "Card Index", "Time", "contacts", "rolodex");
        add("🗒️", "Spiral Notepad", "Time", "notepad", "notes", "note");

        // ---- Arrows, playback and media controls ----------------------------------------
        add("↔️", "Left-Right Arrow", "Symbols", "left", "right", "horizontal", "both", "link",
                "either", "middle", "swap");
        add("↕️", "Up-Down Arrow", "Symbols", "up", "down", "vertical", "sort", "vertical");
        add("↗️", "Up-Right Arrow", "Symbols", "up", "right", "north east", "share", "increase");
        add("↩️", "Right Arrow Curving Left", "Symbols", "return", "reply", "back", "undo", "enter");
        add("↪️", "Left Arrow Curving Right", "Symbols", "redo", "reply", "forward", "tab", "return");
        add("➡️", "Right Arrow", "Symbols", "right", "forward", "next", "arrow", "direction");
        add("⬅️", "Left Arrow", "Symbols", "left", "back", "previous", "arrow", "direction");
        add("⬆️", "Up Arrow", "Symbols", "up", "above", "increase", "arrow", "raise");
        add("⬇️", "Down Arrow", "Symbols", "down", "below", "decrease", "arrow", "lower");
        add("🔝", "Top", "Symbols", "top", "over", "above", "up", "peak");
        add("🔚", "End", "Symbols", "end", "last", "finish", "done", "final");
        add("🔜", "Soon", "Symbols", "soon", "later", "next", "coming");
        add("🔙", "Back", "Symbols", "back", "return", "previous", "undo");
        add("🔀", "Shuffle Tracks Button", "Symbols", "shuffle", "random", "mix", "play");
        add("🔁", "Repeat", "Symbols", "repeat", "loop", "again", "cycle", "replay");
        add("🔂", "Repeat Single", "Symbols", "repeat", "again", "loop", "one");
        add("🔄", "Counterclockwise Arrows Button", "Symbols", "refresh", "reload", "update",
                "sync", "rotate", "repeat", "refresh");
        add("▶️", "Play Button", "Symbols", "play", "start", "resume", "go");
        add("⏸️", "Pause Button", "Symbols", "pause", "hold", "break", "wait");
        add("⏹️", "Stop Button", "Symbols", "stop", "end", "halt", "cancel");
        add("⏺️", "Record Button", "Symbols", "record", "capture", "film", "recording");
        add("⌫", "Backspace", "Symbols", "backspace", "delete", "erase", "remove", "rub out");
        add("⌨️", "Keyboard", "Tech", "keyboard", "type", "typing", "keys", "input");
        add("🖽️", "Framed Picture", "Objects", "picture", "frame", "photo", "art", "wall", "print");
        add("🖨️", "Printer", "Tech", "printer", "print", "printing", "paper", "printout");
        add("🖱️", "Computer Mouse", "Tech", "mouse", "computer", "click", "pointer");
        add("🕹️", "Joystick", "Objects", "joystick", "game", "console", "control");
        add("☐️", "Empty Check Box", "Symbols", "checkbox", "empty", "box", "tick", "todo");
        add("☑️", "Checked Check Box", "Symbols", "checkbox", "checked", "tick", "done", "ok");
        add("♨️", "Hot Springs", "Nature", "hot", "steam", "spa", "bath", "geothermal", "heat");
        add("⚖️", "Balance Scale", "Symbols", "law", "justice", "balance", "legal", "court", "weigh");
        add("⛑️", "Rescue Worker's Helmet", "Objects", "helmet", "hard hat", "safety", "construction",
                "rescue", "builder");
        add("✋", "Raised Hand", "Gestures", "hand", "stop", "high five", "raised", "five", "palm");
        add("🖐️", "Hand with Fingers Splayed", "Gestures", "hand", "stop", "five", "palm", "open hand");
        add("✍️", "Writing Hand", "Gestures", "write", "writing", "signature", "sign", "pen", "note");
        add("👤", "Bust in Silhouette", "People", "user", "profile", "account", "person", "silhouette");
        add("👥", "Busts in Silhouette", "People", "people", "users", "team", "group", "crowd", "audience");
        add("👫", "Woman and Man Holding Hands", "People", "couple", "relationship", "partners",
                "together", "romance");
        add("👪", "Family", "People", "family", "parents", "children", "home", "together");
        add("👨‍👩‍👧", "Family Man Woman Girl", "People", "family", "parents", "daughter", "home");
        add("👴", "Old Man", "People", "grandpa", "grandfather", "grandparent", "elderly", "old");
        add("👵", "Old Woman", "People", "grandma", "grandmother", "grandparent", "elderly", "old");
        add("🧒", "Child", "People", "child", "kid", "children", "young", "small");
        add("👶", "Baby", "People", "baby", "infant", "newborn", "child", "toddler");
        add("👨", "Man", "People", "man", "male", "guy", "gentleman", "mr", "adult");
        add("👩", "Woman", "People", "woman", "female", "lady", "girl", "ms", "mrs", "adult");
        add("👦", "Boy", "People", "boy", "son", "child", "kid", "young man");
        add("👧", "Girl", "People", "girl", "daughter", "child", "kid", "young woman");
        add("👨‍⚕️", "Man Health Worker", "People", "doctor", "man", "health", "medical", "clinician");
        add("👩‍⚕️", "Woman Health Worker", "People", "nurse", "woman", "health", "medical", "clinician");
        add("🧑‍⚖️", "Person in Suit Levitating", "People", "lawyer", "legal", "solicitor", "barrister",
                "justice", "court");
        add("👨‍⚖️", "Man in Suit Levitating", "People", "judge", "lawyer", "legal", "court", "verdict");
        add("👨‍🌾", "Man Farmer", "People", "farmer", "farm", "crops", "harvest", "agriculture");
        add("👨‍🍳", "Man Cook", "People", "cook", "chef", "kitchen", "bake", "cooking", "cuisine");
        add("👨‍🏫", "Man Teacher", "People", "teacher", "school", "education", "teach", "lesson", "tutor");
        add("👨‍💻", "Man Technologist", "People", "developer", "coder", "programmer", "tech", "software");
        add("🧑‍🤝‍🧑", "People Holding Hands", "People", "friends", "together", "unity", "support",
                "friendship", "bond");
        add("💒", "Heart with Arrow", "People", "wedding", "marriage", "love", "bride", "groom", "romance");
        add("💌", "Love Letter", "Office", "love letter", "letter", "romance", "valentine", "note");
        add("💉", "Syringe", "Health", "syringe", "injection", "needle", "vaccine", "shot", "blood");
        add("💊", "Pill", "Health", "pill", "medicine", "medication", "drug", "tablet", "capsule",
                "prescription");
        add("💓", "Beating Heart", "Health", "heartbeat", "love", "pulse", "health", "alive");
        add("🩸", "Drop of Blood", "Health", "blood", "bleed", "injury", "drop", "red");
        add("🩹", "Adhesive Bandage", "Health", "bandage", "plaster", "wound", "injury", "patch", "hurt");
        add("🤒", "Face with Thermometer", "Health", "sick", "ill", "fever", "temperature", "unwell");
        add("🤕", "Face with Head-Bandage", "Health", "hurt", "pain", "injury", "bandage", "unwell");
        add("🤒", "Face with Thermometer", "Health", "sick", "fever", "ill");
        add("😷", "Face with Medical Mask", "Health", "mask", "sick", "ill", "covid", "protect", "doctor");
        add("😪", "Sleepy Face", "Smileys", "sleepy", "tired", "sleep", "drowsy", "zzz");
        add("😴", "Sleeping Face", "Smileys", "sleep", "sleeping", "tired", "asleep", "zzz", "rest");
        add("😮", "Face with Open Mouth", "Smileys", "surprise", "wow", "shocked", "amazed", "gasp");
        add("😱", "Face Screaming in Fear", "Smileys", "scared", "shock", "horror", "fear", "terrified");
        add("😨", "Fearful Face", "Smileys", "scared", "fear", "afraid", "frightened", "worried");
        add("😰", "Anxious Face with Sweat", "Smileys", "anxious", "worried", "nervous", "stress",
                "uneasy", "panic");
        add("😳", "Flushed Face", "Smileys", "embarrassed", "shy", "flustered", "surprised", "awkward");
        add("😵‍💫", "Face with Spiral Eyes", "Smileys", "dizzy", "confused", "knocked out", "spinning");
        add("🥵", "Hot Face", "Smileys", "hot", "sweating", "heat", "warm", "boiling", "overheating");
        add("🥶", "Cold Face", "Smileys", "cold", "freezing", "winter", "ice", "chilly", "frozen");
        add("🤠", "Cowboy Hat Face", "Smileys", "cowboy", "western", "hat");
        add("🥰", "Smiling Face with Hearts", "Smileys", "love", "adore", "affection", "crush", "adore");
        add("🥳", "Partying Face", "Smileys", "party", "celebrate", "birthday", "fun", "wild");
        add("🥸", "Disguised Face", "Smileys", "disguise", "incognito", "mask", "spy");
        add("🤩", "Star-Struck", "Smileys", "star struck", "amazed", "excited", "wow", "amazing");
        add("🤗", "Smiling Face with Open Hands", "Smileys", "hug", "hugging", "embrace", "warm", "welcome");
        add("🤫", "Shushing Face", "Smileys", "quiet", "secret", "silence", "shh", "hush", "tell");
        add("🤭", "Face with Hand Over Mouth", "Smileys", "oops", "giggle", "secret", "chuckle");
        add("🤔", "Thinking Face", "Smileys", "think", "hmm", "ponder", "consider", "wonder");
        add("🤷", "Person Shrugging", "People", "shrug", "whatever", "dunno", "unsure", "unknown");
        add("😐", "Neutral Face", "Smileys", "neutral", "meh", "blank", "expressionless", "unimpressed");
        add("😕", "Confused Face", "Smileys", "confused", "puzzled", "unsure", "perplexed");
        add("😟", "Worried Face", "Smileys", "worried", "anxious", "concerned", "troubled");
        add("😣", "Persevering Face", "Smileys", "struggling", "effort", "frustrated", "persevere");
        add("😤", "Face with Steam From Nose", "Smileys", "frustrated", "annoyed", "angry", "fed up");
        add("😩", "Weary Face", "Smileys", "weary", "exhausted", "tired", "done in");
        add("😞", "Disappointed Face", "Smileys", "disappointed", "sad", "let down", "deflated");
        add("😫", "Tired Face", "Smileys", "awful", "terrible", "exhausted", "wretched", "bad");
        add("😖", "Confounded Face", "Smileys", "frustrated", "confounded", "upset");
        add("😇", "Smiling Face with Halo", "Smileys", "innocent", "angel", "good", "pure", "halo");
        add("🥲", "Smiling Face with Tear", "Smileys", "relieved", "tear", "happy cry", "grateful");
        add("😃", "Grinning Face with Big Eyes", "Smileys", "happy", "grin", "joy", "cheerful", "great");
        add("😄", "Grinning Face with Smiling Eyes", "Smileys", "happy", "smile", "joy", "laugh", "cheerful");
        add("😅", "Grinning Face with Sweat", "Smileys", "relief", "nervous laugh", "awkward", "phew");
        add("🙃", "Upside-Down Face", "Smileys", "silly", "sarcasm", "irony", "sarcastic", "weird");
        add("🤡", "Clown Face", "Smileys", "clown", "fool", "joker", "stupid", "circus");
        add("🙈", "See-No-Evil Monkey", "Smileys", "hide", "shy", "embarrassed", "monkey", "peek");
        add("🙉", "Hear-No-Evil Monkey", "Smileys", "ignore", "monkey", "listen", "hear");
        add("🙊", "Speak-No-Evil Monkey", "Smileys", "secret", "monkey", "speak", "silence", "oops");
        add("🚫", "Prohibited", "Symbols", "no", "forbidden", "banned", "prohibited", "not allowed");
        add("🤷", "Shrug", "People", "shrug", "whatever", "dunno", "unsure");
        add("🤿", "Diving Mask", "Travel", "dive", "diving", "diver", "snorkel", "underwater", "mask");
        add("😇", "Innocent", "Smileys", "innocent", "angel", "pure");
        add("🥷", "Ninja", "People", "ninja", "stealth", "spy", "thief", "secret", "shadow");
        add("🕵️", "Detective", "People", "detective", "spy", "investigator", "sleuth", "clue", "private eye");
        add("🎃", "Jack-O-Lantern", "Objects", "pumpkin", "halloween", "lantern", "spooky", "october");
        add("🌞", "Sun with Face", "Nature", "sun", "smiling", "summer", "sunshine", "day");
        add("🌐", "Globe with Meridians", "Nature", "internet", "web", "worldwide", "global",
                "website", "online", "earth");
        add("🍈", "Melon", "Food", "melon", "papaya", "fruit", "watermelon", "cantaloupe");
        add("🎒", "Backpack", "Objects", "backpack", "rucksack", "school bag", "bag", "satchel");
        add("🎓", "Graduation Cap", "Objects", "graduation", "graduate", "degree", "education",
                "university", "diploma");
        add("🎩", "Top Hat", "Objects", "hat", "top hat", "formal", "classy", "magic");
        add("👒", "Bonnet", "Objects", "hat", "bonnet", "sun hat", "cap", "brim");
        add("👑", "Crown", "Objects", "crown", "royal", "king", "queen", "monarchy", "champion");
        add("👓", "Glasses", "Objects", "glasses", "specs", "spectacles", "eyewear", "vision", "read");
        add("🕶️", "Sunglasses", "Objects", "sunglasses", "shades", "cool", "sun", "glasses");
        add("👔", "Necktie", "Objects", "necktie", "tie", "formal", "shirt", "office", "business");
        add("👕", "T-Shirt", "Objects", "shirt", "tshirt", "tee", "clothing", "top", "casual");
        add("👖", "Jeans", "Objects", "jeans", "trousers", "denim", "pants", "clothing");
        add("👗", "Dress", "Objects", "dress", "gown", "skirt", "fashion", "clothing", "formal");
        add("👛", "Purse", "Objects", "purse", "handbag", "wallet", "money", "clutch");
        add("👜", "Handbag", "Objects", "handbag", "purse", "bag", "tote", "clutch", "fashion");
        add("👟", "Running Shoe", "Objects", "shoe", "sneaker", "trainers", "footwear", "boot");
        add("👠", "High-Heeled Shoe", "Objects", "heels", "high heels", "shoe", "fashion", "dress");
        add("👢", "Woman's Boot", "Objects", "boot", "boots", "wellies", "footwear", "shoe");
        add("👣", "Footprints", "Objects", "footprints", "steps", "walking", "tracks", "shoes");
        add("🧢", "Billed Cap", "Objects", "cap", "baseball cap", "hat", "beanie", "headwear");
        add("🧣", "Scarf", "Objects", "scarf", "wrap", "winter", "wool", "neckwear");
        add("🧤", "Gloves", "Objects", "gloves", "glove", "warm", "winter", "hand");
        add("🧥", "Coat", "Objects", "coat", "jacket", "winter", "warm", "outerwear", "clothing");
        add("🧦", "Socks", "Objects", "socks", "sock", "footwear", "clothing", "feet");
        add("🥻", "Sweater", "Objects", "sweater", "jumper", "knitwear", "wool", "warm");
        add("🥾", "Hiking Boot", "Objects", "boots", "boot", "hiking", "walking", "outdoors", "shoe");
        add("👞", "Man's Shoe", "Objects", "shoe", "formal", "leather", "footwear");
        add("🩰", "Ballet Shoes", "Objects", "ballet", "shoes", "pointe", "dance", "pointe shoes");
        add("🩳", "Shorts", "Objects", "shorts", "trousers", "clothing", "summer", "pants");
        add("🩴", "Thong Sandal", "Objects", "sandal", "flip flop", "shoe", "summer", "beach");
        add("🩱", "One-Piece Swimsuit", "Objects", "swimsuit", "swimming", "beach", "pool", "summer");
        add("🩲", "Briefs", "Objects", "underwear", "briefs", "boxers", "clothing");
        add("🥣", "Bowl with Spoon", "Food", "bowl", "porridge", "oatmeal", "cereal", "soup", "meal");
        add("🥫", "Canned Food", "Food", "tin", "can", "canned", "soup", "beans", "preserves");
        add("🫘", "Beans", "Food", "beans", "legume", "pulse", "protein", "lentils");
        add("🫛", "Peas", "Food", "peas", "pea", "green", "vegetable", "legume");
        add("🫓", "Flatbread", "Food", "flatbread", "tortilla", "wrap", "pita", "lavash");
        add("🫠", "Melting Face", "Smileys", "melting", "melt", "hot", "dying", "embarrassed");
        add("🫧", "Bubbles", "Nature", "bubbles", "bubble", "foam", "fizzy", "sparkling", "soap");
        add("🪰", "Fly", "Animals", "fly", "insect", "pest", "buzz", "swarm");
        add("🪲", "Beetle", "Animals", "beetle", "bug", "insect", "ladybird");
        add("🪳", "Cockroach", "Animals", "cockroach", "roach", "pest", "insect");
        add("🪼", "Jellyfish", "Animals", "jellyfish", "sea", "ocean", "sting", "marine");
        add("🦠", "Microbe", "Nature", "microbe", "bacteria", "germ", "virus", "infection", "sick");
        add("🪰", "Fly", "Animals", "fly", "insect");
        add("🪐", "Ringed Planet", "Nature", "planet", "saturn", "space", "ringed", "universe", "space");
        add("🪞", "Mirror", "Household", "mirror", "reflection", "glass", "look", "selfie");
        add("🪒", "Lotion", "Household", "razor", "shave", "shaving", "blade", "grooming");
        add("🪖", "Military Helmet", "Objects", "helmet", "army", "soldier", "military", "war", "hat");
        add("🛞", "Wheel", "Travel", "wheel", "tyre", "tire", "car", "round", "rim");
        add("🟰", "Equals", "Symbols", "equals", "equal", "same", "sign", "equals sign");
        add("🔟", "Keycap 10", "Symbols", "ten", "10", "number", "perfect", "keycap");
        for (int n = 0; n <= 9; n++) add("" + n + "️⃣", "Keycap " + n, "Symbols",
                String.valueOf(n), "number", "keycap", "digit");
        add("🆄", "SQUARED ID", "Symbols", "id", "identity", "new", "square", "button");
        add("🈵", "Japanese Vacancy Button", "Symbols", "full", "occupied", "no vacancy", "sold out");
        add("📛", "Name Badge", "Office", "name badge", "badge", "id", "name", "tag", "staff");
        add("📣", "Megaphone", "Objects", "megaphone", "announcement", "announce", "loudspeaker",
                "marketing", "shout", "promote");
        add("📿", "Prayer Beads", "Objects", "prayer beads", "rosary", "beads", "religion", "worship");
        add("💹", "Chart Increasing with Yen", "Office", "chart", "yen", "economy", "market", "trading",
                "japan", "yen sign");
        add("💭", "Thought Balloon", "Smileys", "think", "thought", "idea", "wonder", "consider",
                "ponder", "hmm");
        add("🔇", "Muted Speaker", "Tech", "mute", "mute button", "silent", "sound", "off", "quiet");
        add("🔊", "Loud Speaker", "Tech", "sound", "speaker", "volume", "loud", "audio", "amplifier");
        add("🔍", "Magnifying Glass Tilted Left", "Objects", "search", "find", "look", "magnify",
                "investigate", "zoom");
        add("🔎", "Magnifying Glass Tilted Right", "Objects", "search", "find", "look", "magnify",
                "investigate");
        add("🔓", "Unlocked", "Objects", "unlock", "unlocked", "open", "secure", "access", "enter");
        add("ƒ", "Latin Small Ligature F", "Symbols", "function", "fx", "f", "formula", "method");
        add("✖️", "Multiply", "Symbols", "multiply", "times", "math", "cross", "wrong", "x");
        add("🆗", "OK Button", "Symbols", "ok", "okay", "button", "approve", "yes", "accept");

        // ---- Remaining body parts, hands and display modes -------------------------------
        add("👂", "Ear", "Nature", "ear", "ears", "hearing", "listen", "sound");
        add("👆", "Backhand Index Pointing Up", "Gestures", "finger", "point", "up", "tap", "one",
                "click", "select", "hand");
        add("👈", "Backhand Index Pointing Left", "Gestures", "left", "swipe", "back", "point", "hand");
        add("👉", "Backhand Index Pointing Right", "Gestures", "right", "point", "hand", "swipe");
        add("👇", "Backhand Index Pointing Down", "Gestures", "down", "point", "hand", "tap");
        add("🤌", "Pinched Fingers", "Gestures", "hand", "pinch", "small", "italian");
        add("🤏", "Pinching Hand", "Gestures", "pinch", "small", "tiny", "hand", "little");
        add("🤟", "Love-You Gesture", "Gestures", "love", "you", "hand", "ily", "rock");
        add("👐", "Open Hands", "Gestures", "open hands", "hug", "jazz", "hands", "spread");
        add("🙌", "Raising Hands", "Gestures", "raised hands", "celebrate", "hooray", "praise",
                "hands", "yay");
        add("🤲", "Palms Up Together", "Gestures", "hands", "prayer", "thanks", "beg");
        add("🫶", "Heart Hands", "Gestures", "heart hands", "love", "care", "hands", "sweet");
        add("💁", "Tipping Hand", "Gestures", "info", "sassy", "hand", "help", "advice");
        add("🤦", "Face Palming", "Gestures", "facepalm", "oops", "disbelief", "hand", "smh");
        add("🗣️", "Speaking Head", "People", "speak", "talk", "voice", "say", "shout", "talking");
        add("💨", "Dashing Away", "Nature", "dash", "smoke", "wind", "fast", "run", "blow", "fume");
        add("🦽", "Manual Wheelchair", "People", "wheelchair", "access", "disabled", "mobility", "chair");
        add("🦼", "Motorized Wheelchair", "People", "wheelchair", "electric", "access", "disabled",
                "mobility");
        add("🦯", "White Cane", "People", "cane", "blind", "guide", "access", "visually impaired");
        add("🛗", "Elevator", "Places", "elevator", "lift", "floor", "up", "down");
        add("🪸", "Coral", "Nature", "coral", "reef", "sea", "ocean", "underwater", "marine");
        add("🔅", "Dim Button", "Tech", "dim", "brightness", "low", "light", "screen");
        add("🔆", "Bright Button", "Tech", "bright", "brightness", "light", "screen", "high");
        add("🔼", "Upwards Button", "Symbols", "up", "above", "raise", "increase", "arrow");
        add("🔽", "Downwards Button", "Symbols", "down", "below", "lower", "decrease", "arrow");
        add("⛶", "Fullscreen", "Tech", "fullscreen", "full screen", "expand", "enlarge", "maximise");
        add("🐀", "Rat", "Animals", "rat", "rodent", "mouse", "pest");
        add("🥱", "Yawning Face", "Smileys", "yawn", "tired", "sleepy", "bored", "drowsy");
        add("😆", "Grinning Squinting Face", "Smileys", "laugh", "lol", "haha", "funny", "joy", "giggle");
        add("😁", "Beaming Face with Smiling Eyes", "Smileys", "grin", "smile", "happy", "beam");
        add("⏩", "Fast-Forward Button", "Symbols", "fast forward", "next", "skip", "forward", "speed");
        add("↘️", "Down-Right Arrow", "Symbols", "down", "right", "south east", "decrease", "diagonal");
        add("↙️", "Down-Left Arrow", "Symbols", "down", "left", "south west", "decrease", "diagonal");
        add("↖️", "Up-Left Arrow", "Symbols", "up", "left", "north west", "diagonal");
        add("↗️", "Up-Right Arrow", "Symbols", "up", "right", "north east", "diagonal");
        add("≈", "Almost Equal To", "Symbols", "almost", "approximately", "nearly", "about", "roughly",
                "close to");
        add("⏪", "Fast-Reverse Button", "Symbols", "rewind", "previous", "back", "reverse", "ago");
        add("◐", "Circle with Left Half Black", "Symbols", "half", "partial", "half circle", "semi");
        add("◔", "Circle with Upper Left Quadrant Black", "Symbols", "quarter", "partial", "fourth");
        add("✔️", "Check Mark Button", "Symbols", "check", "tick", "done", "complete", "yes", "correct");
        add("😬", "Grimacing Face", "Smileys", "grimace", "awkward", "eek", "nervous", "yikes");
        add("🙋", "Person Raising Hand", "People", "raise hand", "me", "volunteer", "question", "hand");
        add("∅", "Empty Set", "Symbols", "empty", "nothing", "none", "null", "zero", "nought", "set");
        add("≠", "Not Equal To", "Symbols", "not equal", "different", "inequality", "unequal", "vs");
        add("⏭️", "Next Track Button", "Symbols", "next", "skip", "forward", "track", "song");
        add("⏮️", "Last Track Button", "Symbols", "previous", "back", "last", "track", "song");
        add("⏯️", "Play or Pause Button", "Symbols", "play", "pause", "toggle", "start", "stop");
        add("🏟️", "Stadium", "Places", "stadium", "arena", "venue", "sport", "ground", "pitch");
        add("📢", "Loudspeaker", "Objects", "announcement", "announce", "speaker", "loudspeaker",
                "marketing", "promote", "notice");
        add("🔘", "Radio Button", "Symbols", "button", "radio", "option", "select", "choice");
        add("😑", "Expressionless Face", "Smileys", "expressionless", "blank", "unimpressed", "meh",
                "indifferent");
        add("🙅", "Person Gesturing No", "People", "no", "stop", "refuse", "reject", "forbidden",
                "disagree");
        add("🙆", "Person Folding Hands", "People", "thank", "please", "apology", "sorry", "prayer");
        add("🥎", "Softball", "Objects", "ball", "sport", "pitch", "throw", "softball");
        add("🧑‍💼", "Office Worker", "People", "office worker", "business", "employee", "professional",
                "staff", "colleague");
        add("🩶", "Grey Heart", "Hearts", "grey", "gray", "heart", "colour", "color");
        add("🩷", "Pink Heart", "Hearts", "pink", "heart", "colour", "color", "love");
        add("🩵", "Blue Heart", "Hearts", "blue", "heart", "colour", "color");
        add("🪝", "Hook", "Objects", "hook", "hanging", "catch", "fish", "pull");
        add("🪤", "Trap", "Objects", "trap", "snare", "catch", "decoy", "lure");
        add("🪶", "Feather", "Nature", "feather", "quill", "light", "bird", "soft", "write");
        add("👃", "Nose", "Nature", "nose", "smell", "sniff", "scent", "nostril");
        add("👅", "Tongue", "Nature", "tongue", "taste", "lick", "yum", "mock");
        add("👄", "Lips", "Nature", "lips", "mouth", "kiss", "smile", "taste", "speech");
        add("💄", "Lipstick", "Objects", "lipstick", "makeup", "cosmetics", "beauty", "colour",
                "colourful", "glam");
        add("💋", "Kiss Mark", "Hearts", "kiss", "lipstick", "love", "romance", "kissed");
        add("🌉", "Bridge at Night", "Places", "bridge", "night", "river", "crossing", "landmark");
        add("🛝", "Slide", "Places", "slide", "playground", "fun", "child", "park", "play");
        add("🛹", "Skateboard", "Objects", "skateboard", "skate", "skating", "board", "ride");
        add("🛷", "Sled", "Objects", "sled", "sledge", "toboggan", "snow", "winter", "slide");
        add("⛸️", "Ice Skate", "Objects", "ice skate", "skate", "skating", "ice", "winter", "rink");
        add("🏒", "Hockey Stick", "Objects", "hockey", "stick", "puck", "ice", "sport", "rink");
        add("🥅", "Goal Net", "Objects", "goal", "net", "score", "sport", "target");
        add("🏑", "Field Hockey", "Objects", "hockey", "field", "stick", "sport", "pitch");
        add("🥍", "Lacrosse", "Objects", "lacrosse", "stick", "sport", "ball");
        add("🫙", "Jar", "Food", "jar", "preserve", "jam", "pickle", "container", "pot");
        add("🫚", "Ginger Root", "Food", "ginger", "root", "spice", "tea", "asian");
        add("🫛", "Peas", "Food", "peas", "pea", "green", "vegetable", "legume");
        add("😮‍💨", "Face Exhaling", "Smileys", "puff", "sigh", "relief", "breath", "asthma", "tired");
        add("🥡", "Takeout Box", "Food", "takeout", "takeaway", "chinese", "container");
        add("🥢", "Chopsticks", "Food", "chopsticks", "asian", "chinese", "japanese", "eat");

        // ---- Paper, documents and office supplies -------------------------------------
        add("📄", "Page Facing Up", "Office", "paper", "page", "document", "doc", "sheet",
                "file", "essay", "homework", "letter", "report", "printout", "printing", "form");
        add("📃", "Page with Curl", "Office", "page", "paper", "document", "scroll", "letter");
        add("📑", "Bookmark Tabs", "Office", "bookmark", "tabs", "divider", "organise");
        add("📜", "Scroll", "Office", "scroll", "certificate", "degree", "diploma", "contract",
                "historic", "antique", "charter", "deed", "legal", "law");
        add("📰", "Newspaper", "Office", "newspaper", "news", "paper", "headlines", "press",
                "journal", "article", "headline", "gazette", "media", "story");
        add("🗞️", "Rolled Newspaper", "Office", "newspaper", "news", "paper", "press");
        add("📓", "Notebook", "Office", "notebook", "notes", "note", "pad", "diary", "journal",
                "write", "writing", "scribble");
        add("📔", "Notebook with Decorative Cover", "Office", "notebook", "notes", "note");
        add("📒", "Ledger", "Office", "ledger", "accounts", "bookkeeping", "finance", "record");
        add("📕", "Closed Book", "Office", "book", "read", "reading", "study");
        add("📗", "Green Book", "Office", "book", "read", "reading");
        add("📘", "Blue Book", "Office", "book", "read", "reading");
        add("📙", "Orange Book", "Office", "book", "read", "reading");
        add("📖", "Open Book", "Office", "book", "open", "read", "reading", "study", "story");
        add("📚", "Books", "Office", "books", "book", "read", "reading", "study", "library",
                "textbook", "revision", "novel", "literature", "coursework", "shelf");
        add("🔖", "Bookmark", "Office", "bookmark", "save", "tag", "label");
        add("🏷️", "Label", "Office", "label", "tag", "price", "sticker", "sale");
        add("✉️", "Envelope", "Office", "envelope", "mail", "letter", "post", "postcard", "email");
        add("📧", "E-mail", "Office", "email", "mail", "inbox", "message", "letter");
        add("📨", "Incoming Envelope", "Office", "mail", "letter", "incoming", "receive");
        add("📩", "Envelope with Arrow", "Office", "mail", "letter", "outgoing", "send", "contact");
        add("📤", "Outbox Tray", "Office", "send", "outgoing", "upload", "share", "deliver");
        add("📥", "Inbox Tray", "Office", "receive", "incoming", "download", "inbox", "deliver");
        add("📦", "Package", "Office", "package", "parcel", "box", "delivery", "post", "courier",
                "shipping", "crate", "carton", "goods", "cargo", "courier");
        add("📮", "Postbox", "Office", "postbox", "mail", "post", "letter", "postal");
        add("📬", "Open Mailbox with Raised Flag", "Office", "mailbox", "mail", "post", "new");
        add("📭", "Closed Mailbox with Lowered Flag", "Office", "mailbox", "mail", "post", "empty");
        add("📯", "Round Pushpin", "Office", "pushpin", "pin", "tack", "notice");
        add("📌", "Pushpin", "Office", "pushpin", "pin", "tack", "thumbtack", "notice");
        add("📍", "Round Pin", "Office", "pin", "location", "place", "here", "map");
        add("📎", "Paperclip", "Office", "paperclip", "clip", "attach", "attachment", "file");
        add("🖇️", "Linked Paperclips", "Office", "paperclip", "clip", "attach", "link");
        add("📏", "Straight Ruler", "Office", "ruler", "measure", "measurement", "length", "units");
        add("📐", "Triangular Ruler", "Office", "ruler", "set square", "measure", "geometry");
        add("✂️", "Scissors", "Office", "scissors", "cut", "cutting", "snip", "shear", "trim");
        add("🗃️", "Card File Box", "Office", "file", "files", "folder", "drawer", "archive",
                "records", "organise");
        add("🗄️", "File Cabinet", "Office", "cabinet", "file", "files", "folder", "drawer",
                "archive", "records", "storage");
        add("📁", "Folder", "Office", "folder", "file", "files", "directory", "documents");
        add("📂", "Open Folder", "Office", "folder", "file", "files", "open", "directory");
        add("🗂️", "Card Index Dividers", "Office", "folder", "dividers", "organise", "files");
        add("📋", "Clipboard", "Office", "clipboard", "copy", "paste", "notes", "list", "form",
                "checklist", "todo");
        add("🖇️", "Linked Paperclips", "Office", "paperclip", "link", "attach");

        // ---- Work, business and finance -----------------------------------------------
        add("💼", "Briefcase", "Office", "briefcase", "work", "business", "job", "office",
                "career", "employment", "suitcase", "professional", "company");
        add("💻", "Laptop", "Tech", "laptop", "computer", "pc", "mac", "work", "notebook",
                "macbook", "desktop", "code", "coding");
        add("📊", "Bar Chart", "Office", "chart", "graph", "graph", "stats", "statistics",
                "data", "results", "report", "analytics", "growth", "performance", "numbers");
        add("📈", "Chart Increasing", "Office", "chart", "graph", "growth", "increase", "up",
                "profit", "rise", "gain", "stock", "trend", "statistics");
        add("📉", "Chart Decreasing", "Office", "chart", "graph", "decrease", "down", "fall",
                "loss", "drop", "decline", "trend", "statistics");
        add("🏦", "Bank", "Places", "bank", "money", "finance", "savings", "account", "loan");
        add("🏧", "ATM Sign", "Places", "atm", "cash", "bank", "money", "machine", "withdraw");
        add("🧮", "Abacus", "Office", "abacus", "calculate", "maths", "count", "total", "sum",
                "arithmetic", "math", "numbers");
        add("💰", "Money Bag", "Office", "money", "cash", "rich", "fortune", "budget", "funds",
                "savings", "dollars", "wealth");
        add("💳", "Credit Card", "Office", "credit card", "card", "payment", "bank", "pay",
                "debit", "money", "checkout", "purchase");
        add("💠", "Diamond With a Dot", "Symbols", "diamond", "shape", "gem", "jewel");
        add("🔗", "Link", "Office", "link", "url", "chain", "connect", "website", "hyperlink");

        // ---- Cleaning, bathroom and household -------------------------------------------
        add("🚿", "Shower", "Household", "shower", "wash", "bathroom", "clean", "water");
        add("🛁", "Bathtub", "Household", "bath", "bathtub", "tub", "bathroom", "wash", "soak");
        add("🧼", "Soap", "Household", "soap", "wash", "clean", "bubble", "bathroom");
        add("🪥", "Toothbrush", "Household", "toothbrush", "teeth", "tooth", "brush", "dental");
        add("🧽", "Sponge", "Household", "sponge", "clean", "wash", "scrub");
        add("🪣", "Bucket", "Household", "bucket", "pail", "wash", "water", "clean", "mop");
        add("🚽", "Toilet", "Household", "toilet", "loo", "wc", "bathroom", "loo");
        add("🚰", "Potable Water", "Household", "water", "tap", "faucet", "drink", "sink");
        add("🧴", "Lotion Bottle", "Household", "lotion", "cream", "moisturiser", "skin", "sunscreen");
        add("🧻", "Roll of Paper", "Household", "toilet roll", "tissue", "paper", "kitchen roll");
        add("🛎️", "Bellhop Bell", "Household", "bell", "hotel", "service", "ring");
        add("🧹", "Broom", "Household", "broom", "clean", "sweep", "sweeping", "mop");

        // ---- Furniture and rooms ------------------------------------------------------
        add("🛋️", "Couch and Lamp", "Furniture", "couch", "sofa", "settee", "living room",
                "lounge", "chair", "seat", "furniture", "sit");
        add("🛏️", "Bed", "Furniture", "bed", "sleep", "bedroom", "rest", "mattress", "nap",
                "night", "sleeping", "duvet", "pillow");
        add("🪑", "Chair", "Furniture", "chair", "seat", "sit", "furniture", "stool");
        add("🚪", "Door", "Furniture", "door", "entrance", "exit", "entry", "gate");
        add("🪟", "Window", "Furniture", "window", "glass", "view", "outlook");
        add("🖼️", "Framed Picture", "Furniture", "picture", "frame", "photo", "painting", "art",
                "wall", "portrait");
        add("🏠", "House", "Places", "house", "home", "building", "property", "address", "door");
        add("🏡", "House with Garden", "Places", "house", "home", "cottage", "suburb", "property");
        add("🏘️", "Houses", "Places", "houses", "homes", "neighbourhood", "suburb", "street");
        add("🏚️", "Derelict House", "Places", "derelict", "abandoned", "old house", "ruin");
        add("🏢", "Office Building", "Places", "office", "work", "building", "business", "company");
        add("🏬", "Department Store", "Places", "shop", "store", "department", "shopping", "mall");
        add("🏣", "Post Office", "Places", "post office", "post", "mail", "stamp", "parcel");
        add("🏨", "Hotel", "Places", "hotel", "accommodation", "stay", "booking", "room", "lodge");
        add("🏥", "Hospital", "Places", "hospital", "doctor", "health", "clinic", "medical", "surgery");
        add("🏦", "Bank", "Places", "bank", "money", "finance", "loan", "savings");
        add("🏤", "Post Office", "Places", "post office", "post", "mail", "stamp");
        add("🏫", "School", "Places", "school", "education", "learn", "learnt", "study", "college");
        add("🏬", "Department Store", "Places", "shop", "store", "shopping", "retail", "mall");
        add("🏛️", "Classical Building", "Places", "courthouse", "government", "museum", "bank",
                "library", "council", "parliament", "official", "civic");

        // ---- Tools, hardware and DIY ----------------------------------------------------
        add("🪓", "Axe", "Tools", "axe", "chop", "cut", "hatchet", "split", "wood");
        add("🪚", "Carpentry Saw", "Tools", "saw", "cut", "carpentry", "wood", "cutting");
        add("🪛", "Screwdriver", "Tools", "screwdriver", "tool", "fix", "repair", "screw");
        add("🔗", "Link", "Tools", "link", "chain", "connect", "url");
        add("⛓️", "Chains", "Tools", "chain", "chains", "link", "connected", "metal");
        add("🧱", "Brick", "Tools", "brick", "bricks", "wall", "build", "build", "masonry");
        add("🧰", "Toolbox", "Tools", "toolbox", "tools", "tool", "kit", "repair", "fix");
        add("🔩", "Nut and Bolt", "Tools", "bolt", "screw", "nut", "spanner", "hardware");
        add("⚙️", "Gear", "Tools", "gear", "settings", "cog", "mechanical", "preferences",
                "options", "configure", "cogs");
        add("🧲", "Magnet", "Tools", "magnet", "attract", "magnets", "magnetic");
        add("🔗", "Link", "Tools", "link", "chain", "connect", "hyperlink");
        add("💥", "Collision", "Symbols", "boom", "explosion", "explode", "collision", "crash",
                "bang", "blast", "impact");
        add("💣", "Bomb", "Tools", "bomb", "explode", "explosion", "boom", "burst");
        add("🧨", "Firecracker", "Objects", "firecracker", "cracker", "firework", "celebrate",
                "party", "explode");
        add("🔨", "Hammer", "Tools", "hammer", "tool", "fix", "nail", "build", "hit", "strike");
        add("🔧", "Wrench", "Tools", "wrench", "spanner", "tool", "fix", "repair", "adjust");
        add("⛏️", "Pick", "Tools", "pick", "pickaxe", "mining", "dig", "tool");
        add("⚒️", "Hammer and Pick", "Tools", "hammer", "pick", "mining", "tool", "tools");
        add("🛠️", "Hammer and Wrench", "Tools", "tool", "tools", "fix", "repair", "build",
                "maintenance");
        add("🔌", "Electric Plug", "Tech", "plug", "socket", "power", "electric", "charger",
                "outlet", "usb");
        add("🔋", "Battery", "Tech", "battery", "power", "charge", "energy", "recharge", "cell");
        add("🔦", "Flashlight", "Tech", "torch", "flashlight", "light", "lamp", "beam");
        add("💡", "Light Bulb", "Tech", "idea", "light", "bulb", "inspiration", "bright", "tip");
        add("🕯️", "Candle", "Household", "candle", "light", "flame", "wax", "romantic");
        add("🪔", "Diya Lamp", "Household", "lamp", "diya", "light", "oil lamp", "candle");
        add("🧯", "Fire Extinguisher", "Tools", "fire extinguisher", "safety", "fire", "emergency");
        add("🪣", "Bucket", "Household", "bucket", "pail", "wash", "water", "clean");
        add("🧵", "Thread", "Objects", "thread", "sew", "sewing", "needle", "stitch");
        add("🪡", "Sewing Needle", "Objects", "needle", "sew", "sewing", "thread", "stitch");
        add("🧶", "Yarn", "Objects", "yarn", "knit", "wool", "knitting", "thread");
        add("🪢", "Knot", "Objects", "knot", "tie", "rope", "loop");
        add("🛡️", "Shield", "Objects", "shield", "protect", "defence", "defense", "guard", "safety");

        // ---- Weapons, safety, transport extras ------------------------------------------
        add("🗡️", "Dagger", "Objects", "dagger", "knife", "blade", "weapon");
        add("⚔️", "Crossed Swords", "Objects", "swords", "sword", "fight", "battle", "war", "duel");
        add("🔫", "Water Pistol", "Objects", "gun", "pistol", "water pistol", "toy", "shoot");
        add("🧳", "Luggage", "Travel", "luggage", "suitcase", "baggage", "travel", "case",
                "trolley case", "packing");
        add("🛂", "Passport Control", "Travel", "passport", "control", "immigration", "border",
                "customs", "travel", "visa");
        add("🛃", "Customs", "Travel", "customs", "baggage", "border", "passport", "import");
        add("🛅", "Left Luggage", "Travel", "luggage", "left luggage", "baggage", "suitcase");
        add("🚎", "Trolleybus", "Travel", "trolleybus", "bus", "bus", "transit");
        add("🏎️", "Racing Car", "Travel", "racing car", "car", "race", "f1", "speed", "fast");
        add("🚔", "Police Car", "Travel", "police car", "police", "patrol", "crime");
        add("🚘", "Oncoming Automobile", "Travel", "car", "automobile", "drive", "vehicle");
        add("🚖", "Oncoming Taxi", "Travel", "taxi", "cab", "uber", "drive");
        add("🚡", "Aerial Tramway", "Travel", "cable car", "tramway", "lift", "mountain");
        add("🚠", "Mountain Cableway", "Travel", "cable car", "ski lift", "mountain", "tramway");
        add("🛶", "Canoe", "Travel", "canoe", "kayak", "paddle", "row", "boat", "river");
        add("🧭", "Compass", "Travel", "compass", "direction", "navigation", "north", "explore",
                "map", "bearing");
        add("💺", "Seat", "Travel", "seat", "chair", "sit", "window seat", "travel");
        add("🪟", "Window", "Travel", "window", "plane window", "view", "glass");

        // ---- Weather and sky ------------------------------------------------------------
        add("☁️", "Cloud", "Nature", "cloud", "clouds", "cloudy", "sky", "overcast", "weather");
        add("🌤️", "Sun Behind Small Cloud", "Nature", "sun", "cloud", "partly sunny", "weather");
        add("⛅", "Sun Behind Cloud", "Nature", "sun", "cloud", "partly cloudy", "weather");
        add("🌥️", "Sun Behind Large Cloud", "Nature", "sun", "cloud", "cloudy", "weather");
        add("☂️", "Umbrella", "Nature", "umbrella", "rain", "parasol", "weather", "wet");
        add("🌧️", "Cloud with Rain", "Nature", "rain", "rainy", "weather", "wet", "drizzle");
        add("🌦️", "Sun Behind Rain Cloud", "Nature", "rain", "sun", "weather", "showers");
        add("🌨️", "Cloud with Snow", "Nature", "snow", "snowy", "weather", "blizzard");
        add("⛈️", "Cloud with Lightning and Rain", "Nature", "storm", "thunder", "lightning",
                "thunderstorm", "weather");
        add("🌩️", "Cloud with Lightning", "Nature", "lightning", "storm", "thunder", "weather");
        add("🌪️", "Tornado", "Nature", "tornado", "twister", "cyclone", "storm", "weather");
        add("🌫️", "Foggy", "Nature", "fog", "mist", "foggy", "haze", "weather");
        add("🌬️", "Wind Face", "Nature", "wind", "windy", "breeze", "gust", "air", "weather");
        add("🌡️", "Thermometer", "Nature", "thermometer", "temperature", "fever", "heat", "weather");
        add("🌊", "Ocean Wave", "Nature", "wave", "ocean", "sea", "water", "surf", "waves", "beach");
        add("🌍", "Globe Europe-Africa", "Nature", "earth", "world", "globe", "planet", "earth");
        add("🌅", "Sunrise", "Nature", "sunrise", "morning", "dawn", "sun");
        add("🌄", "Sunrise over Mountains", "Nature", "sunrise", "morning", "dawn", "mountain");
        add("🌇", "Sunset", "Nature", "sunset", "evening", "dusk", "sun");
        add("🌃", "Night with Stars", "Nature", "night", "stars", "city", "skyline", "evening");
        add("🌌", "Milky Way", "Nature", "galaxy", "milky way", "space", "stars", "universe", "night");
        add("⭐", "Star", "Nature", "star", "favourite", "favorite", "stars", "rating", "night");

        // ---- Plants, garden ---------------------------------------------------------------
        add("🌿", "Herb", "Nature", "herb", "herbs", "plant", "leaf", "green", "parsley", "mint");
        add("🍀", "Four Leaf Clover", "Nature", "clover", "luck", "lucky", "leaf", "irish");
        add("🌱", "Seedling", "Nature", "seedling", "sprout", "plant", "grow", "growing", "seed");
        add("🪴", "Potted Plant", "Nature", "potted plant", "plant", "houseplant", "pot", "green");
        add("🌵", "Cactus", "Nature", "cactus", "succulent", "plant", "desert", "spiky");
        add("🌾", "Sheaf of Rice", "Nature", "wheat", "rice", "grain", "harvest", "farm", "field");
        add("🌰", "Chestnut", "Food", "chestnut", "nut", "nuts");
        add("🥜", "Peanuts", "Food", "peanut", "peanuts", "nuts", "snack");
        add("🌻", "Sunflower", "Nature", "sunflower", "flower", "sun", "yellow");
        add("🌼", "Blossom", "Nature", "blossom", "flower", "flowers", "spring");
        add("🌷", "Tulip", "Nature", "tulip", "flower", "flowers", "spring", "holland");
        add("💐", "Bouquet", "Nature", "bouquet", "flowers", "flower", "gift", "romantic");
        add("🌹", "Rose", "Nature", "rose", "flower", "flowers", "love", "romantic", "red");
        add("🥀", "Wilted Flower", "Nature", "wilted", "flower", "dead flower", "sad");
        add("🌺", "Hibiscus", "Nature", "hibiscus", "flower", "flowers", "tropical");
        add("🍄", "Mushroom", "Nature", "mushroom", "fungus", "toadstool", "fungi");
        add("🌿", "Herb", "Nature", "herb", "herbs", "plant", "leaf", "green");
        add("🌲", "Evergreen Tree", "Nature", "tree", "pine", "forest", "woods", "woodland");
        add("🌳", "Tree", "Nature", "tree", "forest", "woods", "woodland", "nature");
        add("🌴", "Palm Tree", "Nature", "palm", "tree", "tropical", "beach", "coconut");
        add("🍃", "Leaf", "Nature", "leaf", "leaves", "autumn", "green", "nature");
        add("🍂", "Fallen Leaf", "Nature", "leaf", "autumn", "fall", "fallen");
        add("🍁", "Maple Leaf", "Nature", "maple", "leaf", "autumn", "canada", "fall");
        add("🪵", "Wood", "Nature", "wood", "timber", "log", "plank", "lumber");
        add("🏔️", "Snow-Capped Mountain", "Nature", "mountain", "snow", "peak", "hill");
        add("⛰️", "Mountain", "Nature", "mountain", "hill", "peak", "hiking", "summit");
        add("🌋", "Volcano", "Nature", "volcano", "eruption", "lava", "mountain");
        add("🏕️", "Camping", "Travel", "camp", "camping", "tent", "campfire", "outdoors");
        add("🏖️", "Beach with Umbrella", "Travel", "beach", "sand", "holiday", "sea", "sunbathing");
        add("🏜️", "Desert", "Nature", "desert", "sand", "sahara", "arid");
        add("🏝️", "Desert Island", "Travel", "island", "tropical", "beach", "holiday", "remote");
        add("🏞️", "National Park", "Nature", "park", "nature", "national park", "outdoors");

        // ---- Animals (extra) -------------------------------------------------------------
        add("🐬", "Dolphin", "Animals", "dolphin", "sea", "ocean", "smart", "flipper");
        add("🦈", "Shark", "Animals", "shark", "fish", "ocean", "sea", "jaws");
        add("🐳", "Whale", "Animals", "whale", "ocean", "sea", "big", "marine");
        add("🐋", "Whale", "Animals", "whale", "ocean", "sea", "marine", "huge");
        add("🐟", "Fish", "Animals", "fish", "sea", "ocean", "fishes", "swim");
        add("🦀", "Crab", "Animals", "crab", "sea", "ocean", "seafood", "shellfish");
        add("🦞", "Lobster", "Animals", "lobster", "sea", "ocean", "seafood");
        add("🦑", "Squid", "Animals", "squid", "sea", "ocean", "tentacles");
        add("🐙", "Octopus", "Animals", "octopus", "sea", "ocean", "tentacles");
        add("🦐", "Shrimp", "Animals", "shrimp", "prawns", "seafood", "ocean");
        add("🦀", "Crab", "Animals", "crab", "sea", "ocean", "crabs");
        add("🐢", "Turtle", "Animals", "turtle", "tortoise", "slow", "reptile", "shell");
        add("🐍", "Snake", "Animals", "snake", "serpent", "reptile", "dangerous");
        add("🦎", "Lizard", "Animals", "lizard", "gecko", "reptile", "iguana");
        add("🐸", "Frog", "Animals", "frog", "toad", "amphibian", "ribbit");
        add("🐝", "Honeybee", "Animals", "bee", "honey", "insect", "bees", "buzz");
        add("🐞", "Lady Beetle", "Animals", "ladybug", "ladybird", "beetle", "insect", "bug");
        add("🐜", "Ant", "Animals", "ant", "insect", "ants", "colony", "small");
        add("🦗", "Cricket", "Animals", "cricket", "insect", "jump");
        add("🕷️", "Spider", "Animals", "spider", "insect", "web", "arachnid");
        add("🐌", "Snail", "Animals", "snail", "slow", "insect", "slug", "shell");
        add("🦋", "Butterfly", "Animals", "butterfly", "insect", "beautiful", "caterpillar");
        add("🐛", "Bug", "Animals", "bug", "insect", "caterpillar", "worm", "grub");
        add("🐢", "Turtle", "Animals", "turtle", "tortoise", "slow", "shell");
        add("🦒", "Giraffe", "Animals", "giraffe", "tall", "safari", "africa");
        add("🦓", "Zebra", "Animals", "zebra", "stripes", "safari", "africa");
        add("🦘", "Kangaroo", "Animals", "kangaroo", "australia", "joey", "hop");
        add("🦥", "Sloth", "Animals", "sloth", "slow", "lazy", "amazon");
        add("🦦", "Otter", "Animals", "otter", "river", "sea", "playful");
        add("🦧", "Penguin", "Animals", "penguin", "bird", "antarctica", "cold");
        add("🐧", "Penguin", "Animals", "penguin", "bird", "cold", "ice");
        add("🦅", "Eagle", "Animals", "eagle", "bird", "america", "prey");
        add("🦆", "Duck", "Animals", "duck", "bird", "quack", "pond");
        add("🦉", "Owl", "Animals", "owl", "bird", "wise", "hoot", "night");
        add("🦢", "Swan", "Animals", "swan", "bird", "graceful", "water");
        add("🕊️", "Dove", "Nature", "dove", "bird", "peace", "peaceful", "white");
        add("🦜", "Parrot", "Animals", "parrot", "bird", "tropical", "talking");
        add("🐕", "Dog", "Animals", "dog", "doggy", "puppy", "pet", "woof");
        add("🐶", "Dog Face", "Animals", "dog", "doggy", "puppy", "pet");
        add("🐱", "Cat Face", "Animals", "cat", "kitten", "kitty", "pet", "meow");
        add("🐈", "Cat", "Animals", "cat", "kitten", "kitty", "pet", "meow");
        add("🐰", "Rabbit", "Animals", "rabbit", "bunny", "easter", "hare", "bunnies");
        add("🐹", "Hamster", "Animals", "hamster", "pet", "rodent", "cage");
        add("🐭", "Mouse", "Animals", "mouse", "mice", "rat", "rodent", "computer mouse");
        add("🐻", "Bear", "Animals", "bear", "grizzly", "animal", "teddy");
        add("🐼", "Panda", "Animals", "panda", "bear", "bamboo", "cute", "china");
        add("🐨", "Koala", "Animals", "koala", "australia", "cute", "eucalyptus");
        add("🐯", "Tiger", "Animals", "tiger", "cat", "stripes", "big cat");
        add("🦁", "Lion", "Animals", "lion", "king", "mane", "big cat", "roar");
        add("🐮", "Cow", "Animals", "cow", "moo", "cattle", "farm", "dairy");
        add("🐄", "Cow", "Animals", "cow", "moo", "cattle", "farm", "beef");
        add("🐖", "Pig", "Animals", "pig", "piggy", "oink", "farm", "pork");
        add("🐷", "Pig Face", "Animals", "pig", "piggy", "oink", "farm");
        add("🐑", "Sheep", "Animals", "sheep", "wool", "lamb", "farm", "ewe");
        add("🐐", "Goat", "Animals", "goat", "farm", "horns", "nanny");
        add("🐴", "Horse", "Animals", "horse", "pony", "stallion", "mare", "gallop");
        add("🦄", "Unicorn", "Animals", "unicorn", "magic", "fantasy", "mythical", "rainbow");
        add("🐘", "Elephant", "Animals", "elephant", "big", "trunk", "animal", "memory");
        add("🦏", "Rhinoceros", "Animals", "rhino", "rhinoceros", "animal", "big", "horn");
        add("🦍", "Gorilla", "Animals", "gorilla", "ape", "strong", "monkey");
        add("🐒", "Monkey", "Animals", "monkey", "ape", "banana", "animal");
        add("🐵", "Monkey Face", "Animals", "monkey", "ape", "banana");
        add("🦧", "Orangutan", "Animals", "orangutan", "ape", "monkey");
        add("🐔", "Chicken", "Animals", "chicken", "hen", "bird", "farm", "eggs");
        add("🐓", "Rooster", "Animals", "rooster", "chicken", "farm", "cock");
        add("🐧", "Penguin", "Animals", "penguin", "bird", "cold", "ice", "antarctica");
        add("🦜", "Parrot", "Animals", "parrot", "bird", "tropical", "talking");
        add("🐇", "Rabbit", "Animals", "rabbit", "bunny", "hare", "easter", "bunnies");
        add("🐿️", "Squirrel", "Animals", "squirrel", "chipmunk", "nut", "acorn", "animal");
        add("🦡", "Badger", "Animals", "badger", "animal", "honey badger");
        add("🦦", "Otter", "Animals", "otter", "sea otter", "river", "playful");
        add("🦨", "Skunk", "Animals", "skunk", "animal", "spray");
        add("🦔", "Hedgehog", "Animals", "hedgehog", "prickly", "animal", "spiny");
        add("🐾", "Paw Prints", "Animals", "paw", "paws", "animal", "pet", "feet");
        add("🐕‍🦺", "Service Dog", "Animals", "service dog", "guide dog", "dog", "assistance");
        add("🦮", "Guide Dog", "Animals", "guide dog", "blind", "dog", "assistance");
        add("🐕‍🦺", "Service Dog", "Animals", "service dog", "dog", "assistance", "working");

        // ---- More food -------------------------------------------------------------------
        add("🫐", "Blueberries", "Food", "blueberry", "blueberries", "berry", "fruit", "berries");
        add("🍓", "Strawberry", "Food", "strawberry", "strawberries", "berry", "fruit", "berries");
        add("🍇", "Grapes", "Food", "grapes", "grape", "fruit", "vine", "wine");
        add("🍉", "Watermelon", "Food", "watermelon", "melon", "fruit", "summer");
        add("🍑", "Peach", "Food", "peach", "peaches", "fruit");
        add("🍐", "Pear", "Food", "pear", "pears", "fruit");
        add("🥭", "Mango", "Food", "mango", "mangoes", "fruit", "tropical");
        add("🍍", "Pineapple", "Food", "pineapple", "fruit", "tropical");
        add("🥥", "Coconut", "Food", "coconut", "tropical", "palm");
        add("🍌", "Banana", "Food", "banana", "bananas", "fruit", "monkey");
        add("🍋", "Lemon", "Food", "lemon", "lemons", "citrus", "sour");
        add("🍊", "Tangerine", "Food", "orange", "tangerine", "oranges", "fruit", "citrus");
        add("🍏", "Green Apple", "Food", "apple", "apples", "fruit", "green");
        add("🍎", "Red Apple", "Food", "apple", "apples", "fruit", "red");
        add("🍅", "Tomato", "Food", "tomato", "tomatoes", "vegetable", "salad");
        add("🥑", "Avocado", "Food", "avocado", "fruit", "guacamole", "healthy");
        add("🥒", "Cucumber", "Food", "cucumber", "pickle", "vegetable", "salad");
        add("🥕", "Carrot", "Food", "carrot", "carrots", "vegetable", "rabbit");
        add("🌽", "Corn", "Food", "corn", "maize", "sweetcorn", "vegetable");
        add("🥔", "Potato", "Food", "potato", "potatoes", "vegetable", "chips");
        add("🧅", "Onion", "Food", "onion", "onions", "vegetable", "cry");
        add("🧄", "Garlic", "Food", "garlic", "vegetable", "flavour");
        add("🌶️", "Hot Pepper", "Food", "pepper", "chilli", "chili", "spicy", "hot", "spice");
        add("🥬", "Leafy Green", "Food", "lettuce", "cabbage", "greens", "vegetable", "salad");
        add("🥦", "Broccoli", "Food", "broccoli", "vegetable", "healthy", "green");
        add("🧅", "Onion", "Food", "onion", "vegetable");
        add("🥚", "Egg", "Food", "egg", "eggs", "breakfast", "protein", "omelette", "boiled");
        add("🍳", "Cooking", "Food", "cooking", "fry", "pan", "fried egg", "breakfast", "chef");
        add("🥓", "Bacon", "Food", "bacon", "breakfast", "meat", "rashers");
        add("🍗", "Poultry Leg", "Food", "chicken", "drumstick", "meat", "fried chicken", "roast");
        add("🍖", "Meat on Bone", "Food", "meat", "steak", "chicken leg", "roast", "drumstick");
        add("🥩", "Cut of Meat", "Food", "steak", "meat", "beef", "pork", "lamb", "raw meat");
        add("🍖", "Meat on Bone", "Food", "meat", "steak", "roast", "chicken", "beef");
        add("🌭", "Hot Dog", "Food", "hotdog", "hot dog", "sausage", "frankfurter", "fast food");
        add("🍔", "Hamburger", "Food", "burger", "hamburger", "beefburger", "fast food", "food");
        add("🍟", "French Fries", "Food", "fries", "chips", "chips", "fast food", "potato");
        add("🥪", "Sandwich", "Food", "sandwich", "lunch", "snack", "sub", "bread");
        add("🌮", "Taco", "Food", "taco", "tacos", "mexican", "fast food");
        add("🌯", "Burrito", "Food", "burrito", "wrap", "mexican", "fast food");
        add("🥙", "Stuffed Flatbread", "Food", "kebab", "gyro", "wrap", "pita", "doner");
        add("🧆", "Falafel", "Food", "falafel", "hummus", "vegan", "middle eastern");
        add("🥗", "Green Salad", "Food", "salad", "healthy", "lettuce", "greens", "diet");
        add("🍲", "Pot of Food", "Food", "stew", "soup", "casserole", "hotpot", "dinner");
        add("🥘", "Shallow Pan of Food", "Food", "paella", "pan", "frying pan", "skillet");
        add("🍜", "Noodles", "Food", "noodles", "noodle", "ramen", "soup", "asian");
        add("🍝", "Spaghetti", "Food", "spaghetti", "pasta", "noodles", "italian");
        add("🍛", "Curry", "Food", "curry", "rice", "indian", "spicy", "asian");
        add("🍚", "Rice", "Food", "rice", "grain", "asian", "staple");
        add("🍣", "Sushi", "Food", "sushi", "japanese", "sashimi", "raw fish");
        add("🍤", "Fried Shrimp", "Food", "shrimp", "prawn", "tempura", "fried seafood");
        add("🍥", "Fish Cake", "Food", "fish cake", "naruto", "japanese", "cake");
        add("🍙", "Rice Ball", "Food", "rice ball", "onigiri", "japanese", "lunch");
        add("🥟", "Dumpling", "Food", "dumpling", "dumplings", "gyoza", "asian");
        add("🥠", "Fortune Cookie", "Food", "fortune cookie", "cookie", "chinese", "lucky");
        add("🥡", "Takeout Box", "Food", "takeout", "takeaway", "chinese", "container");
        add("🍱", "Bento", "Food", "bento", "lunchbox", "lunch", "japanese", "japan");
        add("🥧", "Pie", "Food", "pie", "dessert", "pastry", "pumpkin pie");
        add("🥞", "Pancakes", "Food", "pancake", "pancakes", "breakfast", "flapjack", "syrup");
        add("🧇", "Waffle", "Food", "waffle", "waffles", "breakfast", "syrup");
        add("🥐", "Croissant", "Food", "croissant", "pastry", "breakfast", "french");
        add("🥖", "Baguette", "Food", "baguette", "bread", "french", "bakery");
        add("🥯", "Bagel", "Food", "bagel", "bread", "bakery", "breakfast");
        add("🍞", "Bread", "Food", "bread", "loaf", "toast", "bakery", "slice");
        add("🧀", "Cheese", "Food", "cheese", "dairy", "cheddar", "toast");
        add("🧈", "Butter", "Food", "butter", "dairy", "toast", "spread");
        add("🍰", "Shortcake", "Food", "cake", "dessert", "slice", "birthday", "icing");
        add("🧁", "Cupcake", "Food", "cupcake", "cake", "dessert", "muffin", "birthday");
        add("🎂", "Birthday Cake", "Food", "birthday", "cake", "celebrate", "candles", "party");
        add("🍪", "Cookie", "Food", "cookie", "biscuit", "dessert", "chocolate chip", "sweet");
        add("🍫", "Chocolate", "Food", "chocolate", "candy", "sweet", "bar", "cocoa");
        add("🍬", "Candy", "Food", "candy", "sweet", "sweets", "sugar");
        add("🍭", "Lollipop", "Food", "lollipop", "candy", "sweet", "sucker");
        add("🍮", "Custard", "Food", "custard", "pudding", "flan", "cream", "dessert");
        add("🍯", "Honey Pot", "Food", "honey", "sweet", "bees", "syrup");
        add("🍿", "Popcorn", "Food", "popcorn", "movie", "cinema", "snack");
        add("🍩", "Doughnut", "Food", "donut", "doughnut", "sweet", "pastry");
        add("🥜", "Peanuts", "Food", "peanut", "peanuts", "nuts", "snack");
        add("🥛", "Milk", "Food", "milk", "drink", "dairy", "calcium");
        add("🍼", "Baby Bottle", "Food", "baby", "bottle", "milk", "feeding", "infant");
        add("🫖", "Teapot", "Food", "teapot", "tea", "brew", "pot");
        add("☕", "Hot Beverage", "Food", "coffee", "tea", "drink", "brew", "espresso", "latte",
                "cafe", "caffeine", "hot drink");
        add("🍵", "Tea", "Food", "tea", "green tea", "drink", "brew", "herbal");
        add("🧃", "Juice Box", "Food", "juice", "drink", "carton", "orange juice");
        add("🥤", "Cup with Straw", "Food", "soda", "drink", "soft drink", "fizzy", "pop", "cup");
        add("🧋", "Bubble Tea", "Food", "bubble tea", "boba", "tea", "drink", "tapioca");
        add("🍺", "Beer", "Food", "beer", "drink", "alcohol", "pub", "lager", "ale");
        add("🍻", "Clinking Beer Mugs", "Food", "cheers", "beer", "toast", "drinks");
        add("🍷", "Wine Glass", "Food", "wine", "drink", "alcohol", "red wine", "vino");
        add("🥃", "Tumbler Glass", "Food", "whiskey", "whisky", "bourbon", "drink", "alcohol");
        add("🍸", "Cocktail", "Food", "cocktail", "martini", "drink", "alcohol", "mixology");
        add("🍹", "Tropical Drink", "Food", "cocktail", "drink", "tropical", "punch");
        add("🍾", "Bottle with Popping Cork", "Food", "champagne", "celebrate", "bottle", "popping");
        add("🍶", "Sake", "Food", "sake", "rice wine", "japanese", "drink");
        add("🥃", "Tumbler Glass", "Food", "whiskey", "whisky", "drink", "bourbon");
        add("🧉", "Mate", "Food", "mate", "tea", "drink", "yerba");
        add("🍽️", "Fork and Knife with Plate", "Food", "dining", "food", "dinner", "meal", "plate",
                "restaurant", "lunch");
        add("🍴", "Fork and Knife", "Food", "fork", "knife", "cutlery", "eat", "dining");
        add("🥄", "Spoon", "Food", "spoon", "cutlery", "eat", "scoop");
        add("🔪", "Kitchen Knife", "Food", "knife", "cut", "chef", "blade", "cook");
        add("🧂", "Salt", "Food", "salt", "seasoning", "season", "snack");
        add("🫗", "Pouring Liquid", "Food", "pour", "drink", "decant", "refill");
        add("🍵", "Tea", "Food", "tea", "drink", "brew");
        add("🧊", "Ice", "Nature", "ice", "cube", "cold", "icy", "chilled", "frozen");
        add("🍧", "Shaved Ice", "Food", "shaved ice", "snow cone", "ice", "dessert");
        add("🍨", "Ice Cream", "Food", "icecream", "ice cream", "dessert", "gelato", "sweet");
        add("🍦", "Soft Ice Cream", "Food", "icecream", "ice cream", "soft serve", "dessert");

        // ---- Music ------------------------------------------------------------------------
        add("🎼", "Musical Score", "Music", "score", "music", "sheet music", "classical");
        add("🎧", "Headphone", "Music", "headphones", "headphone", "music", "listen", "audio",
                "earphones", "podcast");
        add("🎷", "Saxophone", "Music", "saxophone", "sax", "jazz", "music", "instrument");
        add("🎸", "Guitar", "Music", "guitar", "music", "rock", "instrument", "strum");
        add("🎹", "Musical Keyboard", "Music", "piano", "keyboard", "music", "keys", "instrument");
        add("🎺", "Trumpet", "Music", "trumpet", "music", "brass", "instrument", "jazz");
        add("🎻", "Violin", "Music", "violin", "music", "strings", "instrument", "orchestra");
        add("🥁", "Drum", "Music", "drum", "drums", "music", "percussion", "beat", "instrument");
        add("🪘", "Long Drum", "Music", "drum", "music", "percussion", "beat");
        add("🪕", "Banjo", "Music", "banjo", "music", "instrument", "string");
        add("🎤", "Microphone", "Music", "microphone", "mic", "sing", "karaoke", "voice", "podcast");
        add("🎙️", "Studio Microphone", "Music", "microphone", "mic", "studio", "podcast", "record");
        add("🎧", "Headphone", "Music", "headphones", "music", "listen", "audio");
        add("🎚️", "Level Slider", "Tech", "slider", "volume", "mix", "fader", "level");
        add("🎛️", "Control Knobs", "Tech", "knobs", "volume", "controls", "settings", "mixer");
        add("📻", "Radio", "Tech", "radio", "broadcast", "am", "fm", "listen");
        add("📺", "Television", "Tech", "television", "tv", "screen", "channel", "watch");
        add("📻", "Radio", "Tech", "radio", "broadcast", "station");
        add("🎥", "Movie Camera", "Objects", "movie", "film", "camera", "cinema", "shoot");
        add("🎬", "Clapper Board", "Objects", "movie", "film", "action", "cinema", "production");
        add("🎭", "Performing Arts", "Objects", "theatre", "theater", "drama", "mask", "acting",
                "play", "stage");
        add("🎪", "Circus Tent", "Objects", "circus", "carnival", "tent", "big top");
        add("🎨", "Artist Palette", "Objects", "artist", "paint", "palette", "art", "design", "creative");
        add("🖌️", "Paint Brush", "Objects", "paintbrush", "brush", "paint", "art", "painting");
        add("🖍️", "Crayon", "Objects", "crayon", "crayons", "colour", "color", "draw", "art");
        add("✏️", "Pencil", "Objects", "pencil", "pen", "write", "writing", "draw", "sketch");
        add("✒️", "Black Nib", "Objects", "pen", "fountain pen", "write", "sign", "ink");
        add("🖋️", "Fountain Pen", "Objects", "pen", "fountain pen", "write", "sign", "ink");
        add("🖊️", "Pen", "Objects", "pen", "write", "ballpoint", "sign", "stationery");
        add("📝", "Memo", "Office", "memo", "note", "write", "notes", "reminder", "list");

        // ---- People and roles ---------------------------------------------------------------
        add("👨", "Man", "People", "man", "male", "guy", "gentleman", "mr");
        add("👩", "Woman", "People", "woman", "female", "lady", "girl", "ms", "mrs");
        add("👦", "Boy", "People", "boy", "son", "child", "kid", "young man");
        add("👧", "Girl", "People", "girl", "daughter", "child", "kid", "young woman");
        add("👶", "Baby", "People", "baby", "infant", "newborn", "child", "bottle");
        add("🧑", "Person", "People", "person", "adult", "someone", "human");
        add("👱", "Person Blonde Hair", "People", "person", "blonde", "hair");
        add("👮", "Police Officer", "People", "police", "officer", "cop", "law", "crime");
        add("👷", "Construction Worker", "People", "builder", "construction", "worker", "engineer",
                "labourer", "hard hat");
        add("🕵️", "Detective", "People", "detective", "spy", "investigator", "sleuth", "clue");
        add("💂", "Guard", "People", "guard", "soldier", "palace", "protect");
        add("👷", "Construction Worker", "People", "construction worker", "builder", "worker");
        add("🤴", "Prince", "People", "prince", "royal", "king", "crown", "monarchy");
        add("👸", "Princess", "People", "princess", "royal", "crown", "monarchy");
        add("👳", "Person Wearing Turban", "People", "turban", "person", "culture");
        add("👲", "Person with Headscarf", "People", "headscarf", "person", "culture");
        add("🧕", "Woman with Headscarf", "People", "hijab", "headscarf", "woman");
        add("🤵", "Person in Tuxedo", "People", "groom", "tuxedo", "wedding", "suit");
        add("👰", "Bride with Veil", "People", "bride", "wedding", "veil", "marriage");
        add("🤰", "Pregnant Woman", "People", "pregnant", "pregnancy", "baby", "mum", "mother");
        add("🤱", "Breast-Feeding", "People", "breastfeeding", "baby", "mother", "feeding", "nursing");
        add("👼", "Angel", "People", "angel", "wings", "heaven", "Christmas", "halo");
        add("🤶", "Mrs Claus", "People", "mrs claus", "santa", "christmas", "reindeer");
        add("🦸", "Superhero", "People", "superhero", "hero", "power", "comics", "cape", "marvel");
        add("🦹", "Supervillain", "People", "supervillain", "villain", "evil", "comics", "cape");
        add("🧙", "Mage", "People", "mage", "wizard", "magic", "spell", "fantasy", "witch");
        add("🧚", "Fairy", "People", "fairy", "magic", "pixie", "wings", "fantasy");
        add("🧛", "Vampire", "People", "vampire", "dracula", "halloween", "blood", "undead");
        add("🧜", "Merperson", "People", "mermaid", "merman", "merperson", "sea", "fantasy");
        add("🧝", "Elf", "People", "elf", "fantasy", "christmas", "santa");
        add("🧞", "Genie", "People", "genie", "magic", "wish", "lamp", "fantasy");
        add("🧟", "Zombie", "People", "zombie", "undead", "halloween", "scary", "brain");
        add("💆", "Person Getting Massage", "People", "massage", "spa", "relax", "facial", "wellness");
        add("💇", "Person Getting Haircut", "People", "haircut", "hair", "barber", "salon", "trim");
        add("🧖", "Person in Steamy Room", "People", "sauna", "steam", "spa", "relax", "wellness");
        add("🧍", "Person Standing", "People", "stand", "standing", "person", "upright");
        add("🧎", "Person Kneeling", "People", "kneel", "kneeling", "person", "submit");
        add("🕴️", "Person in Suit Levitating", "People", "suit", "levitate", "business", "hover");
        add("👯", "People with Bunny Ears", "People", "twins", "partners", "friends", "pair");
        add("🧑‍🚀", "Astronaut", "People", "astronaut", "space", "nasa", "rocket", "moon", "cosmic");
        add("🧑‍🍳", "Cook", "People", "cook", "chef", "kitchen", "bake", "cooking", "chef");
        add("🧑‍🌾", "Farmer", "People", "farmer", "farm", "crops", "harvest", "agriculture");
        add("🧑‍🏫", "Teacher", "People", "teacher", "school", "education", "teach", "lesson", "tutor");
        add("🧑‍⚕️", "Health Worker", "People", "doctor", "nurse", "health", "medical", "clinician");
        add("🧑‍🎤", "Singer", "People", "singer", "musician", "song", "vocalist", "band");
        add("🧑‍🎨", "Artist", "People", "artist", "paint", "designer", "creative", "art");
        add("🧑‍🔧", "Mechanic", "People", "mechanic", "engineer", "repair", "tools", "garage");
        add("🧑‍💻", "Technologist", "People", "developer", "coder", "programmer", "tech", "software");
        add("🧑‍🔬", "Scientist", "People", "scientist", "research", "lab", "study", "chemistry");
        add("🧑‍🚒", "Firefighter", "People", "firefighter", "fire", "rescue", "emergency", "brigade");
        add("🧑‍✈️", "Pilot", "People", "pilot", "plane", "aviation", "flight", "captain");
        add("🧑‍🎓", "Graduate", "People", "graduate", "graduation", "degree", "cap", "university");

        // ---- Shapes, colours and symbols ------------------------------------------------------
        add("🔴", "Red Circle", "Symbols", "red", "circle", "red circle", "colour", "color");
        add("🟠", "Orange Circle", "Symbols", "orange", "circle", "colour", "color");
        add("🟡", "Yellow Circle", "Symbols", "yellow", "circle", "colour", "color", "caution");
        add("🟢", "Green Circle", "Symbols", "green", "circle", "colour", "color", "go");
        add("🔵", "Blue Circle", "Symbols", "blue", "circle", "colour", "color");
        add("🟣", "Purple Circle", "Symbols", "purple", "circle", "colour", "color");
        add("⚫", "Black Circle", "Symbols", "black", "circle", "colour", "color");
        add("⚪", "White Circle", "Symbols", "white", "circle", "colour", "color", "empty");
        add("🟤", "Brown Circle", "Symbols", "brown", "circle", "colour", "color");
        add("🔺", "Red Triangle", "Symbols", "triangle", "red", "shape", "up", "delta");
        add("🔻", "Downwards Triangle", "Symbols", "triangle", "down", "shape", "delta");
        add("🔸", "Small Orange Diamond", "Symbols", "diamond", "shape", "orange", "small");
        add("🔹", "Small Blue Diamond", "Symbols", "diamond", "shape", "blue", "small");
        add("🔶", "Large Orange Diamond", "Symbols", "diamond", "shape", "orange", "large");
        add("🔷", "Large Blue Diamond", "Symbols", "diamond", "shape", "blue", "large");
        add("🔳", "White Square Button", "Symbols", "square", "white", "box", "shape");
        add("🔲", "Black Square Button", "Symbols", "square", "black", "box", "shape");
        add("◾", "Black Small Square", "Symbols", "square", "black", "small", "shape");
        add("◽", "White Small Square", "Symbols", "square", "white", "small", "shape");
        add("◼️", "Black Medium Square", "Symbols", "square", "black", "medium", "shape");
        add("◻️", "White Medium Square", "Symbols", "square", "white", "medium", "shape");
        add("⬛", "Black Large Square", "Symbols", "black", "square", "large", "shape", "stop");
        add("⬜", "White Large Square", "Symbols", "white", "square", "large", "shape");
        add("🟥", "Red Square", "Symbols", "red", "square", "shape");
        add("🟧", "Orange Square", "Symbols", "orange", "square", "shape");
        add("🟨", "Yellow Square", "Symbols", "yellow", "square", "shape");
        add("🟩", "Green Square", "Symbols", "green", "square", "shape");
        add("🟦", "Blue Square", "Symbols", "blue", "square", "shape");
        add("🟪", "Purple Square", "Symbols", "purple", "square", "shape");
        add("🟫", "Brown Square", "Symbols", "brown", "square", "shape");
        add("❎", "Negative Squared Cross Mark", "Symbols", "no", "wrong", "cross", "cancel", "x");
        add("➰", "Curly Loop", "Symbols", "loop", "curly", "wave", "squiggle", "curl");
        add("➿", "Double Curly Loop", "Symbols", "loop", "curly", "wave", "curl");
        add("〽️", "Part Alternation Mark", "Symbols", "part", "alternation", "m", "mark");
        add("✳️", "Eight Spoked Asterisk", "Symbols", "asterisk", "star", "eight", "spoke");
        add("✴️", "Eight Pointed Star", "Symbols", "star", "eight", "pointed", "sparkle");
        add("❇️", "Sparkle", "Symbols", "sparkle", "shine", "star", "glitter");
        add("‼️", "Double Exclamation Mark", "Symbols", "exclamation", "important", "urgent", "wow");
        add("⁉️", "Exclamation Question Mark", "Symbols", "exclamation", "question", "surprise",
                "what", "wow");
        add("❗", "Red Exclamation Mark", "Symbols", "exclamation", "important", "alert", "urgent");
        add("❕", "White Exclamation Mark", "Symbols", "exclamation", "important", "alert");
        add("❓", "Question Mark", "Symbols", "question", "what", "why", "ask", "query", "doubt");
        add("❔", "White Question Mark", "Symbols", "question", "what", "ask", "doubt");
        add("⭕", "Heavy Circle Outline", "Symbols", "circle", "outline", "correct", "right");
        add("🚸", "Children Crossing", "Symbols", "school", "crossing", "children", "road", "warning");
        add("🔞", "No One Under Eighteen", "Symbols", "adult", "18", "restricted", "age");
        add("♾️", "Infinity", "Symbols", "infinity", "forever", "endless", "unlimited", "eternal");
        add("⚛️", "Atom Symbol", "Symbols", "atom", "science", "physics", "nuclear", "radioactive");
        add("☢️", "Radioactive", "Symbols", "radioactive", "danger", "hazard", "nuclear", "toxic");
        add("☣️", "Biohazard", "Symbols", "biohazard", "danger", "hazard", "toxic", "virus");
        add("⚠️", "Warning", "Symbols", "warning", "caution", "alert", "danger", "beware");
        add("🔱", "Trident Emblem", "Symbols", "trident", "emblem", "poseidon", "spear");
        add("⚜️", "Fleur-de-lis", "Symbols", "fleur", "lily", "emblem", "scouts", "heraldry");
        add("🔰", "Japanese Symbol for Beginner", "Symbols", "beginner", "novice", "easy", "new");
        add("⭕", "Heavy Circle Outline", "Symbols", "circle", "outline", "correct");
        add("♻️", "Recycling Symbol", "Symbols", "recycle", "recycling", "green", "environment",
                "sustainability", "reuse");
        add("🆗", "OK Button", "Symbols", "ok", "okay", "button", "approve", "yes", "button");
        add("🆕", "NEW Button", "Symbols", "new", "button", "fresh", "latest");
        add("🆓", "FREE Button", "Symbols", "free", "button", "no charge", "complimentary");
        add("🆒", "UP! Button", "Symbols", "up", "button", "upgrade", "high");
        add("🆙", "UP! Button", "Symbols", "up", "button", "upgrade", "high");
        add("🔠", "Input Latin Uppercase", "Symbols", "uppercase", "caps", "capital", "letters");
        add("🔡", "Input Latin Lowercase", "Symbols", "lowercase", "letters", "lower", "letters");
        add("🔢", "Input Numbers", "Symbols", "numbers", "digits", "numeric", "123");
        add("🔣", "Input Symbols", "Symbols", "symbols", "characters", "special");
        add("🔤", "Input Latin Letters", "Symbols", "letters", "abc", "alphabet");
        add("🔠", "Input Latin Uppercase", "Symbols", "uppercase", "caps", "abc");

        // ---- Extra flags, sports and misc -------------------------------------------------
        add("🏁", "Checkered Flag", "Objects", "finish", "racing", "race", "checkered", "winner");
        add("🚩", "Triangular Flag", "Objects", "flag", "red flag", "marker", "warning");
        add("🏴", "Black Flag", "Objects", "flag", "black");
        add("🏳️", "White Flag", "Objects", "flag", "white", "surrender", "white flag");
        add("🏳️‍🌈", "Rainbow Flag", "Objects", "pride", "rainbow", "lgbt", "lgbtq", "flag");
        add("🏴‍☠️", "Pirate Flag", "Objects", "pirate", "flag", "skull and crossbones");
        add("🎌", "Crossed Flags", "Objects", "flags", "japan", "japanese");
        add("🏅", "Sports Medal", "Objects", "medal", "gold", "win", "winner", "award", "prize");
        add("🎖️", "Military Medal", "Objects", "medal", "award", "honour", "honor", "service");
        add("🥇", "Gold Medal", "Objects", "gold", "medal", "winner", "win", "first", "champion");
        add("🥈", "Silver Medal", "Objects", "silver", "medal", "second", "runner up");
        add("🥉", "Bronze Medal", "Objects", "bronze", "medal", "third");
        add("🏅", "Sports Medal", "Objects", "medal", "award", "prize", "win");
        add("🎗️", "Reminder Ribbon", "Objects", "ribbon", "awareness", "reminder", "support");
        add("🎟️", "Admission Tickets", "Objects", "ticket", "tickets", "admission", "event");
        add("🎫", "Ticket", "Objects", "ticket", "cinema", "event", "pass", "boarding pass");
        add("🎁", "Wrapped Gift", "Objects", "gift", "present", "birthday", "christmas", "surprise");
        add("🎀", "Ribbon", "Objects", "ribbon", "bow", "gift", "decoration");
        add("🎈", "Balloon", "Objects", "balloon", "party", "birthday", "floating");
        add("🎉", "Party Popper", "Objects", "party", "celebrate", "congrats", "congratulations",
                "celebration", "tada");
        add("🎊", "Confetti Ball", "Objects", "confetti", "party", "celebrate", "celebration");
        add("🎆", "Fireworks", "Objects", "fireworks", "firework", "celebrate", "new year", "july");
        add("🎇", "Sparkler", "Objects", "sparkler", "firework", "celebrate", "sparkle");
        add("🎆", "Fireworks", "Objects", "fireworks", "celebrate");
        add("🧧", "Red Envelope", "Objects", "red envelope", "hongbao", "chinese", "lucky", "money");
        add("🪅", "Pinata", "Objects", "pinata", "party", "candy", "mexico");
        add("🪆", "Nesting Dolls", "Objects", "nesting dolls", "matryoshka", "russian", "dolls");
        add("🪩", "Mirror Ball", "Objects", "disco", "dance", "party", "ball");
        add("🎭", "Performing Arts", "Objects", "theatre", "theater", "mask", "drama", "acting");
        add("🎪", "Circus Tent", "Objects", "circus", "carnival", "big top");
        add("🃏", "Joker", "Objects", "joker", "card", "cards", "wild card", "game");
        add("🎴", "Flower Cards", "Objects", "cards", "hanafuda", "japanese", "game");
        add("🀄", "Mahjong", "Objects", "mahjong", "tiles", "game");
        add("🎲", "Game Die", "Objects", "dice", "die", "game", "luck", "roll", "random");
        add("♟️", "Chess Pawn", "Objects", "chess", "pawn", "game", "board game", "strategy");
        add("🎯", "Bullseye", "Objects", "target", "goal", "aim", "bullseye", "dart", "precise");
        add("🎳", "Bowling", "Objects", "bowling", "bowl", "pins", "strike", "sport");
        add("🪀", "Carousel Horse", "Objects", "carousel", "merry go round", "fair", "horse");
        add("🪁", "Kite", "Objects", "kite", "wind", "flying", "fly");
        add("🪃", "Boomerang", "Objects", "boomerang", "throw", "return", "australia");
        add("🪄", "Magic Wand", "Objects", "magic", "wand", "spell", "wizard", "abracadabra");
        add("🪄", "Magic Wand", "Objects", "magic wand", "magic");
        add("🧩", "Puzzle Piece", "Objects", "puzzle", "jigsaw", "piece", "solve");
        add("🪀", "Carousel Horse", "Objects", "carousel", "merry go round", "fair");

        // ---- Travel and places extras ----------------------------------------------------
        add("🗼", "Tokyo Tower", "Places", "tokyo", "tower", "japan", "landmark");
        add("🗽", "Statue of Liberty", "Places", "liberty", "statue", "new york", "america", "landmark");
        add("🗿", "Moai", "Places", "moai", "statue", "easter island", "stone", "monument");
        add("🗼", "Tokyo Tower", "Places", "tokyo", "tower", "japan");
        add("🏰", "Castle", "Places", "castle", "fortress", "palace", "royal", "medieval");
        add("🏯", "Japanese Castle", "Places", "castle", "japan", "japanese", "shrine");
        add("⛩️", "Shrine", "Places", "shrine", "shinto", "japan", "japanese", "torii");
        add("🕌", "Mosque", "Places", "mosque", "muslim", "islam", "islamic", "religion");
        add("🕍", "Synagogue", "Places", "synagogue", "jewish", "judaism", "religion");
        add("🛕", "Hindu Temple", "Places", "temple", "hindu", "religion", "india");
        add("⛪", "Church", "Places", "church", "christian", "religion", "worship", "cathedral");
        add("🕋", "Kaaba", "Places", "kaaba", "mecca", "hajj", "pilgrimage", "islam");
        add("⚱️", "Funeral Urn", "Places", "funeral", "urn", "memorial", "grave");
        add("🪦", "Headstone", "Places", "headstone", "grave", "cemetery", "memorial", "tomb");
        add("🗳️", "Ballot Box with Ballot", "Places", "ballot", "vote", "voting", "election", "poll");
        add("🗺️", "World Map", "Places", "map", "world", "globe", "atlas", "directions", "travel");
        add("🗾", "Map of Japan", "Places", "japan", "map");
        add("🏞️", "National Park", "Places", "park", "national park", "nature", "outdoors");
        add("🏕️", "Camping", "Travel", "camping", "camp", "tent", "campfire", "outdoors", "wilderness");
        add("🛖", "Hut", "Places", "hut", "cabin", "shack", "wood", "shelter");
        add("🏠", "House", "Places", "house", "home", "property", "building", "door", "address");
        add("🚗", "Automobile", "Travel", "car", "drive", "driving", "vehicle", "automobile", "motor");
        add("🚕", "Taxi", "Travel", "taxi", "cab", "uber", "hire car");
        add("🚌", "Bus", "Travel", "bus", "coach", "transit", "public transport");
        add("🚐", "Minibus", "Travel", "minibus", "van", "shuttle", "bus");
        add("🚚", "Delivery Truck", "Travel", "truck", "delivery", "lorry", "haulage", "van");
        add("🚛", "Articulated Lorry", "Travel", "truck", "lorry", "semi", "articulated");
        add("🚜", "Tractor", "Travel", "tractor", "farm", "plough", "plow", "harvest");
        add("🛵", "Motor Scooter", "Travel", "scooter", "vespa", "moped");
        add("🏍️", "Motorcycle", "Travel", "motorcycle", "motorbike", "bike", "moto");
        add("🛴", "Kick Scooter", "Travel", "scooter", "kick scooter", "push");
        add("🚲", "Bicycle", "Travel", "bicycle", "bike", "cycling", "cycle", "ride", "pedal");
        add("🚴", "Bicyclist", "Travel", "cyclist", "bike", "cycling", "ride", "pedal");
        add("🚂", "Locomotive", "Travel", "train", "steam", "locomotive", "railway");
        add("🚆", "Train", "Travel", "train", "rail", "railway", "travel");
        add("🚇", "Metro", "Travel", "metro", "subway", "underground", "tube", "city");
        add("🚊", "Tram", "Travel", "tram", "streetcar", "light rail", "transit");
        add("🚉", "Station", "Travel", "station", "platform", "train station", "transport");
        add("✈️", "Airplane", "Travel", "plane", "airplane", "flight", "fly", "travel", "air");
        add("🛩️", "Small Airplane", "Travel", "plane", "airplane", "flight", "jet");
        add("🛫", "Airplane Departure", "Travel", "departure", "takeoff", "flight", "flying", "airport");
        add("🛬", "Airplane Arrival", "Travel", "arrival", "landing", "flight", "flying", "airport");
        add("🚀", "Rocket", "Travel", "rocket", "space", "launch", "nasa", "spacex", "moon");
        add("🛸", "Flying Saucer", "Travel", "ufo", "saucer", "alien", "space", "flying");
        add("🚁", "Helicopter", "Travel", "helicopter", "chopper", "heli", "flight");
        add("⛵", "Sailboat", "Travel", "sailboat", "boat", "sail", "yacht", "sea");
        add("🚤", "Speedboat", "Travel", "speedboat", "boat", "speed", "sea");
        add("⛴️", "Ferry", "Travel", "ferry", "boat", "crossing", "sea", "island");
        add("🚢", "Ship", "Travel", "ship", "boat", "cruise", "sea", "vessel");
        add("⚓", "Anchor", "Travel", "anchor", "ship", "sail", "harbour", "harbor", "moor");
        add("⛽", "Fuel Pump", "Travel", "fuel", "petrol", "gas", "pump", "diesel", "forecourt");
        add("🚧", "Construction", "Travel", "construction", "roadwork", "barrier", "works");
        add("🚦", "Traffic Light", "Travel", "traffic", "traffic light", "lights", "signal", "junction");
        add("🛑", "Stop Sign", "Travel", "stop", "stop sign", "halt", "road");
        add("🅿️", "Parking", "Travel", "parking", "park", "car park", "p");
        add("🚨", "Police Car Light", "Travel", "siren", "police", "alert", "emergency");
        add("🚑", "Ambulance", "Travel", "ambulance", "medical", "emergency", "paramedic", "hospital");
        add("🚒", "Fire Engine", "Travel", "fire engine", "firetruck", "fire", "emergency");
        add("🛟", "Lifebuoy", "Travel", "lifebuoy", "life ring", "rescue", "safety", "help");
        add("🆘", "SOS Button", "Travel", "sos", "help", "emergency", "distress");

        buildIndex();
    }

    private static void add(String emoji, String name, String category, String... keywords) {
        EmojiEntry entry = new EmojiEntry(emoji, name, category, keywords);
        emojis.add(entry);
        categoryMap.computeIfAbsent(category, k -> new ArrayList<>()).add(entry);
        byEmoji.put(emoji, entry);
    }

    /** Adds extra trigger words to an already-registered emoji. Unknown emoji is ignored. */
    public static void addSynonyms(String emoji, String... words) {
        EmojiEntry entry = byEmoji.get(emoji);
        if (entry == null) return;
        for (String w : words) {
            if (w == null) continue;
            String kw = w.trim().toLowerCase(Locale.ROOT);
            if (kw.isEmpty() || entry.keywords.contains(kw)) continue;
            entry.keywords.add(kw);
        }
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

    /**
     * All emojis worth showing for a completed word, best match first.
     * Only fires on whole words (the caller triggers it after a space) so a partial word
     * such as "hot" while typing "hotel" never matches anything.
     */
    public static List<EmojiEntry> getEmojiForWord(String word) {
        return getEmojiForWord(word, MAX_WORD_MATCHES);
    }

    public static List<EmojiEntry> getEmojiForWord(String word, int limit) {
        if (word == null) return Collections.emptyList();
        String w = normalize(word);
        if (w.isEmpty()) return Collections.emptyList();
        Map<String, EmojiEntry> picked = new LinkedHashMap<>();

        // Exact keyword / full-name matches win, in table order.
        for (String variant : variants(w)) collectExact(variant, picked);
        // Then weaker matches from the individual words of multi-word names/keywords.
        for (String variant : variants(w)) collectToken(variant, picked);

        List<EmojiEntry> out = new ArrayList<>(picked.values());
        if (out.size() > limit) return new ArrayList<>(out.subList(0, limit));
        return out;
    }

    private static void collectExact(String variant, Map<String, EmojiEntry> picked) {
        List<EmojiEntry> hits = exactIndex.get(variant);
        if (hits == null) return;
        for (EmojiEntry e : hits) picked.putIfAbsent(e.emoji, e);
    }

    private static void collectToken(String variant, Map<String, EmojiEntry> picked) {
        List<EmojiEntry> hits = tokenIndex.get(variant);
        if (hits == null) return;
        for (EmojiEntry e : hits) picked.putIfAbsent(e.emoji, e);
    }

    /**
     * Case/punctuation/plural variants of the typed word, most literal first.
     * "books" also matches a "book" keyword, "parking" also matches "park".
     */
    private static List<String> variants(String w) {
        Set<String> set = new LinkedHashSet<>();
        set.add(w);
        addIf(set, singular(w));
        if (w.length() > 4 && w.endsWith("ing")) {
            // studying -> study, cooking -> cook, running -> run
            addIf(set, w.substring(0, w.length() - 3));
            addIf(set, undouble(w.substring(0, w.length() - 3)));
            addIf(set, w.substring(0, w.length() - 3) + "e");
        }
        if (w.length() > 3 && w.endsWith("ed")) {
            addIf(set, w.substring(0, w.length() - 2));
            addIf(set, undouble(w.substring(0, w.length() - 2)));
        }
        if (w.length() > 4 && w.endsWith("ly")) {
            // happily -> happy, quickly -> quick, nicely -> nice, really -> real
            addIf(set, w.substring(0, w.length() - 2));
            addIf(set, w.substring(0, w.length() - 1));
            addIf(set, undouble(w.substring(0, w.length() - 1)));
        }
        if (w.length() > 2 && (w.endsWith("s") || w.endsWith("d"))) {
            addIf(set, w.substring(0, w.length() - 1));
        }
        return new ArrayList<>(set);
    }

    private static void addIf(Set<String> set, String value) {
        if (value != null && value.length() >= 2) set.add(value);
    }

    /** "running" -> "run", "planned" -> "plan" (drops a doubled final consonant). */
    private static String undouble(String stem) {
        int n = stem.length();
        if (n >= 3 && stem.charAt(n - 1) == stem.charAt(n - 2) && !isVowel(stem.charAt(n - 1))) {
            return stem.substring(0, n - 1);
        }
        return null;
    }

    private static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    /** Very small, conservative singulariser - avoids mangling real words. */
    private static String singular(String w) {
        if (w.length() < 4) return null;
        if (w.endsWith("ies")) return w.substring(0, w.length() - 3) + "y";
        if (w.endsWith("ves")) return w.substring(0, w.length() - 3) + "f";
        if (w.endsWith("ses") || w.endsWith("xes") || w.endsWith("zes")
                || w.endsWith("ches") || w.endsWith("shes")) {
            return w.substring(0, w.length() - 2);
        }
        if (w.endsWith("s") && !w.endsWith("ss") && !w.endsWith("us") && !w.endsWith("is")) {
            return w.substring(0, w.length() - 1);
        }
        return null;
    }

    private static String normalize(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        String lower = s.trim().toLowerCase(Locale.ROOT);
        for (int i = 0; i < lower.length(); i++) {
            char c = lower.charAt(i);
            if (c >= 'a' && c <= 'z') sb.append(c);
            else if (c >= '0' && c <= '9') sb.append(c);
            else if (c == ' ' || c == '-' || c == '_' || c == '\'') sb.append(' ');
        }
        // Only the first word matters: the keyboard matches one word at a time.
        String first = sb.toString().trim().split("\\s+")[0];
        return first;
    }

    /** Called once after every add()/synonym pass to build the lookup indexes. */
    static void buildIndex() {
        EmojiSynonyms.apply();
        exactIndex.clear();
        tokenIndex.clear();
        for (EmojiEntry e : emojis) {
            indexPhrase(e, e.name.toLowerCase(Locale.ROOT), true);
            for (String kw : e.keywords) indexPhrase(e, kw, false);
        }
    }

    /**
     * Indexes one phrase. {@code isName} phrases (official emoji names) contribute only their
     * meaningful words, so "Face with Tears of Joy" does not turn "face" into a suggestion.
     * Explicit keywords are indexed whole and are never filtered.
     */
    private static void indexPhrase(EmojiEntry e, String phrase, boolean isName) {
        String p = phrase.trim().toLowerCase(Locale.ROOT);
        if (p.isEmpty()) return;
        if (isName && STOP_WORDS.contains(p)) return;
        // Whole phrase -> best kind of match
        List<EmojiEntry> exact = exactIndex.get(p);
        if (exact == null || !exact.contains(e)) exactIndex.computeIfAbsent(p, k -> new ArrayList<>()).add(e);
        // Individual significant words -> weaker match, so "fast food" still fires on "food"
        for (String token : p.split("\\s+")) {
            if (token.length() < 2) continue;
            if (isName && STOP_WORDS.contains(token)) continue;
            if (token.equals(p)) continue;
            List<EmojiEntry> list = tokenIndex.computeIfAbsent(token, k -> new ArrayList<>());
            if (!list.contains(e)) list.add(e);
        }
    }
}
