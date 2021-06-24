package org.utilitymods.friendapi.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Map;

public class MapAdapter implements JsonSerializer<Map<?, ?>> {

    @Override
    public JsonElement serialize(Map<?, ?> src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null || src.isEmpty())
            return null;
        JsonObject obj = new JsonObject();
        for (Map.Entry<?, ?> entry : src.entrySet()) {
            obj.add(entry.getKey().toString(), context.serialize(entry.getValue()));
        }
        return obj;
    }

}
