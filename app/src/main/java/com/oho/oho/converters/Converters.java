package com.oho.oho.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oho.oho.models.PromptAnswer;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<PromptAnswer> fromString(String value) {
        Type listType = new TypeToken<List<PromptAnswer>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<PromptAnswer> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

}
