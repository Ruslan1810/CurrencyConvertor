package ru.ruslan.currencyconvertor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import ru.ruslan.currencyconvertor.R;
import ru.ruslan.currencyconvertor.adapters.CurrencyRecyclerViewAdapter;
import ru.ruslan.currencyconvertor.viewmodel.CurrencyViewModel;

public class ListCurenciesActivity extends AppCompatActivity {

    private CurrencyRecyclerViewAdapter adapter;
    private CurrencyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("СПИСОК ВАЛЮТ");
        Button refresh = findViewById(R.id.refresh);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        viewModel = new ViewModelProvider(this).get(CurrencyViewModel.class);
        recyclerView.setHasFixedSize(true);
        adapter = new CurrencyRecyclerViewAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel.getDataFromDB(adapter);

        //Обновление в ручную
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewModel.getDataFromDB(adapter);
            }
        });
    }

    @Override
    protected void onDestroy() {
        viewModel.disposeDisposable();
        super.onDestroy();
    }
}