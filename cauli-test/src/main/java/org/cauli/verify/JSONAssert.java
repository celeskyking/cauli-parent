package org.cauli.verify;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.*;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

/**
 * Created by tianqing.wang on 14-3-12
 */
public class JSONAssert {


    public static void assertEquals(String expectedStr, JSONObject actual, boolean strict)
            throws JSONException {
        assertEquals(expectedStr, actual, strict ? JSONCompareMode.STRICT : JSONCompareMode.LENIENT);
    }

    /**
     * Asserts that the JSONObject provided does not match the expected string.  If it is it throws an
     * {@link AssertionError}.
     *
     * @see #assertEquals(String JSONObject, boolean)
     *
     * @param expectedStr Expected JSON string
     * @param actual JSONObject to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
    public static void assertNotEquals(String expectedStr, JSONObject actual, boolean strict)
            throws JSONException {
        assertNotEquals(expectedStr, actual, strict ? JSONCompareMode.STRICT : JSONCompareMode.LENIENT);
    }

    /**
     * Asserts that the JSONObject provided matches the expected string.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actual JSONObject to compare
     * @param compareMode Specifies which comparison mode to use
     * @throws JSONException
     */
    public static void assertEquals(String expectedStr, JSONObject actual, JSONCompareMode compareMode)
            throws JSONException {
        Object expected = JSONParser.parseJSON(expectedStr);
        if (expected instanceof JSONObject) {
            assertEquals((JSONObject)expected, actual, compareMode);
        }
        else {
            throw new AssertionError("Expecting a JSON array, but passing in a JSON object");
        }
    }

    /**
     * Asserts that the JSONObject provided does not match the expected string.  If it is it throws an
     * {@link AssertionError}.
     *
     * @see #assertEquals(String, JSONObject, JSONCompareMode)
     *
     * @param expectedStr Expected JSON string
     * @param actual JSONObject to compare
     * @param compareMode Specifies which comparison mode to use
     * @throws JSONException
     */
    public static void assertNotEquals(String expectedStr, JSONObject actual, JSONCompareMode compareMode)
            throws JSONException {
        Object expected = JSONParser.parseJSON(expectedStr);
        if (expected instanceof JSONObject) {
            assertNotEquals((JSONObject) expected, actual, compareMode);
        }
        else {
            throw new AssertionError("Expecting a JSON array, but passing in a JSON object");
        }
    }

    /**
     * Asserts that the JSONArray provided matches the expected string.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actual JSONArray to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
    public static void assertEquals(String expectedStr, JSONArray actual, boolean strict)
            throws JSONException {
        assertEquals(expectedStr, actual, strict ? JSONCompareMode.STRICT : JSONCompareMode.LENIENT);
    }

    /**
     * Asserts that the JSONArray provided does not match the expected string.  If it is it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actual JSONArray to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
    public static void assertNotEquals(String expectedStr, JSONArray actual, boolean strict)
            throws JSONException {
        assertNotEquals(expectedStr, actual, strict ? JSONCompareMode.STRICT : JSONCompareMode.LENIENT);
    }

    /**
     * Asserts that the JSONArray provided matches the expected string.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actual JSONArray to compare
     * @param compareMode Specifies which comparison mode to use
     * @throws JSONException
     */
    public static void assertEquals(String expectedStr, JSONArray actual, JSONCompareMode compareMode)
            throws JSONException {
        Object expected = JSONParser.parseJSON(expectedStr);
        if (expected instanceof JSONArray) {
            assertEquals((JSONArray) expected, actual, compareMode);
        }
        else {
            throw new AssertionError("Expecting a JSON object, but passing in a JSON array");
        }
    }

    /**
     * Asserts that the JSONArray provided does not match the expected string.  If it is it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actual JSONArray to compare
     * @param compareMode Specifies which comparison mode to use
     * @throws JSONException
     */
    public static void assertNotEquals(String expectedStr, JSONArray actual, JSONCompareMode compareMode)
            throws JSONException {
        Object expected = JSONParser.parseJSON(expectedStr);
        if (expected instanceof JSONArray) {
            assertNotEquals((JSONArray) expected, actual, compareMode);
        }
        else {
            throw new AssertionError("Expecting a JSON object, but passing in a JSON array");
        }
    }

    /**
     * Asserts that the JSONArray provided matches the expected string.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actualStr String to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
    public static void assertEquals(String expectedStr, String actualStr, boolean strict)
            throws JSONException {
        assertEquals(expectedStr, actualStr, strict ? JSONCompareMode.STRICT : JSONCompareMode.LENIENT);
    }

    /**
     * Asserts that the JSONArray provided does not match the expected string.  If it is it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actualStr String to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
    public static void assertNotEquals(String expectedStr, String actualStr, boolean strict)
            throws JSONException {
        assertNotEquals(expectedStr, actualStr, strict ? JSONCompareMode.STRICT : JSONCompareMode.LENIENT);
    }

    /**
     * Asserts that the JSONArray provided matches the expected string.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actualStr String to compare
     * @param compareMode Specifies which comparison mode to use
     * @throws JSONException
     */
    public static void assertEquals(String expectedStr, String actualStr, JSONCompareMode compareMode)
            throws JSONException {
        JSONCompareResult result = JSONCompare.compareJSON(expectedStr, actualStr, compareMode);
        if (result.failed()) {
            throw new AssertionError(result.getMessage());
        }
    }

