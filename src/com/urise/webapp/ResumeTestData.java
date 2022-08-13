package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
        contacts.put(ContactType.TELEPHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "skype:grigory.kislin");
        contacts.put(ContactType.EMAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.STACKOVERFLOW_PROFILE, "https://stackoverflow.com/users/548473");
        contacts.put(ContactType.HOME_PAGE, "http://gkislin.ru/");

        resume.setContacts(contacts);

        Map<SectionType, AbstractSection> sections = new EnumMap<SectionType, AbstractSection>(SectionType.class);
        sections.put(SectionType.OBJECTIVE, new ListSection(Collections.singletonList("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям")));
        sections.put(SectionType.PERSONAL, new ListSection(Collections.singletonList("Аналитический склад ума, сильная логика, креативность, инициативность." +
                " Пурист кода и архитектуры.")));
        sections.put(SectionType.ACHIEVEMENT, new ListSection(
                Arrays.asList("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет",
                        "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.",
                        "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                        "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                        "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                        "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                        "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.")
        ));

        sections.put(SectionType.QUALIFICATIONS, new ListSection(
                Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                        "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                        "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB",
                        "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy",
                        "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
                        "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                        "Python: Django.",
                        "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                        "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                        "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                        "Инструменты: Maven + plugin development, Gradle, настройка Ngnix",
                        "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer",
                        "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования",
                        "Родной русский, английский \"upper intermediate\""

                )
        ));
        List<ExperienceStage> workExperience = Arrays.asList(
                new ExperienceStage(LocalDate.of(2013, Month.APRIL, 1), LocalDate.now(), "Java Online Projects", "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."),
                new ExperienceStage(LocalDate.of(2014, Month.OCTOBER, 1), LocalDate.of(2016, Month.JANUARY, 1), "Wrike", "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."),
                new ExperienceStage(LocalDate.of(2012, Month.APRIL, 1), LocalDate.of(2014, Month.APRIL, 1), "RIT Center", "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"),
                new ExperienceStage(LocalDate.of(2010, Month.DECEMBER, 1), LocalDate.of(2012, Month.APRIL, 1), "Luxoft (Deutsche Bank)", "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."),
                new ExperienceStage(LocalDate.of(2008, Month.JUNE, 1), LocalDate.of(2010, Month.DECEMBER, 1), "Yota", "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"),
                new ExperienceStage(LocalDate.of(2007, Month.MARCH, 1), LocalDate.of(2008, Month.JUNE, 1), "Enkata", "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."),
                new ExperienceStage(LocalDate.of(2005, Month.JANUARY, 1), LocalDate.of(2007, Month.FEBRUARY, 1), "Siemens AG", "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."),
                new ExperienceStage(LocalDate.of(1997, Month.SEPTEMBER, 1), LocalDate.of(2005, Month.JANUARY, 1), "Alcatel", "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")
        );
        ExperienceSection experienceSection = new ExperienceSection(workExperience);
        sections.put(SectionType.EXPERIENCE, experienceSection);

        List<ExperienceStage> educationStages = Arrays.asList(
                new ExperienceStage(LocalDate.of(2013, Month.MARCH, 1), LocalDate.of(2013, Month.MAY, 1), "Coursera", null, "Functional Programming Principles in Scala' by Martin Odersky"),
                new ExperienceStage(LocalDate.of(2011, Month.MARCH, 1), LocalDate.of(2011, Month.APRIL, 1), "Luxoft", null, "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'"),
                new ExperienceStage(LocalDate.of(2005, Month.JANUARY, 1), LocalDate.of(2005, Month.APRIL, 1), "Siemens AG", null, "3 месяца обучения мобильным IN сетям (Берлин)"),
                new ExperienceStage(LocalDate.of(1997, Month.SEPTEMBER, 1), LocalDate.of(1998, Month.MARCH, 1), "Alcatel", null, "6 месяцев обучения цифровым телефонным сетям (Москва)"),
                new ExperienceStage(LocalDate.of(1993, Month.SEPTEMBER, 1), LocalDate.of(1996, Month.JULY, 1), "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", null, "Аспирантура (программист С, С++)"),
                new ExperienceStage(LocalDate.of(1987, Month.SEPTEMBER, 1), LocalDate.of(1993, Month.JULY, 1), "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", null, "Инженер (программист Fortran, C)"),
                new ExperienceStage(LocalDate.of(1984, Month.APRIL, 1), LocalDate.of(1987, Month.MARCH, 1), "Заочная физико-техническая школа при МФТИ", null, "Закончил с отличием")
        );
        ExperienceSection educationSection = new ExperienceSection(educationStages);
        sections.put(SectionType.EDUCATION, educationSection);

        resume.setSections(sections);

        System.out.println(resume.getFullName() + "\n");

        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }

        System.out.println();

        for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
            System.out.println(entry.getKey().getTitle() + "\n" + entry.getValue());
        }
    }
}
