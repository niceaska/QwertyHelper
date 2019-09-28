package ru.niceaska.qwertyhelper;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText editText;
    private TextView resultView;
    private String[] rus;
    private String[] latin;
    private PasswordsHelper passwordsHelper;
    private View copyButton;
  //  private ComponeButton CheckCaps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_source);
        resultView = findViewById(R.id.res_view);
        rus = getResources().getStringArray(R.array.russians);
        latin = getResources().getStringArray(R.array.latin);
        passwordsHelper = new PasswordsHelper(rus, latin);

        copyButton = findViewById(R.id.copypast);

        copyButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        if (manager != null) {
                            manager.setPrimaryClip(ClipData.newPlainText(
                                    "Пароль", resultView.getText().toString()));
                        }
                        Toast.makeText(MainActivity.this, R.string.copied, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resultView.setText(passwordsHelper.convert(s));
                copyButton.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
