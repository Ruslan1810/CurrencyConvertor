package ru.ruslan.currencyconvertor.activities;

import static java.lang.String.format;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;

import ru.ruslan.currencyconvertor.R;

public class CalculationActivity extends AppCompatActivity {

    private TextView output_for_currency;
    private EditText input_for_rub;
    private double total = 0;
    private double count_rub = 0;
    private String nominal;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        setTitle("КОНВЕРТОР ВАЛЮТ");

        ImageView flag_rus = findViewById(R.id.flag_rus);
        ImageView flag_country = findViewById(R.id.flag_country);
        output_for_currency = findViewById(R.id.output_for_currency);
        input_for_rub = findViewById(R.id.input_for_rub);
        TextView currencyComparison = findViewById(R.id.currencyComparison);
        TextView code_currency = findViewById(R.id.code_currency);

        Intent intent = getIntent();
        String charCode = intent.getStringExtra("charCode");
        value = intent.getStringExtra("value");
        nominal = intent.getStringExtra("nominal");

        flag_country.setImageResource(getIdResource(charCode));
        flag_rus.setImageResource(R.drawable.rus);

        value = value.substring(0, value.length() - 2).replace(",", "."); //из стоимости валюты убирается символ рубля и запятая заменяется на точку
        currencyComparison.setText(MessageFormat
                .format("{0}{1}{2}{3}{4}{5}{6}", nominal, " ", charCode, " = ", value, " ", "RUB"));
        code_currency.setText(charCode);

        input_for_rub.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String strOfInput = input_for_rub.getText().toString();

                try {
                    if (strOfInput.length() == 0) strOfInput = "0";
                    count_rub = Double.parseDouble(strOfInput.trim());

                } catch (NumberFormatException e) {

                    if (!strOfInput.isEmpty()) showTost();
                    if (strOfInput.length() > 0) {
                        input_for_rub.setText(strOfInput.substring(0, input_for_rub.length() - 1));//удаление не числового элемента
                        input_for_rub.setSelection(input_for_rub.getText().length());//курсор в конец строки
                    }
                }

                    calculation();
            }
        });
    }

    //получение имени изображения флага из кода валюты
    private int getIdResource(String charCode){
        String nameResource = charCode.toLowerCase();
        if (charCode.equals("TRY")) nameResource = "tur";
        int idResource = this.getResources().getIdentifier(nameResource, "drawable", this.getPackageName());
        return idResource;
    }

    private void showTost() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, findViewById(R.id.toast_layout_root));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void calculation() {
        System.out.println(value);
        double currency = Double.parseDouble(value);

        switch (nominal) {
            case "1":
                total = count_rub * currency;
                break;
            case "10":
                total = currency / 10 * count_rub;
                break;
            case "100":
                total = currency / 100 * count_rub;
                break;
            case "1000":
                total = currency / 1000 * count_rub;
                break;
            case "10000":
                total = currency / 10000 * count_rub;
                break;
        }

        @SuppressLint("DefaultLocale") String formattedTotal = format("%.1f", total);
        output_for_currency.setText(formattedTotal);
    }
}
