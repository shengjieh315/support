package com.fire.support.model.db;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * 版本2在版本1的基础上，数据库添加一个字段
 */
@Migration(version = 2, database = AppDatabase.class)
public class Migration_2_ItemBean extends AlterTableMigration<ItemBean> {

    public Migration_2_ItemBean(Class<ItemBean> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        // Simple ALTER TABLE migration wraps the statements into a nice builder notation
        addColumn(SQLiteType.INTEGER, "status");
    }
}
