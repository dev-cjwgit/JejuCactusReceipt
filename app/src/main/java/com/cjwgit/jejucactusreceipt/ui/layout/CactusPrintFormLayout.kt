package com.cjwgit.jejucactusreceipt.ui.layout

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.print.PrintHelper
import com.cjwgit.jejucactusreceipt.R
import com.cjwgit.jejucactusreceipt.databinding.LayoutCactusPrintFormBinding
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.CactusPrintFormVM
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date

class CactusPrintFormLayout : AppCompatActivity() {
    private lateinit var binding: LayoutCactusPrintFormBinding
    private val viewModel: CactusPrintFormVM by inject()

    private var one = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.layout_cactus_print_form)
        binding.viewModel = viewModel

        viewModel.init()
//        setContentView(binding.root)
        one = false
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (!one) {
            one = true
            val photoPrinter = PrintHelper(this)
            photoPrinter.scaleMode = PrintHelper.SCALE_MODE_FIT
            photoPrinter.colorMode = PrintHelper.COLOR_MODE_MONOCHROME
            photoPrinter.orientation = PrintHelper.ORIENTATION_LANDSCAPE
            val view: View = binding.linear
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_4444)
            val canvas = Canvas(bitmap)
            canvas.translate(view.scrollX.toFloat(), view.scaleY)
            canvas.drawARGB(0, 0, 0, 0)
            view.draw(canvas)
            //패키지명은 연락처  액티비티명은 최근기록 입력
            val intent23 = Intent()
            val imageUris = ArrayList<Uri?>()
            imageUris.add(getImageUri(this, bitmap))
            //            imageUris.add(getImageUri(this, bitmap));
            intent23.action = Intent.ACTION_SEND_MULTIPLE
            intent23.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
            intent23.type = "image/*"
            startActivity(Intent.createChooser(intent23, "프린트"))
            finish()
        } else {
//            finish();
        }
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver, inImage, SimpleDateFormat("yyyy년MM월dd일 HH시mm분ss초").format(
                Date()
            ), null
        )
        return Uri.parse(path)
    }
}