package com.eahm.thesearchframeworkprovidedbyandroid.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.eahm.thesearchframeworkprovidedbyandroid.CUSTOM_SEARCH_DATA
import com.eahm.thesearchframeworkprovidedbyandroid.R
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        /* If the current activity is not the searchable activity,
        then the normal activity lifecycle events are triggered once
        the user executes a search (the current activity receives
        onPause() and so forth, as described in the Activities document)*/

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchViewSearch2.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchViewSearch2.isIconified = false

        searchManager.setOnDismissListener {
            Log.i("SearchTag", "NewsActivity: onDismiss Called()")
        }

        /* OnCancelListener only pertains to events in which the user explicitly exited the
         search dialog, so it is not called when a search is executed (in which case, the
         search dialog naturally disappears). */
        searchManager.setOnCancelListener {
            Log.i("SearchTag", "NewsActivity: onCancel Called()")
        }

        buttonSearch2.setOnClickListener{
            onSearchRequested()
        }
    }

    /* You can provide additional data in the intent that the system sends
     to your searchable activity.
     You can pass the additional data in the APP_DATA Bundle, which is included in the ACTION_SEARCH intent.
     */
    override fun onSearchRequested(): Boolean {
        /* You can pass the additional data in the APP_DATA Bundle, which is
        included in the ACTION_SEARCH intent. */
        val appData = Bundle().apply {
            putBoolean(CUSTOM_SEARCH_DATA, true)
        }

        /* Call startSearch() to activate the search dialog. */
        // Never call the startSearch() method from outside the onSearchRequested()
        startSearch(null, false, appData, false)

        /* Returning "true" indicates that you have successfully handled this callback event and
        called startSearch() to activate the search dialog. */
        return true
    }

    /* If the current activity is not the searchable activity (and is not!), then the normal activity
    lifecycle events are triggered once the user executes a search. */
    override fun onPause() {
        super.onPause()
        Log.i("SearchTag", "NewsActivity: onPause Called()")
        //finishNewsActivity()
    }

    private fun finishNewsActivity(){
        // When the search is performed, close this activity
        this@NewsActivity.finish()
    }
}