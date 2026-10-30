package com.androidsystempro.app

import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

private val Background = Color(0xFF080D18)
private val Panel = Color(0xFF111B2D)
private val Accent = Color(0xFF39A7FF)
private val Muted = Color(0xFF9AAAC2)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AndroidSystemProApp() }
    }
}

@Composable
fun AndroidSystemProApp() {
    var selected by remember { mutableStateOf("Início") }
    val tabs = listOf("Início", "Dispositivo", "Sistema", "Segurança")
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Accent,
            background = Background,
            surface = Panel,
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        Scaffold(
            containerColor = Background,
            bottomBar = {
                NavigationBar(containerColor = Panel, contentColor = Color.White) {
                    val icons = listOf(Icons.Default.Home, Icons.Default.PhoneAndroid,
                        Icons.Default.Memory, Icons.Default.Security)
                    tabs.forEachIndexed { index, tab ->
                        NavigationBarItem(
                            selected = selected == tab,
                            onClick = { selected = tab },
                            icon = { Icon(icons[index], contentDescription = tab) },
                            label = { Text(tab, fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Accent,
                                selectedTextColor = Accent,
                                indicatorColor = Color(0xFF1C3554),
                                unselectedIconColor = Muted,
                                unselectedTextColor = Muted
                            )
                        )
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(padding)
                    .background(Background)
            ) {
                TopHeader()
                when (selected) {
                    "Início" -> HomeScreen()
                    "Dispositivo" -> DeviceScreen()
                    "Sistema" -> SystemScreen()
                    "Segurança" -> SecurityScreen()
                }
            }
        }
    }
}

@Composable
private fun TopHeader() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(42.dp).background(Accent, RoundedCornerShape(13.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Android, contentDescription = null, tint = Color.White)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text("ANDROID SYSTEM", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("PRO  •  CENTRAL DO DISPOSITIVO", color = Muted, fontSize = 9.sp, letterSpacing = 1.sp)
        }
        Icon(Icons.Default.Bolt, contentDescription = "Status", tint = Accent)
    }
}

@Composable
private fun HomeScreen() {
    val device = "${Build.MANUFACTURER} ${Build.MODEL}".trim()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text("Painel de controle", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                Text("Visão geral do seu aparelho", color = Muted, fontSize = 13.sp)
            }
        }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF122943)),
                shape = RoundedCornerShape(22.dp)) {
                Row(Modifier.fillMaxWidth().padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("SISTEMA ATIVO", color = Accent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(7.dp))
                        Text(device, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Android ${Build.VERSION.RELEASE}", color = Muted, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.PhoneAndroid, null, tint = Accent, modifier = Modifier.size(42.dp))
                }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoTile("ANDROID", Build.VERSION.SDK_INT.toString(), Icons.Default.Android, Modifier.weight(1f))
                InfoTile("VERSÃO", Build.VERSION.RELEASE ?: "—", Icons.Default.Info, Modifier.weight(1f))
            }
        }
        item {
            SectionTitle("Acesso rápido")
            QuickTile(Icons.Default.Memory, "Informações do sistema", "Memória, armazenamento e versão")
            QuickTile(Icons.Default.Security, "Central de segurança", "Orientações e verificações básicas")
            QuickTile(Icons.Default.Settings, "Configurações", "Personalização do aplicativo")
        }
        item {
            Text("ANDROID SYSTEM PRO • V1.0.0", color = Muted, fontSize = 10.sp,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
        }
    }
}

@Composable
private fun DeviceScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { SectionTitle("Identificação do dispositivo") }
        item { DetailRow("Fabricante", Build.MANUFACTURER) }
        item { DetailRow("Modelo", Build.MODEL) }
        item { DetailRow("Marca", Build.BRAND) }
        item { DetailRow("Produto", Build.PRODUCT) }
        item { DetailRow("Dispositivo", Build.DEVICE) }
        item { DetailRow("Android", Build.VERSION.RELEASE ?: "Desconhecido") }
        item { DetailRow("Nível da API", Build.VERSION.SDK_INT.toString()) }
        item { DetailRow("Build", Build.DISPLAY ?: "Não disponível") }
        item { DetailRow("Arquitetura", Build.SUPPORTED_ABIS.joinToString()) }
        item {
            Text("Alguns identificadores são fornecidos pelo próprio Android e podem variar conforme o fabricante.",
                color = Muted, fontSize = 11.sp, modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
private fun SystemScreen() {
    val stats = remember {
        runCatching {
            val stat = StatFs(Environment.getDataDirectory().path)
            val total = stat.totalBytes
            val free = stat.availableBytes
            val used = (total - free).coerceAtLeast(0)
            Triple(formatBytes(total), formatBytes(free), formatBytes(used))
        }.getOrElse { Triple("Indisponível", "Indisponível", "Indisponível") }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { SectionTitle("Resumo do sistema") }
        item { DetailRow("Armazenamento total", stats.first) }
        item { DetailRow("Espaço disponível", stats.second) }
        item { DetailRow("Espaço utilizado", stats.third) }
        item { DetailRow("Versão do Android", Build.VERSION.RELEASE ?: "—") }
        item { DetailRow("Nível da API", Build.VERSION.SDK_INT.toString()) }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel)) {
                Column(Modifier.padding(16.dp)) {
                    Text("Sobre as informações", color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text("O aplicativo consulta apenas informações disponibilizadas pelas APIs públicas do Android. Não acessa arquivos privados de outros aplicativos.",
                        color = Muted, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun SecurityScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SectionTitle("Central de segurança") }
        item {
            Card(colors = CardDefaults.cardColors(containerColor = Panel)) {
                Column(Modifier.padding(16.dp)) {
                    Icon(Icons.Default.VerifiedUser, null, tint = Accent, modifier = Modifier.size(32.dp))
                    Spacer(Modifier.height(8.dp))
                    Text("Proteção e privacidade", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Mantenha o Android atualizado, revise as permissões dos aplicativos e instale apps de fontes confiáveis.",
                        color = Muted, fontSize = 12.sp)
                }
            }
        }
        item { DetailRow("Permissões especiais", "Gerencie nas configurações do Android") }
        item { DetailRow("Atualizações", "Verifique em Configurações do sistema") }
        item { DetailRow("Play Protect", "Disponível na Google Play Store") }
        item {
            Text("Este painel é informativo. Ele não substitui antivírus, Play Protect ou as ferramentas de segurança do fabricante.",
                color = Muted, fontSize = 11.sp)
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 5.dp, bottom = 4.dp))
}

@Composable
private fun InfoTile(title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector,
                     modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Panel),
        shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(15.dp)) {
            Icon(icon, null, tint = Accent)
            Spacer(Modifier.height(10.dp))
            Text(title, color = Muted, fontSize = 10.sp)
            Text(value, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun QuickTile(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(16.dp)) {
        Row(Modifier.fillMaxWidth().padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = Accent, modifier = Modifier.size(25.dp))
            Spacer(Modifier.width(13.dp))
            Column {
                Text(title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(subtitle, color = Muted, fontSize = 11.sp)
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(14.dp)) {
        Row(Modifier.fillMaxWidth().padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(label, color = Muted, fontSize = 12.sp, modifier = Modifier.weight(1f))
            Text(value, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

private fun formatBytes(bytes: Long): String {
    if (bytes <= 0) return "0 B"
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (kotlin.math.log10(bytes.toDouble()) / kotlin.math.log10(1024.0)).toInt()
        .coerceIn(0, units.lastIndex)
    val value = bytes / Math.pow(1024.0, digitGroups.toDouble())
    return String.format(Locale.getDefault(), "%.1f %s", value, units[digitGroups])
}
