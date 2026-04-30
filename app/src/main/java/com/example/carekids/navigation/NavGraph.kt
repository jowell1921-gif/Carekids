package com.example.carekids.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.carekids.HomeScreen
import com.example.carekids.ui.emotional.EmotionalHistoryScreen
import com.example.carekids.ui.emotional.EmotionalScreen
import com.example.carekids.ui.hospital.HospitalScreen
import com.example.carekids.ui.hospital.ProcedureDetailScreen
import com.example.carekids.ui.hospital.ProceduresScreen
import com.example.carekids.ui.hospital.RightsScreen
import com.example.carekids.ui.hospital.TeamScreen
import com.example.carekids.ui.learn.LearnScreen
import com.example.carekids.ui.learn.MythGameScreen
import com.example.carekids.ui.pet.PetScreen
import com.example.carekids.ui.profile.ProfileScreen
import com.example.carekids.ui.rewards.RewardsScreen

object Routes {
    const val HOME               = "home"
    const val PROFILE            = "profile"
    const val PET                = "pet"
    const val REWARDS            = "rewards"
    const val EMOTIONAL          = "emotional"
    const val EMOTIONAL_HISTORY  = "emotional_history"
    const val LEARN              = "learn"
    const val MYTH_GAME          = "myth_game/{diseaseId}"
    const val HOSPITAL           = "hospital"
    const val PROCEDURES         = "procedures"
    const val PROCEDURE_DETAIL   = "procedure_detail/{procedureId}"
    const val TEAM               = "team"
    const val RIGHTS             = "rights"

    fun mythGame(diseaseId: String)       = "myth_game/$diseaseId"
    fun procedureDetail(id: String)       = "procedure_detail/$id"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController    = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onProfileClick   = { navController.navigate(Routes.PROFILE) },
                onPetClick       = { navController.navigate(Routes.PET) },
                onDialogClick    = { navController.navigate(Routes.REWARDS) },
                onEmotionalClick = { navController.navigate(Routes.EMOTIONAL) },
                onLearnClick     = { navController.navigate(Routes.LEARN) },
                onHospitalClick  = { navController.navigate(Routes.HOSPITAL) }
            )
        }

        composable(Routes.PROFILE) {
            ProfileScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.PET) {
            PetScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.REWARDS) {
            RewardsScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.EMOTIONAL) {
            EmotionalScreen(
                onBack         = { navController.popBackStack() },
                onHistoryClick = { navController.navigate(Routes.EMOTIONAL_HISTORY) }
            )
        }

        composable(Routes.EMOTIONAL_HISTORY) {
            EmotionalHistoryScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.LEARN) {
            LearnScreen(
                onBack          = { navController.popBackStack() },
                onDiseaseClick  = { diseaseId -> navController.navigate(Routes.mythGame(diseaseId)) }
            )
        }

        composable(
            route     = Routes.MYTH_GAME,
            arguments = listOf(navArgument("diseaseId") { type = NavType.StringType })
        ) {
            MythGameScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.HOSPITAL) {
            HospitalScreen(
                onBack             = { navController.popBackStack() },
                onProceduresClick  = { navController.navigate(Routes.PROCEDURES) },
                onTeamClick        = { navController.navigate(Routes.TEAM) },
                onRightsClick      = { navController.navigate(Routes.RIGHTS) }
            )
        }

        composable(Routes.PROCEDURES) {
            ProceduresScreen(
                onBack            = { navController.popBackStack() },
                onProcedureClick  = { id -> navController.navigate(Routes.procedureDetail(id)) }
            )
        }

        composable(
            route     = Routes.PROCEDURE_DETAIL,
            arguments = listOf(navArgument("procedureId") { type = NavType.StringType })
        ) {
            ProcedureDetailScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.TEAM) {
            TeamScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.RIGHTS) {
            RightsScreen(onBack = { navController.popBackStack() })
        }
    }
}
