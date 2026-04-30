package com.example.carekids.data.model

import androidx.compose.ui.graphics.Color

data class MythStatement(
    val text: String,
    val isReality: Boolean,
    val explanation: String
)

data class Disease(
    val id: String,
    val emoji: String,
    val name: String,
    val tagline: String,
    val color: Color,
    val statements: List<MythStatement>
)

val allDiseases = listOf(

    Disease(
        id      = "asthma",
        emoji   = "🫁",
        name    = "Asma",
        tagline = "Cuida tus pulmones",
        color   = Color(0xFF8FC6FF),
        statements = listOf(
            MythStatement(
                "El asma se puede curar completamente",
                false,
                "El asma es crónica, pero con el tratamiento correcto puedes llevar una vida normal y activa."
            ),
            MythStatement(
                "Hacer ejercicio puede ayudar a controlar el asma",
                true,
                "El ejercicio fortalece los pulmones. Muchos deportistas profesionales tienen asma y compiten sin problema."
            ),
            MythStatement(
                "Solo los adultos pueden tener asma",
                false,
                "El asma es muy común en niños. De hecho, suele comenzar en la infancia."
            ),
            MythStatement(
                "El inhalador abre las vías respiratorias en pocos minutos",
                true,
                "El inhalador de rescate actúa rápido relajando los músculos que rodean las vías respiratorias."
            ),
            MythStatement(
                "Tener mascotas en casa puede empeorar el asma",
                true,
                "El pelo y la caspa de animales son alérgenos frecuentes que pueden desencadenar síntomas de asma."
            )
        )
    ),

    Disease(
        id      = "adhd",
        emoji   = "🧠",
        name    = "TDAH",
        tagline = "Tu cerebro es especial",
        color   = Color(0xFFC7B6FF),
        statements = listOf(
            MythStatement(
                "El TDAH significa que eres menos inteligente",
                false,
                "¡Falso! Muchas personas brillantes tienen TDAH. Einstein y Da Vinci posiblemente lo tenían."
            ),
            MythStatement(
                "Las personas con TDAH pueden ser muy creativas",
                true,
                "El cerebro con TDAH piensa diferente — y eso puede ser una superpotencia para la creatividad."
            ),
            MythStatement(
                "El TDAH es solo una excusa para no estudiar",
                false,
                "El TDAH es una condición neurológica real. El cerebro funciona diferente, no peor."
            ),
            MythStatement(
                "Hacer descansos cortos ayuda a concentrarse mejor",
                true,
                "Pequeñas pausas entre tareas ayudan al cerebro con TDAH a recuperar la concentración."
            ),
            MythStatement(
                "El TDAH desaparece completamente al hacerse mayor",
                false,
                "Aunque algunos síntomas mejoran con la edad, muchos adultos conviven con TDAH y lo gestionan muy bien."
            )
        )
    ),

    Disease(
        id      = "obesity",
        emoji   = "🍎",
        name    = "Obesidad",
        tagline = "Cuida tu cuerpo",
        color   = Color(0xFFC8F1E1),
        statements = listOf(
            MythStatement(
                "La obesidad es solo un problema de aspecto físico",
                false,
                "La obesidad puede afectar el corazón, los huesos y la energía. Cuidar el cuerpo es cuidar la salud."
            ),
            MythStatement(
                "Beber agua en lugar de refrescos ayuda al cuerpo",
                true,
                "El agua hidrata sin azúcar añadida. Los refrescos tienen mucho azúcar que el cuerpo no necesita."
            ),
            MythStatement(
                "El ejercicio está prohibido para niños con obesidad",
                false,
                "¡Todo lo contrario! Moverse es muy beneficioso. Empieza poco a poco con actividades que te gusten."
            ),
            MythStatement(
                "Comer despacio ayuda a sentirse lleno antes",
                true,
                "El cerebro tarda 20 minutos en recibir la señal de saciedad. Comer tranquilo evita comer de más."
            ),
            MythStatement(
                "La obesidad depende solo de cuánto comes",
                false,
                "Influyen muchos factores: genes, hormonas, sueño, estrés y hábitos familiares, no solo la cantidad de comida."
            )
        )
    ),

    Disease(
        id      = "allergies",
        emoji   = "🌸",
        name    = "Alergias",
        tagline = "Conoce tus defensas",
        color   = Color(0xFFFFE49A),
        statements = listOf(
            MythStatement(
                "Las alergias solo ocurren en primavera",
                false,
                "Hay alergias todo el año: al polvo, a los alimentos, a las mascotas. No solo al polen de primavera."
            ),
            MythStatement(
                "Las alergias se pueden contagiar",
                false,
                "Las alergias no son contagiosas. Son una respuesta única de tu sistema inmune."
            ),
            MythStatement(
                "Leer etiquetas es importante si tienes alergia alimentaria",
                true,
                "¡Muy importante! Ingredientes ocultos en alimentos pueden causar reacciones graves."
            ),
            MythStatement(
                "Los días de lluvia son mejores para los alérgicos al polen",
                true,
                "La lluvia limpia el aire y reduce el polen. ¡Los días lluviosos son tus aliados!"
            ),
            MythStatement(
                "Algunas alergias infantiles pueden mejorar con el tiempo",
                true,
                "Alergias como la de la leche suelen mejorar con la edad. Tu médico puede hacer seguimiento."
            )
        )
    )
)
