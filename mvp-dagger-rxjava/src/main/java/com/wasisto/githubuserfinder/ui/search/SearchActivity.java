/*
 * Copyright (c) 2018 Andika Wasisto
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.wasisto.githubuserfinder.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.*;
import com.wasisto.githubuserfinder.R;
import com.wasisto.githubuserfinder.data.github.model.SearchUserResult;
import com.wasisto.githubuserfinder.data.searchhistory.model.SearchHistoryItem;
import com.wasisto.githubuserfinder.ui.userdetails.UserDetailsActivity;
import dagger.android.support.DaggerAppCompatActivity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_SHORT;

public class SearchActivity extends DaggerAppCompatActivity implements SearchView {

    @Inject
    public SearchPresenter presenter;

    private ProgressBar loadingIndicator;

    private EditText queryEditText;

    private Button searchButton;

    private RecyclerView resultRecyclerView;

    private RecyclerView historyRecyclerView;

    private TextView noResultsTextView;

    private ResultAdapter resultAdapter;

    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        loadingIndicator = findViewById(R.id.loadingIndicator);
        queryEditText = findViewById(R.id.queryEditText);
        searchButton = findViewById(R.id.searchButton);
        resultRecyclerView = findViewById(R.id.resultRecyclerView);
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        noResultsTextView = findViewById(R.id.noResultsTextView);

        resultAdapter = new ResultAdapter(new ArrayList<>());
        resultAdapter.setOnItemClickListener(resultItem -> presenter.onResultItemClick(resultItem));

        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.setAdapter(resultAdapter);

        historyAdapter = new HistoryAdapter(new ArrayList<>());
        historyAdapter.setOnItemClickListener(historyItem -> presenter.onHistoryItemClick(historyItem));

        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyRecyclerView.setAdapter(historyAdapter);

        searchButton.setOnClickListener(v -> presenter.onSearch(queryEditText.getText().toString()));

        presenter.onViewReady();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onViewDestroyed();
    }

    @Override
    public void showLoadingIndicator() {
        loadingIndicator.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        loadingIndicator.setVisibility(GONE);
    }

    @Override
    public void setQuery(String query) {
        queryEditText.setText(query);
    }

    @Override
    public void showResultItems(List<SearchUserResult.Item> resultItems) {
        resultRecyclerView.setVisibility(VISIBLE);

        resultAdapter.setData(resultItems);
        resultAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideResultItems() {
        resultRecyclerView.setVisibility(GONE);
    }

    @Override
    public void showHistory(List<SearchHistoryItem> history) {
        historyRecyclerView.setVisibility(VISIBLE);

        historyAdapter.setData(history);
        historyAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideHistory() {
        historyRecyclerView.setVisibility(GONE);
    }

    @Override
    public void showNoResultsText() {
        noResultsTextView.setVisibility(VISIBLE);
    }

    @Override
    public void hideNoResultsText() {
        noResultsTextView.setVisibility(GONE);
    }

    @Override
    public void openUserDetailsActivity(String username) {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        intent.putExtra(UserDetailsActivity.EXTRA_USERNAME, username);

        startActivity(intent);
    }

    @Override
    public void showToast(int resId) {
        Toast.makeText(this, resId, LENGTH_SHORT).show();
    }
}