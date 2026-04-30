package com.example.carekids.data.model

data class ProcedureStep(
    val emoji: String,
    val title: String,
    val description: String
)

data class Procedure(
    val id: String,
    val emoji: String,
    val name: String,
    val duration: String,
    val painLevel: String,
    val steps: List<ProcedureStep>
)

val allProcedures = listOf(

    Procedure(
        id        = "blood_test",
        emoji     = "🩸",
        name      = "Analítica de sangre",
        duration  = "5 minutos",
        painLevel = "Pinchazo rápido",
        steps = listOf(
            ProcedureStep("🧴", "Limpieza", "La enfermera limpiará tu brazo con un algodón frío y húmedo."),
            ProcedureStep("💉", "El pinchazo", "Sentirás un pequeño pinchazo, dura solo un segundo. Puedes mirar hacia otro lado si quieres."),
            ProcedureStep("🩸", "La muestra", "Un tubito pequeño recoge un poco de tu sangre. Es normal sentir una ligera presión."),
            ProcedureStep("🩹", "¡Listo!", "Te pondrán una tirita y habrás terminado. ¡Eres muy valiente!")
        )
    ),

    Procedure(
        id        = "mri",
        emoji     = "🔬",
        name      = "Resonancia magnética",
        duration  = "20-45 minutos",
        painLevel = "Sin dolor",
        steps = listOf(
            ProcedureStep("👕", "Preparación", "Te pedirán que te quites joyas, cinturones y cualquier objeto metálico."),
            ProcedureStep("🛏️", "La camilla", "Te tumbarás en una camilla cómoda que entra lentamente dentro del escáner."),
            ProcedureStep("🔊", "Los ruidos", "La máquina hace ruidos fuertes, como golpes o zumbidos. ¡Es completamente normal y no duele!"),
            ProcedureStep("🎧", "Con ayuda", "Te darán tapones o auriculares para los ruidos. A veces puedes escuchar música."),
            ProcedureStep("🏆", "¡Terminado!", "La camilla saldrá y habrás acabado. El médico estudiará las imágenes después.")
        )
    ),

    Procedure(
        id        = "vaccine",
        emoji     = "💉",
        name      = "Vacuna",
        duration  = "2 minutos",
        painLevel = "Pinchazo breve",
        steps = listOf(
            ProcedureStep("🧴", "Preparación", "La enfermera limpiará la zona del brazo o muslo con alcohol. Notarás el frío."),
            ProcedureStep("💉", "El pinchazo", "Un pinchazo muy rápido. Puede doler un segundo pero pasa enseguida."),
            ProcedureStep("🤕", "Después", "El brazo puede doler o estar rojo unos días. Es normal, es tu cuerpo aprendiendo a protegerse."),
            ProcedureStep("🛡️", "¡Protegido!", "Tu sistema inmune ahora está preparado para defenderte. ¡Las vacunas salvan vidas!")
        )
    ),

    Procedure(
        id        = "ecg",
        emoji     = "💓",
        name      = "Electrocardiograma",
        duration  = "10 minutos",
        painLevel = "Sin dolor",
        steps = listOf(
            ProcedureStep("🛏️", "Tumbarse", "Te tumbarás en una camilla y te pedirán que estés tranquilo y quieto."),
            ProcedureStep("🔌", "Los adhesivos", "Pondrán unos adhesivos (como pegatinas) en el pecho, brazos y piernas."),
            ProcedureStep("💓", "Sin dolor", "No duele nada. Los cables solo escuchan cómo late tu corazón, no envían electricidad."),
            ProcedureStep("✅", "¡Rápido y listo!", "En pocos minutos quitarán los adhesivos. ¡Has ayudado al médico a ver tu corazón!")
        )
    ),

    Procedure(
        id        = "xray",
        emoji     = "🩻",
        name      = "Radiografía",
        duration  = "5 minutos",
        painLevel = "Sin dolor",
        steps = listOf(
            ProcedureStep("👕", "Preparación", "Te pedirán que te quites joyas y objetos metálicos de la zona a fotografiar."),
            ProcedureStep("🧍", "Quieto un momento", "Tendrás que quedarte muy quieto unos segundos. Es como hacerse una foto."),
            ProcedureStep("📸", "La imagen", "Una máquina especial toma una foto de tus huesos y órganos internos. No duele nada."),
            ProcedureStep("✅", "¡Terminado!", "El médico estudiará la imagen para ver cómo estás por dentro. ¡Eres una estrella!")
        )
    ),

    Procedure(
        id        = "surgery",
        emoji     = "🏥",
        name      = "Operación",
        duration  = "Variable",
        painLevel = "Sin dolor (duermesа)",
        steps = listOf(
            ProcedureStep("🍽️", "Antes", "No podrás comer ni beber varias horas antes. Es importante para estar seguro."),
            ProcedureStep("👕", "La bata", "Te pondrás una bata especial del hospital. Tu familia estará contigo hasta el quirófano."),
            ProcedureStep("😴", "Anestesia", "El anestesista te dará medicación para que duermas profundamente y no sientas nada."),
            ProcedureStep("🏥", "Durante", "Mientras duermes, los médicos hacen su trabajo con mucho cuidado. No sabrás nada."),
            ProcedureStep("🛏️", "Al despertar", "Te despertarás en una sala especial con enfermeras que te cuidarán."),
            ProcedureStep("👨‍👩‍👧", "¡Con tu familia!", "Pronto podrás ver a tus padres. Ellos han estado esperando y pensando en ti todo el tiempo.")
        )
    )
)
