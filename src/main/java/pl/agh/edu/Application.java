package pl.agh.edu;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class Application {

    private final static Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {

        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner demo(PersonRepository personRepository, TermRepository termRepository, SubjectRepository subjectRepository) {
        return args -> {
            createSample(personRepository, termRepository, subjectRepository);

            String name = "Jarema Leskiw";
            printFriendsForPerson(personRepository, name);

            String name1 = "Marek Moczol";
            String name2 = "Agnieszka Bujda";
            printPathBetweenNames(personRepository, name1, name2);

            String courseName = "DB";
            printParticipantsForCourse(subjectRepository, courseName);
        };
    }

    private void printParticipantsForCourse(SubjectRepository subjectRepository, String courseName) {
        List<Person> participants = subjectRepository.findCourseParticipants(courseName);
        String listString = participants.stream().map(Object::toString).collect(Collectors.joining(", "));
        log.info(String.format("Course %s participants: %s", courseName, listString));
    }

    private void printPathBetweenNames(PersonRepository personRepository, String name1, String name2) {
        List<Person> list = personRepository.findConnection(name1, name2);
        String listString = list.stream().map(Object::toString).collect(Collectors.joining(", "));
        log.info(String.format("Connection: %s", listString));
    }

    private void printFriendsForPerson(PersonRepository personRepository, String name) {
        Person p = personRepository.findByName(name);
        List<Person> friends = personRepository.findFriendsForName(p.getName());

        String friendsString = friends.stream().map(Object::toString).collect(Collectors.joining(", "));
        log.info(String.format("%s has friends: %s",p.getName(), friendsString));
    }

    public void createSample(PersonRepository personRepository, TermRepository termRepository, SubjectRepository subjectRepository){
        personRepository.deleteAll();
        termRepository.deleteAll();
        subjectRepository.deleteAll();

        Person student1 = new Person("Jarema Leskiw");
        Person student2 = new Person("Marek Moczol");
        Person student3 = new Person("Agnieszka Bujda");
        Person student4 = new Person("Marek Mujek");
        student1.knows(student2);
        student2.knows(student1);
        student1.knows(student3);
        student4.knows(student3);

        Person mateuszPiech = new Person("Mateusz Piech");
        Person robertMarcjan = new Person("Robert Marcjan");
        robertMarcjan.knows(mateuszPiech);
        mateuszPiech.knows(robertMarcjan);

        LocalTime time1 = LocalTime.of(17,50);
        Term term1 = new Term( "DB-A", time1, mateuszPiech);
        term1.addStudent(student1);
        term1.addStudent(student2);

        LocalTime time2 = LocalTime.of(9,35);
        Term term2 = new Term( "DB-B", time2, robertMarcjan);
        term2.addStudent(student3);
        term2.addStudent(student4);

        Subject subject = new Subject("DB", mateuszPiech);
        subject.addTerm(term1);
        subject.addTerm(term2);


        personRepository.save(student1);
        personRepository.save(student2);
        personRepository.save(student3);
        personRepository.save(student4);
        personRepository.save(mateuszPiech);
        personRepository.save(robertMarcjan);

        termRepository.save(term1);
        termRepository.save(term2);

        subjectRepository.save(subject);
    }
}