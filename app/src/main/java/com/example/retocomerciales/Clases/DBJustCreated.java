package com.example.retocomerciales.Clases;

public class DBJustCreated {
    private static DBJustCreated instance;
    private boolean dbJustCreated = false;

    private DBJustCreated(){}

    public static DBJustCreated getInstance(){
        if (instance == null){
            instance = new DBJustCreated();
        }
        return instance;
    }

    public boolean isDbJustCreated() {return dbJustCreated;}
    public void dbJustCreated(){ dbJustCreated = true;}
}
