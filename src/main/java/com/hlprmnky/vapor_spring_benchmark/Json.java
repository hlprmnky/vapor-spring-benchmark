package com.hlprmnky.vapor_spring_benchmark;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Json {

    private final long id;

    private  List<Integer> anArray;
    private  Map<String, Integer> aMap;
    private  int anInt;
    private  String aString;
    private  double aDouble;
    private  final Object alwaysNull = null;


    public Json () { /* supports Jackson deserialization */
        this(1L, Arrays.asList(1, 2, 3),
                ImmutableMap.of("one", 1, "two", 2, "three", 3),
                "test", 42, 3.14);

    }

    public Json(long id, List<Integer> anArray, Map<String, Integer> aMap, String aString, int anInt, double aDouble) {
        this.anArray = anArray;
        this.aMap = aMap;
        this.id = id;
        this.aString = aString;
        this.anInt = anInt;
        this.aDouble = aDouble;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    @JsonProperty("string")
    public String getaString() { return aString; }

    @JsonProperty("array")
    public List<Integer> getAnArray() { return anArray; }

    @JsonProperty("dict")
    public Map<String, Integer> getaMap() { return aMap; }

    @JsonProperty("int")
    public int getAnInt() { return anInt; }

    @JsonProperty("double")
    public double getaDouble() { return aDouble; }

    @JsonProperty("null")
    public Object getAlwaysNull() { return alwaysNull; }
}
