package com.example.md.givename;


public class Name {
    private int id;
    private String name , comment , gender , national,creator;
    public Name(){
    }
    public Name(int id, String name ,String comment ,String gender ,String national ,String creator){
        this.setId(id);
        this.setName(name);
        this.setComment(comment);
        this.setGender(gender);
        this.setNational(national);
        this.setCreator(creator);
    }
    public Name( String name ,String comment ,String gender ,String national ,String creator){
        this.setName(name);
        this.setComment(comment);
        this.setGender(gender);
        this.setNational(national);
        this.setCreator(creator);
    }
    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getComment(){
        return comment;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
    public String getGender(){
        return gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public String getNational(){
        return national;
    }
    public void setNational(String national){
        this.national=national;
    }
    public String getCreator(){
        return creator;
    }
    public void setCreator(String creator){
        this.creator=creator;
    }

}
