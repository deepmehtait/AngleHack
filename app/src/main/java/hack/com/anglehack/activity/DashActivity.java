package hack.com.anglehack.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import hack.com.anglehack.R;
import hack.com.anglehack.adapters.FeedItem;
import hack.com.anglehack.adapters.FeedListAdapter;

public class DashActivity extends AppCompatActivity {

    /** The Recycler View in the layout */
    private RecyclerView recyclerView;

    private FeedListAdapter listAdapter;

    /** Feed Items. */
    private ArrayList<FeedItem> feedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateDummyData();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView)findViewById(R.id.meal_log);
        recyclerView.setLayoutManager(llm);

        listAdapter = new FeedListAdapter<FeedItem>(feedItems, this);
        listAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(listAdapter);
    }

    public void populateDummyData() {
        HashMap<String, String> dinner = new HashMap<String, String>();
        dinner.put("mealType", "Dinner");
        dinner.put("monthName", "June");
        dinner.put("date", "25");
        dinner.put("calorieCount", "250");
        dinner.put("mealName", "Bean Burrito");
        dinner.put("mealDescription", "Lorem ipsum dolor sum This is some description stuff. Short Description");
        dinner.put("mealStatus", "OK");
        for(int i = 0; i < 3; i++) {
            FeedItem item = new FeedItem(dinner);
            feedItems.add(item);
        }
    }
}