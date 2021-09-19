package com.example.ggsa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;

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



}
