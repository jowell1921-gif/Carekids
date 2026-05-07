package com.example.carekids.data.model

data class Story(
    val id: String,
    val title: String,
    val author: String,
    val emoji: String,
    val coverImageUrl: String,
    val pages: List<String>,
    val pointsReward: Int = 30
)

val allStories = listOf(

    Story(
        id           = "tortoise_hare",
        title        = "La Tortuga y la Liebre",
        author       = "Esopo",
        emoji        = "🐢",
        coverImageUrl = "https://upload.wikimedia.org/wikipedia/commons/e/ea/The_Tortoise_and_the_Hare_-_Project_Gutenberg_etext_19994.jpg",
        pointsReward = 30,
        pages = listOf(
            "Había una vez una liebre muy veloz que siempre se burlaba de la tortuga por caminar tan despacio.\n\n\"¡Nadie puede ganarme en una carrera!\", presumía la liebre ante todos los animales del bosque.",
            "Un día, la tortuga se cansó de las burlas y desafió a la liebre a una carrera. Todos los animales del bosque vinieron a ver quién ganaría.\n\nLa liebre soltó una carcajada. \"¿Tú, competir conmigo? ¡Esto será muy divertido!\"",
            "La carrera comenzó. La liebre salió disparada tan rápido que en pocos segundos dejó a la tortuga muy atrás.\n\n\"¡Tengo tiempo de sobra!\", pensó. \"Descansaré un poco bajo este árbol.\"",
            "La liebre se tumbó a la sombra y cerró los ojos confiada. Estaba tan segura de ganar que se quedó profundamente dormida.\n\nMientras tanto, la tortuga seguía caminando. Paso a paso, sin descanso, sin prisa pero sin pausa.",
            "Cuando la liebre despertó, vio que el sol había bajado bastante. Echó a correr con todas sus fuerzas hacia la meta.\n\nPero era demasiado tarde. La tortuga acababa de cruzar la línea entre los aplausos y vítores de todos los animales.",
            "La liebre llegó jadeando y no podía creerlo. La tortuga había ganado.\n\n\"¿Cómo es posible?\", preguntó avergonzada.\n\nLa tortuga sonrió con calma: \"Lento pero seguro, siempre llega a la meta.\"\n\n🌟 Moraleja: La constancia y el esfuerzo pueden más que la velocidad y la soberbia."
        )
    ),

    Story(
        id           = "ant_grasshopper",
        title        = "La Cigarra y la Hormiga",
        author       = "Esopo",
        emoji        = "🐜",
        coverImageUrl = "https://upload.wikimedia.org/wikipedia/commons/6/6d/The_Ant_and_the_Grasshopper_by_Charles_H._Bennett.jpg",
        pointsReward = 30,
        pages = listOf(
            "Durante todo el verano, la pequeña hormiga trabajaba sin descanso. Cada día recogía semillas, granos y trocitos de comida, guardándolos con cuidado en su hormiguero.",
            "La cigarra, en cambio, se pasaba el verano cantando y bailando bajo el sol. Un día vio a la hormiga cargando un pesado grano y se acercó curiosa.\n\n\"¿Por qué trabajas tanto con este calor? ¡Ven a cantar conmigo!\"",
            "La hormiga se detuvo y respondió con amabilidad:\n\n\"Estoy guardando comida para el invierno. Tú también deberías hacerlo. Los días fríos llegarán pronto.\"\n\nPero la cigarra se rió y siguió cantando. \"¡El invierno está muy lejos!\"",
            "Llegó el otoño y luego el invierno. El frío fue terrible y cayó mucha nieve. La cigarra no tenía nada que comer ni donde guarecerse del frío.\n\nTemblando y hambrienta, fue a llamar a la puerta de la hormiga.",
            "La hormiga abrió la puerta y vio a la cigarra en un estado lamentable.\n\n\"Por favor\", suplicó la cigarra, \"¿podrías darme algo de comer? Me morí de cantar todo el verano y no guardé nada.\"",
            "La hormiga la recibió dentro y le dio comida y abrigo.\n\n\"Te ayudaré esta vez\", le dijo con bondad. \"Pero recuerda: hay que trabajar antes de disfrutar. El futuro hay que prepararlo en el presente.\"\n\n🌟 Moraleja: Es importante prepararse para el futuro sin dejar todo para después."
        )
    ),

    Story(
        id           = "lion_mouse",
        title        = "El León y el Ratón",
        author       = "Esopo",
        emoji        = "🦁",
        coverImageUrl = "https://upload.wikimedia.org/wikipedia/commons/9/96/The_Lion_and_the_Mouse_-_Project_Gutenberg_etext_19994.jpg",
        pointsReward = 30,
        pages = listOf(
            "En el corazón de la selva vivía un enorme y poderoso león que era el rey de todos los animales. Un día, mientras dormía tranquilamente a la sombra de un árbol, un pequeño ratón pasó corriendo y sin querer le pisó la nariz.",
            "El león se despertó de un salto y atrapó al ratón entre sus enormes garras.\n\n\"¡Pequeño insolente! ¿Cómo te atreves a molestar a tu rey? ¡Me lo comeré de un bocado!\", rugió furioso.",
            "El ratón temblaba de miedo, pero levantó la cabeza con valentía.\n\n\"¡Por favor, noble rey, no me mates! Soy tan pequeño que ni te llenaré. Si me perdonas la vida, te juro que algún día podré ayudarte.\"\n\nEl león soltó una gran carcajada. \"¿Tú, ayudarme a mí?\"",
            "Pero el rey de la selva, divertido por la valentía del ratoncillo, decidió ser misericordioso.\n\n\"Está bien, pequeño. Me has hecho reír, así que te dejo libre. ¡Largo de aquí!\"\n\nEl ratón se alejó a toda velocidad, prometiéndose no olvidar ese favor.",
            "Días después, el ratón oyó unos rugidos desesperados en el bosque. Corrió hacia el sonido y encontró al poderoso león enredado en una enorme red que habían tendido unos cazadores.\n\nEl ratón no lo dudó ni un segundo.",
            "Con sus pequeños pero afilados dientes, el ratón comenzó a roer los gruesos nudos de la red. Trabajó sin descanso hasta que hizo un agujero suficientemente grande.\n\n¡El gran rey de la selva quedó libre!\n\n\"¡Gracias, pequeño amigo!\", rugió el león emocionado. \"Tenías razón: nadie es demasiado pequeño para ayudar.\"\n\n🌟 Moraleja: Nadie es demasiado pequeño para ser importante. Un amigo vale más que un tesoro."
        )
    ),

    Story(
        id           = "ugly_duckling",
        title        = "El Patito Feo",
        author       = "Hans Christian Andersen",
        emoji        = "🦢",
        coverImageUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2a/The_Ugly_Duckling.jpg",
        pointsReward = 40,
        pages = listOf(
            "En una granja junto a un río tranquilo, una mamá pato esperaba con ilusión que nacieran sus huevos. Uno a uno fueron rompiéndose... ¡y salieron unos patitos preciosos de color amarillo!\n\nPero el último huevo, el más grande, tardó mucho más en abrirse.",
            "Del último huevo salió un patito muy diferente a los demás: era grande, torpe, y de color gris oscuro.\n\n\"¡Qué raro es!\", decían sus hermanos. \"¡Es muy feo!\"\n\nIncluso los demás animales de la granja se alejaban de él.",
            "El patito se sentía muy triste y solo. Nadie quería jugar con él ni ser su amigo. Un día, incapaz de soportar más las burlas, decidió alejarse de la granja para buscar un lugar donde ser aceptado tal como era.",
            "Pasó el otoño viajando solo, buscando amigos. Pero en todas partes lo rechazaban por su aspecto diferente. Llegó el invierno y con él el frío más terrible. El patito pensó que nunca encontraría su lugar en el mundo.",
            "Una mañana de primavera, cuando el hielo se derretía y florecían las flores, el patito llegó a un lago hermoso. Vio allí unos pájaros blancos y majestuosos nadando con elegancia.\n\nSe acercó despacio, preparándose para otro rechazo.",
            "Pero antes de que los cisnes dijeran nada, el patito miró su reflejo en el agua clara del lago... y no reconoció lo que vio.\n\n¡Ya no era un patito gris y torpe! Durante el invierno se había transformado en un cisne blanco y elegante.\n\nLos cisnes lo rodearon con alegría y lo aceptaron como uno de los suyos.\n\n🌟 Moraleja: Ser diferente no es malo. Todos somos especiales a nuestra manera, y el tiempo revela nuestra verdadera belleza."
        )
    ),

    Story(
        id           = "three_pigs",
        title        = "Los Tres Cerditos",
        author       = "Cuento tradicional",
        emoji        = "🐷",
        coverImageUrl = "https://upload.wikimedia.org/wikipedia/commons/8/8b/Three_little_pigs_1904_straw_house.jpg",
        pointsReward = 35,
        pages = listOf(
            "Había una vez tres cerditos hermanos que ya eran mayores para vivir solos. Su mamá los llamó y les dijo: \"Hijos míos, es hora de que construyáis vuestras propias casas. ¡Y cuidado con el lobo!\"\n\nCada uno tomó un camino diferente.",
            "El primer cerdito era muy perezoso. Quería terminar rápido para tener todo el día libre. Encontró un montón de paja y con ella construyó su casa en apenas unas horas.\n\n\"¡Listo!\", dijo satisfecho. \"Ahora a jugar.\"",
            "El segundo cerdito era algo más trabajador, pero tampoco quería esforzarse demasiado. Cortó ramas y troncos y construyó una casa de madera. Era más sólida que la de paja, pero tampoco tardó mucho. \"Suficiente\", pensó.\n\nEl tercer cerdito, el más trabajador, pasó semanas construyendo su casa de ladrillos, ladrillo a ladrillo, con mucho cuidado.",
            "Un día llegó el lobo hambriento. Primero se acercó a la casa de paja.\n\n\"¡Cerdito, déjame entrar!\"\n\n\"¡No, no y no!\"\n\nEl lobo sopló y sopló con todas sus fuerzas... ¡y la casa salió volando! El cerdito escapó corriendo a casa de su hermano.",
            "El lobo encontró la casa de madera y sopló con aún más fuerza. Los tablones crujieron, las paredes temblaron... ¡y la casa se derrumbó!\n\nLos dos cerditos salieron corriendo tan rápido como sus patitas les permitían hacia la sólida casa de ladrillos de su hermano mayor.",
            "El lobo llegó a la casa de ladrillos y sopló y sopló hasta quedarse sin aliento. Pero la casa no se movió ni un centímetro.\n\nFurioso, el lobo intentó entrar por la chimenea... pero los tres cerditos astutos habían colocado un caldero con agua hirviendo justo debajo.\n\n\"¡Ayyy!\"\n\nEl lobo salió por el tejado con el trasero escaldado y nunca más volvió a molestarlos.\n\n🌟 Moraleja: El trabajo bien hecho y el esfuerzo siempre dan sus frutos cuando más se necesitan."
        )
    )
)
