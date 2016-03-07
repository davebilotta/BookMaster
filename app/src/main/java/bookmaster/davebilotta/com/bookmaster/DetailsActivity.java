package bookmaster.davebilotta.com.bookmaster;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent i = getIntent();

        Book book = (Book)i.getSerializableExtra("ObjectID");

        TextView title = (TextView)findViewById(R.id.detail_title);
        title.setText(book.getTitle());

        TextView authors = (TextView)findViewById(R.id.detail_authors);
        authors.setText(book.getAuthors());

        TextView desc = (TextView)findViewById(R.id.detail_desc);
        desc.setText(book.getDesc());

        TextView year = (TextView)findViewById(R.id.detail_year);
        year.setText(book.getYear());

        TextView publisher = (TextView)findViewById(R.id.detail_publisher);
        publisher.setText(book.getPublisher());

    }

}
