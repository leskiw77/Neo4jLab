package pl.agh.edu;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Term {
    @GraphId
    public Long id;

    public String name;

    public LocalTime time;

    @Relationship(type = "TEACHER", direction = Relationship.OUTGOING)
    public Person preceptor;

    @Relationship(type = "ATTEND", direction = Relationship.OUTGOING)
    public Set<Person> students;

    public Term(String name, LocalTime time, Person preceptor) {
        this.name = name;
        this.time = time;
        this.preceptor = preceptor;
    }

    public Term(){
    }

    public void addStudent(Person p){
        if (students == null) {
            students = new HashSet<>();
        }
        students.add(p);
    }

    @Override
    public String toString(){
        return String.format("%s - %s",this.name, this.time.toString());
    }
}
