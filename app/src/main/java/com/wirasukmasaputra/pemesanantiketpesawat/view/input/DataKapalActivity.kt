package com.wirasukmasaputra.pemesanantiketpesawat.view.input

import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.wirasukmasaputra.pemesanantiketpesawat.R
import com.wirasukmasaputra.pemesanantiketpesawat.databinding.ActivityDataKapalBinding
import com.wirasukmasaputra.pemesanantiketpesawat.viewmodel.InputDataViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DataKapalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataKapalBinding
    private lateinit var inputDataViewModel: InputDataViewModel
    private val strAsal = arrayOf("Jakarta", "Semarang", "Surabaya", "Bali")
    private val strTujuan = arrayOf("Jakarta", "Semarang", "Surabaya", "Bali")
    private val strKelas = arrayOf("Eksekutif", "Bisnis", "Ekonomi")

    private lateinit var sAsal: String
    private lateinit var sTujuan: String
    private lateinit var sTanggal: String
    private lateinit var sNama: String
    private lateinit var sTelp: String
    private lateinit var sKelas: String

    private var hargaDewasa = 0
    private var hargaAnak = 0
    private var hargaTotal = 0
    private var countAnak = 0
    private var countDewasa = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDataKapalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBar()
        setToolbar()
        setInitView()
        setViewModel()
        setSpinnerAdapter()
        setJmlPenumpang()
        setInputData()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun setInitView() {
        binding.inputTanggal.setOnClickListener {
            val tanggalJemput = Calendar.getInstance()
            val date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                tanggalJemput.set(Calendar.YEAR, year)
                tanggalJemput.set(Calendar.MONTH, monthOfYear)
                tanggalJemput.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val simpleDateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
                binding.inputTanggal.setText(simpleDateFormat.format(tanggalJemput.time))
            }
            DatePickerDialog(
                this@DataKapalActivity, date,
                tanggalJemput[Calendar.YEAR],
                tanggalJemput[Calendar.MONTH],
                tanggalJemput[Calendar.DAY_OF_MONTH]
            ).show()
        }
    }

    private fun setViewModel() {
        inputDataViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(InputDataViewModel::class.java)
    }

    private fun setSpinnerAdapter() {
        val adapterAsal = ArrayAdapter(this, R.layout.spinner_item, strAsal)
        adapterAsal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spBerangkat.adapter = adapterAsal

        val adapterTujuan = ArrayAdapter(this, R.layout.spinner_item, strTujuan)
        adapterTujuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spTujuan.adapter = adapterTujuan

        val adapterKelas = ArrayAdapter(this, R.layout.spinner_item, strKelas)
        adapterKelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spKelas.adapter = adapterKelas

        binding.spBerangkat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sAsal = strAsal[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spTujuan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sTujuan = strTujuan[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spKelas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sKelas = strKelas[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setJmlPenumpang() {
        binding.imageAdd1.setOnClickListener {
            countAnak++
            binding.tvJmlAnak.text = countAnak.toString()
        }

        binding.imageMinus1.setOnClickListener {
            if (countAnak > 0) {
                countAnak--
                binding.tvJmlAnak.text = countAnak.toString()
            }
        }

        binding.imageAdd2.setOnClickListener {
            countDewasa++
            binding.tvJmlDewasa.text = countDewasa.toString()
        }

        binding.imageMinus2.setOnClickListener {
            if (countDewasa > 0) {
                countDewasa--
                binding.tvJmlDewasa.text = countDewasa.toString()
            }
        }
    }

    private fun setInputData() {
        binding.btnCheckout.setOnClickListener {
            sNama = binding.inputNama.text.toString()
            sTanggal = binding.inputTanggal.text.toString()
            sTelp = binding.inputTelepon.text.toString()

            if (sNama.isEmpty() || sTanggal.isEmpty() || sTelp.isEmpty() || hargaTotal == 0 || sKelas.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi data pemesanan!", Toast.LENGTH_SHORT).show()
            } else if (sAsal == sTujuan) {
                Toast.makeText(this, "Asal dan Tujuan tidak boleh sama!", Toast.LENGTH_SHORT).show()
            } else {
                inputDataViewModel.addDataPemesan(
                    sNama, sAsal, sTujuan, sTanggal, sTelp, countAnak, countDewasa, hargaTotal, sKelas, "1"
                )
                Toast.makeText(this, "Booking Tiket berhasil!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            layoutParams.flags = if (on) layoutParams.flags or bits else layoutParams.flags and bits.inv()
            window.attributes = layoutParams
        }
    }
}
