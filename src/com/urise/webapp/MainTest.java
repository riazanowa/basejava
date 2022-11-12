package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.FileStorage;
import com.urise.webapp.storage.serializers.DataStreamSerializer;

import java.io.File;

public class MainTest {
    protected static final File STORAGE_DIR = new File("C:\\Users\\katri\\myProjects\\resume-store");

    public static void main(String[] args) {
        FileStorage fileStorage = new FileStorage(STORAGE_DIR, new DataStreamSerializer());
        fileStorage.save(ResumeTestData.createResume("uuid4", "Григорий Кислин"));
        Resume resume = fileStorage.get("uuid4");
        ResumeTestData.printResume(resume);
    }
}
