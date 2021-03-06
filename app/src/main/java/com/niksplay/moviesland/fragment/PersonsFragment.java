package com.niksplay.moviesland.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.niksplay.moviesland.R;
import com.niksplay.moviesland.activity.MediaSearchActivity;
import com.niksplay.moviesland.activity.PersonActivity;
import com.niksplay.moviesland.activity.PersonSearchActivity;
import com.niksplay.moviesland.adapter.PersonsAdapter;
import com.niksplay.moviesland.app.App;
import com.niksplay.moviesland.model.Person;
import com.niksplay.moviesland.model.response.PagedResponse;
import com.niksplay.moviesland.widget.EndlessRecyclerScrollListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Response;

/**
 * Created by nikita on 15.11.15.
 */
public class PersonsFragment extends NavigationFragment
        implements LoaderManager.LoaderCallbacks<PagedResponse<Person>>, PersonsAdapter.OnItemSelectedListener {

    @Bind(R.id.recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.progress_bar) ProgressBar mProgressBar;
    @Bind(R.id.empty_view) View mEmptyView;

    private int mPage;

    private int mTotalPages;

    private boolean mLoading = false;

    private PersonsAdapter mAdapter;

    @Override
    protected int getTitleResId() {
        return R.string.title_persons;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPage = 0;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_persons, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        int columnsCount = getResources().getInteger(R.integer.column_count);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), columnsCount);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (!mLoading && mPage + 1 <= mTotalPages) {
                    getLoaderManager().restartLoader(0, null, PersonsFragment.this);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter = new PersonsAdapter());
        mAdapter.setItemSelectedListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_persons, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                getActivity().startActivity(new Intent(getActivity(), PersonSearchActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Person person) {
        startActivity(PersonActivity.createIntent(getActivity(), person));
    }

    @Override
    public final Loader<PagedResponse<Person>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<PagedResponse<Person>>(getActivity()) {
            @Override
            public PagedResponse<Person> loadInBackground() {
                Response<PagedResponse<Person>> response;
                try {
                    response = App.getInstance().getApiClient().personsPopular(mPage + 1).execute();
                    if (response.isSuccess()) {
                        return response.body();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                mLoading = true;
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<PagedResponse<Person>> loader, PagedResponse<Person> data) {
        mLoading = false;
        mProgressBar.setVisibility(View.GONE);
        if (data != null) {
            mTotalPages = data.getTotal_pages();
            mPage = data.getPage();
            mAdapter.addAll(data.getResults());
        }
        mEmptyView.setVisibility(mAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<PagedResponse<Person>> loader) {
        //do nothing
    }
}
