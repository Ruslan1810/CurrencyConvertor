package ru.ruslan.currencyconvertor.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Класс, описывающий каждую валюту,
 * и предоставляющий методы для назначения и считывания значений полей
 */

@Entity(tableName = "currency")
public class Currency {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String charCode;
    private String name;
    private String nominal;
    private String value;
    private String previous;

    public Currency(int id, String charCode, String name, String nominal, String value, String previous) {
        this.id = id;
        this.charCode = charCode;
        this.name = name;
        this.nominal = nominal;
        this.value = value;
        this.previous = previous;
    }

    @Ignore
    public Currency(String charCode, String name, String nominal, String value, String previous) {
        this.charCode = charCode;
        this.name = name;
        this.nominal = nominal;
        this.value = value;
        this.previous = previous;
    }

   public String getPrevious() {
       return previous;
   }

   public void setPrevious(String previous) {
        this.previous = previous;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
