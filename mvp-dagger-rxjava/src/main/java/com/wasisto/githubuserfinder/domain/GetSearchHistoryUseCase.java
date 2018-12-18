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

package com.wasisto.githubuserfinder.domain;

import com.wasisto.githubuserfinder.data.searchhistory.SearchHistoryDataSource;
import com.wasisto.githubuserfinder.data.searchhistory.model.SearchHistoryItem;
import com.wasisto.githubuserfinder.util.scheduler.SchedulerProvider;
import io.reactivex.Observable;

import javax.inject.Inject;
import java.util.List;

public class GetSearchHistoryUseCase extends UseCase<Void, List<SearchHistoryItem>> {

    private SearchHistoryDataSource searchHistoryDataSource;

    @Inject
    public GetSearchHistoryUseCase(SchedulerProvider schedulerProvider,
                                   SearchHistoryDataSource searchHistoryDataSource) {
        super(schedulerProvider);
        this.searchHistoryDataSource = searchHistoryDataSource;
    }

    @Override
    public Observable<List<SearchHistoryItem>> createUseCaseObservable(Void params) {
        return searchHistoryDataSource.getAll();
    }
}