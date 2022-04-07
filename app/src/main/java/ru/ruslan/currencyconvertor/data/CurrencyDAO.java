package ru.ruslan.currencyconvertor.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import io.reactivex.Flowable;
import ru.ruslan.currencyconvertor.models.Currency;

@Dao
public interface CurrencyDAO {
    @Query("SELECT * FROM currency")
    Flowable<List<Currency>> getAllCurrencies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrency(List<Currency> currency);

    @Delete
    void deleteCurrency(Currency currency);

    @Query("DELETE FROM currency")
    void deleteAllCurrencies();

    @Update()
    void upDate(Currency currency);
}
