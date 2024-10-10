package com.saputroekosulistiyo.lemonade2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.saputroekosulistiyo.lemonade2.ui.theme.Lemonade2Theme

// Kelas utama untuk aktivitas aplikasi
class MainActivity : ComponentActivity() {
    // Metode ini dipanggil saat aktivitas dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge() // Mengaktifkan tampilan edge-to-edge
        super.onCreate(savedInstanceState)
        // Mengatur konten UI dengan tema Lemonade2 dan memanggil fungsi LemonadeApp
        setContent {
            Lemonade2Theme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) // Menggunakan API eksperimental
@Composable
fun LemonadeApp() {
    // State untuk menyimpan langkah saat ini dan jumlah squeeze
    var currentStep by remember { mutableIntStateOf(1) }
    var squeezeCount by remember { mutableIntStateOf(0) }

    // Struktur Scaffold untuk tata letak
    Scaffold(
        topBar = {
            // Menampilkan AppBar di bagian atas
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lemonade", // Judul aplikasi
                        fontWeight = FontWeight.Bold // Menebalkan font judul
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer // Warna latar belakang AppBar
                )
            )
        }
    ) { innerPadding -> // Padding di dalam Scaffold
        Surface(
            modifier = Modifier
                .fillMaxSize() // Mengisi seluruh ukuran
                .padding(innerPadding) // Padding berdasarkan innerPadding
                .background(MaterialTheme.colorScheme.tertiaryContainer), // Warna latar belakang permukaan
            color = MaterialTheme.colorScheme.background // Warna latar belakang utama
        ) {
            // Menampilkan konten berdasarkan langkah saat ini
            when (currentStep) {
                1 -> { // Langkah 1: Memilih lemon
                    LemonTextAndImage(
                        textLabelResourceId = R.string.lemon_select, // Resource untuk label teks
                        drawableResourceId = R.drawable.lemon_tree, // Gambar lemon tree
                        contentDescriptionResourceId = R.string.lemon_tree_content_description, // Deskripsi konten
                        onImageClick = {
                            // Mengubah langkah dan menghasilkan jumlah squeeze acak
                            currentStep = 2
                            squeezeCount = (2..4).random()
                        }
                    )
                }
                2 -> { // Langkah 2: Mengambil squeeze dari lemon
                    LemonTextAndImage(
                        textLabelResourceId = R.string.lemon_squeeze, // Resource untuk label teks
                        drawableResourceId = R.drawable.lemon_squeeze, // Gambar lemon squeeze
                        contentDescriptionResourceId = R.string.lemon_content_description, // Deskripsi konten
                        onImageClick = {
                            squeezeCount-- // Mengurangi jumlah squeeze
                            if (squeezeCount == 0) { // Jika sudah selesai, pindah ke langkah 3
                                currentStep = 3
                            }
                        }
                    )
                }
                3 -> { // Langkah 3: Menikmati lemonade
                    LemonTextAndImage(
                        textLabelResourceId = R.string.lemon_drink, // Resource untuk label teks
                        drawableResourceId = R.drawable.lemon_drink, // Gambar lemonade
                        contentDescriptionResourceId = R.string.lemonade_content_description, // Deskripsi konten
                        onImageClick = {
                            currentStep = 4 // Pindah ke langkah 4
                        }
                    )
                }
                4 -> { // Langkah 4: Mengosongkan gelas
                    LemonTextAndImage(
                        textLabelResourceId = R.string.lemon_empty_glass, // Resource untuk label teks
                        drawableResourceId = R.drawable.lemon_restart, // Gambar gelas kosong
                        contentDescriptionResourceId = R.string.empty_glass_content_description, // Deskripsi konten
                        onImageClick = {
                            currentStep = 1 // Kembali ke langkah 1
                        }
                    )
                }
            }
        }
    }
}

// Fungsi komposabel untuk menampilkan teks dan gambar lemon
@Composable
fun LemonTextAndImage(
    textLabelResourceId: Int, // Resource ID untuk label teks
    drawableResourceId: Int, // Resource ID untuk gambar
    contentDescriptionResourceId: Int, // Resource ID untuk deskripsi konten
    onImageClick: () -> Unit, // Lambda untuk menangani klik gambar
    modifier: Modifier = Modifier // Modifier untuk pengaturan tambahan
) {
    Box(
        modifier = modifier // Modifier untuk Box
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Mengatur penyejajaran horizontal
            verticalArrangement = Arrangement.Center, // Mengatur penyejajaran vertikal
            modifier = Modifier.fillMaxSize() // Mengisi seluruh ukuran
        ) {
            // Tombol untuk menampilkan gambar dan menangani klik
            Button(
                onClick = onImageClick,
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)), // Sudut tombol melengkung
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer) // Warna tombol
            ) {
                // Menampilkan gambar lemon
                Image(
                    painter = painterResource(drawableResourceId), // Memuat gambar
                    contentDescription = stringResource(contentDescriptionResourceId), // Deskripsi konten untuk aksesibilitas
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.button_image_width)) // Lebar gambar
                        .height(dimensionResource(R.dimen.button_image_height)) // Tinggi gambar
                        .padding(dimensionResource(R.dimen.button_interior_padding)) // Padding dalam tombol
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_vertical))) // Ruang antara tombol dan teks
            // Menampilkan teks label
            Text(
                text = stringResource(textLabelResourceId), // Mengambil string resource untuk teks
                style = MaterialTheme.typography.bodyLarge // Menggunakan gaya tipografi besar
            )
        }
    }
}

// Fungsi untuk pratinjau layar Lemonade
@Preview
@Composable
fun LemonPreview() {
    Lemonade2Theme { // Menggunakan tema Lemonade2
        LemonadeApp() // Menampilkan aplikasi Lemonade
    }
}
