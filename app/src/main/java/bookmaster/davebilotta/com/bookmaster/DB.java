package bookmaster.davebilotta.com.bookmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Created by Dave on 2/11/2016.
 */
public class DB {
    private SQLiteDatabase db;
    private final Context context;
    private final DBHelper helper;

    public DB(Context c) {
        context = c;
        helper = new DBHelper(context, DBShared.DATABASE_NAME, null, DBShared.DATABASE_VERSION);
    }

    public void close() {
        db.close();
    }

    public void open() throws SQLiteException {
        try {
            db = helper.getWritableDatabase();
        }
        catch (SQLiteException e) {
            Utils.log("Open Exception Caught",e.getMessage());
            db = helper.getReadableDatabase();
        }
    }

    // Call this, passing in a book that would have been created on the edit/create screen
    /*public long insertEntry(Book book) {
        try {
            ContentValues entry = new ContentValues();
            entry.put(DBShared.ITEM_TITLE,book.getTitle());
            entry.put(DBShared.ITEM_COUNT,1);
            entry.put(DBShared.ITEM_ISBN,book.getIsbn());
            entry.put(DBShared.ITEM_DESC,book.getDesc());
            entry.put(DBShared.ITEM_AUTHORS,book.getAuthors());
            entry.put(DBShared.ITEM_YEAR,book.getYear());
            entry.put(DBShared.ITEM_PUBLISHER,book.getPublisher());
            entry.put(DBShared.CREATE_DATE,java.lang.System.currentTimeMillis());

            return db.insert(DBShared.TABLE_NAME,null,entry);
        }
        catch (SQLiteException e) {
            Utils.log("Insert Exception found",e.getMessage());
            return -1;
        }
    } */

    public long insertBook(String title, String desc, String authors, String year, String publisher, String isbn) {
        try {
            ContentValues entry = new ContentValues();
            entry.put(DBShared.ITEM_TITLE, title);
            entry.put(DBShared.ITEM_COUNT, 1);
            entry.put(DBShared.ITEM_DESC, desc);

            // TODO: Figure this out - how will multiples be returned by api?
            entry.put(DBShared.ITEM_AUTHORS, authors);
            entry.put(DBShared.ITEM_YEAR, year);
            entry.put(DBShared.ITEM_PUBLISHER, publisher);
            entry.put(DBShared.ITEM_ISBN, isbn);

            entry.put(DBShared.CREATE_DATE, System.currentTimeMillis());

            return db.insert(DBShared.TABLE_NAME, null, entry);
        } catch (SQLiteException e) {
            Utils.log("Insert Exception found", e.getMessage());
            return -1;
        }
    }

    public Cursor getBooks() {
        Cursor c = db.query(DBShared.TABLE_NAME,null,null,null,null,null,null);
        return c;
    }

    public int getNextID() {
        // TODO: Fix this later - for now just return the time in ms
        return (int) System.currentTimeMillis();
    }



}
