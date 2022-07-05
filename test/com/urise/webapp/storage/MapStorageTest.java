package com.urise.webapp.storage;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void saveOverflow() {
    }

}
