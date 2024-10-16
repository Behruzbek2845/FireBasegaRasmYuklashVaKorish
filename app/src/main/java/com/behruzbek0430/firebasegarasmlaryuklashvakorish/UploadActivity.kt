package com.behruzbek0430.firebasegarasmlaryuklashvakorish

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UploadActivity : AppCompatActivity() {
    private lateinit var storageRef: StorageReference
    private val imageUris = mutableListOf<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload2)

        storageRef = FirebaseStorage.getInstance().reference

        val buttonSelect = findViewById<Button>(R.id.buttonSelect)
        buttonSelect.setOnClickListener {
            selectImages()
        }

        val buttonUpload2 = findViewById<Button>(R.id.ient)
        buttonUpload2.setOnClickListener {
            val intent = Intent(this, ViewPagerActivity::class.java)
            intent.putStringArrayListExtra("imageUrls", ArrayList(imageUris.map { it.toString() }))
            startActivity(intent)
        }

        val buttonUpload = findViewById<Button>(R.id.buttonUpload)
        buttonUpload.setOnClickListener {
            uploadImages()
        }
    }

    private fun selectImages() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "image/*"
        }
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            data?.let {
                if (it.clipData != null) {
                    val count = it.clipData!!.itemCount
                    for (i in 0 until count) {
                        val imageUri = it.clipData!!.getItemAt(i).uri
                        imageUris.add(imageUri)
                    }
                } else {
                    it.data?.let { uri -> imageUris.add(uri) }
                }
            }
        }
    }

    private fun uploadImages() {
        for (uri in imageUris) {
            val fileRef = storageRef.child("images/${uri.lastPathSegment}")
            fileRef.putFile(uri)
                .addOnSuccessListener {
                    // Rasm muvaffaqiyatli yuklandi
                }
                .addOnFailureListener {
                    // Rasm yuklashda xato
                }
        }
    }
}