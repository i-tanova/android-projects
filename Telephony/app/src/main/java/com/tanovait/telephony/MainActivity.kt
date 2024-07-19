package com.tanovait.telephony

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.CellInfoCdma
import android.telephony.CellInfoGsm
import android.telephony.CellInfoLte
import android.telephony.CellInfoNr
import android.telephony.CellInfoTdscdma
import android.telephony.CellInfoWcdma
import android.telephony.TelephonyManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.tanovait.telephony.ui.theme.TelephonyTheme

class MainActivity : ComponentActivity() {

    private lateinit var telephonyManager: TelephonyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions.all { it.value }) {
                // Permissions granted
                setContent {
                    CellInfoScreen(telephonyManager)
                }
            } else {
                // Permissions not granted
                setContent {
                    NoPermissionScreen()
                }
            }
        }

//        setContent {
//            CellInfoScreen(telephonyManager)
//        }

        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_PHONE_STATE
                    ) == PackageManager.PERMISSION_GRANTED -> {
                // Permissions already granted
                setContent {
                    CellInfoScreen(telephonyManager)
                }
            }

            else -> {
                // Request permissions
                requestPermissionLauncher.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.READ_PHONE_STATE
                    )
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CellInfoScreen(telephonyManager: TelephonyManager) {
    var cellInfoText by remember { mutableStateOf("Loading cell info...") }

    LaunchedEffect(Unit) {
        val cellInfoList = try {
            telephonyManager.allCellInfo
        } catch (e: SecurityException) {
            return@LaunchedEffect
        }
        val cellInfoStringBuilder = StringBuilder()

        cellInfoStringBuilder.append("Serving cell and neighboring cells info\n\n")

        for (cellInfo in cellInfoList) {
           // when (cellInfo) {
//                is CellInfoGsm -> {
//                    val cellIdentity = cellInfo.cellIdentity
//                    cellInfoStringBuilder.append("GSM Cell ID: ${cellIdentity.cid}\n")
//                    cellInfoStringBuilder.append("GSM LAC: ${cellIdentity.lac}\n")
//                }
//
//                is CellInfoLte -> {
//                    val cellIdentity = cellInfo.cellIdentity
//                    cellInfoStringBuilder.append("LTE Cell ID: ${cellIdentity.ci}\n")
//                    cellInfoStringBuilder.append("LTE TAC: ${cellIdentity.tac}\n")
//                }
//
//                is CellInfoWcdma -> {
//                    val cellIdentity = cellInfo.cellIdentity
//                    cellInfo.
//                    cellInfoStringBuilder.append("WCDMA Cell ID: ${cellIdentity.cid}\n")
//                    cellInfoStringBuilder.append("WCDMA LAC: ${cellIdentity.lac}\n")
//                }
//
//                else -> {
//                    cellInfoStringBuilder.append("Unknown Cell Info\n")
//                }
                when (cellInfo) {
                    is CellInfoGsm -> {
                        val cellIdentity = cellInfo.cellIdentity
                        cellInfoStringBuilder.append("GSM Cell Info:\n")
                        cellInfoStringBuilder.append("CID: ${cellIdentity.cid}\n")
                        cellInfoStringBuilder.append("LAC: ${cellIdentity.lac}\n")
                        cellInfoStringBuilder.append("MCC: ${cellIdentity.mcc}\n")
                        cellInfoStringBuilder.append("MNC: ${cellIdentity.mnc}\n")
                        cellInfoStringBuilder.append("Signal Strength: ${cellInfo.cellSignalStrength}\n")
                    }
                    is CellInfoLte -> {
                        val cellIdentity = cellInfo.cellIdentity
                        cellInfoStringBuilder.append("LTE Cell Info:\n")
                        cellInfoStringBuilder.append("CI: ${cellIdentity.ci}\n")
                        cellInfoStringBuilder.append("TAC: ${cellIdentity.tac}\n")
                        cellInfoStringBuilder.append("MCC: ${cellIdentity.mcc}\n")
                        cellInfoStringBuilder.append("MNC: ${cellIdentity.mnc}\n")
                        cellInfoStringBuilder.append("Signal Strength: ${cellInfo.cellSignalStrength}\n")
                    }
                    is CellInfoWcdma -> {
                        val cellIdentity = cellInfo.cellIdentity
                        cellInfoStringBuilder.append("WCDMA Cell Info:\n")
                        cellInfoStringBuilder.append("CID: ${cellIdentity.cid}\n")
                        cellInfoStringBuilder.append("LAC: ${cellIdentity.lac}\n")
                        cellInfoStringBuilder.append("MCC: ${cellIdentity.mcc}\n")
                        cellInfoStringBuilder.append("MNC: ${cellIdentity.mnc}\n")
                        cellInfoStringBuilder.append("Signal Strength: ${cellInfo.cellSignalStrength}\n")
                    }
                    is CellInfoCdma -> {
                        val cellIdentity = cellInfo.cellIdentity
                        cellInfoStringBuilder.append("CDMA Cell Info:\n")
                        cellInfoStringBuilder.append("Basestation ID: ${cellIdentity.basestationId}\n")
                        cellInfoStringBuilder.append("Network ID: ${cellIdentity.networkId}\n")
                        cellInfoStringBuilder.append("System ID: ${cellIdentity.systemId}\n")
                        cellInfoStringBuilder.append("Signal Strength: ${cellInfo.cellSignalStrength}\n")
                    }
                    is CellInfoTdscdma -> {
                        val cellIdentity = cellInfo.cellIdentity
                        cellInfoStringBuilder.append("TDSCDMA Cell Info:\n")
                        cellInfoStringBuilder.append("CID: ${cellIdentity.cid}\n")
                        cellInfoStringBuilder.append("LAC: ${cellIdentity.lac}\n")
                        cellInfoStringBuilder.append("MCC: ${cellIdentity.mccString}\n")
                        cellInfoStringBuilder.append("MNC: ${cellIdentity.mncString}\n")
                        cellInfoStringBuilder.append("Signal Strength: ${cellInfo.cellSignalStrength}\n")
                    }
                    is CellInfoNr -> {
                        val cellIdentity = cellInfo.cellIdentity
                        cellInfoStringBuilder.append("NR Cell Info:\n")
                        cellInfoStringBuilder.append("Operator Alpha long: ${cellIdentity.operatorAlphaLong}")
//                        cellInfoStringBuilder.append("NCI: ${cellIdentity.nc}\n")
//                        cellInfoStringBuilder.append("PCI: ${cellIdentity.pci}\n")
//                        cellInfoStringBuilder.append("TAC: ${cellIdentity.tac}\n")
//                        cellInfoStringBuilder.append("MCC: ${cellIdentity.mcc}\n")
//                        cellInfoStringBuilder.append("MNC: ${cellIdentity.mnc}\n")
                        cellInfoStringBuilder.append("Signal Strength: ${cellInfo.cellSignalStrength}\n")
                    }

                    else -> {
                        cellInfoStringBuilder.append("Unknown Cell Info\n")
                    }
                }
            cellInfoStringBuilder.append("\n")
        }

        cellInfoText = cellInfoStringBuilder.toString()
    }

    CellInfoDisplay(cellInfoText)
}

@Composable
fun CellInfoDisplay(cellInfoText: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = cellInfoText)
        }
    }
}

@Composable
fun NoPermissionScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Permissions not granted.")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TelephonyTheme {
        CellInfoDisplay("Sample Cell Info")
    }
}