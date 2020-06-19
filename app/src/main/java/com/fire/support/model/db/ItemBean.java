package com.fire.support.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class, name = ItemBean.NAME, insertConflict = ConflictAction.REPLACE)
public class ItemBean extends BaseModel {

    public static final String NAME = "ItemBean";


    //自增ID
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String name;

    @Column
    public int status;

}
