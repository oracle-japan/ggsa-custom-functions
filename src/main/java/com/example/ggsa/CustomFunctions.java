package com.example.ggsa;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneRulesException;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.oracle.cep.api.annotations.OsaFunction;

public class CustomFunctions {
    private static final DateTimeFormatter ISO8601_ZONED = DateTimeFormatter.ISO_ZONED_DATE_TIME;
    private static final DateTimeFormatter ISO8601_OFFSET = DateTimeFormatter.ISO_OFFSET_DATE_TIME;


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
     * json文字列からkeyで指定されたノードの値をtimestamp(long)として返す
     * 
     * @param jsonText json文字列
     * @param key /で区切られたノードの位置
     * @return 1970年1月1日からの経過ミリ秒数
     */
    @OsaFunction(name = "getTimestampFromJson", description = "get timestamp(long) from json")
    public static long getTimestampFromJson(String jsonText, String key){
        JsonElement jsonElement = getJsonElement(jsonText, key);
        return Instant.parse(jsonElement.getAsString()).toEpochMilli();
    }

    /**
     * timestamp(long)をISO8601文字列(UTC)に変換する
     * 
     * @param timestamp タイムスタンプ（1970年1月1日からの経過ミリ秒数）
     * @return 日時を表すISO8601書式の文字列
     */
    @OsaFunction(name = "getISO8601", description = "get UTC ISO8601 string")
    public static String getISO8601(long timestamp){
        return ISO8601_OFFSET.format(Instant.ofEpochMilli(timestamp).atOffset(ZoneOffset.UTC));
    }

    /**
     * timestamp(long)をISO8601文字列(オフセット形式)に変換する
     * 
     * @param timestamp タイムスタンプ（1970年1月1日からの経過ミリ秒数）
     * @param offsetId オフセット +09:00 など
     * @return オフセット形式の日時を表すISO8601書式の文字列
     */
    @OsaFunction(name = "getOffsetISO8601", description = "get offset ISO8601 string")
    public static String getOffsetISO8601(long timestamp, String offsetId){
        return ISO8601_OFFSET.format(Instant.ofEpochMilli(timestamp).atOffset(ZoneOffset.of(offsetId)));
    }

    /**
     * timestamp(long)をISO8601文字列(ゾーン形式)に変換する
     * 
     * @param timestamp タイムスタンプ（1970年1月1日からの経過ミリ秒数）
     * @param zoneId ゾーン JST, [Asia/Tokyo] など
     * @return ゾーン形式の日時を表すISO8601書式の文字列
     */
    @OsaFunction(name = "getZonedISO8601", description = "get zoned ISO8601 string")
    public static String getZonedISO8601(long timestamp, String zoneId){
        ZoneId id = null;
        try{
            id = ZoneId.of(zoneId);
        }catch(ZoneRulesException e){
            id = ZoneId.of(zoneId, ZoneId.SHORT_IDS);
        }
        return ISO8601_ZONED.format(Instant.ofEpochMilli(timestamp).atZone(id));
    }


    /**
     * json文字列からkeyで指定されたノードの値(timestamp)をISO8601文字列(UTC)として返す
     * 
     * @param jsonText json文字列
     * @param key /で区切られたノードの位置
     * @return 日時を表すISO8601書式の文字列
     */
    @OsaFunction(name = "getISO8601FromJson", description = "get UTC ISO8601 string from timestamp(long) json element")
    public static String getISO8601FromJson(String jsonText, String key){
        JsonElement jsonElement = getJsonElement(jsonText, key);
        return getISO8601(jsonElement.getAsLong());
    }

    /**
     * json文字列からkeyで指定されたノードの値(timestamp)をISO8601文字列(オフセット形式)として返す
     * 
     * @param jsonText json文字列
     * @param key /で区切られたノードの位置
     * @param offsetId オフセット +09:00 など
     * @return オフセット形式の日時を表すISO8601書式の文字列
     */
    @OsaFunction(name = "getOffsetISO8601FromJson", description = "get offset ISO8601 string from timestamp(long) json element")
    public static String getOffsetISO8601FromJson(String jsonText, String key, String offsetId){
        JsonElement jsonElement = getJsonElement(jsonText, key);
        return getOffsetISO8601(jsonElement.getAsLong(), offsetId);
    }

    /**
     * json文字列からkeyで指定されたノードの値(timestamp)をISO8601文字列(ゾーン形式)として返す
     * 
     * @param jsonText json文字列
     * @param key /で区切られたノードの位置
     * @param zoneId ゾーン JST, [Asia/Tokyo] など
     * @return ゾーン形式の日時を表すISO8601書式の文字列
     */
    @OsaFunction(name = "getZonedISO8601", description = "get zoned ISO8601 string from timestamp(long) json element")
    public static String getZonedISO8601FromJson(String jsonText, String key, String zoneId){
        JsonElement jsonElement = getJsonElement(jsonText, key);
        return getZonedISO8601(jsonElement.getAsLong(), zoneId);
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
