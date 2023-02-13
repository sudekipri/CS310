package com.newsapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.newsapp.request.model.category.Categories;
import com.newsapp.request.model.category.Item;
import com.newsapp.request.newslist.NewsList;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {
    static ViewPager2 viewPager2;
    public static int categoryNumber = 0;
    public static List<Item> categoryList = null;
    public static NewsList newsList = new NewsList();

    public static Context context;
    public static ListView listView;
    public static FragmentManager fm;

    AtomicReference<Categories> categories = new AtomicReference<>();
    TabLayout tabLayout;


    public MainActivity() {
        super();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);
        context = getApplicationContext();
        listView = new ListView(this);
        fm = getSupportFragmentManager();
        tabLayout.removeAllTabs();
        viewPager2 = findViewById(R.id.viewPager2);
        ProgressBar spinner = findViewById(R.id.progressBar2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cs310 News");


        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://10.3.0.14:8080/newsapp/getallnewscategories";
            spinner.setVisibility(View.VISIBLE);


            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        System.out.println(response);
                        categories.set(new Gson().fromJson(response, Categories.class));
                        if (categories.get() != null) {
                            categoryList = categories.get().getItems();
                            Collections.sort(categoryList);
                            categoryNumber = categories.get().getItems().size();
                            new TabLayoutMediator(tabLayout, viewPager2,
                                    (tab, position) -> tab.setText(categories.get().getItems().get(position).getName())).attach();
                        }
                    }, Throwable::printStackTrace);

            queue.add(stringRequest);
            spinner.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
            spinner.setVisibility(View.GONE);
        }


        MyPagerAdapter adapter = new MyPagerAdapter(this);
        viewPager2.setAdapter(adapter);
        getSupportFragmentManager();

        MyPagerAdapter sa = new MyPagerAdapter(fm, getLifecycle());
        viewPager2.setAdapter(sa);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        viewPager2.setAdapter(sa);
        tabLayout.setSmoothScrollingEnabled(true);
        super.onCreate(savedInstanceState);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public static class MyFragment extends Fragment {

        public static final String ARG_PAGE = "arg_page";
        private View rootView;
        private RecyclerView recyclerView;

        private MyFragment() {

        }

        public static MyFragment newInstance(int pageNumber) {
            MyFragment myFragment = new MyFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(ARG_PAGE, pageNumber);
            myFragment.setArguments(arguments);

            return myFragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Bundle argument = getArguments();
            int pageNumber = argument.getInt(ARG_PAGE);

            rootView = inflater.inflate(R.layout.news_item, container, false);
            recyclerView = rootView.findViewById(R.id.recycler_view_bookmarks);

            DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(divider);

            try {
                int id = 1;
                if (categoryList != null) {
                    id = categoryList.get(pageNumber).getId();
                }
                RequestQueue queue = Volley.newRequestQueue(context);
                String url = "http://10.3.0.14:8080/newsapp/getbycategoryid/" + id;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        response -> {
                            newsList = new Gson().fromJson(response, NewsList.class);

                            System.out.println(newsList);
                            if (newsList != null && newsList.getItems() != null) {
                                NewsListAdapter adapter = new NewsListAdapter(newsList, getActivity());
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                                recyclerView.addItemDecoration(divider);

                            } else {
                                NewsListAdapter adapter = new NewsListAdapter(null, getActivity());
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                                recyclerView.addItemDecoration(divider);
                                System.out.println("Error");
                            }
                        }, Throwable::printStackTrace);
                queue.add(stringRequest);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return rootView;
        }
    }

    public static class MyPagerAdapter extends FragmentStateAdapter {


        public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public MyPagerAdapter(FragmentManager fm, Lifecycle lifecycle) {
            super(fm, lifecycle);
        }


        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return MyFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return 3;
        }


    }

}