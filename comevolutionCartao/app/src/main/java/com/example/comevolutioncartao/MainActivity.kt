package com.example.comevolutioncartao

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.comevolutioncartao.ui.theme.ComevolutionCartaoTheme

// Cores do aplicativo
val VerdeFundo = Color(0xFFD5EAD8)
val VerdeEscuro = Color(0xFF087B50)
val AzulEscuro = Color(0xFF073B4C)
val TextoPrincipal = Color(0xFF263C32)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComevolutionCartaoTheme {
                CartaoDigital()
            }
        }
    }
}

@Composable
fun CartaoDigital() {

    // Dados do proprietário do cartão
    val nome = "Washington Santos"
    val profissao = "Desenvolvedor Android"
    val telefone = "+55 61 98215-0216"
    val email = "w.santos_contato@hotmail.com"
    val usuario = "@WashingtonDev"

    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = VerdeFundo
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = 28.dp, vertical = 32.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Identidade visual
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Logotipo com iniciais
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .background(
                            AzulEscuro,
                            RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "WS",
                        color = Color.White,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                // Nome
                Text(
                    text = nome,
                    fontSize = 29.sp,
                    color = TextoPrincipal,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Profissão
                Text(
                    text = profissao,
                    fontSize = 15.sp,
                    color = VerdeEscuro,
                    fontWeight = FontWeight.Bold
                )
            }

            // Informações de contato
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ContactRow(
                    simbolo = "☎",
                    informacao = telefone
                ) {
                    val cleanedPhone = telefone.filter { it.isDigit() || it == '+' }
                    val intent = Intent(
                        Intent.ACTION_DIAL,
                        "tel:$cleanedPhone".toUri()
                    )
                    context.startActivity(intent)
                }

                ContactRow(
                    simbolo = "↗",
                    informacao = usuario
                ) {
                    compartilhar(context, nome, profissao,
                        telefone, email)
                }

                ContactRow(
                    simbolo = "✉",
                    informacao = email
                ) {
                    val intent = Intent(
                        Intent.ACTION_SENDTO,
                        "mailto:$email".toUri()
                    )
                    context.startActivity(intent)
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "CARTÃO DE VISITA DIGITAL",
                    fontSize = 10.sp,
                    color = VerdeEscuro,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }
    }
}

// Componente reutilizável para os contatos
@Composable
fun ContactRow(
    simbolo: String,
    informacao: String,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 9.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = simbolo,
            fontSize = 20.sp,
            color = VerdeEscuro
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = informacao,
            fontSize = 13.sp,
            color = TextoPrincipal
        )
    }
}

// Função para compartilhar o cartão
fun compartilhar(
    context: Context,
    nome: String,
    profissao: String,
    telefone: String,
    email: String
) {

    val mensagem = """
        $nome
        $profissao

        Telefone: $telefone
        E-mail: $email
    """.trimIndent()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, mensagem)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            "Compartilhar meu cartão"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun CartaoDigitalPreview() {
    ComevolutionCartaoTheme {
        CartaoDigital()
    }
}
