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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.print.PrintHelper
import com.cjwgit.jejucactusreceipt.databinding.LayoutAuctionPrintFormBinding
import com.cjwgit.jejucactusreceipt.domain.AuctionBasketVO
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.AuctionPrintFormVM
import com.cjwgit.jejucactusreceipt.ui.viewmodel.layout.AuctionPrintFormUiState
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date

class AuctionPrintFormLayout : AppCompatActivity() {
    private lateinit var binding: LayoutAuctionPrintFormBinding
    private val viewModel: AuctionPrintFormVM by inject()

    private var one = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutAuctionPrintFormBinding.inflate(layoutInflater)

        val basketItems =
            intent.getParcelableArrayListExtra("items", AuctionBasketVO::class.java)
        binding.viewModel = viewModel
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.observe(this@AuctionPrintFormLayout) { state ->
                    when (state) {
                        is AuctionPrintFormUiState.PrintForm -> {
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
                    AuctionBasketVO(
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

    private fun printBasket() {

    }
}