    /**
     * Asserts that the JSONArray provided does not match the expected string.  If it is it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actualStr String to compare
     * @param compareMode Specifies which comparison mode to use
     * @throws JSONException
     */
    public static void assertNotEquals(String expectedStr, String actualStr, JSONCompareMode compareMode)
            throws JSONException {
        JSONCompareResult result = JSONCompare.compareJSON(expectedStr, actualStr, compareMode);
        if (result.passed()) {
            throw new AssertionError(result.getMessage());
        }
    }

    /**
     * Asserts that the json string provided matches the expected string.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actualStr String to compare
     * @param comparator Comparator
     * @throws JSONException
     */
    public static void assertEquals(String expectedStr, String actualStr, JSONComparator comparator)
            throws JSONException {
        JSONCompareResult result = JSONCompare.compareJSON(expectedStr, actualStr, comparator);
        if (result.failed()) {
            throw new AssertionError(result.getMessage());
        }
    }

    /**
     * Asserts that the json string provided does not match the expected string.  If it is it throws an
     * {@link AssertionError}.
     *
     * @param expectedStr Expected JSON string
     * @param actualStr String to compare
     * @param comparator Comparator
     * @throws JSONException
     */
    public static void assertNotEquals(String expectedStr, String actualStr, JSONComparator comparator)
            throws JSONException {
        JSONCompareResult result = JSONCompare.compareJSON(expectedStr, actualStr, comparator);
        if (result.passed()) {
            throw new AssertionError(result.getMessage());
        }
    }

    /**
     * Asserts that the JSONObject provided matches the expected JSONObject.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expected Expected JSONObject
     * @param actual JSONObject to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
    public static void assertEquals(JSONObject expected, JSONObject actual, boolean strict)
            throws JSONException {
        assertEquals(expected, actual, strict ? JSONCompareMode.STRICT : JSONCompareMode.LENIENT);
    }

    /**
     * Asserts that the JSONObject provided does not match the expected JSONObject.  If it is it throws an
     * {@link AssertionError}.
     *
     * @param expected Expected JSONObject
     * @param actual JSONObject to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
    public static void assertNotEquals(JSONObject expected, JSONObject actual, boolean strict)
            throws JSONException {
        assertNotEquals(expected, actual, strict ? JSONCompareMode.STRICT : JSONCompareMode.LENIENT);
    }

    /**
     * Asserts that the JSONObject provided matches the expected JSONObject.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expected Expected JSONObject
     * @param actual JSONObject to compare
     * @param compareMode Specifies which comparison mode to use
     * @throws JSONException
     */
    public static void assertEquals(JSONObject expected, JSONObject actual, JSONCompareMode compareMode)
            throws JSONException
    {
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, compareMode);
        if (result.failed()) {
            throw new AssertionError(result.getMessage());
        }
    }

    /**
     * Asserts that the JSONObject provided does not match the expected JSONObject.  If it is it throws an
     * {@link AssertionError}.
     *
     * @param expected Expected JSONObject
     * @param actual JSONObject to compare
     * @param compareMode Specifies which comparison mode to use
     * @throws JSONException
     */
    public static void assertNotEquals(JSONObject expected, JSONObject actual, JSONCompareMode compareMode)
            throws JSONException
    {
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, compareMode);
        if (result.passed()) {
            throw new AssertionError(result.getMessage());
        }
    }

    /**
     * Asserts that the JSONArray provided matches the expected JSONArray.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expected Expected JSONArray
     * @param actual JSONArray to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
    public static void assertEquals(JSONArray expected, JSONArray actual, boolean strict)
            throws JSONException {
        assertEquals(expected, actual, strict ? JSONCompareMode.STRICT : JSONCompareMode.LENIENT);
    }

    /**
     * Asserts that the JSONArray provided does not match the expected JSONArray.  If it is it throws an
     * {@link AssertionError}.
     *
     * @param expected Expected JSONArray
     * @param actual JSONArray to compare
     * @param strict Enables strict checking
     * @throws JSONException
     */
    public static void assertNotEquals(JSONArray expected, JSONArray actual, boolean strict)
            throws JSONException {
        assertNotEquals(expected, actual, strict ? JSONCompareMode.STRICT : JSONCompareMode.LENIENT);
    }

    /**
     * Asserts that the JSONArray provided matches the expected JSONArray.  If it isn't it throws an
     * {@link AssertionError}.
     *
     * @param expected Expected JSONArray
     * @param actual JSONArray to compare
     * @param compareMode Specifies which comparison mode to use
     * @throws JSONException
     */
    public static void assertEquals(JSONArray expected, JSONArray actual, JSONCompareMode compareMode)
            throws JSONException {
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, compareMode);
        if (result.failed()) {
            throw new AssertionError(result.getMessage());
        }
    }

    /**
     * Asserts that the JSONArray provided does not match the expected JSONArray.  If it is it throws an
     * {@link AssertionError}.
     *
     * @param expected Expected JSONArray
     * @param actual JSONArray to compare
     * @param compareMode Specifies which comparison mode to use
     * @throws JSONException
     */
    public static void assertNotEquals(JSONArray expected, JSONArray actual, JSONCompareMode compareMode)
            throws JSONException {
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, compareMode);
        if (result.passed()) {
            throw new AssertionError(result.getMessage());
        }
    }

}
