package com.paramonov.challenge.data.repository.remote.firebase

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ValueEventListener
import com.paramonov.challenge.data.repository.model.*
import com.paramonov.challenge.data.repository.remote.firebase.model.User
import com.paramonov.challenge.ui.feature.login.Result

class AppFirebaseRepository(
    private val auth: FirebaseAuth,
    private val fbDatabase: FirebaseDatabase
) : FirebaseRepository {

    companion object {
        private val TAG = AppFirebaseRepository::class.java.simpleName
        private const val GROUP_CATEGORIES = "categories"
        private const val GROUP_CHALLENGES = "challenges"
        private const val GROUP_USERS = "users"
    }

    override fun checkAuth(): Boolean = auth.currentUser != null

    override fun auth(email: String, password: String): LiveData<Result> =
        MutableLiveData<Result>().apply {
            value = Result.Authorization(false)
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    value = Result.Authorization(true)
                }
                .addOnFailureListener {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            value = Result.Authorization(true)
                        }
                        .addOnFailureListener { value = Result.Error(it) }
                }
        }

    override fun getEmail() = auth.currentUser!!.email!!

    override fun logOut() {
        auth.signOut()
    }

    override fun updateUser(user: User) {
        val groupUsersRef = fbDatabase.reference.child(
            GROUP_USERS
        )
        val currentUserId = auth.currentUser?.uid.toString()
        val userRef = groupUsersRef.child(currentUserId)
        userRef.setValue(user)
    }

    override fun getUser(): LiveData<User> {
        return MutableLiveData<User>().apply {
            val groupUsersRef = fbDatabase.reference.child(
                GROUP_USERS
            )
            val currentUserId = auth.currentUser?.uid.toString()
            val userRef = groupUsersRef.child(currentUserId)

            val listener = object : ValueEventListener {
                override fun onDataChange(data: DataSnapshot) {
                    val user = data.getValue(User::class.java)
                    user?.let {
                        value = user
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, error.toString())
                }
            }
            userRef.addValueEventListener(listener)
        }
    }

    override fun removeChallenges(categoryId: String, challengeId: String) {
        val groupChallengesRef = fbDatabase.reference.child(
            GROUP_CHALLENGES
        )
        val challengesRef = groupChallengesRef.child(categoryId)
        val challengeRef = challengesRef.child(challengeId)
        challengeRef.removeValue()
    }

    override fun getChallenges(categoryId: String): LiveData<List<Challenge>> {
        return MutableLiveData<List<Challenge>>().apply {
            val groupChallengesRef = fbDatabase.reference.child(
                GROUP_CHALLENGES
            )
            val challengesRef = groupChallengesRef.child(categoryId)

            val listener = object : ValueEventListener {
                override fun onDataChange(data: DataSnapshot) {
                    val listChallenge = mutableListOf<Challenge>()
                    for (snapshot in data.children) {
                        val challenge = snapshot.getValue(Challenge::class.java)
                        challenge?.let {
                            listChallenge.add(it)
                        }
                    }
                    value = listChallenge
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, error.toString())
                }
            }
            challengesRef.addValueEventListener(listener)
        }
    }

    override fun getAllCategories(): LiveData<List<Category>> {
        return MutableLiveData<List<Category>>().apply {
            val categoriesRef = fbDatabase.reference.child(
                GROUP_CATEGORIES
            )

            val listener = object : ValueEventListener {
                override fun onDataChange(data: DataSnapshot) {
                    val listCategory = mutableListOf<Category>()
                    for (snapshot in data.children) {
                        val category = snapshot.getValue(Category::class.java)
                        category?.let {
                            listCategory.add(it)
                        }
                    }
                    value = listCategory
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, error.toString())
                }
            }
            categoriesRef.addValueEventListener(listener)
        }
    }
}