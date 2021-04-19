package com.easycopy.screen.qrcode

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.easycopy.BR
import com.easycopy.R
import com.easycopy.data.Constant
import com.easycopy.databinding.ActivityMainBinding
import com.easycopy.screen.base.BaseActivity
import com.easycopy.screen.home.HomeActivity
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import timber.log.Timber
import javax.inject.Inject


class QrCodeActivity : BaseActivity<ActivityMainBinding?, QrCodeViewModel?>(), QrCodeNavigator {

    @Inject
    lateinit var qrCodeViewModel: QrCodeViewModel

    private var qrScan: IntentIntegrator? = null

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_qr_code

    override val viewModel: ViewModel
        get() = qrCodeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        qrCodeViewModel.setNavigator(this)
        init()
    }

    private fun init() {
        qrScan = IntentIntegrator(this).setBeepEnabled(false)
        qrScan!!.initiateScan()

        qrCodeViewModel.init()

        var intentFilter = IntentFilter()
        addIntentActions(intentFilter)
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            //if qr code has nothing in it
            if (result.contents == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show()
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    val salt = result.contents

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra(com.easycopy.utils.Constant.EXTRA_SALT, salt);
                    startActivity(intent)

                    Timber.w("DATA in QrCode: %s", salt)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        var intentFilter = IntentFilter()
        addIntentActions(intentFilter)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

    }

    private fun addIntentActions(intentFilter: IntentFilter) {
        intentFilter.addAction(Constant.WS_ACTION_CONNECTED);
        intentFilter.addAction(Constant.WS_ACTION_DISCONNECTED);
        intentFilter.addAction(Constant.WS_ACTION_ERROR);
        intentFilter.addAction(Constant.WS_ACTION_UNKNOWN);
    }


}