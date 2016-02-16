package bookmaster.davebilotta.com.bookmaster;

/**
 * Created by Dave on 2/11/2016.
 */
public class Book {
    private int id;
    private int count;
    private String isbn;
    private String title;
    private String[] authors;
    private String desc;
    private int year;
    private String publisher;

    public Book(int id, int count, String isbn, String title, String desc, String[] authors, int year, String publisher) {
        this.id = id;               // internal ID of book
        this.count = count;         // Number of copies of this book
        this.isbn = isbn;           // ISBN-10 or ISBN-13 number
        this.title = title;         // Title of book
        this.desc = desc;           // Description of book
        this.authors = authors;     // Author(s) of book
        this.year = year;           // Year book was published
        this.publisher = publisher; // Publisher of book
    }

    public void update(String title, String desc, int count) {
        // TODO: How many of these should be updated? Create separate method for each?

        this.title = title;
        this.desc = desc;
        this.count = count;
    }

    public int getId() {
        return this.id;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getCount() {
        return this.count;
    }

    public String getAuthors() {
        String authors = "";
        for (int i = 0; i < this.authors.length; i++) {
            if ((i + 1) == this.authors.length) {
                // We're at the end, so just add author's name
                authors += this.authors[i];
            }
            else {
                // Still more after this, so add name plus comma
                authors += this.authors[i] + ", ";
            }
        }
        return authors;
    }

    public int getYear() {
        return year;
    }

    public String getPublisher() {
        return publisher;
    }
  }
