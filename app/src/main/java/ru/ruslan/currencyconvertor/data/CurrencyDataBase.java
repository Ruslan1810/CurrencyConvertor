package ru.ruslan.currencyconvertor.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ru.ruslan.currencyconvertor.models.Currency;

@Database(entities = {Currency.class}, version = 1, exportSchema = false)
public abstract class CurrencyDataBase extends RoomDatabase {
    private static CurrencyDataBase database;
    private static final String DB_NAME = "currency.db";

    public static CurrencyDataBase getInstance(Context context) {
            if (database == null) {
                database = Room.databaseBuilder(context, CurrencyDataBase.class, DB_NAME)
                        .allowMainThreadQueries()
                        .build();
            }

        return database;
    }

    public abstract CurrencyDAO currencyDAO();


}
