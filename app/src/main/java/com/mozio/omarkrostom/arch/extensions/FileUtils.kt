package com.mozio.omarkrostom.arch.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import com.mozio.omarkrostom.BuildConfig
import java.io.*


fun Bitmap.convertToFile(context: Context): File {
    val file = File(context.cacheDir, "image.jpeg")
    file.createNewFile()

    //Convert bitmap to byte array
    val bos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 40, bos)
    val bitMapData = bos.toByteArray()

    //write the bytes in file
    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(file)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }
    try {
        fos?.write(bitMapData)
        fos?.flush()
        fos?.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return file
}

fun Uri.convertUriToFile(activity: FragmentActivity): File? {
    val selectedImage = if (Build.VERSION.SDK_INT > 27) {
        val source: ImageDecoder.Source =
            ImageDecoder.createSource(activity.contentResolver, this)
        ImageDecoder.decodeBitmap(source)
    } else {
        MediaStore.Images.Media.getBitmap(activity.contentResolver, this)
    }

    return selectedImage.convertToFile(activity.applicationContext ?: return null)
}

/**
 * Saves the image as PNG to the app's cache directory.
 * @param image Bitmap to save.
 * @return Uri of the saved file or null
 */
fun Context.getImageUriFromBitmap(image: Bitmap): Uri? {
    val imagesFolder = File(externalCacheDir, "shared_images")
    var uri: Uri? = null
    try {
        imagesFolder.mkdirs()
        val file = File(imagesFolder, "shared_image.png")
        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, 90, stream)
        stream.flush()
        stream.close()
        uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID +
                ".fileprovider", file)
    } catch (e: IOException) {
        Log.d("File Utils: ", "IOException while trying to write file for sharing: " + e.message)
    }
    return uri
}