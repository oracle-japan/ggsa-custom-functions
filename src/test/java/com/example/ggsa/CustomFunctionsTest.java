package com.example.ggsa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.time.Instant;

import com.google.gson.JsonObject;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class CustomFunctionsTest {

    @Test
    public void getFromJsonTest() throws Exception{
        String jsonText = null;

        try(InputStream in = CustomFunctionsTest.class.getResourceAsStream("/test.json")){
            jsonText = IOUtils.toString(in, "UTF-8");
        }

        String s = CustomFunctions.getTextFromJson(jsonText, "id");
        System.out.println(s);
        assertEquals("file", s, "string on simple path");
        double d = CustomFunctions.getDoubleFromJson(jsonText, "value");
        System.out.println(d);
        assertEquals(12.345, d, "double on simple path");
        s = CustomFunctions.getTextFromJson(jsonText, "/popup/name");
        System.out.println(s);
        assertEquals("popup menu", s, "string on nested path");
        s = CustomFunctions.getJsonTextFromJson(jsonText, "/popup/menuitem");
        System.out.println(s);
        assertTrue(s.startsWith("[{") && s.endsWith("}]"), "json object");
        

    }

    @Test
    public void createJsonTest(){

        String keys = "key1,key2,key3,key4,key5,key6,key7,key8,key9,key10,key11,key12";
        String types = "str,string,text,int,integer,long,float,double,bigdecimal,number,boolean,boolean";
        String values = "ABC,xyz,###,10,88,9999,11.1,99.9,123.45,678.9,True,False";
        String delimiter = ",";
        String result = CustomFunctions.createJsonText(keys, types, values, delimiter);
        System.out.println(result);

        String expected = "{\"key1\":\"ABC\",\"key2\":\"xyz\",\"key3\":\"###\",\"key4\":10,\"key5\":88,\"key6\":9999,\"key7\":11.1,\"key8\":99.9,\"key9\":123.45,\"key10\":678.9,\"key11\":true,\"key12\":false}";
        assertEquals(expected, result, "using , as delimiter");

        keys = "key1\tkey2\tkey3";
        types = "str\tstring\ttext";
        values = "A, B, C\tx,y,z\t#,#,#";
        delimiter = "\t";
        result = CustomFunctions.createJsonText(keys, types, values, delimiter);
        System.out.println(result);

        expected = "{\"key1\":\"A, B, C\",\"key2\":\"x,y,z\",\"key3\":\"#,#,#\"}";
        assertEquals(expected, result, "using tab as delimiter");

        keys = "key1,key2";
        types = "";
        values = "ABC,###";
        delimiter = ",";
        result = CustomFunctions.createJsonText(keys, types, values, delimiter);
        System.out.println(result);

        expected = "{\"key1\":\"ABC\",\"key2\":\"###\"}";
        assertEquals(expected, result, "no types");

        keys = "key1\tkey2";
        types = "int\tjson";
        values = "101\t{\"message\":\"Hello!\"}";
        delimiter = "\t";
        result = CustomFunctions.createJsonText(keys, types, values, delimiter);
        System.out.println(result);

        expected = "{\"key1\":101,\"key2\":{\"message\":\"Hello!\"}}";
        assertEquals(expected, result, "json type");


    }

    @Test
    public void ISO8601Test() throws Exception{
        String datetime = "2000-01-01T00:00:00.000Z";
        long instant = Instant.parse(datetime).toEpochMilli();

        assertEquals(instant, CustomFunctions.parseDate("2000-01-01T00:00:00.000Z", "yyyy-MM-dd'T'HH:mm:ss.SSSX"), "parse utc");
        assertEquals(instant, CustomFunctions.parseDate("2000-01-01T00:00:00.000Z", "UTC"), "parse wtih UTC literal");
        assertEquals(instant, CustomFunctions.parseDate("2000-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss"), "parse local");

        JsonObject json = new JsonObject();
        json.addProperty("datetime", datetime);
        json.addProperty("timestamp", instant);
        String jsonText = json.toString();
        System.out.println(jsonText);

        String result = CustomFunctions.getISO8601FromJson(jsonText, "timestamp");
        System.out.println(result);
        assertEquals("2000-01-01T00:00:00Z", result, "utc");

        result = CustomFunctions.getOffsetISO8601FromJson(jsonText, "timestamp", "+09:00");
        System.out.println(result);
        assertEquals("2000-01-01T09:00:00+09:00", result, "+09:00 offset");

        result = CustomFunctions.getZonedISO8601FromJson(jsonText, "timestamp", "JST");
        System.out.println(result);
        assertEquals("2000-01-01T09:00:00+09:00[Asia/Tokyo]", result, "zone");

        result = CustomFunctions.getZonedISO8601FromJson(jsonText, "timestamp", "Etc/GMT");
        System.out.println(result);
        assertEquals("2000-01-01T00:00:00Z[Etc/GMT]", result, "zone");

        long dt = CustomFunctions.parseDateFromJson(jsonText, "datetime", "yyyy-MM-dd'T'HH:mm:ss.SSSX");
        System.out.println(dt);
        assertEquals(instant, dt, "datetime");


    }


}
