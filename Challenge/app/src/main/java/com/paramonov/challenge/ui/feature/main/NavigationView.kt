package com.paramonov.challenge.ui.feature.main

interface NavigationView {

    interface Item {
        fun navigateToStatistics()
        fun navigateToCollection()
        fun navigateToCategoryList()
        fun navigateToPlanner()
        fun navigateToSettings()
    }
}