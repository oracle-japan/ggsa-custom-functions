package com.example.ggsa;

import java.math.BigDecimal;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.oracle.cep.api.annotations.OsaFunction;

public class CustomFunctions {
    
    private static Gson gson = new Gson();

    private static JsonElement getJsonElement(String jsonText, String key){
        //System.out.println("jsonText: " + jsonText);
        String[] paths = key.replaceAll("^/", "").split("/");
        JsonElement jsonElement = gson.fromJson(jsonText, JsonElement.class);
        for(String path : paths){
            jsonElement = jsonElement.getAsJsonObject().get(path);
        }
        return jsonElement;
    }

    /**
     * json文字列からkeyで指定されたノードの値を文字列として返す
     * 
     * @param jsonText json文字列
     * @param key  /で区切られたノードの位置
     * @return 文字列
     */
    @OsaFunction(name = "getTextFromJson", description = "get text from json")
    public static String getTextFromJson(String jsonText, String key){
        JsonElement jsonElement = getJsonElement(jsonText, key);
        if(jsonElement.isJsonPrimitive()){
            return jsonElement.getAsString();
        }else{
            return gson.toJson(jsonElement);
        }
    }

    /**
     * json文字列からkeyで指定されたノードの値(jsonオブジェクト)をjson文字列として返す
     * 
     * @param jsonText json文字列
     * @param key /で区切られたノードの位置
     * @return json文字列
     */
    @OsaFunction(name = "getJsonTextFromJson", description = "get json object from json")
    public static String getJsonTextFromJson(String jsonText, String key){
        JsonElement jsonElement = getJsonElement(jsonText, key);
        return gson.toJson(jsonElement);
    }

    /**
     * json文字列からkeyで指定されたノードの値をBigDecimalとして返す
     * 
     * @param jsonText json文字列
     * @param key  /で区切られたノードの位置
     * @return BigDecimalの値
     */
    @OsaFunction(name = "getBigDecimalFromJson", description = "get bigdecial from json")
    public static BigDecimal getBigDecimalFromJson(String jsonText, String key){
        JsonElement jsonElement = getJsonElement(jsonText, key);
        return jsonElement.getAsBigDecimal();
    }

    /**
     * json文字列からkeyで指定されたノードの値をintとして返す
     * 
     * @param jsonText json文字列
     * @param key  /で区切られたノードの位置
     * @return intの値
     */
    @OsaFunction(name = "getIntFromJson", description = "get int from json")
    public static int getIntFromJson(String jsonText, String key){
        JsonElement jsonElement = getJsonElement(jsonText, key);
        return jsonElement.getAsInt();
    }

    /**
     * json文字列からkeyで指定されたノードの値をlongとして返す
     * 
     * @param jsonText json文字列
     * @param key  /で区切られたノードの位置
     * @return longの値
     */
    @OsaFunction(name = "getLongFromJson", description = "get long from json")
    public static long getLongFromJson(String jsonText, String key){
        JsonElement jsonElement = getJsonElement(jsonText, key);
        return jsonElement.getAsLong();
    }

    /**
     * json文字列からkeyで指定されたノードの値をfloatとして返す
     * 
     * @param jsonText json文字列
     * @param key  /で区切られたノードの位置
     * @return floatの値
     */
    @OsaFunction(name = "getFloatFromJson", description = "get float from json")
    public static float getFloatFromJson(String jsonText, String key){
        JsonElement jsonElement = getJsonElement(jsonText, key);
        return jsonElement.getAsFloat();
    }

    /**
     * json文字列からkeyで指定されたノードの値をdoubleとして返す
     * 
     * @param jsonText json文字列
     * @param key  /で区切られたノードの位置
     * @return doubleの値
     */
    @OsaFunction(name = "getDoubleFromJson", description = "get double from json")
    public static double getDoubleFromJson(String jsonText, String key){
        JsonElement jsonElement = getJsonElement(jsonText, key);
        return jsonElement.getAsDouble();
    }

    /**
     * json文字列からkeyで指定されたノードの値をbooleanとして返す
     * 
     * @param jsonText json文字列
     * @param key  /で区切られたノードの位置
     * @return booleanの値
     */
    @OsaFunction(name = "getBooleanFromJson", description = "get boolean from json")
    public static boolean getBooleanFromJson(String jsonText, String key){
        JsonElement jsonElement = getJsonElement(jsonText, key);
        return jsonElement.getAsBoolean();
    }

    /**
     * 区切り文字で区切られたキー、タイプ、値の配列を使って新しいjson文字列を作成する
     * <p>
     * (例)<br>
     * keys = "ke1,key2,kye3"<br>
     * types = "str,number,boolean"<br>
     * values = "abc,123,false"<br>
     * delimiter = ","<br>
     * 結果: {"key1":"abc","key2",123,"key3",false}
     * </p>
     * @param keys jsonオブジェクトのノードとなるキー値の配列（文字列）
     * @param types ノードのタイプの配列（文字列）
     *              <br> 指定可能な値: text, string, str, int, integer, long, float, double, bigdecial, number, boolean, json
     * @param values ノードの値の配列（文字列）
     * @param delimiter 配列の区切り文字
     * @return json文字列
     */
    @OsaFunction(name = "createJsonText", description = "create json text from keys, types and values")
    public static String createJsonText(String keys, String types, String values, String delimiter){
        String d = (Objects.isNull(delimiter) || 0 == delimiter.length()) ? "\t" : delimiter;
        boolean isNoType = (Objects.isNull(types) || 0 == types.length()) ? true : false;

        String[] k = keys.split(d);
        String[] t = isNoType ? null : types.split(d);
        String[] v = values.split(d);

        JsonObject json = new JsonObject();
        for(int i = 0 ; i < k.length ; i++){
            if(isNoType || t[i].equalsIgnoreCase("TEXT") || t[i].equalsIgnoreCase("String") || t[i].equalsIgnoreCase("Str")){
                json.addProperty(k[i], v[i]);
            }else if(t[i].equalsIgnoreCase("Int") || t[i].equalsIgnoreCase("Integer")){
                json.addProperty(k[i], Integer.parseInt(v[i]));
            }else if(t[i].equalsIgnoreCase("Long")){
                json.addProperty(k[i], Long.parseLong(v[i]));
            }else if(t[i].equalsIgnoreCase("Float")){
                json.addProperty(k[i], Float.parseFloat(v[i]));
            }else if(t[i].equalsIgnoreCase("Double")){
                json.addProperty(k[i], Double.parseDouble(v[i]));
            }else if(t[i].equalsIgnoreCase("BigDecimal") || t[i].equalsIgnoreCase("Number")){
                json.addProperty(k[i], new BigDecimal(v[i]));
            }else if(t[i].equalsIgnoreCase("Boolean")){
                json.addProperty(k[i], Boolean.parseBoolean(v[i]));
            }else if(t[i].equalsIgnoreCase("json")){
                json.add(k[i], gson.fromJson(v[i], JsonElement.class));
            }else{
                throw new IllegalArgumentException("Unknown type: " + t[i]);
            }
        }
        return gson.toJson(json);
    }

}
