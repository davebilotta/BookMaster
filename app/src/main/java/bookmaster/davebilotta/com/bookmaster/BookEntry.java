package bookmaster.davebilotta.com.bookmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Dave on 2/16/2016.
 *
 * This is ued to create a new Book (manual entry) or update an existing entry
 */
public class BookEntry extends Activity  {

    EditText title,desc,authors,year,publisher, isbn;
    Button cancel,save;
    static DB db;
    String id;                   // id of the item we're editing
    Book book;                // Book we're editing
    private boolean editMode; // true if editing an existing entry, else false

       @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_entry);
        db = new DB(this);

        //db.open();

         Intent i = getIntent();
         Book book = (Book)i.getSerializableExtra("ObjectID");

         Serializable extra = i.getSerializableExtra("id");
         if (extra != null) {
             id = (String) extra;
         }

         if (i.getSerializableExtra("EditMode") == null) {
             editMode = false;
         }
           else {
             editMode = true;
         }
         Utils.log("EDITMODE IS " + editMode);

         if (book != null) {
             TextView title = (TextView) findViewById(R.id.titleText);
             title.setText(book.getTitle());

             TextView authors = (TextView) findViewById(R.id.authorsText);
             authors.setText(book.getAuthors());

             TextView desc = (TextView) findViewById(R.id.descText);
             desc.setText(book.getDesc());

             TextView year = (TextView) findViewById(R.id.yearText);
             year.setText(book.getYear());

             TextView publisher = (TextView) findViewById(R.id.publisherText);
             publisher.setText(book.getPublisher());

             TextView isbn = (TextView) findViewById(R.id.isbnText);
             isbn.setText(book.getIsbn());
       }

        else {
             title = (EditText) findViewById(R.id.titleText);
         }

        // Button components
        cancel = (Button) findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookEntry.this, ViewList.class);
                startActivity(i);
            }});

        save = (Button) findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEntry();
                finishActivity(1);
                Intent i = new Intent(BookEntry.this, ViewList.class);
                startActivity(i);
            }
        });
    }

    public void saveEntry() {
        //String title, String desc, String authors, String year, String publisher, String isbn) {
        title = (EditText) findViewById(R.id.titleText);
        desc = (EditText) findViewById(R.id.descText);
        authors = (EditText) findViewById(R.id.authorsText);
        year = (EditText) findViewById(R.id.yearText);
        publisher = (EditText) findViewById(R.id.publisherText);
        isbn = (EditText) findViewById(R.id.isbnText);

        try {
            db.open();

            if (editMode) {
                updateBook();
            } else {
                createBook();

            }

            db.close();

            // Now clear everything
            /*title.setText("");
            desc.setText("");
            authors.setText("");
            year.setText("");
            publisher.setText("");
            isbn.setText(""); */

            // TODO: Show Snackbar so user knows item was saved and they can keep adding more
            // TODO: Allow them to Undo?
            if (!editMode) Utils.showUserMessage(this, "Book saved");
        } catch (Exception e) {
            Utils.log(e.getMessage());
        }
    }


        private void createBook() {
            db.insertBook(
                    title.getText().toString(),
                    desc.getText().toString(),
                    authors.getText().toString(),
                    year.getText().toString(),
                    publisher.getText().toString(),
                    isbn.getText().toString());
        }


        private void updateBook() {
            db.updateBook(
                id,
                title.getText().toString(),
                desc.getText().toString(),
                authors.getText().toString(),
                year.getText().toString(),
                publisher.getText().toString(),
                isbn.getText().toString());
    }

}
