package com.errorsmasher.passworddrive

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.errorsmasher.passworddrive.databinding.ActivityMainBinding
import com.errorsmasher.passworddrive.utils.LogUtil
import com.errorsmasher.passworddrive.utils.from
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), ExampleDialog.ExampleDialogListener {
    private lateinit var binding: ActivityMainBinding
    var mList: MutableList<Password> = ArrayList()
    lateinit var database: PasswordDatabase
    lateinit var passwordAdapter: PasswordAdapter
    private lateinit var fragmentManager: FragmentManager
    private val MY_REQUEST_CODE = 100
    lateinit var appUpdateManager: AppUpdateManager
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var mAdView: AdView
    private var mInterstitialAd: InterstitialAd? = null

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics
        loadBannerAd()
        loadInterstitialAd()
        database = PasswordDatabase.getDatabase(this)
        binding.userView.layoutManager = LinearLayoutManager(this)
        fragmentManager = supportFragmentManager
        passwordAdapter = PasswordAdapter(database, fragmentManager)
        binding.userView.adapter = passwordAdapter
        CheckForUpdate()
        binding.add.setOnClickListener() {
            saveData(database)
        }
        // passwordAdapter.initOnClick(this)
        database.contactDao().getContact().observe(this) {
            val mutableList: MutableList<Password> = ArrayList()
            it.forEach(mutableList::add)
            mList = mutableList
            passwordAdapter.setData(mutableList)

        }
    }


    private fun loadBannerAd() {
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        mAdView.adListener = object : AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                LogUtil.i("loadBannerAd", "user clicks on an ad")
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                LogUtil.i(
                    "loadBannerAd",
                    "user is about to return to the app after tapping on an ad"
                )

            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                LogUtil.i("loadBannerAd", "ad request fails.")

            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
                LogUtil.i("loadBannerAd", "an impression is recorded for an ad.")

            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                LogUtil.i("loadBannerAd", "an ad finishes loading.")

            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                LogUtil.i("loadBannerAd", "an ad opens an overlay that covers the screen.")

            }
        }
    }

    private fun loadInterstitialAd() {
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            this,
            "ca-app-pub-6593282491044481/2809585143",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    LogUtil.i("loadInterstitialAd", "onAdFailedToLoad")
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    LogUtil.i("loadInterstitialAd", "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterstitialAd(function: () -> Unit) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                    LogUtil.i("showInterstitialAd", "Ad was clicked.")

                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    LogUtil.i("showInterstitialAd", "Ad dismissed fullscreen content.")
                    function()
                    mInterstitialAd = null
                }

//                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
//                    // Called when ad fails to show.
//                    LogUtil.i("MainActivity", "Ad failed to show fullscreen content.")
//                    mInterstitialAd = null
//                }

                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    LogUtil.i("showInterstitialAd", "Ad recorded an impression.")
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    LogUtil.i("showInterstitialAd", "Ad showed fullscreen content.")
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun saveData(database: PasswordDatabase) {
        openDialog();
//        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.custom_layout)
//        val name = dialog.findViewById(R.id.name) as EditText
//        val pass = dialog.findViewById(R.id.pass) as EditText
//        val submit = dialog.findViewById(R.id.sumb) as Button
//        val cnsl = dialog.findViewById(R.id.cnsl) as Button
//        submit.setOnClickListener {
//            GlobalScope.launch {
//                database.contactDao()
//                    .insertContact(Password(0, name.text.toString(), pass.text.toString()))
//            }
//            dialog.dismiss()
//        }
//        cnsl.setOnClickListener { dialog.dismiss() }
//        dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
//
//        dialog.show()

    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Do you want to exit?")
        builder.setTitle("Alert !")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->
                finish()
            } as DialogInterface.OnClickListener)
        builder.setNegativeButton("No",
            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
                showInterstitialAd {
                    dialog.cancel()
                }
            } as DialogInterface.OnClickListener)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // R.id.impt -> Toast.makeText(this, "imp", Toast.LENGTH_SHORT).show()
            R.id.impt -> {
                openACtivity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun openACtivity() {
        from = "Main"
        val intent = Intent(this@MainActivity, CreateAccountActivity::class.java)
        finish()
        startActivity(intent)
    }

    //    override fun onSelect(mSelectedList: MutableList<Password>) {
//        title = if (mSelectedList.isEmpty()) {
//            "Password Drive"
//        } else {
//            mSelectedList.size.toString()+ " item Selected"
//        }
//
//    }
    fun openDialog() {
        val exampleDialog = ExampleDialog()
        exampleDialog.show(supportFragmentManager, "example dialog")
    }

    override fun applyTexts(username: String?, password: String?) {
        GlobalScope.launch {
            database.contactDao()
                .insertContact(Password(0, username.toString(), password.toString()))
        }

    }


    fun CheckForUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                // Request the update.
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                    AppUpdateType.FLEXIBLE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    MY_REQUEST_CODE
                )
            }
        }
        appUpdateManager.registerListener(listener)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

    // Create a listener to track request state updates.
    val listener = InstallStateUpdatedListener { state ->
        // (Optional) Provide a download progress bar.
        if (state.installStatus() == InstallStatus.DOWNLOADING) {
            val bytesDownloaded = state.bytesDownloaded()
            val totalBytesToDownload = state.totalBytesToDownload()
            // Show update progress bar.
        }
        // Log state or install the update.
    }

    // Displays the snackbar notification and call to action.
    fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(android.R.id.content),
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("INSTALL") { appUpdateManager.completeUpdate() }
            setActionTextColor(resources.getColor(android.R.color.holo_blue_bright))
            show()
        }
    }

    override fun onStop() {
        super.onStop()
        appUpdateManager.unregisterListener(listener)
    }

    // Checks that the update is not stalled during 'onResume()'.
// However, you should execute this check at all app entry points.
    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                // If the update is downloaded but not installed,
                // notify the user to complete the update.
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            }
    }
}