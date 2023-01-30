package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.urise.webapp.ResumeTestData.createResume;
import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();

    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final String FULLNAME_1 = "fullname1";
    public static final String FULLNAME_2 = "fullname2";
    public static final String FULLNAME_3 = "fullname3";
    public static final String FULLNAME_4 = "fullname4";

    public static final Resume R_1;
    public static final Resume R_2;
    public static final Resume R_3;

    private Storage storage;

    static {
        STORAGE_DIR.mkdir();
        R_1 = createResume(UUID_1, FULLNAME_1);
        R_2 = createResume(UUID_2, FULLNAME_2);
        R_3 = createResume(UUID_3, FULLNAME_3);
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(R_1);
        storage.save(R_2);
        storage.save(R_3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume newResume = createResume(UUID_3, FULLNAME_3);
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_3));
    }

    @Test
    public void updateNotExistentResume() {
        Resume r_4 = createResume(UUID_4, FULLNAME_4);
        assertThrows(NotExistStorageException.class, () -> storage.update(r_4));
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
    }

    @Test
    public void deleteNonexistentResume() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = Arrays.asList(R_1, R_2, R_3);
        List<Resume> actual = storage.getAllSorted();
        assertEquals(expected, actual);
    }

    @Test
    public void get() {
        assertEquals(R_3, storage.get(UUID_3));
    }

    @Test
    public void getNotExistentResume() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
    }

    @Test
    public void save() {
        Resume r_4 = createResume(UUID_4, FULLNAME_4);
        storage.save(r_4);
        assertEquals(r_4, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test
    public void saveExistentResume() {
        assertThrows(ExistStorageException.class, () -> storage.save(R_3));
    }

    public Storage getStorage() {
        return storage;
    }
}
