package bookmaster.davebilotta.com.bookmaster;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewList extends ListActivity implements AdapterView.OnItemClickListener,
        PopupMenu.OnMenuItemClickListener {

    DB db;
    BookListAdapter adapter;
    View lastClicked;
    long lastId;

    private class BookView {
        public String title;
        public String desc;
        public String authors;
        public String year;
        public String publisher;
        public String isbn;

        //ISBN13 format is:
        //    N-NNN-NNNNN-N (e.g. 0-394-82472-5)

        // ISBN13 format is:
        //    NNN-N-NNNN-NNNN-N (e.g. 978-0-7172-6059-1)

        public BookView(String title, String desc, String authors, String year, String publisher, String isbn) {
            this.title = title;
            this.desc = desc;
            this.authors = authors;
            this.year = year;
            this.publisher = publisher;
            this.isbn = isbn;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DB(this);
        db.open();
        setContentView(R.layout.content_view_list);

        super.onCreate(savedInstanceState);
        adapter = new BookListAdapter(this);
        this.setListAdapter(adapter);

        // TODO: Re-introduce Floating Action Bar
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */
    }

    private class BookListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<BookView> books;
        public BookListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            books = new ArrayList<BookView>();
            buildBooksTest();

        }

        public ArrayList<BookView> getBooks() {
            return this.books;
        }

        public void buildBooksTest() {
            /* Test method used to populate view screen with date */
            // Title, Description, Authors, Year, Publisher, ISBN
            //

            String[][] bookList = {
                    {"The Cat In The Hat","Children's Classic Book","Theodor Geisel (Dr. Seuss)","1957","Random House","978-0-7172-6059-1"},
                    {"How The Grinch Stole Christmas!","Children's Classic Book","Theodor Geisel (Dr. Seuss)","1957","Random House","0-394-80079-6"},
                    {"Pride and Prejudice","","Jane Austen","1813","",""},
                    {"To Kill A Mockingbird","Classic Book","Harper Lee","1960","Harper Classics","978-0-7172-6059-1"},
                    {"The Great Gatsby","","F. Scott Fitzgerald","1925","Random House",""},
                    {"Jane Eyre","Classic","Charlotte Bronte","1827","",""},
                    {"Charlie and the Chocolate Factory","Lukas' Favorite","Roald Dahl","1964","Alfred A. Knopf, Inc.",""},
                    {"The Mouse and the Motorcycle","Children's Classic","Beverly Cleary","1965","William Morrow",""},
                    {"Charlie and the Great Glass Elevator","Sequel to Charlie and the Chocolate Factory","Roald Dahl","1972","Alfred A. Knopf, Inc.","0-394-82472-5"},

            };

            // TODO: Get date it was entered


            for (int i = 0; i < bookList.length; i++) {
                String[] b = bookList[i];
                books.add(new BookView(b[0],b[1],b[2],b[3],b[4],b[5]));
            }

        }

        public void buildBooks() {
            Cursor c = db.getBooks();
            int count = 0;

            // TODO: Need to fix this later, method is deprecated
            startManagingCursor(c);

            if (c.moveToFirst()) {
                do {
                    String title = c.getString(c.getColumnIndex(DBShared.ITEM_TITLE));
                    String desc = c.getString(c.getColumnIndex(DBShared.ITEM_DESC));
                    String authors = c.getString(c.getColumnIndex(DBShared.ITEM_AUTHORS));
                    String year = c.getString(c.getColumnIndex(DBShared.ITEM_YEAR));
                    String publisher = c.getString(c.getColumnIndex(DBShared.ITEM_PUBLISHER));
                    String isbn = c.getString(c.getColumnIndex(DBShared.ITEM_ISBN));

                    // TODO: Get date it was entered

                    BookView temp = new BookView(title,desc,authors,year,publisher,isbn);
                    books.add(temp);
                    count++;
                }
                while (c.moveToNext());

            }
            Utils.log("There are " + count + " books");
        }

        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public BookView getItem(int position) {
            return books.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final BookViewHolder holder;
            View v = convertView;

            if ((v == null) || (v.getTag() == null)) {
                v = inflater.inflate(R.layout.view_list_row, null);
                holder = new BookViewHolder();
                holder.title = (TextView)v.findViewById(R.id.row_title);
                holder.authors = (TextView) v.findViewById(R.id.row_authors);
                /* holder.desc = (TextView)v.findViewById(R.id.row_desc);
                holder.year = (TextView)v.findViewById(R.id.row_year);
                holder.publisher = (TextView)v.findViewById(R.id.row_publisher); */
                v.setTag(holder);
            }
            else {
                holder = (BookViewHolder) v.getTag();

            }

            holder.books = getItem(position);
            holder.title.setText(holder.books.title);
            holder.authors.setText(holder.books.authors);
            /* holder.desc.setText(holder.books.desc);
            holder.year.setText(holder.books.year);
            holder.publisher.setText(holder.books.publishers); */


            v.setTag(holder);
            return v;
        }

        public class BookViewHolder {
            BookView books;
            TextView title,authors,desc,year,publisher;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setSelected(true);


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // Utils.log("click1");

        if (lastClicked != null) {
            LinearLayout layout = (LinearLayout)lastClicked.findViewById(R.id.row_inflate);
            layout.removeAllViews();
        }

        LinearLayout inflate = (LinearLayout)v.findViewById(R.id.row_inflate);
        View inflatedView= getLayoutInflater().inflate(R.layout.view_list_row_selected, null);
        inflate.addView(inflatedView);

        lastClicked = v;
        lastId = id;
        v.setSelected(true);

        //BookListAdapter.BookViewHolder b = (BookListAdapter.BookViewHolder)getListAdapter().getItem(position);
        //Utils.log("You clicked on " + b.books.title);
        // getSelectedItemPosition();

        Utils.log("You clicked on " + adapter.getBooks().get((int) id).title);

    }

    public Book createBook() {
        BookView view = adapter.getBooks().get((int)lastId);

        Book book = new Book((int)lastId,1,view.isbn,view.title,view.desc,view.authors,view.year,view.publisher);

        return book;
    }

    public void iconOnClick(View v) {
        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.selected_popup);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        View v = findViewById(android.R.id.content);

        switch (item.getItemId()) {
            case R.id.popup_details:
                detailsOnClick(v);
                break;

            case R.id.popup_edit:
                editOnClick(v);
                break;
            case R.id.popup_delete:
                deleteOnClick(v);
                break;
            default:
        }
        return true;
    }

    public void detailsOnClick(View v) {
        Intent i = new Intent(this, DetailsActivity.class);
        i.putExtra("id",lastId);
        i.putExtra("ObjectID",createBook());

        startActivity(i);

    }

    public void editOnClick(View v) {
        Utils.log("Editing " + adapter.getBooks().get((int) lastId).title);

        // TODO: Launch BookEntryActivity
        Intent i = new Intent(this, BookEntry.class);
        i.putExtra("id",lastId);
        i.putExtra("ObjectID", createBook());

        startActivity(i);

    }

    public void deleteOnClick(View v) {
        Utils.log("Deleting " + adapter.getBooks().get((int)lastId).title);

    }

}
