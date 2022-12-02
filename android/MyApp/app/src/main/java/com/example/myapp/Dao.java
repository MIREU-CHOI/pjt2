package com.example.myapp;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Query("select * from tb_sample")
    List<Entity> selectEntityList();

    @Query("select * from tb_sample where id=:id")
    Entity selectEntity(int id);    // id 변수명 동일하게 해줘야 됨

    @Insert
    void insertEntity(Entity entity);


}
