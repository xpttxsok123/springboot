package com.xp.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

/** 
* DetailController Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 6, 2019</pre> 
* @version 1.0 
*/ 
public class DetailControllerTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: index()
     */
    @Test
    public void testIndex() throws Exception {
        boolean a = 1==1;
        Assert.assertTrue(a);
    }

    /**
     * Method: indexq()
     */
    @Test
    public void testIndexq() throws Exception {
        boolean a = 1==2;
        Assert.assertTrue(a);
    }



} 
