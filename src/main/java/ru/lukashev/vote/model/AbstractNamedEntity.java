package ru.lukashev.vote.model;

public abstract class AbstractNamedEntity {

    protected Integer id;

    protected String name;


    public AbstractNamedEntity (){}

    public AbstractNamedEntity (Integer id, String name){this.id = id; this.name= name;}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
