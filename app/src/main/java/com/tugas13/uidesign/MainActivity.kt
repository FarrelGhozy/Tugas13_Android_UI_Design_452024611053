package com.tugas13.uidesign

import android.os.Bundle
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.snackbar.Snackbar
import com.tugas13.uidesign.databinding.ActivityMainBinding

/**
 * MainActivity — mendemonstrasikan Material Design Components:
 *
 * 1. TextInputLayout + TextInputEditText   (form input)
 * 2. MaterialCardView                       (kartu konten)
 * 3. FloatingActionButton (FAB)             (tombol aksi mengambang)
 * 4. BottomNavigationView                   (navigasi bawah)
 * 5. SwitchMaterial                         (toggle pengaturan)
 * 6. MaterialButton                         (tombol aksi)
 * 7. Snackbar                               (feedback)
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDarkModeSwitch()
        setupBottomNavigation()
        setupFab()
        setupSaveButton()
    }

    /**
     * BottomNavigationView — scroll ke section terkait saat item dipilih.
     */
    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            val scrollView = binding.scrollView
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    scrollView.smoothScrollTo(0, 0)
                    true
                }
                R.id.nav_settings -> {
                    scrollView.post {
                        scrollView.smoothScrollTo(0, binding.cardSettings.top)
                    }
                    true
                }
                R.id.nav_about -> {
                    scrollView.post {
                        scrollView.smoothScrollTo(0, binding.cardAbout.top)
                    }
                    true
                }
                else -> false
            }
        }
    }

    /**
     * FAB — menampilkan Snackbar sebagai feedback.
     * Ini adalah contoh pemanggilan Snackbar dari kode.
     */
    private fun setupFab() {
        binding.fabEdit.setOnClickListener { view ->
            Snackbar.make(
                view,
                getString(R.string.snackbar_saved),
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.snackbar_undo)) {
                    Snackbar.make(view, getString(R.string.snackbar_reverted), Snackbar.LENGTH_SHORT).show()
                }
                .setAnchorView(binding.bottomNavigation)
                .show()
        }
    }

    /**
     * Save button — validasi dan simpan data.
     */
    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener { view ->
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()

            // Validasi sederhana
            var hasError = false

            if (name.isEmpty()) {
                binding.inputLayoutName.error = getString(R.string.error_empty_field)
                hasError = true
            } else {
                binding.inputLayoutName.error = null
            }

            if (email.isEmpty()) {
                binding.inputLayoutEmail.error = getString(R.string.error_empty_field)
                hasError = true
            } else if (!email.contains("@") || !email.contains(".")) {
                binding.inputLayoutEmail.error = getString(R.string.error_invalid_email)
                hasError = true
            } else {
                binding.inputLayoutEmail.error = null
            }

            if (phone.isEmpty()) {
                binding.inputLayoutPhone.error = getString(R.string.error_empty_field)
                hasError = true
            } else {
                binding.inputLayoutPhone.error = null
            }

            if (!hasError) {
                Snackbar.make(
                    view,
                    getString(R.string.snackbar_saved),
                    Snackbar.LENGTH_SHORT
                )
                    .setAnchorView(binding.bottomNavigation)
                    .show()
            } else {
                Snackbar.make(
                    view,
                    getString(R.string.snackbar_error),
                    Snackbar.LENGTH_SHORT
                )
                    .setAnchorView(binding.bottomNavigation)
                    .show()
            }
        }
    }

    /**
     * Dark Mode switch — toggle antara Light dan Dark mode.
     */
    private fun setupDarkModeSwitch() {
        val currentMode = resources.configuration.uiMode and
                android.content.res.Configuration.UI_MODE_NIGHT_MASK
        binding.switchDarkMode.isChecked = currentMode ==
                android.content.res.Configuration.UI_MODE_NIGHT_YES

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}
