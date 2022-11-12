package com.urise.webapp.storage;

import com.urise.webapp.storage.serializers.ObjectStreamSerializer;


public class DataStreamFileStorageTest extends AbstractStorageTest {

    public DataStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}
