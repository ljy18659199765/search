package com.search.service;

import com.search.config.ActionNames;
import com.wolf.framework.remote.FrameworkSessionBeanRemote;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aladdin
 */
public class SearchRemoteJUnitTest {

    public SearchRemoteJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private FrameworkSessionBeanRemote getRemote() {
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        props.setProperty("org.omg.CORBA.ORBInitialHost", "192.168.19.219");
        props.setProperty("org.omg.CORBA.ORBInitialPort", "5837");
        FrameworkSessionBeanRemote remote;
        try {
            InitialContext ic = new InitialContext(props);
            remote = (FrameworkSessionBeanRemote) ic.lookup("com.wolf.framework.remote.FrameworkSessionBeanRemote");

        } catch (NamingException e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
        return remote;
    }
    //

    @Test
    public void testTimerManager() {
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("timerState", "TIMER_UPDATE_EMPLOYEE_STATE");
        parameterMap.put("option", "start");
        FrameworkSessionBeanRemote remote = this.getRemote();
        String result = remote.execute(ActionNames.TIMER_MANAGE, parameterMap);
        System.out.println(result);
        parameterMap.put("timerState", "TIMER_UPDATE_TAG_TOTAL_STATE");
        result = remote.execute(ActionNames.TIMER_MANAGE, parameterMap);
        System.out.println(result);
    }
    
//    @Test
    public void testRemote() {
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        FrameworkSessionBeanRemote remote = this.getRemote();
        String result = remote.execute(ActionNames.CONSOLE_PARSE_ALL_EMPLOYEE_TAG, parameterMap);
        System.out.println(result);
    }
}
