package bookmaster.davebilotta.com.bookmaster;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewList extends ListActivity {

    DB db;
    BookListAdapter adapter;

    private class BookView {
        public String title;
        public String desc;
        public String authors;
        public String year;
        public String publishers;
        public String isbn;

        public BookView(String title, String desc, String authors, String year, String publishers, String isbn) {
            this.title = title;
            this.desc = desc;
            this.authors = authors;
            this.year = year;
            this.publishers = publishers;
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
                buildBooks();
        }

        public void buildBooks() {
            Cursor c = db.getBooks();
            // TODO: Need to fix this later, method is deprecated

            int count = 0;
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
                v = inflater.inflate(R.layout.view_list_row,null);
                holder = new BookViewHolder();
                holder.title = (TextView)v.findViewById(R.id.row_title);
                holder.authors = (TextView) v.findViewById(R.id.row_authors);
                holder.desc = (TextView)v.findViewById(R.id.row_desc);
                holder.year = (TextView)v.findViewById(R.id.row_year);
                holder.publisher = (TextView)v.findViewById(R.id.row_publisher);
                v.setTag(holder);
            }
            else {
                holder = (BookViewHolder) v.getTag();
            }

           holder.books = getItem(position);

            holder.title.setText(holder.books.title);
            holder.authors.setText(holder.books.authors);
            holder.desc.setText(holder.books.desc);
            holder.year.setText(holder.books.year);
            holder.publisher.setText(holder.books.publishers);

            v.setTag(holder);
            return v;
        }

        public class BookViewHolder {
            BookView books;
            TextView title,authors,desc,year,publisher;

        }
    }
}
