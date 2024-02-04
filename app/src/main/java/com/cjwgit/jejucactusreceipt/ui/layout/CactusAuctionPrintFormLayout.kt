package com.cjwgit.jejucactusreceipt.ui.layout

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.print.PrintHelper
import com.cjwgit.jejucactusreceipt.databinding.LayoutCactusPrintFormBinding
import com.cjwgit.jejucactusreceipt.domain.CactusBasketVO
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.CactusPrintFormVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.CactusPrintUiState
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date

class CactusAuctionPrintFormLayout : AppCompatActivity() {
    private lateinit var binding: LayoutCactusPrintFormBinding
    private val viewModel: CactusPrintFormVM by viewModels()

    private var one = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutCactusPrintFormBinding.inflate(layoutInflater)

        val basketItems = intent.getParcelableArrayListExtra("items", CactusBasketVO::class.java)
        binding.viewModel = viewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.observe(this@CactusAuctionPrintFormLayout) { state ->
                    when (state) {
                        is CactusPrintUiState.Print -> {
                            printBasket()
                        }

                        else -> {

                        }
                    }
                }
            }
        }
        basketItems?.let {
            val paddingItemSize = 24 - basketItems.size
            val start = basketItems.size

            for (i in 0 until paddingItemSize) {
                basketItems.add(
                    CactusBasketVO(
                        start + i + 1L,
                        "",
                        0L,
                        0L,
                        0L
                    )
                )
            }

            binding.items = basketItems
            viewModel.init(basketItems)
        }

        setContentView(binding.root)
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

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver, inImage, SimpleDateFormat("yyyy년MM월dd일 HH시mm분ss초").format(
                Date()
            ), null
        )
        return Uri.parse(path)
    }

    private fun printBasket() {

    }
}