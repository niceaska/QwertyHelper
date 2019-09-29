package ru.niceaska.qwertyhelper;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.CompoundButton;

public class MainActivity extends Activity implements View.OnClickListener{

    private EditText editText;
    private TextView resultView;
    private TextView passwordDiffView;
    private TextView generateView;
    private TextView seekBarCaption;
    private String[] rus;
    private String[] latin;
    private PasswordsHelper passwordsHelper;
    private PasswordGenerator passwordGenerator;
    private View copyButton;
    private View copyButtonGen;
    private View genButton;
    private SeekBar seekBar;
    private ImageView passDiffImage;
    private CompoundButton genCaps;
    private CompoundButton genDigits;
    private CompoundButton genSpecial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_source);
        resultView = findViewById(R.id.res_view);
        passwordDiffView = findViewById(R.id.password_diff);
        generateView = findViewById(R.id.gen_view);
        rus = getResources().getStringArray(R.array.russians);
        latin = getResources().getStringArray(R.array.latin);
        passwordsHelper = new PasswordsHelper(rus, latin);
        copyButton = findViewById(R.id.copypast);
        copyButtonGen = findViewById(R.id.copy_second);
        genButton = findViewById(R.id.genpass);
        passDiffImage = findViewById(R.id.password_mark);
        genCaps = findViewById(R.id.checkcaps);
        genDigits = findViewById(R.id.checkdigits);
        genSpecial = findViewById(R.id.count_symbols);
        seekBar = findViewById(R.id.pass_len_bar);
        seekBarCaption = findViewById(R.id.seekbar_caption);
        passwordGenerator = new PasswordGenerator();

        copyButton.setEnabled(false);
        copyButtonGen.setEnabled(false);


        copyButton.setOnClickListener(this);
        genButton.setOnClickListener(this);
        copyButtonGen.setOnClickListener(this);

        seekBarCaption.setText(getResources().getString(R.string.seekbar_oninit));

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resultView.setText(passwordsHelper.convert(s));
                copyButton.setEnabled(s.length() > 0);

                int level = passwordsHelper.checkPassword(s) * 400;
                String [] difficult = getResources().getStringArray(R.array.passwords_difficulty);
                passDiffImage.getDrawable().setLevel(level > 10000 ? 10000 : level);

                if (level == 0) {
                    passwordDiffView.setText("");
                } else if (level <= 3333) {
                    passwordDiffView.setText(difficult[0]);
                } else if (level <= 6666) {
                    passwordDiffView.setText(difficult[1]);
                } else  if (level < 10000){
                    passwordDiffView.setText(difficult[2]);
                } else {
                    passwordDiffView.setText(difficult[3]);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarCaption.setText(getResources()
                        .getQuantityString(R.plurals.symbol_count, 8 + progress, 8 + progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.copypast:
                copyPassword(resultView);
                break;
            case R.id.copy_second:
                copyPassword(generateView);
                break;
            case R.id.genpass:
                int len = 8 + seekBar.getProgress();
                copyButtonGen.setEnabled(true);
                generateView.setText(passwordGenerator.generatePassword(parseCheckBoxes(), len));
                break;
            default:
                break;
        }
    }

    private int parseCheckBoxes() {
        int mode = 0;

        mode |= (genCaps.isChecked()) ? PasswordGenerator.FL_CAPS : 0;
        mode |= (genDigits.isChecked()) ? PasswordGenerator.FL_DIGITS : 0;
        mode |= (genSpecial.isChecked()) ? PasswordGenerator.FL_SPEACIAL : 0;

        return mode;
    }

    private void copyPassword(TextView copyView) {
        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            manager.setPrimaryClip(ClipData.newPlainText(
                    getString(R.string.password), copyView.getText().toString()));
        }
        Toast.makeText(MainActivity.this, R.string.copied, Toast.LENGTH_SHORT).show();
    }
}
