package ru.ruslan.currencyconvertor.adapters;

import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import ru.ruslan.currencyconvertor.R;
import ru.ruslan.currencyconvertor.activities.CalculationActivity;
import ru.ruslan.currencyconvertor.models.Currency;

/**
 * Класс-адаптер для размещения списеа валют в RecyclerView
 */

public class CurrencyRecyclerViewAdapter extends RecyclerView.Adapter<CurrencyRecyclerViewAdapter.Holder> {
    private List<Currency> currencies;
    private Context context;

    public CurrencyRecyclerViewAdapter(Context context) {
        currencies = new ArrayList<>();
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public TextView charCode, name, nominal, value;
        public ImageView arrow, flag;

        public Holder(View itemView) {
            super(itemView);

            charCode = itemView.findViewById(R.id.currency_char_code);
            name = itemView.findViewById(R.id.currency_name);
            nominal = itemView.findViewById(R.id.currency_nominal);
            value = itemView.findViewById(R.id.currency_value);
            arrow = itemView.findViewById(R.id.arrow);
            flag = itemView.findViewById(R.id.flag_country);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, CalculationActivity.class);
                    intent.putExtra("charCode", charCode.getText().toString());
                    intent.putExtra("value", value.getText().toString());
                    intent.putExtra("nominal", nominal.getText().toString());
                    context.startActivity(intent);
                }
            });
        }

        private void getFlag(String charCode){
            context.getResources().getIdentifier(charCode, "drawable", "ru.ruslan.currencyconvertor");
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Currency currency = currencies.get(position);
        @SuppressLint("DefaultLocale") String formattedValue = format("%.2f", Double.parseDouble(currency.getValue()));

        holder.charCode.setText(currency.getCharCode());
        holder.name.setText(currency.getName());
        holder.nominal.setText(currency.getNominal());
        holder.value.setText(MessageFormat.format("{0}{1}", formattedValue, " ₽"));

        String nameResource = currency.getCharCode().toLowerCase();
        if(currency.getCharCode().equals("TRY")) nameResource = "tur";
        int idResource = context.getResources().getIdentifier(nameResource, "drawable", context.getPackageName());
        holder.flag.setImageResource(idResource);

        if (Double.parseDouble(currency.getValue()) > Double.parseDouble(currency.getPrevious())) {
            holder.arrow.setImageResource(R.drawable.arrow_up);
        } else{
            holder.arrow.setImageResource(R.drawable.arrow_down);
        }


    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }


}
