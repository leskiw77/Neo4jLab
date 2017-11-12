package pl.agh.edu;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Person {

    @GraphId private Long id;

    private String name;

    private Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    @Relationship(type = "KNOWS", direction = Relationship.OUTGOING)
    public Set<Person> knowSet;

    public void knows(Person person) {
        if (knowSet == null) {
            knowSet = new HashSet<>();
        }
        knowSet.add(person);
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}