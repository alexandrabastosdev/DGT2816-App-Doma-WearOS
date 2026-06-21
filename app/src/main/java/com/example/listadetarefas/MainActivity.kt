package com.example.listadetarefas

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioDeviceCallback
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var audioManager: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Aqui você vincularia o seu layout activity_main.xml se tivesse criado a interface gráfica completa
        // setContentView(R.layout.activity_main)

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        verificarDispositivosDeAudio()
        registrarCallbackDeAudio()
    }

    private fun audioOutputAvailable(type: Int): Boolean {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)) {
            return false
        }
        return audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS).any { it.type == type }
    }

    private fun verificarDispositivosDeAudio() {
        val isSpeakerAvailable = audioOutputAvailable(AudioDeviceInfo.TYPE_BUILTIN_SPEAKER)
        val isBluetoothHeadsetConnected = audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)

        if (isSpeakerAvailable) {
            Toast.makeText(this, "Alto-falante detectado. Sistema Doma pronto.", Toast.LENGTH_SHORT).show()
        }

        if (!isBluetoothHeadsetConnected) {
            solicitarConexaoBluetooth()
        }
    }

    private fun solicitarConexaoBluetooth() {
        Toast.makeText(this, "Por favor, conecte um fone Bluetooth para melhor acessibilidade.", Toast.LENGTH_LONG).show()
        val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            putExtra("EXTRA_CONNECTION_ONLY", true)
            putExtra("EXTRA_CLOSE_ON_CONNECT", true)
            putExtra("android.bluetooth.devicepicker.extra.FILTER_TYPE", 1)
        }
        startActivity(intent)
    }

    private fun registrarCallbackDeAudio() {
        audioManager.registerAudioDeviceCallback(object : AudioDeviceCallback() {
            override fun onAudioDevicesAdded(addedDevices: Array<out AudioDeviceInfo>?) {
                super.onAudioDevicesAdded(addedDevices)
                if (audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)) {
                    Toast.makeText(this@MainActivity, "Fone Bluetooth conectado!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onAudioDevicesRemoved(removedDevices: Array<out AudioDeviceInfo>?) {
                super.onAudioDevicesRemoved(removedDevices)
                if (!audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)) {
                    Toast.makeText(this@MainActivity, "Fone Bluetooth desconectado.", Toast.LENGTH_SHORT).show()
                }
            }
        }, null)
    }
}