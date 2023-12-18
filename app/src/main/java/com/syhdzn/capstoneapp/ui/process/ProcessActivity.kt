package com.syhdzn.capstoneapp.ui.process

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.syhdzn.capstoneapp.R
import com.syhdzn.capstoneapp.databinding.ActivityProcessBinding
import com.syhdzn.capstoneapp.ui.dashboard.DashboardActivity
import com.syhdzn.capstoneapp.ui.result.ResultActivity
import java.io.File

class ProcessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProcessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleImage()
        setupAction()
    }

    private fun handleImage() {
        val imageUriString = intent.getStringExtra("imageUri")
        val isBackCamera = intent.getBooleanExtra("isBackCamera", false)
        val pictureFile = intent.getSerializableExtra("picture") as? File

        if (imageUriString != null) {
            val imageUri = Uri.parse(imageUriString)
            handleGalleryImage(imageUri)
        } else if (isBackCamera && pictureFile != null) {
            val rotatedBitmap = BitmapFactory.decodeFile(pictureFile.absolutePath)
            binding.ivItemProcess.setImageBitmap(rotatedBitmap)
        } else if (!isBackCamera && pictureFile != null) {
            binding.ivItemProcess.setImageURI(Uri.fromFile(pictureFile))
        } else {
        }
    }

    private fun handleGalleryImage(imageUri: Uri) {
        binding.ivItemProcess.setImageURI(imageUri)
    }


    private fun setupAction() {
        binding.ivBgReplace.setOnClickListener {
            showDialogReplace()
        }
        binding.btnProcessImage.setOnClickListener {
            showDialogProcess()
        }

    }

    private fun showDialogProcess() {

        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val customDialogView = inflater.inflate(R.layout.costum_dialog_process, null)

        builder.setView(customDialogView)
        val dialog = builder.create()

        val btnMakanan = customDialogView.findViewById<Button>(R.id.btn_makanan)
        val btnJajanan = customDialogView.findViewById<Button>(R.id.btn_jajanan)
        val btnExit = customDialogView.findViewById<ImageView>(R.id.btn_exit_process)

        btnMakanan.setOnClickListener {
            startActivity(Intent(this, ResultActivity::class.java))
        }
        btnJajanan.setOnClickListener {
            startActivity(Intent(this, ResultActivity::class.java))
        }
        btnExit.setOnClickListener {
            dialog.hide()
        }

        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_rounded_14)

        customDialogView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim))

        dialog.setCanceledOnTouchOutside(false)

        dialog.show()

    }

    private fun showDialogReplace() {

        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val customDialogView = inflater.inflate(R.layout.costum_dialog_replace, null)

        builder.setView(customDialogView)
        val dialog = builder.create()

        val btnYes = customDialogView.findViewById<Button>(R.id.btn_yes)
        val btnNo = customDialogView.findViewById<Button>(R.id.btn_no)

        btnYes.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.putExtra("switchToFragment", "DetectionFragment")
            intent.putExtra("selectMenuItem", R.id.cam)
            startActivity(intent)
        }
        btnNo.setOnClickListener {
            dialog.hide()
        }
        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_rounded_14)

        customDialogView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim))

        dialog.setCanceledOnTouchOutside(false)

        dialog.show()

    }



    private fun setupLoading(){
        val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#06283D")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(true)
        pDialog.show()
    }

    private fun hideLoading(){
        val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#06283D")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(true)
        pDialog.hide()
    }

    override fun onBackPressed() {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("switchToFragment", "DetectionFragment")
        intent.putExtra("selectMenuItem", R.id.cam)
        startActivity(intent)
    }

}
