package net.codecision.startask.permissions.sample

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_permissions.*
import net.codecision.startask.permissions.Permission
import net.codecision.startask.permissions.sample.utils.ktx.setSingleClickListener

class PermissionsActivity : AppCompatActivity() {

    private val cameraPermission: Permission by lazy {
        Permission.Builder(Manifest.permission.CAMERA)
                .setRequestCode(MY_PERMISSIONS_REQUEST_CODE)
                .build()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions)

        initView()
        initListeners()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestLocationPermissionResult(requestCode, grantResults)
    }

    @SuppressLint("SetTextI18n")
    private fun checkLocationPermission() {
        cameraPermission.check(this)
                .onGranted {
                    statusView.text = "Granted!"
                }.onShowRationale {
                    showRationaleDialog()
                }
    }

    @SuppressLint("SetTextI18n")
    private fun onRequestLocationPermissionResult(requestCode: Int, grantResults: IntArray) {
        cameraPermission.onRequestPermissionsResult(this, requestCode, grantResults)
                .onGranted {
                    statusView.text = "Granted!"
                }.onDenied {
                    statusView.text = "Denied!"
                }.onNeverAskAgain {
                    statusView.text = "NeverAskAgain!"
                }
    }

    private fun showRationaleDialog() {
        AlertDialog.Builder(this)
                .setTitle("Camera permission")
                .setMessage("Allow app to use your camera to take photos and record videos.")
                .setPositiveButton("Allow") { _, _ ->
                    cameraPermission.request(this)
                }
                .setNegativeButton("Deny") { _, _ ->

                }
                .create()
                .show()
    }

    private fun initView() {
        statusView.text = "Unknown!"
    }

    private fun initListeners() {
        checkButton.setSingleClickListener {
            checkLocationPermission()
        }
    }

    companion object {
        const val MY_PERMISSIONS_REQUEST_CODE = 99
    }

}