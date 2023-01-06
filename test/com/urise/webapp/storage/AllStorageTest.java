package com.urise.webapp.storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageTest.class,
        SortedArrayStorageTest.class,
        ResumeMapStorageTest.class,
        JsonPathStorageTest.class,
        SqlStorageTest.class
})
public class AllStorageTest {
}
