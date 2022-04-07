package ru.ruslan.currencyconvertor.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.ruslan.currencyconvertor.adapters.CurrencyRecyclerViewAdapter;
import ru.ruslan.currencyconvertor.api.CurrencyJSONParser;
import ru.ruslan.currencyconvertor.data.CurrencyDataBase;
import ru.ruslan.currencyconvertor.models.Currency;

public class CurrencyViewModel extends AndroidViewModel {

    private CompositeDisposable compositeDisposable;
    private Disposable disposable;
    private static CurrencyDataBase database;
    private Flowable<List<Currency>> currencies;

    public CurrencyViewModel(@NonNull Application application) {
        super(application);

        database = CurrencyDataBase.getInstance(getApplication());
        currencies = database.currencyDAO().getAllCurrencies();
    }

    public Flowable<List<Currency>> getCurrencies() {
        return currencies;
    }

    public void insertCurrencies(List<Currency> currencies) {
        compositeDisposable = new CompositeDisposable();
        disposable =  Completable.fromAction(() -> database.currencyDAO()
                .insertCurrency(currencies))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.e("ErrorInsertCurrencies", "ErrorInsertCurrencies"))
                .subscribe();

        compositeDisposable.add(disposable);
    }
    @SuppressLint("CheckResult")
    public void deleteAllCurrencies() {
        compositeDisposable = new CompositeDisposable();
        disposable =  Completable.fromAction(() -> database.currencyDAO()
                .deleteAllCurrencies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("ErrorDeleteAllCurrencies", "ErrorDeleteAllCurrencies");
                    }
                })
                .subscribe();

        compositeDisposable.add(disposable);
    }

    public void deleteCurrency(Currency currency) {
        compositeDisposable = new CompositeDisposable();
        disposable =  Completable.fromAction(() -> database.currencyDAO()
                .deleteCurrency(currency))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("ErrorDeleteCurrency", "ErrorDeleteCurrency");
                    }
                })
                .subscribe();

        compositeDisposable.add(disposable);
    }

    @SuppressLint("CheckResult")
    public void getDataFromDB(CurrencyRecyclerViewAdapter adapter) {
        compositeDisposable = new CompositeDisposable();
        new CurrencyJSONParser(getApplication(), this).loadJSONFromURL();

        disposable =  getCurrencies().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Currency>>() {
                    @Override
                    public void accept(List<Currency> currencies) throws Exception {
                        adapter.setCurrencies(currencies);
                    }
                });

        compositeDisposable.add(disposable);
    }

    public void disposeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
