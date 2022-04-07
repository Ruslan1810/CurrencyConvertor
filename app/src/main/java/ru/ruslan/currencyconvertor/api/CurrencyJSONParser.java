package ru.ruslan.currencyconvertor.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ru.ruslan.currencyconvertor.models.Currency;
import ru.ruslan.currencyconvertor.viewmodel.CurrencyViewModel;

/**
 * Класс-парсер для заполнения списка элементов Currency данными из json-файла
 */

public class CurrencyJSONParser {

    private final Context context;
    private final CurrencyViewModel viewModel;
    private List<Currency> listOfCurrency;
    private static final String JSON_URL = "https://www.cbr-xml-daily.ru/daily_json.js";// UTF-8

    public CurrencyJSONParser(Context context, CurrencyViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    public void loadJSONFromURL() {
        listOfCurrency = new ArrayList<>();
        viewModel.deleteAllCurrencies();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);
                            JSONObject jSonValute = object.getJSONObject("Valute");
                            Iterator<String> arrayKey = jSonValute.keys();

                            while (arrayKey.hasNext()) {
                                String key = arrayKey.next();
                                JSONObject jSonItem = jSonValute.getJSONObject(key);
                                Currency currency = new Currency(
                                        jSonItem.getString("CharCode"),
                                        jSonItem.getString("Name"),
                                        jSonItem.getString("Nominal"),
                                        jSonItem.getString("Value"),
                                        jSonItem.getString("Previous")
                                );
                                listOfCurrency.add(currency);
                            }

                            viewModel.insertCurrencies(listOfCurrency);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ErrorResponse", error.getMessage());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

}