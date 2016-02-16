package bookmaster.davebilotta.com.bookmaster;

/**
 * Created by Dave on 2/11/2016.
 */
public class DBShared {
    public static final String DATABASE_NAME = "datastorage";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "books";
    public static final String KEY_ID = "_id";
    public static final String CREATE_DATE = "creationdate";

    public static final String ITEM_COUNT = "count";         // Number of copies of this book
    public static final String ITEM_ISBN = "isbn";           // ISBN-10 or ISBN-13 number
    public static final String ITEM_TITLE = "title";         // Title of book
    public static final String ITEM_DESC = "desc";           // Description of book
    public static final String ITEM_AUTHORS = "authors";     // Author(s) of book
    public static final String ITEM_YEAR = "year";           // Year book was published
    public static final String ITEM_PUBLISHER = "publisher"; // Publisher of book


}
