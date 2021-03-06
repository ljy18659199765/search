package com.search.service;

import com.search.AbstractSearchTest;
import com.search.config.ActionNames;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class InquireEmployeeServiceImplJUnitTest extends AbstractSearchTest {

    public InquireEmployeeServiceImplJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

    @Test
    public void test() {
        Map<String, String> parameterMap = new HashMap<String, String>(4, 1);
        parameterMap.put("pageIndex", "2");
        parameterMap.put("pageSize", "15");
        parameterMap.put("tag", "java");
        String result = this.testHandler.execute(ActionNames.INQUIRE_EMPLOYEE, parameterMap);
        System.out.println(result);
    }
}
