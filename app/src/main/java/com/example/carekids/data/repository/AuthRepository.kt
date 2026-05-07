package com.example.carekids.data.repository

import com.example.carekids.data.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.TimeoutCancellationException


/**
 * Project: CareKids
 * From: com.example.carekids.data.repository
 * Created by: Joel Arturo Osorio
 * On: 30/04/2026 at 18: 08
 * All rights reserved 2026.
 */
class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Result<UserData> {
        val cleanName = name.trim()
        val cleanEmail = email.trim()

        return try {
            val authResult = withTimeout(10_000) { auth.createUserWithEmailAndPassword(cleanEmail, password).await() }
            val firebaseUser = authResult.user ?: throw Exception("No se pudo crear el usuario")

            val userData = UserData(
                uid = firebaseUser.uid,
                name = cleanName,
                email = cleanEmail
            )

            try {
                withTimeout(10_000) {
                    firestore.collection("users")
                        .document(firebaseUser.uid)
                        .set(userData)
                        .await()
                }
            } catch (_: Exception) {
            }

            Result.success(userData)
        }
        catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(Exception("Este correo ya está registrado"))
        }   catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(Exception("La contraseña debe tener al menos 6 caracteres"))
        }   catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("El correo no tiene un formato válido"))
        }   catch (e: TimeoutCancellationException) {
            Result.failure(Exception("La conexión ha tardado demasiado. Inténtalo otra vez"))
        }   catch (e: Exception) {
            Result.failure(Exception(e.message ?: "No se pudo completar el registro"))
        }
    }

    suspend fun loginUser(
        email: String,
        password: String
    ): Result<UserData> {
        val cleanEmail = email.trim()

        return try {
            val authResult = withTimeout(10_000) { auth.signInWithEmailAndPassword(cleanEmail, password).await() }
            val firebaseUser = authResult.user ?: throw Exception("No se pudo iniciar sesión")

            val document = withTimeout(10_000) {
                firestore.collection("users")
                    .document(firebaseUser.uid)
                    .get()
                    .await()
            }

            val userData = document.toObject(UserData::class.java)
                ?: UserData(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: cleanEmail
                )

            Result.success(userData)
        }
        catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("No existe una cuenta con este correo"))

        }   catch (e: FirebaseAuthInvalidCredentialsException) {
            val message = when (e.errorCode) {
                "ERROR_INVALID_EMAIL" -> "El correo no tiene un formato válido"
                "ERROR_WRONG_PASSWORD" -> "La contraseña es incorrecta"
                else -> "Las credenciales no son válidas"
            }
            Result.failure(Exception(message))

        }   catch (e: TimeoutCancellationException) {
            Result.failure(Exception("La conexión ha tardado demasiado. Inténtalo otra vez"))

        }   catch (e: Exception) {
            Result.failure(Exception("No se pudo iniciar sesión")) }
    }

    fun logout() {
        auth.signOut()
    }

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }
}
