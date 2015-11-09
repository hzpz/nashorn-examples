package de.ams;

import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class NashornObjectMappingTest {

    private static final String ENGINE_NAME = "nashorn";

    private ScriptEngine engine;

    @Before
    public void setUpEngine() {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        engine = scriptEngineManager.getEngineByName(ENGINE_NAME);
        JavaScriptAdapter adapter = new JavaScriptAdapter();
        engine.put("adapter", adapter);
    }

    @Test
    public void shouldNeverPassInternalObjectToJava() throws ScriptException {
        Object result = engine.eval("adapter.getClassName({ key: 'value' });");

        assertThat(result.toString(), not(containsString("internal")));
    }

    public static class JavaScriptAdapter {

        public String getClassName(Object theObject) {
            return theObject.getClass().getName();
        }

    }

}
