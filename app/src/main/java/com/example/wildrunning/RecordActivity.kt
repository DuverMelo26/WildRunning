package com.example.wildrunning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat

class RecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_record)
        setSupportActionBar(toolbar)

        toolbar.title = getString(R.string.menuRecord)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.gray_dark))
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_light))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.orders_records_by, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.orderby_date -> {
                if (item.title == getString(R.string.orderBy_dateZA)) item.title = getString(R.string.orderBy_dateAZ)
                else item.title = getString(R.string.orderBy_dateZA)
                return true
            }
            R.id.orderby_duration -> {
                if (item.title == getString(R.string.orderBy_durationZA)) item.title = getString(R.string.orderBy_durationAZ)
                else item.title = getString(R.string.orderBy_durationZA)
                return true
            }
            R.id.orderby_avgSpeed -> {
                if (item.title == getString(R.string.orderBy_avgSpeedZA)) item.title = getString(R.string.orderBy_avgSpeedAZ)
                else item.title = getString(R.string.orderBy_avgSpeedZA)
                return true
            }
            R.id.orderby_distance -> {
                if (item.title == getString(R.string.orderBy_distanceZA)) item.title = getString(R.string.orderBy_distanceAZ)
                else item.title = getString(R.string.orderBy_distanceZA)
                return true
            }
            R.id.orderby_maxSpeed -> {
                if (item.title == getString(R.string.orderBy_maxSpeedZA)) item.title = getString(R.string.orderBy_maxSpeedAZ)
                else item.title = getString(R.string.orderBy_maxSpeedZA)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun callHome(v: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}