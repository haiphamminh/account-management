package org.management.account.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.management.account.constant.Const.JOIN_DELIMITER;
import static org.management.account.constant.Const.SPACE_DELIMITER;
import static org.management.account.utils.StringUtils.join;
import static org.management.account.utils.StringUtils.parse;

@RunWith(JUnit4.class)
public class StringUtilsTest {

    @Test(expected = IllegalArgumentException.class)
    public void parseFailureWithEmptyParamThrowException() {
        parse("", SPACE_DELIMITER);
    }

    @Test
    public void parseSuccessfully() {
        String[] separatedStr = parse("QUERY CEO", SPACE_DELIMITER);
        Assert.assertArrayEquals(new String[]{"QUERY", "CEO"}, separatedStr);
    }

    @Test
    public void joinSuccessfully() {
        Set<String> set = new HashSet<>(Arrays.asList("a", "b", "c"));
        String result = join(set, JOIN_DELIMITER);
        Assert.assertTrue(result.equals("a, b, c"));
    }
}
