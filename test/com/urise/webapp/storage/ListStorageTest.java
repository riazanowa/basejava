package com.urise.webapp.storage;

class ListStorageTest extends AbstractStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    public void saveOverflow() {
    }
}
