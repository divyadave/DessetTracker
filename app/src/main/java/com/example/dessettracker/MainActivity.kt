package com.example.dessettracker

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.example.dessettracker.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

const val KEY_REVENUE = "revenue_key"
const val KEY_DESSERT_SOLD = "dessert_sold_key"
const val KEY_TIMER_SECONDS = "timer_seconds_key"
class MainActivity : AppCompatActivity() {
    private var revenue = 0
    private var dessertsold = 0


    private lateinit var dessertTimer: DessertTimer;
    private lateinit var binding: ActivityMainBinding


    data class Dessert(val imageId: Int, val price: Int, val startProductionAmount: Int)

    private val allDesserts = listOf(
        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 20),
        Dessert(R.drawable.froyo, 30, 50),
        Dessert(R.drawable.gingerbread, 50, 100),
        Dessert(R.drawable.honeycomb, 100, 200),
        Dessert(R.drawable.icecreamsandwitch, 500, 500),
        Dessert(R.drawable.jellybean, 1000, 1000),
        Dessert(R.drawable.kitkat, 2000, 2000),
        Dessert(R.drawable.lollipop, 3000, 4000),
        Dessert(R.drawable.marshmellow, 4000, 8000),
        Dessert(R.drawable.nougat, 5000, 1600),
        Dessert(R.drawable.oreo, 6000, 20000)
    )
    private var currentDessert = allDesserts[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.dessertButton.setOnClickListener {
            onDessetClicked()

        }
        dessertTimer = DessertTimer(this.lifecycle)
        if(savedInstanceState != null) {
            Timber.i("savedInstance is called")
            revenue = savedInstanceState.getInt(KEY_REVENUE , 0)
            dessertsold = savedInstanceState.getInt(KEY_DESSERT_SOLD, 0)
            dessertTimer.secondsCount = savedInstanceState.getInt(KEY_TIMER_SECONDS, 0)
            showcurrentDessert()


        }
        binding.revenue = revenue
        binding.amountsold = dessertsold

        binding.dessertButton.setImageResource(currentDessert.imageId)
    }
    private fun onDessetClicked() {
        revenue = currentDessert.price
        dessertsold++
        binding.revenue = revenue
        binding.amountsold = dessertsold
        showcurrentDessert()

    }

    private fun showcurrentDessert() {
        var newDessert = allDesserts[0]
        for (dessert in allDesserts) {
            if (dessertsold >= dessert.startProductionAmount) {
                newDessert = dessert
            } else break;

        }
        if (newDessert !== currentDessert) {
            currentDessert = newDessert
            binding.dessertButton.setImageResource(currentDessert.imageId)
        }
    }
    @SuppressLint("StringFormatInvalid")
    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(getString(R.string.share_text, dessertsold, revenue))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        }
        catch(ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available), Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_REVENUE, revenue)
        outState.putInt(KEY_DESSERT_SOLD, dessertsold)
        outState.putInt(KEY_TIMER_SECONDS, dessertTimer.secondsCount)
        Timber.i("ÖnSaveInstance is called")
        super.onSaveInstanceState(outState)

    }

    override fun onStart() {
        super.onStart()
        //dessertTimer.startTimer()
        Timber.i("OnStart called")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("OnResume called")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("OnPause called")
    }

    override fun onStop() {
        super.onStop()
        //dessertTimer.stopTimer()
        Timber.i("OnStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("OnDestroy called")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("OnRestart called")
    }




}
