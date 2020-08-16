package com.zeuslearning.automation.selenium.interactions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.zeuslearning.automation.interactions.IExecuteJavascript;

public class ExpectedJsConditions {

	private ExpectedJsConditions() {
		// Utility class
	}

	public static ExpectedCondition<Boolean> scriptValueMatches(final String str, final String script,
			final IExecuteJavascript jsExec) {
		return new ExpectedCondition<Boolean>() {
			private String res = "";

			public Boolean apply(WebDriver driver) {
				try {
					res = (String) jsExec.executeScript(script);
				} catch (Exception e) {
				}
				return str.equalsIgnoreCase(res);
			}
		};
	}

	public static ExpectedCondition<Boolean> scriptValueChanges(final String str, final String script,
			final IExecuteJavascript jsExec) {
		return new ExpectedCondition<Boolean>() {
			private String res = "";

			public Boolean apply(WebDriver driver) {
				try {
					res = (String) jsExec.executeScript(script);
				} catch (Exception e) {
				}
				return !str.equalsIgnoreCase(res);
			}
		};
	}
}
