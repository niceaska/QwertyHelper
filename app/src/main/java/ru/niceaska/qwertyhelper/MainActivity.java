package ru.niceaska.qwertyhelper;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private EditText editText;
    private TextView resultView;
    private String[] rus;
    private String[] latin;
    private PassworsHelper passworsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = findViewById(R.id.edit_source);
        resultView = findViewById(R.id.res_view);
        rus = getResources().getStringArray(R.array.russians);
        latin = getResources().getStringArray(R.array.latin);
        passworsHelper = new PassworsHelper(rus, latin);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                resultView.setText(passworsHelper.convert(editText.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
