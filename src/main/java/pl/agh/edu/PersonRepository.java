package pl.agh.edu;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends GraphRepository<Person> {
    Person findByName(String name);

    @Query("match (p1:Person {name:{name}}) -[:KNOWS]->(p2:Person) return p2")
    List<Person> findFriendsForName(@Param("name") String name);

    @Query("MATCH p=shortestPath((p1:Person {name:{name1}})-[*]-(p2:Person{name:{name2}})) RETURN p")
    List<Person> findConnection(@Param("name1") String name1, @Param("name2") String name2);
}