package com.urise.webapp.storage.serializers;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataStreamSerializer implements SerializeStrategy {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String NULL_HOLDER = "NULL_HOLDER";

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            Set<Map.Entry<ContactType, String>> entrySet = contacts.entrySet();
            writeCollection(dos, entrySet, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            // TODO Implements sections
            Map<SectionType, AbstractSection> sections = resume.getSections();
            writeCollection(dos, sections.entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection listSection = (ListSection) entry.getValue();
                        writeCollection(dos, listSection.getSectionItems(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        ExperienceSection experienceSection = (ExperienceSection) entry.getValue();
                        writeCollection(dos, experienceSection.getExperienceStages(), organization -> {
                            Link link = organization.getLink();
                            if (link == null) {
                                dos.writeUTF(NULL_HOLDER);
                            } else {
                                dos.writeUTF(link.getSiteName());
                                dos.writeUTF(link.getUrl());
                            }
                            writeCollection(dos, organization.getPeriods(), period -> {
                                dos.writeUTF(period.getStartDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                                dos.writeUTF(period.getPosition());
                                dos.writeUTF(period.getDescription());
                            });
                        });
                        break;
                    default:
                        throw new IllegalArgumentException("Data is not correct");
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());

            Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
            readCollection(dis, () -> {
                contacts.put(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });
            resume.setContacts(contacts);

            Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
            readCollection(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (Objects.requireNonNull(sectionType)) {
                    case OBJECTIVE:
                    case PERSONAL:
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> sectionItems = readList(dis, dis::readUTF);
                        sections.put(sectionType, new ListSection(sectionItems));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizations = readList(dis, () -> {
                            Link link = null;
                            String value = dis.readUTF();
                            if (!value.equals(NULL_HOLDER)) {
                                link = new Link(value, dis.readUTF());
                            }
                            List<Period> periods = readList(dis, () ->
                                    new Period(LocalDate.parse(dis.readUTF(), FORMATTER),
                                            LocalDate.parse(dis.readUTF(), FORMATTER),
                                            dis.readUTF(),
                                            dis.readUTF()));
                            return new Organization(link, periods);
                        });
                        sections.put(sectionType, new ExperienceSection(organizations));
                        break;
                    default:
                        throw new IllegalArgumentException("Data is not correct");
                }
            });
            resume.setSections(sections);
            return resume;
        }
    }

    public static <T> void writeCollection(DataOutputStream dos, Collection<T> collection, CustomConsumer<T> c) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            c.accept(item);
        }
    }

    public static <T> List<T> readList(DataInputStream dis, ElementSupplier<T> supplier) throws IOException {
        int count = dis.readInt();
        List<T> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    interface ElementSupplier<T> {
        T get() throws IOException;
    }

    public static <T> void readCollection(DataInputStream dis, CustomSupplier<T> supplier) throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            supplier.get();
        }
    }
}

interface CustomConsumer<T> {
    void accept(T t) throws IOException;
}

interface CustomSupplier<T> {
    void get() throws IOException;
}
