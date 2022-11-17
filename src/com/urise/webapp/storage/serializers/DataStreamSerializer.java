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
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            // TODO Implements sections
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        dos.writeUTF(sectionType.name());
                        ListSection listSection = (ListSection) entry.getValue();
                        List<String> items = listSection.getSectionItems();
                        dos.writeInt(items.size());
                        for (String item : items) {
                            dos.writeUTF(item);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        dos.writeUTF(sectionType.name());
                        ExperienceSection experienceSection = (ExperienceSection) entry.getValue();
                        List<Organization> experienceStages = experienceSection.getExperienceStages();
                        dos.writeInt(experienceStages.size());
                        for (Organization organization : experienceStages) {
                            Link link = organization.getLink();
                            if (link == null) {
                                dos.writeUTF(NULL_HOLDER);
                            } else {
                                dos.writeUTF(link.getSiteName());
                                dos.writeUTF(link.getUrl());

                            }
                            List<Period> periods = organization.getPeriods();
                            dos.writeInt(periods.size());
                            for (Period period : periods) {
                                dos.writeUTF(period.getStartDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                                dos.writeUTF(period.getPosition());
                                dos.writeUTF(period.getDescription());
                            }
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Data is not correct");
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
            for (int i = 0; i < size; i++) {
                ContactType contactType = ContactType.valueOf(dis.readUTF());
                String value = dis.readUTF();
                contacts.put(contactType, value);
            }
            resume.setContacts(contacts);
            int sizeOfSections = dis.readInt();
            Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
            for (int i = 0; i < sizeOfSections; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (Objects.requireNonNull(sectionType)) {
                    case OBJECTIVE:
                    case PERSONAL:
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int sizeOfListSection = dis.readInt();
                        List<String> sectionItems = new ArrayList<>();
                        for (int j = 0; j < sizeOfListSection; j++) {
                            sectionItems.add(dis.readUTF());
                        }
                        sections.put(sectionType, new ListSection(sectionItems));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int sizeOfExperienceSection = dis.readInt();
                        List<Organization> organizations = new ArrayList<>();
                        for (int j = 0; j < sizeOfExperienceSection; j++) {
                            Link link = null;
                            String value = dis.readUTF();
                            if (!value.equals(NULL_HOLDER)) {
                                String siteName = value;
                                String url = dis.readUTF();
                                link = new Link(siteName, url);
                            }
                            int sizeOfPeriods = dis.readInt();
                            List<Period> periods = new ArrayList<>();
                            for (int k = 0; k < sizeOfPeriods; k++) {
                                LocalDate startDate = LocalDate.parse(dis.readUTF(), FORMATTER);
                                LocalDate endDate = LocalDate.parse(dis.readUTF(), FORMATTER);
                                String position = dis.readUTF();
                                String description = dis.readUTF();
                                periods.add(new Period(startDate, endDate, position, description));
                            }
                            organizations.add(new Organization(link, periods));
                        }
                        sections.put(sectionType, new ExperienceSection(organizations));
                        break;
                    default:
                        throw new IllegalArgumentException("Data is not correct");
                }
            }
            resume.setSections(sections);
            return resume;
        }
    }
}
