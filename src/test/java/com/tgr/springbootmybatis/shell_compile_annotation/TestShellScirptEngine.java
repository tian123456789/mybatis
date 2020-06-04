package com.tgr.springbootmybatis.shell_compile_annotation;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

public class TestShellScirptEngine {

	@Test
	public void test() throws ScriptException {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		engine.eval("var s = 'sss'");
		//engine.eval("console.log(1111)");
		engine.eval("n=1");
		System.err.println(engine.eval("n+1"));
	}
}
