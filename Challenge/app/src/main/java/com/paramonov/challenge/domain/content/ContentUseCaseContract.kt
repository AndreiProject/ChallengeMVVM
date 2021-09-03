package com.paramonov.challenge.domain.content

import androidx.lifecycle.LiveData
import com.paramonov.challenge.data.repository.model.*
import io.reactivex.rxjava3.core.Single

interface ContentUseCaseContract {
    fun removeChallenges(categoryId: String, challengeId: String)
    fun getChallenges(categoryId: String): LiveData<List<Challenge>>
    fun getAllCategories(): LiveData<List<Category>>
    fun getCategoriesWithChallenges(): Single<List<Category>>
    fun insertCategoryWithChallenges(category: Category)
    fun saveImg(imgURL: String)
}