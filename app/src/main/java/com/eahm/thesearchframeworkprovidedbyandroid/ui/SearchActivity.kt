package com.eahm.thesearchframeworkprovidedbyandroid.ui

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.eahm.thesearchframeworkprovidedbyandroid.CUSTOM_SEARCH_DATA
import com.eahm.thesearchframeworkprovidedbyandroid.R
import com.eahm.thesearchframeworkprovidedbyandroid.provider.SearchSuggestionProvider
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        // Note: Use launchMode=singletop only if theres a searchfunction in this activity, that way
        // the activity is not going to relaunch a new activity and will instead reuse itself.
        
        /*
        a. Receiving the query
           When a user executes a search from the search dialog or widget, the system
           starts your searchable activity and sends it a ACTION_SEARCH intent. This intent
           carries the search query in the QUERY string extra.

           ** The QUERY string is always included with the ACTION_SEARCH intent.
        */
        handleIntent()

        buttonSearch3.setOnClickListener{
            startActivity(Intent(this@SearchActivity, NewsActivity::class.java))
        }
    }

    private fun handleIntent(){
        if(Intent.ACTION_SEARCH == intent.action){
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->

                /* To populate your collection of recent queries, add each query received by your
                   searchable activity to your SearchRecentSuggestionsProvider

                   SearchRecentSuggestions constructor requires the same authority and database
                       mode declared by your content provider (See SearchSuggestionProvider.kt).

                   saveRecentQuery() takes the search query string and optionally, a second string to
                       include as the second line of the suggestion (or null).

                    ** The second parameter is only used if you've enabled two-line mode for the search
                       suggestions with DATABASE_MODE_2LINES. If you have enabled two-line mode, then
                       the query text is also matched against this second line when the system looks for
                       matching suggestions. */
                SearchRecentSuggestions(this, SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE)
                    .saveRecentQuery(query, null)

                performSearch(query)
            }

            /* Passing search context data. See NewsActivity.kt -> onSearchRequested() */
            val jargon: Boolean = intent.getBundleExtra(SearchManager.APP_DATA)?.getBoolean(
                CUSTOM_SEARCH_DATA
            ) ?: false
        }

        /* When using a Custom Search - Handling the Intent
         Now that you provide custom search suggestions with custom intents, you need your
         searchable activity to handle these intents when the user selects a suggestion.
         combine with the above if to validate each intent action.
         */
        when(intent.action) {
            Intent.ACTION_SEARCH -> {
                // Handle the normal search query case
                intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                    //doSearch(query)
                }
            }
            Intent.ACTION_VIEW -> {
                // Handle a suggestions click (because the suggestions all use ACTION_VIEW)
                //showResult(intent.data)
            }
        }
    }

    /* Clearing the Suggestion Data
    To protect the user's privacy, you should always provide a way for the user to clear the recent query suggestions. */
    private fun clearSearchSuggestions(){
        /* You should also provide a confirmation dialog to verify that the user wants to delete their search history. */
        SearchRecentSuggestions(this, SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE)
            .clearHistory()
    }

    private fun performSearch(query: String) {
        /* b. Searching your data */

        /* c. Presenting the results */
        textViewSearchQuery.text = query
    }

    /* If the current activity is the default searchable activity, then
        one of two things happens:

        1. By default, the searchable activity receives the ACTION_SEARCH
        intent with a call to onCreate() and a new instance of the activity
        is brought to the top of the activity stack. There are now two
        instances of your searchable activity in the activity stack (so pressing
        the Back button goes back to the previous instance of the searchable
        activity, rather than exiting the searchable activity).

        2. If you set android:launchMode to "singleTop", then the searchable activity
        receives the ACTION_SEARCH intent with a call to onNewIntent(Intent),
        passing the new ACTION_SEARCH intent here. */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        /* here's how you  might handle the 2nd case, in which the
        searchable activity's launch mode is "singleTop"*/

        /* 1. You should call setIntent(Intent) inside onNewIntent(Intent) so
        that the intent saved by the activity is updated (in case you call getIntent() in the future).*/
        setIntent(intent)

        /* 2. The code to handle the search intent is now in the handleIntent() method, so
        that both onCreate() and onNewIntent() can execute it.*/
        handleIntent()

    }

}