package de.ams;

import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class NashornMethodSelectionTest {

    private static final String ENGINE_NAME = "nashorn";

    private ScriptEngine engine;

    @Before
    public void setUpEngine() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        engine = scriptEngineManager.getEngineByName(ENGINE_NAME);
        SomeJavaAPI api = new SomeJavaAPI();
        engine.put("api", api);
    }

    @Test
    public void shouldSelectMethod() throws Exception {
        Object result = engine.eval("api.doSomething('foo','bar')");

        assertThat(result.toString(), equalTo("object_string"));
    }

    public static class SomeJavaAPI {

        public String doSomething(Object input, String xsltFile) {
            return "object_string";
        }

        public String doSomething(String xsltFile, Map<String, Object> params) {
            return "string_map";
        }

    }
}
