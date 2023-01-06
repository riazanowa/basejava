package com.urise.webapp.storage;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(new SqlStorage());
    }

    @Override
    public void updateNotExistentResume() {
    }

    @Override
    public void deleteNonexistentResume() {
    }

    @Override
    public void saveExistentResume() {
    }
}
