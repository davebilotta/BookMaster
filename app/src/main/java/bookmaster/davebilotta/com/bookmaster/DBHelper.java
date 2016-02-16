package bookmaster.davebilotta.com.bookmaster;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dave on 2/11/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String CREATE_TABLE="create table " +
            DBShared.TABLE_NAME + " (" +
            DBShared.KEY_ID + " integer primary key autoincrement, " +
            DBShared.ITEM_TITLE + " text not null, " +
            DBShared.ITEM_COUNT + " integer, " +
            DBShared.ITEM_ISBN + " text not null, " +
            DBShared.ITEM_DESC + " text not null, " +
            DBShared.ITEM_AUTHORS + " text not null, " +
            DBShared.ITEM_YEAR + " text not null, " +
            DBShared.ITEM_PUBLISHER + " text not null, " +
            DBShared.CREATE_DATE + " long);";

    public DBHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super (context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Utils.log("DBHelper onCreate, creating tables");
        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLiteException e) {
            Utils.log("Create table exception " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Utils.log("OnUpgrade","Upgrading from version " + oldVersion + " to " + newVersion
        + ", which will destroy all old data");

            db.execSQL("drop table if exists " + DBShared.TABLE_NAME);
            onCreate(db);
    }
}
