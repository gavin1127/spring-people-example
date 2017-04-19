package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Gavin on 4/14/17.
 */
@Component
public class PeopleRepository {
    @Autowired
    JdbcTemplate template;

    public List<Person> listPeople(String name) {
        return template.query("SELECT * FROM person Where lower(firstname) " + "LIKE lower(?) " +
                "OR lower(lastname) LIKE lower(?) order by personid "+"LIMIT 200",
                new Object[] {"%"+name+"%", "%"+name+"%"},
                (rs, row) -> new Person(
                        rs.getInt("personid"),
                        rs.getString("title"),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("lastname"),
                        rs.getString("suffix")
                )
        );
    }

    public Person specificPerson(Integer id) {
        return template.queryForObject("SELECT * FROM person Where personid=?" ,
                new Object[]{id},
                (rs, row) -> new Person(
                        rs.getInt("personid"),
                        rs.getString("title"),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("lastname"),
                        rs.getString("suffix")
                )
        );
    }
    public void savePerson(Person person){
        if(person.getPersonId() == null){
            template.update("INSERT INTO person(title,firstname,middlename,lastname,suffix) " +
                            "VALUES (?,?,?,?,?)",
                    new Object[]{person.getTitle(),
                            person.getFirstName(),
                            person.getMiddleName(),
                            person.getLastName(),
                            person.getSuffix()});
        }
        else
            template.update("UPDATE person SET title = ?,firstname=?,middlename=?,lastname=?,suffix=? WHERE personid =?",
                    new Object[]{
                            person.getTitle(),
                            person.getFirstName(),
                            person.getMiddleName(),
                            person.getLastName(),
                            person.getSuffix(),
                            person.getPersonId()});
    }

}
