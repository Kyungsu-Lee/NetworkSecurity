package mbis.lks.networksecurity.json;

import com.google.gson.*;

public class JsonParser
{
    private Gson gson;
    private JsonObject object;
    private JsonElement jsonElement;
    private com.google.gson.JsonParser parser;

    public JsonParser()
    {
        gson = new Gson();
        object = new JsonObject();
        parser = new com.google.gson.JsonParser();
    }

    public JsonParser(String json)
    {
        gson = new Gson();
        object = new JsonObject();
        parser = new com.google.gson.JsonParser();
        this.jsonElement = parser.parse(json);
    }

    public JsonParser add(String property, String value)
    {
        this.object.addProperty(property, value);
        this.jsonElement = parser.parse(gson.toJson(object));


        return this;
    }

    public String get(String property)
    {
        return jsonElement.getAsJsonObject().get(property).getAsString();
    }

    public static JsonParser parse(String json)
    {
        return new JsonParser(json);
    }

    public String toString()
    {
        return this.gson.toJson(object);
    }
}
