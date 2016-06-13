package com.hlprmnky.vapor_spring_benchmark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

import java.util.Arrays;

public class JsonTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void testThatJsonDeserializes() throws Exception {
        final Json json = new Json(1L, Arrays.asList(1, 2, 3),
                ImmutableMap.of("one", 1, "two", 2, "three", 3),
                "test", 42, 3.14);

        final String expected = "{\"array\":[1,2,3],\"dict\":{\"one\":1,\"two\":2,\"three\":3}," +
                "\"int\":42,\"string\":\"test\",\"double\":3.14,\"null\":null}";

        assertThat(MAPPER.writeValueAsString(json)).isEqualTo(expected);

    }


}
