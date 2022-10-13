package com.urise.webapp.storage;

public class ObjectStreamPathStorage  {
// public class ObjectStreamPathStorage extends AbstractPathStorage {
//     protected ObjectStreamPathStorage(String dir) {
//        super(dir);
//    }
//
//    @Override
//    protected void doWrite(Resume r, OutputStream os) throws IOException {
//        ObjectOutputStream oos = new ObjectOutputStream(os);
//        oos.writeObject(r);
//    }
//
//    @Override
//    protected Resume doRead(InputStream inputStream) throws IOException {
//        Resume resume = null;
//        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
//            resume = (Resume) ois.readObject();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return resume;
//    }
}
