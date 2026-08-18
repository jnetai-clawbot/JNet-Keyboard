package com.jnetai.keyboard.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.jnetai.keyboard.clipboard.ClipboardManager;
import com.jnetai.keyboard.diagnostics.Diagnostics;
import com.jnetai.keyboard.emoji.EmojiDatabase;
import com.jnetai.keyboard.remapping.KeyRemapping;
import com.jnetai.keyboard.symbols.SymbolDatabase;
import com.jnetai.keyboard.translation.TranslationManager;
import com.jnetai.keyboard.unicode.UnicodeStyleDatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private static final String[][] LANGS = {
            {"auto", "Auto Detect"},
            {"en", "England / English"},
            {"fr", "France / French"},
            {"de", "Germany / German"},
            {"es", "Spain / Spanish"},
            {"it", "Italy / Italian"},
            {"pt", "Portugal / Portuguese"},
            {"nl", "Netherlands / Dutch"},
            {"ru", "Russia / Russian"},
            {"uk", "Ukraine / Ukrainian"},
            {"pl", "Poland / Polish"},
            {"cs", "Czechia / Czech"},
            {"sk", "Slovakia / Slovak"},
            {"ro", "Romania / Romanian"},
            {"hu", "Hungary / Hungarian"},
            {"bg", "Bulgaria / Bulgarian"},
            {"sr", "Serbia / Serbian"},
            {"hr", "Croatia / Croatian"},
            {"sl", "Slovenia / Slovenian"},
            {"el", "Greece / Greek"},
            {"tr", "Turkey / Turkish"},
            {"ar", "Arabia / Arabic"},
            {"he", "Israel / Hebrew"},
            {"fa", "Iran / Persian"},
            {"hi", "India / Hindi"},
            {"bn", "Bangladesh / Bengali"},
            {"ta", "Tamil Nadu / Tamil"},
            {"te", "Telugu"},
            {"kn", "Kannada"},
            {"ml", "Kerala / Malayalam"},
            {"ur", "Pakistan / Urdu"},
            {"pa", "Punjabi"},
            {"gu", "Gujarati"},
            {"th", "Thailand / Thai"},
            {"vi", "Vietnam / Vietnamese"},
            {"id", "Indonesia / Indonesian"},
            {"ms", "Malaysia / Malay"},
            {"tl", "Philippines / Tagalog"},
            {"ja", "Japan / Japanese"},
            {"zh", "China / Chinese"},
            {"ko", "South Korea / Korean"},
            {"af", "South Africa / Afrikaans"},
            {"sq", "Albania / Albanian"},
            {"az", "Azerbaijan / Azerbaijani"},
            {"be", "Belarus / Belarusian"},
            {"bs", "Bosnia / Bosnian"},
            {"ca", "Catalonia / Catalan"},
            {"ceb", "Cebuano"},
            {"cy", "Wales / Welsh"},
            {"da", "Denmark / Danish"},
            {"eo", "Esperanto"},
            {"et", "Estonia / Estonian"},
            {"eu", "Basque Country / Basque"},
            {"fi", "Finland / Finnish"},
            {"fy", "Friesland / Frisian"},
            {"ga", "Ireland / Irish"},
            {"gd", "Scotland / Scottish Gaelic"},
            {"gl", "Galicia / Galician"},
            {"ha", "Hausa"},
            {"haw", "Hawaii / Hawaiian"},
            {"hmn", "Hmong"},
            {"ht", "Haiti / Haitian Creole"},
            {"hy", "Armenia / Armenian"},
            {"ig", "Igbo"},
            {"is", "Iceland / Icelandic"},
            {"jw", "Java / Javanese"},
            {"ka", "Georgia / Georgian"},
            {"kk", "Kazakhstan / Kazakh"},
            {"km", "Cambodia / Khmer"},
            {"ku", "Kurdistan / Kurdish"},
            {"ky", "Kyrgyzstan / Kyrgyz"},
            {"la", "Latin"},
            {"lb", "Luxembourg / Luxembourgish"},
            {"lo", "Laos / Lao"},
            {"lt", "Lithuania / Lithuanian"},
            {"lv", "Latvia / Latvian"},
            {"mg", "Madagascar / Malagasy"},
            {"mi", "New Zealand / Maori"},
            {"mk", "North Macedonia / Macedonian"},
            {"mn", "Mongolia / Mongolian"},
            {"mr", "Maharashtra / Marathi"},
            {"mt", "Malta / Maltese"},
            {"my", "Myanmar / Burmese"},
            {"ne", "Nepal / Nepali"},
            {"no", "Norway / Norwegian"},
            {"ny", "Chichewa"},
            {"or", "Odisha / Odia"},
            {"ps", "Afghanistan / Pashto"},
            {"si", "Sri Lanka / Sinhala"},
            {"sm", "Samoa / Samoan"},
            {"sn", "Zimbabwe / Shona"},
            {"so", "Somalia / Somali"},
            {"st", "Lesotho / Sesotho"},
            {"su", "Sundanese"},
            {"sv", "Sweden / Swedish"},
            {"sw", "East Africa / Swahili"},
            {"tg", "Tajikistan / Tajik"},
            {"tk", "Turkmenistan / Turkmen"},
            {"tt", "Tatarstan / Tatar"},
            {"ug", "Uyghur"},
            {"uz", "Uzbekistan / Uzbek"},
            {"xh", "South Africa / Xhosa"},
            {"yi", "Yiddish"},
            {"yo", "Nigeria / Yoruba"},
            {"zu", "South Africa / Zulu"},
    };
    private KeyboardSettings settings;
    private LinearLayout contentLayout;
    private String currentSection = "general";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = KeyboardSettings.getInstance(this);

        ScrollView scrollView = new ScrollView(this);
        contentLayout = new LinearLayout(this);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(32, 16, 32, 16);
        scrollView.addView(contentLayout);
        setContentView(scrollView);

        String openFragment = getIntent().getStringExtra("open_fragment");
        if (openFragment != null) {
            currentSection = openFragment;
        }

        buildUI();
    }

    private void buildUI() {
        contentLayout.removeAllViews();
        addSectionHeader("J~Net Keyboard Settings");
        addNavButton("General", "general");
        addNavButton("Unicode Styles", "unicode");
        addNavButton("Translation", "translation");
        addNavButton("Appearance", "appearance");
        addNavButton("Key Remapping", "remapping");
        addNavButton("Clipboard Manager", "clipboard");
        addNavButton("Emoji Search", "emoji");
        addNavButton("Diagnostics", "diagnostics");
        addNavButton("About", "about");

        switch (currentSection) {
            case "general": buildGeneral(); break;
            case "unicode": buildUnicode(); break;
            case "translation": buildTranslation(); break;
            case "appearance": buildAppearance(); break;
            case "remapping": buildRemapping(); break;
            case "clipboard": buildClipboard(); break;
            case "emoji": buildEmojiSearch(); break;
            case "diagnostics": buildDiagnostics(); break;
            case "about": buildAbout(); break;
        }
    }

    private void addSectionHeader(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(22);
        tv.setTextColor(0xFF8AB4F8);
        tv.setPadding(0, 16, 0, 16);
        contentLayout.addView(tv);
    }

    private void addNavButton(String label, String section) {
        Button btn = new Button(this);
        btn.setText(label);
        btn.setTextColor(currentSection.equals(section) ? 0xFF8AB4F8 : 0xFFFFFFFF);
        btn.setBackgroundColor(0xFF2D2D2D);
        btn.setOnClickListener(v -> {
            currentSection = section;
            buildUI();
        });
        contentLayout.addView(btn, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void addLabel(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(16);
        tv.setTextColor(0xFFCCCCCC);
        tv.setPadding(0, 12, 0, 4);
        contentLayout.addView(tv);
    }

    private void addSwitch(String label, boolean checked, CompoundButton.OnCheckedChangeListener listener) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(0, 8, 0, 8);

        TextView tv = new TextView(this);
        tv.setText(label);
        tv.setTextSize(14);
        tv.setTextColor(0xFFFFFFFF);
        tv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        Switch sw = new Switch(this);
        sw.setChecked(checked);
        sw.setOnCheckedChangeListener(listener);

        row.addView(tv);
        row.addView(sw);
        contentLayout.addView(row);
    }

    private void addSpinner(String label, String[] items, String selected, AdapterView.OnItemSelectedListener listener) {
        addLabel(label);
        final Spinner spinner = new Spinner(this);
        ArrayAdapter<String> adapter = createDarkAdapter(items);
        spinner.setAdapter(adapter);
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(selected)) {
                spinner.setSelection(i);
                break;
            }
        }
        final TextView selectionLabel = createSelectionLabel(selected);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                selectionLabel.setText("Selected: " + p.getItemAtPosition(pos));
                if (listener != null) listener.onItemSelected(p, v, pos, id);
            }
            @Override public void onNothingSelected(AdapterView<?> p) {
                if (listener != null) listener.onNothingSelected(p);
            }
        });
        contentLayout.addView(spinner);
        contentLayout.addView(selectionLabel);
    }

    private ArrayAdapter<String> createDarkAdapter(String[] items) {
        return new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                return styledSpinnerView(getItem(position));
            }
            @Override
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                return styledSpinnerDropDownView(getItem(position));
            }
        };
    }

    private TextView styledSpinnerView(String text) {
        TextView tv = new TextView(SettingsActivity.this);
        tv.setText(text);
        tv.setTextSize(16);
        tv.setTextColor(0xFFFFFFFF);
        tv.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        tv.setPadding(16, 14, 16, 14);
        tv.setBackgroundColor(0xFF3C3C3C);
        return tv;
    }

    private TextView styledSpinnerDropDownView(String text) {
        TextView tv = new TextView(SettingsActivity.this);
        tv.setText(text);
        tv.setTextSize(16);
        tv.setTextColor(0xFFFFFFFF);
        tv.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        tv.setPadding(24, 16, 24, 16);
        tv.setBackgroundColor(0xFF2D2D2D);
        return tv;
    }

    private TextView createSelectionLabel(String initial) {
        TextView tv = new TextView(this);
        tv.setText(initial != null ? "Selected: " + initial : "Selected: none");
        tv.setTextSize(12);
        tv.setTextColor(0xFF8AB4F8);
        tv.setPadding(4, 2, 4, 10);
        return tv;
    }

    private void addEditText(String label, String value, TextView.OnEditorActionListener listener) {
        addLabel(label);
        EditText et = new EditText(this);
        et.setText(value);
        et.setTextColor(0xFFFFFFFF);
        et.setBackgroundColor(0xFF3C3C3C);
        et.setPadding(16, 12, 16, 12);
        et.setOnEditorActionListener(listener);
        contentLayout.addView(et);
    }

    private void addButton(String label, View.OnClickListener listener) {
        Button btn = new Button(this);
        btn.setText(label);
        btn.setTextColor(0xFFFFFFFF);
        btn.setBackgroundColor(0xFF1A73E8);
        btn.setOnClickListener(listener);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 8, 0, 8);
        contentLayout.addView(btn, params);
    }

    private void buildGeneral() {
        addSectionHeader("General Settings");

        String[] themes = {"Dark", "Light", "System"};
        String currentTheme = settings.getTheme();
        String themeDisplay = "dark".equals(currentTheme) ? "Dark" : "light".equals(currentTheme) ? "Light" : "System";
        addSpinner("Theme", themes, themeDisplay, new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                settings.setTheme(themes[pos].toLowerCase());
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        });

        String[] layouts = {"UK QWERTY", "US QWERTY"};
        String currentLayout = settings.getKeyboardLayout();
        addSpinner("Keyboard Layout", layouts, "uk".equals(currentLayout) ? "UK QWERTY" : "US QWERTY",
                new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                settings.setKeyboardLayout(pos == 0 ? "uk" : "us");
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        });

        addSwitch("Number Row", settings.isNumberRowEnabled(), (btn, checked) -> settings.setNumberRowEnabled(checked));
        addSwitch("Haptic Feedback", settings.isHapticFeedback(), (btn, checked) -> settings.setHapticFeedback(checked));
        addSwitch("Key Sound", settings.isKeySound(), (btn, checked) -> settings.setKeySound(checked));
        addSwitch("Enter Key Sends Message", settings.isEnterSendsMessage(), (btn, checked) -> settings.setEnterSendsMessage(checked));
        addSwitch("Word Suggestions (suggest only)", settings.isSuggestionsEnabled(), (btn, checked) -> settings.setSuggestionsEnabled(checked));
        addSwitch("Auto-Correct (change to nearest word)", settings.isAutoCorrectEnabled(), (btn, checked) -> settings.setAutoCorrectEnabled(checked));
        addSwitch("Next Word Prediction", settings.isNextWordPrediction(), (btn, checked) -> settings.setNextWordPrediction(checked));
        addSwitch("Add Words to Dictionary", settings.isAddWordEnabled(), (btn, checked) -> settings.setAddWordEnabled(checked));
        addLabel("Tip: suggestions only finish or correct the word you are typing. Long-press a suggestion you added to remove it.");
    }

    private void buildUnicode() {
        addSectionHeader("Unicode Styles");
        addSwitch("Enable Unicode Transformation", settings.isUnicodeEnabled(),
                (btn, checked) -> settings.setUnicodeEnabled(checked));

        addLabel("Current Style: " + settings.getCurrentStyleId());
        addLabel("Preview: " + UnicodeStyleDatabase.transform("Hello World", settings.getCurrentStyleId()));

        addButton("Clear Selection", v -> {
            settings.setCurrentStyleId(UnicodeStyleDatabase.getDefaultStyleId());
            buildUI();
        });

        addLabel("Available Styles:");

        List<UnicodeStyleDatabase.UnicodeStyle> allStyles = UnicodeStyleDatabase.getAllStyles();
        List<UnicodeStyleDatabase.UnicodeStyle> sorted = new ArrayList<>(allStyles);
        Collections.sort(sorted, (a, b) -> {
            if (a.favourite != b.favourite) return a.favourite ? -1 : 1;
            return a.displayName.compareToIgnoreCase(b.displayName);
        });

        for (UnicodeStyleDatabase.UnicodeStyle style : sorted) {
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(0, 8, 0, 8);
            row.setBackgroundColor(0xFF2D2D2D);

            LinearLayout textCol = new LinearLayout(this);
            textCol.setOrientation(LinearLayout.VERTICAL);
            textCol.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            TextView nameTv = new TextView(this);
            nameTv.setText(style.displayName + " (" + style.category + ")");
            nameTv.setTextSize(14);
            nameTv.setTextColor(0xFFFFFFFF);
            textCol.addView(nameTv);

            TextView previewTv = new TextView(this);
            previewTv.setText(style.preview);
            previewTv.setTextSize(16);
            previewTv.setTextColor(0xFF8AB4F8);
            textCol.addView(previewTv);

            row.addView(textCol);

            Button selectBtn = new Button(this);
            selectBtn.setText(style.id.equals(settings.getCurrentStyleId()) ? "Active" : "Select");
            selectBtn.setTextSize(12);
            selectBtn.setOnClickListener(v -> {
                settings.setCurrentStyleId(style.id);
                buildUI();
            });
            row.addView(selectBtn);

            Button favBtn = new Button(this);
            boolean isFav = settings.isStyleFavourite(style.id);
            favBtn.setText(isFav ? "★" : "☆");
            favBtn.setTextSize(12);
            favBtn.setOnClickListener(v -> {
                settings.setStyleFavourite(style.id, !isFav);
                buildUI();
            });
            row.addView(favBtn);

            contentLayout.addView(row);
        }
    }

    private void buildTranslation() {
        addSectionHeader("Translation Settings");
        addSwitch("Enable Translation", settings.isTranslationEnabled(),
                (btn, checked) -> settings.setTranslationEnabled(checked));

        String[] providers = {"Google Translate", "LibreTranslate", "MyMemory"};
        String currentProvider = settings.getTranslationProvider();
        String providerDisplay;
        if ("libretranslate".equals(currentProvider)) {
            providerDisplay = "LibreTranslate";
        } else if ("mymemory".equals(currentProvider)) {
            providerDisplay = "MyMemory";
        } else {
            providerDisplay = "Google Translate";
        }
        addSpinner("Translation Provider", providers, providerDisplay,
                new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                switch (pos) {
                    case 1: settings.setTranslationProvider("libretranslate"); break;
                    case 2: settings.setTranslationProvider("mymemory"); break;
                    default: settings.setTranslationProvider("google"); break;
                }
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        });

        addSwitch("Auto-Detect Source Language", settings.isAutoDetectSource(),
                (btn, checked) -> settings.setAutoDetectSource(checked));

        addLabel("Translate From (source language)");
        Spinner srcSpinner = new Spinner(this);
        final String[] srcNames = new String[LANGS.length];
        for (int i = 0; i < LANGS.length; i++) srcNames[i] = LANGS[i][1];
        ArrayAdapter<String> srcAdapter = createDarkAdapter(srcNames);
        srcSpinner.setAdapter(srcAdapter);
        final TextView srcSelectionLabel = createSelectionLabel(srcNames.length > 0 ? srcNames[0] : null);
        String currentSrc = settings.getSourceLanguage();
        for (int i = 0; i < LANGS.length; i++) {
            if (LANGS[i][0].equals(currentSrc)) {
                srcSpinner.setSelection(i);
                srcSelectionLabel.setText("Selected: " + srcNames[i]);
                break;
            }
        }
        srcSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                settings.setSourceLanguage(LANGS[pos][0]);
                srcSelectionLabel.setText("Selected: " + srcNames[pos]);
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        });
        contentLayout.addView(srcSpinner);
        contentLayout.addView(srcSelectionLabel);

        addLabel("Translate To (destination language)");
        Spinner destSpinner = new Spinner(this);
        final String[] destNames = new String[LANGS.length];
        for (int i = 0; i < LANGS.length; i++) destNames[i] = LANGS[i][1];
        ArrayAdapter<String> destAdapter = createDarkAdapter(destNames);
        destSpinner.setAdapter(destAdapter);
        final TextView destSelectionLabel = createSelectionLabel(destNames.length > 0 ? destNames[0] : null);
        String currentDest = settings.getDestinationLanguage();
        for (int i = 0; i < LANGS.length; i++) {
            if (LANGS[i][0].equals(currentDest)) {
                destSpinner.setSelection(i);
                destSelectionLabel.setText("Selected: " + destNames[i]);
                break;
            }
        }
        destSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                settings.setDestinationLanguage(LANGS[pos][0]);
                destSelectionLabel.setText("Selected: " + destNames[pos]);
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        });
        contentLayout.addView(destSpinner);
        contentLayout.addView(destSelectionLabel);

        addEditText("API URL (optional)", settings.getApiUrl(),
                (v, actionId, event) -> {
                    settings.setApiUrl(((EditText) v).getText().toString());
                    return false;
                });

        addEditText("API Key (optional)", settings.getApiKey(),
                (v, actionId, event) -> {
                    settings.setApiKey(((EditText) v).getText().toString());
                    return false;
                });
    }

    private void buildAppearance() {
        addSectionHeader("Appearance Settings");

        String[] themes = {"Dark", "Light", "System"};
        String currentTheme = settings.getTheme();
        String themeDisplay = "dark".equals(currentTheme) ? "Dark" : "light".equals(currentTheme) ? "Light" : "System";
        addSpinner("Theme", themes, themeDisplay, new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                settings.setTheme(themes[pos].toLowerCase());
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        });

        addSwitch("Compact Layout", settings.isCompactLayout(),
                (btn, checked) -> settings.setCompactLayout(checked));
    }

    private void buildRemapping() {
        addSectionHeader("Key Remapping");
        addLabel("Enter a key character and its replacement:");

        EditText keyInput = new EditText(this);
        keyInput.setHint("Key to remap (single character)");
        keyInput.setTextColor(0xFFFFFFFF);
        keyInput.setBackgroundColor(0xFF3C3C3C);
        keyInput.setPadding(16, 12, 16, 12);
        contentLayout.addView(keyInput);

        EditText valueInput = new EditText(this);
        valueInput.setHint("Replacement text");
        valueInput.setTextColor(0xFFFFFFFF);
        valueInput.setBackgroundColor(0xFF3C3C3C);
        valueInput.setPadding(16, 12, 16, 12);
        contentLayout.addView(valueInput);

        addButton("Set Remapping", v -> {
            String key = keyInput.getText().toString();
            String val = valueInput.getText().toString();
            if (key.length() == 1) {
                KeyRemapping kr = new KeyRemapping(this);
                kr.setRemapping(String.valueOf((int) key.charAt(0)), val);
                keyInput.setText("");
                valueInput.setText("");
                buildUI();
            }
        });

        addButton("Reset All Remappings", v -> {
            KeyRemapping kr = new KeyRemapping(this);
            kr.resetAll();
            buildUI();
        });

        addLabel("Current Remappings:");
        KeyRemapping kr = new KeyRemapping(this);
        for (java.util.Map.Entry<String, String> entry : kr.getAllRemappings().entrySet()) {
            int code = Integer.parseInt(entry.getKey());
            String display = String.valueOf((char) code) + " → " + entry.getValue();
            TextView tv = new TextView(this);
            tv.setText(display);
            tv.setTextSize(14);
            tv.setTextColor(0xFFCCCCCC);
            tv.setPadding(0, 4, 0, 4);
            contentLayout.addView(tv);
        }
    }

    private void buildClipboard() {
        addSectionHeader("Clipboard Manager");
        ClipboardManager cm = new ClipboardManager(this);
        List<String> items = cm.getAll();

        if (items.isEmpty()) {
            addLabel("No saved items.");
        } else {
            for (int i = 0; i < items.size(); i++) {
                final int index = i;
                String text = items.get(i);
                String display = text.length() > 50 ? text.substring(0, 47) + "..." : text;

                LinearLayout row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setPadding(0, 4, 0, 4);

                TextView tv = new TextView(this);
                tv.setText(display);
                tv.setTextSize(14);
                tv.setTextColor(0xFFFFFFFF);
                tv.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                row.addView(tv);

                Button pasteBtn = new Button(this);
                pasteBtn.setText("Paste");
                pasteBtn.setTextSize(12);
                pasteBtn.setOnClickListener(v -> {
                    android.content.ClipboardManager clip =
                            (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    android.content.ClipData clipData = android.content.ClipData.newPlainText("JNet", text);
                    clip.setPrimaryClip(clipData);
                    Toast.makeText(this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                });
                row.addView(pasteBtn);

                Button delBtn = new Button(this);
                delBtn.setText("X");
                delBtn.setTextSize(12);
                delBtn.setOnClickListener(v -> {
                    cm.delete(index);
                    buildUI();
                });
                row.addView(delBtn);

                contentLayout.addView(row);
            }
        }

        addButton("Clear All", v -> {
            cm.clear();
            buildUI();
        });
    }

    private void buildEmojiSearch() {
        addSectionHeader("Emoji Search");
        EditText searchInput = new EditText(this);
        searchInput.setHint("Search emojis by name or keyword...");
        searchInput.setTextColor(0xFFFFFFFF);
        searchInput.setBackgroundColor(0xFF3C3C3C);
        searchInput.setPadding(16, 12, 16, 12);
        contentLayout.addView(searchInput);

        LinearLayout resultsLayout = new LinearLayout(this);
        resultsLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.addView(resultsLayout);

        searchInput.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                resultsLayout.removeAllViews();
                String query = s.toString();
                if (query.isEmpty()) {
                    addLabel("Type to search emojis...");
                    return;
                }
                List<EmojiDatabase.EmojiEntry> results = EmojiDatabase.search(query);
                if (results.isEmpty()) {
                    TextView tv = new TextView(SettingsActivity.this);
                    tv.setText("No emojis found for \"" + query + "\"");
                    tv.setTextColor(0xFFCCCCCC);
                    resultsLayout.addView(tv);
                } else {
                    for (EmojiDatabase.EmojiEntry e : results) {
                        TextView tv = new TextView(SettingsActivity.this);
                        tv.setText(e.emoji + "  " + e.name);
                        tv.setTextSize(18);
                        tv.setTextColor(0xFFFFFFFF);
                        tv.setPadding(0, 8, 0, 8);
                        tv.setOnClickListener(v -> {
                            android.content.ClipboardManager clip =
                                    (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            android.content.ClipData clipData = android.content.ClipData.newPlainText("emoji", e.emoji);
                            clip.setPrimaryClip(clipData);
                            Toast.makeText(SettingsActivity.this, "Copied: " + e.emoji, Toast.LENGTH_SHORT).show();
                        });
                        resultsLayout.addView(tv);
                    }
                }
            }
            @Override public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void buildDiagnostics() {
        addSectionHeader("Diagnostics");
        String report = Diagnostics.getFullReport();
        TextView tv = new TextView(this);
        tv.setText(report);
        tv.setTextSize(12);
        tv.setTextColor(0xFFCCCCCC);
        tv.setTypeface(android.graphics.Typeface.MONOSPACE);
        contentLayout.addView(tv);

        addButton("Clear Diagnostics", v -> {
            Diagnostics.clear();
            buildUI();
        });
    }

    private void buildAbout() {
        addSectionHeader("About");
        addLabel("Made by jnetai.com");
        addLabel("Version v1.1.4");

        addButton("Check for Updates", v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/jnetai-clawbot/JNet-Keyboard/releases/latest"));
            startActivity(intent);
        });

        addButton("Share App", v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    "Check out J~Net Keyboard: https://github.com/jnetai-clawbot/JNet-Keyboard/releases/latest");
            startActivity(Intent.createChooser(shareIntent, "Share J~Net Keyboard"));
        });
    }
}
